package projet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Bahlel
 */

public class Hopital {

	private String nomBase;
	private List<Chirurgie> meschirurgies;
	private List<Planning<Chirurgien>> pc;
	private List<Planning<Bloc>> pb;
	private List<Planning<ChirurgienBloc>> pcb;
	private List<Conflit> lesconflits;
	private Statistiques stats;
	
	public Hopital(String nomBase) {
		this.nomBase = nomBase;
		this.chirurgies();
		this.planning();
		this.conflits();
		this.correction();
		
	}

	/**
	 * Cette méthode permet d'instancier les attributs de la classe et dans le
	 * cas ou y a des erreurs dans la base de données elle fait appel à d'autres
	 * méthods pour les corriger, elle crée à la fin une nouvelle base de données sans erreurs
	 * 
	 * @see Hopital#chirurgies()
	 * @see Hopital#planning()
	 * @see Hopital#conflits() 
	 * @see Hopital#solve(Writer)
	 * @see Hopital#output()
	 * @see projet.Statistiques#Statistiques(int, int)
	 * @see projet.Statistiques#getReussite()
	 * @see projet.Statistiques#nbrConflitsResout()
	 * @see projet.Statistiques#moyenne()
	 * @see projet.Statistiques#durree()
	 * @see List#size()
	 */
	public void correction() {
		int numconfs;
		Writer out=null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("logs.txt")), "UTF8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}
		numconfs = this.lesconflits.size();
		if (numconfs == 0) {
			System.out.print("Il n y a aucun conflit.");
		} else {
			System.out.println("Il y a au total " + this.lesconflits.size() + " conflits.");
			stats = new Statistiques(numconfs, 0);
			this.solve(out);
			this.conflits();
			while (numconfs != this.lesconflits.size()) {
				numconfs = this.lesconflits.size();
				this.solve(out);
				this.conflits();
			}
			stats.setStatsApres(this.lesconflits.size());
			System.out.println(stats.getReussite() + "% des conflits(" + stats.nbrConflitsResout()
					+ " conflits) ont été résolus en " + stats.durree() + "ms.");
			System.out.println("En moyenne, un conflit est résolu chaque " + stats.moyenne() + "ms");
			this.output();
		}
		
		try {
			out.flush();
			out.close();
			System.out.println("Le fichier logs.txt contenant tout les changements effectué a été généré.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cette méthod permet de créer une nouvelle base de données corrigée dans un nouveau fichier
	 * 
	 * @see Chirurgie#getCreneau()
	 */
	public void output() {
		List<Chirurgie> list = new ArrayList<Chirurgie>();
		for (Planning<Chirurgien> unpc : pc) {
			for (Chirurgie c : unpc.getCreneau()) {
				list.add(c);
			}
		}
		Collections.sort(list, new Comparator<Chirurgie>() {
			@Override
			public int compare(Chirurgie c1, Chirurgie c2) {

				return new Integer(c1.getId()).compareTo(new Integer(c2.getId()));
			}
		});
		meschirurgies = list;
		Writer out;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("BaseCorrige.csv")), "UTF8"));
			out.append("ID CHIRURGIE;DATE CHIRURGIE;HEURE_DEBUT CHIRURGIE;HEURE_FIN CHIRURGIE;SALLE;CHIRURGIEN")
					.append("\r\n");
			for (Chirurgie c : meschirurgies) {
				out.append(c.toString()).append("\r\n");
			}
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Cette méthod détécte les conflits entre chirurgies qui concernent un chrirugien, un bloc ou les deux à la fois, elle crée ensuite des objets de type Conflit qu'elle ajoute dans l'attibut : lesconflits 
	 * 
	 * @see Planning#conflit()
	 * @see Planning#isConflit(Chirurgie)
	 * @see Planning#getCreneau()
	 * @see Chirurgie#getBloc()
	 * @see Chirurgie#getChirurgien()
	 * @see ChirurgienBloc#ChirurgienBloc(Chirurgien, Bloc)
	 * @see ChirurgienBloc#createConflit(Chirurgie, Chirurgie)
	 * @see Bloc#createConflit(Chirurgie, Chirurgie)
	 * @see Bloc#equals(Object)
	 * @see Hopital#confExist(Conflit)
	 */
	public void conflits() {
		Chirurgie c2;
		lesconflits = new ArrayList<Conflit>();
		for (Planning<ChirurgienBloc> p : pcb) {
			if (p.conflit()) {
				for (Chirurgie c : p.getCreneau()) {
					c2 = null;
					c2 = p.isConflit(c);
					if (c2 != null) {
						ChirurgienBloc unpcb = new ChirurgienBloc(c.getChirurgien(), c.getBloc());
						if (!confExist(unpcb.createConflit(c, c2)) && c.getChirurgien().equals(c2.getChirurgien())
								&& c.getBloc().equals(c2.getBloc()))
							lesconflits.add(unpcb.createConflit(c, c2));
					}
				}
			}
		}
		for (Planning<Chirurgien> p : pc) {
			if (p.conflit()) {
				for (Chirurgie c : p.getCreneau()) {
					c2 = null;
					c2 = p.isConflit(c);
					if (c2 != null) {
						if (!confExist(c.getChirurgien().createConflit(c, c2))
								&& c.getChirurgien().equals(c2.getChirurgien()))
							lesconflits.add(c.getChirurgien().createConflit(c, c2));
					}
				}
			}
		}

		for (Planning<Bloc> p : pb) {
			if (p.conflit()) {
				for (Chirurgie c : p.getCreneau()) {
					c2 = null;
					c2 = p.isConflit(c);
					if (c2 != null) {
						if (!confExist(c.getBloc().createConflit(c, c2)) && c.getBloc().equals(c2.getBloc()))
							lesconflits.add(c.getBloc().createConflit(c, c2));
					}
				}
			}
		}
	}


	/**
	 * Cette méthod résout les conflits crées et placés dans la liste : lesconflits, chacun en méthod de son type 
	 * @see Hopital#trouver(Chirurgie, Chirurgie, List)
	 * @see Conflit#solve(Ressource)
	 * @param out : Writer, utilisé pour géneré le fichier logs
	 *
	 */
	public void solve(Writer out) {
		Conflit a;
		Chirurgien c;
		Bloc b;
		Chirurgie chirur;
		ChirurgienBloc cb;
		long start;
		long end;
		Iterator<Conflit> it = lesconflits.iterator();

		while (it.hasNext()) {
			a = it.next();
			start = System.currentTimeMillis();
			if (a instanceof ConflitChirurgienBloc) {
				cb = trouver(a.getUn(), a.getDeux(), pcb);
				while (cb == null) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(a.getUn().getDate());
					cal.add(Calendar.DATE, 1);
					a.getUn().setDate(cal.getTime());
					cb = trouver(a.getUn(), a.getDeux(), pcb);
				}
				try {
					out.append("Conflits entre les deux chirurgies: ["+a.getUn().toString()+" et "+a.getDeux().toString()+"] dans la chirurgie portant l'ID : "+a.getUn().getId()+" le creneau "+a.getUn().getCreneau().toString()+" a été remplacé par le creneau "+cb.getCreneau().toString())
					.append("\r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
				chirur = a.solve(cb);
				this.replace(chirur, cb);
				it.remove();
				end = System.currentTimeMillis();
				this.stats.addTemps(end - start); 
			}

			if (a instanceof ConflitChirurgien) {

				c = (Chirurgien) trouver(a.getUn(), a.getDeux(), pc);
				try {
					out.append("Conflits entre les deux chirurgies: ["+a.getUn().toString()+" et "+a.getDeux().toString()+"] dans la chirurgie portant l'ID : "+a.getUn().getId()+" le chirurgien "+a.getUn().getChirurgien().toString()+" a été remplacé par le chirurgien "+c.toString())
					.append("\r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
				chirur = a.solve(c);
				this.replace(chirur, c);
				it.remove();
			}
			if (a instanceof ConflitBloc) {
				b = trouver(a.getUn(), a.getDeux(), pb);
				try {
					out.append("Conflits entre les deux chirurgies: ["+a.getUn().toString()+" et "+a.getDeux().toString()+"] dans la chirurgie portant l'ID : "+a.getUn().getId()+" le bloc "+a.getUn().getBloc().toString()+" a été remplacé par le bloc "+b.toString())
					.append("\r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
				chirur = a.solve(b);
				this.replace(chirur, b);
				it.remove();
			}
			
		}
	}
	
	public int getNombreConflits() {
		return this.lesconflits.size();
	}
	
	
	/**
	 * Cette methode fait la lécture du fichier de base, crée des chirurgies et les ajoute à la liste : meschirurgies  
	 */
	public void chirurgies() {
		this.meschirurgies = new ArrayList<Chirurgie>();
		BufferedReader br;
		String[] line;
		String r;
		Chirurgie unechirurgie;

		try {
			br = new BufferedReader(new FileReader(this.nomBase));
			r = br.readLine();
			while ((r = br.readLine()) != null) {
				line = r.split(";");
				unechirurgie = new Chirurgie(Integer.parseInt(line[0]),
						new SimpleDateFormat("dd/MM/yyyy").parse(line[1]),
						new Creneau(new SimpleDateFormat("HH:mm:ss").parse(line[2]),
								new SimpleDateFormat("HH:mm:ss").parse(line[3])),
						new Bloc(line[4]), new Chirurgien(line[5]));
				meschirurgies.add(unechirurgie);
			}
			br.close();
		} catch (IOException e) {

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cette methode à partir de la liste : meschirurgies , crée et instancie les planning des chirurgiens, blocs et chirurgienBloc
	 */
	void planning() {
		this.pc = new ArrayList<Planning<Chirurgien>>();
		this.pb = new ArrayList<Planning<Bloc>>();
		this.pcb = new ArrayList<Planning<ChirurgienBloc>>();
		Date d = null;
		Chirurgien c = null;
		Creneau uncreneau = null;
		Bloc b = null;
		Planning<Chirurgien> unpc;
		Planning<Bloc> unpb;
		Planning<ChirurgienBloc> unpcb;
		for (int i = 0; i < meschirurgies.size(); i++) {

			if (tupleExist(pc, meschirurgies.get(i).getDate(), meschirurgies.get(i).getChirurgien())) {
				continue;
			}
			d = meschirurgies.get(i).getDate();
			c = meschirurgies.get(i).getChirurgien();

			unpc = new Planning<Chirurgien>(d, c);

			uncreneau = meschirurgies.get(i).getCreneau();

			unpc.add(meschirurgies.get(i));

			for (int j = i + 1; j < meschirurgies.size(); j++) {
				uncreneau = meschirurgies.get(j).getCreneau();
				if (meschirurgies.get(j).getChirurgien().equals(c) && meschirurgies.get(j).getDate().equals(d)) {
					unpc.add(meschirurgies.get(j));
				}

			}
			pc.add(unpc);
		}

		for (int i = 0; i < meschirurgies.size(); i++) {
			if (tupleExist(pb, meschirurgies.get(i).getDate(), meschirurgies.get(i).getBloc())) {
				continue;
			}
			d = meschirurgies.get(i).getDate();
			b = meschirurgies.get(i).getBloc();

			unpb = new Planning<Bloc>(d, b);

			uncreneau = meschirurgies.get(i).getCreneau();

			unpb.add(meschirurgies.get(i));

			for (int j = i + 1; j < meschirurgies.size(); j++) {
				uncreneau = meschirurgies.get(j).getCreneau();
				if (meschirurgies.get(j).getBloc().equals(b) && meschirurgies.get(j).getDate().equals(d)) {
					unpb.add(meschirurgies.get(j));
				}

			}
			pb.add(unpb);
		}

		for (int i = 0; i < meschirurgies.size(); i++) {

			if (tupleExist(pcb, meschirurgies.get(i).getDate(),
					new ChirurgienBloc(meschirurgies.get(i).getChirurgien(), meschirurgies.get(i).getBloc()))) {
				continue;
			}
			d = meschirurgies.get(i).getDate();
			c = meschirurgies.get(i).getChirurgien();
			b = meschirurgies.get(i).getBloc();
			uncreneau = meschirurgies.get(i).getCreneau();
			unpcb = new Planning<ChirurgienBloc>(d, new ChirurgienBloc(c, b));

			uncreneau = meschirurgies.get(i).getCreneau();

			unpcb.add(meschirurgies.get(i));

			for (int j = i + 1; j < meschirurgies.size(); j++) {
				uncreneau = meschirurgies.get(j).getCreneau();
				if (meschirurgies.get(j).getChirurgien().equals(c) && meschirurgies.get(j).getBloc().equals(b)
						&& meschirurgies.get(j).getDate().equals(d)) {
					unpcb.add(meschirurgies.get(j));
				}

			}
			pcb.add(unpcb);
		}
	}

	/**
	 * @param conf1 Une des deux chirugies qui posent conflit  
	 * @param conf2 Une des deux chirugies qui posent conflit
	 * @param pc planning des chirurgiens  
	 * @param <R> : une ressource (Un chirurgien, un bloc ou un couple de chirurgienbloc)
	 * @return R : le chirurgien(ou bloc) qui doit remplacer le chirurgien(ou bloc) actuel dans la chirurgien ou bien un objet de type ChirurgienBloc qui contient le nouveau créneau horaire de la chirurgie
	 */
	public <R extends Ressource> R trouver(Chirurgie conf1, Chirurgie conf2, List<Planning<R>> pc) {
		if (pc.get(0).getRessource() instanceof Chirurgien) {
			for (Planning<R> plan : pc) {
				if (totalementLibre(plan.getRessource(), conf1.getDate(), pc)) {
					return plan.getRessource();
				}
			}
			for (Planning<R> plan : pc) {
				if (!plan.getRessource().equals(conf1.getChirurgien()) && plan.getJour().equals(conf1.getDate())) {
					if (plan.isConflit(conf1) == null && plan.isConflit(conf2) == null) {
						return plan.getRessource();

					}
				}
			}

		} else if (pc.get(0).getRessource() instanceof Bloc) {
			for (Planning<R> plan : pc) {
				if (totalementLibre(plan.getRessource(), conf1.getDate(), pc)) {
					return plan.getRessource();
				}
			}
			for (Planning<R> plan : pc) {
				if (!plan.getRessource().equals(conf1.getBloc()) && plan.getJour().equals(conf1.getDate())) {
					if (plan.isConflit(conf1) == null && plan.isConflit(conf2) == null) {
						return plan.getRessource();

					}	
				}
			}

		} else {
			long duree = conf1.getCreneau().getDuree();
			Calendar cal = Calendar.getInstance();
			Date hd, hf;
			Creneau cren = null;
			boolean found = false;

			try {
				hd = new SimpleDateFormat("HH:mm:ss").parse("08:00:00");
				cal.setTime(hd);
				cal.add(Calendar.MILLISECOND, (int) duree);
				hf = cal.getTime();
				cren = new Creneau(hd, hf);

				while (found == false && hd.before(new SimpleDateFormat("HH:mm:ss").parse("23:59:59"))
						&& hd.after(new SimpleDateFormat("HH:mm:ss").parse("07:59:59"))
						&& hf.before(new SimpleDateFormat("HH:mm:ss").parse("23:59:59"))) {
					found = true;
					for (Chirurgie c2 : meschirurgies) {
						if (c2.getDate().equals(conf1.getDate()) && c2.getCreneau().conflit(cren)) {
							found = false;
							break;

						}
					}

					if (found == false) {
						cal.setTime(hd);
						cal.add(Calendar.MINUTE, 5);
						hd = cal.getTime();
						cal.add(Calendar.MILLISECOND, (int) duree);
						hf = cal.getTime();
						cren = new Creneau(hd, hf);
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (!found) {
				return null;
			}
			ChirurgienBloc cb = new ChirurgienBloc(conf1.getChirurgien(), conf1.getBloc());
			cb.setCreneau(cren);
			return (R) cb;
		}

		return null;
	}

	/**
	 * @param c : Une Ressources (Un chirurgien ou un Bloc ou un couple ChirurgienBloc)
	 * @param jour : Une date 
	 * @param pc : Une Liste de planning de chirurgien ou de bloc de couple de ChirurgienBloc
	 * @param <R> : une ressource (Un chirurgien, un bloc ou un couple de chirurgienbloc)
	 * @return une variable de type boolean qui indique si la Ressource est libre un certain jour
	 * Cette méthode renvoie une variable de type boolean qui indique si la Ressource est libre un certain jour
	 */
	public <R extends Ressource> boolean totalementLibre(R c, Date jour, List<Planning<R>> pc) {
		boolean libre = true;
		for (Planning<R> plan : pc) {
			if (plan.getRessource().equals(c) && plan.getJour().equals(jour)) {
				libre = false;
			}
		}
		return libre;
	}
	public <R extends Ressource> void addToPlanning(Chirurgie c, R ress) {
		if (ress instanceof Chirurgien) {
			Iterator<Planning<Chirurgien>> it = pc.iterator();
			Planning<Chirurgien> unpc, deuxpc = null;
			boolean add = false;
			while (it.hasNext()) {
				unpc = it.next();
				add = false;
				if (unpc.getRessource().equals(c.getChirurgien()) && c.getDate().equals(unpc.getJour())) {
					unpc.add(c);
				} else if (unpc.getRessource().equals(c.getChirurgien())) {
					deuxpc = new Planning<Chirurgien>(c.getDate(), c.getChirurgien());
					deuxpc.add(c);
					add = true;
					break;
				}
			}
			if (add)
				pc.add(deuxpc);
		} else if (ress instanceof Bloc) {
			Iterator<Planning<Bloc>> it = pb.iterator();
			Planning<Bloc> unpb, deuxpb = null;
			boolean add = false;
			while (it.hasNext()) {
				unpb = it.next();
				add = false;
				if (unpb.getRessource().equals(c.getBloc()) && c.getDate().equals(unpb.getJour())) {
					unpb.add(c);
				} else if (unpb.getRessource().equals(c.getChirurgien())) {
					deuxpb = new Planning<Bloc>(c.getDate(), c.getBloc());
					deuxpb.add(c);
					add = true;
					break;
				}
			}
			if (add)
				pb.add(deuxpb);
		}
	}

	/**
	 * Cette méthode remplace la ressource de la chirurgie c par la ressource ress dans les planning
	 * @param c La chirugie qui pose conflit  
	 * @param ress La ressource à changer pour résoudre le conflit 
	 * @param <R> : une ressource (Un chirurgien, un bloc ou un couple de chirurgienbloc)
	 */
	public <R extends Ressource> void replace(Chirurgie c, R ress) {
		Iterator<Planning<Chirurgien>> it = pc.iterator();
		Iterator<Planning<Bloc>> it2 = pb.iterator();
		Iterator<Planning<ChirurgienBloc>> it3 = pcb.iterator();
		Planning<Chirurgien> unpc;
		Planning<Bloc> unpb;
		Planning<ChirurgienBloc> unpcb;
		int id;
		if (ress instanceof Chirurgien) {
			while (it.hasNext()) {
				unpc = it.next();
				if (unpc.containsChirurgie(c)) {
					unpc.deleteUneChirurgie(c);
					break;
				}
			}
			
			addToPlanning(c, c.getChirurgien());
		} else if (ress instanceof Bloc) {

			while (it2.hasNext()) {
				unpb = it2.next();
				if (unpb.containsChirurgie(c)) {
					unpb.deleteUneChirurgie(c);
					break;
				}
			}
			addToPlanning(c, c.getBloc());
		} else if (ress instanceof ChirurgienBloc) {
			while (it.hasNext()) {
				unpc = it.next();
				id = pc.indexOf(unpc);
				if (unpc.containsChirurgie(c)) {
					unpc.changeUneChirurgie(c, new ChirurgienBloc(c.getChirurgien(), c.getBloc()));
					pc.set(id, unpc);
				}
			}
			while (it2.hasNext()) {
				unpb = it2.next();
				id = pb.indexOf(unpb);
				if (unpb.containsChirurgie(c)) {
					unpb.changeUneChirurgie(c, new ChirurgienBloc(c.getChirurgien(), c.getBloc()));
					pb.set(id, unpb);
				}
			}
			while (it3.hasNext()) {
				unpcb = it3.next();
				id = pcb.indexOf(unpcb);
				if (unpcb.containsChirurgie(c)) {
					unpcb.changeUneChirurgie(c, new ChirurgienBloc(c.getChirurgien(), c.getBloc()));
					pcb.set(id, unpcb);
				}
			}
		}

	}

	
	/**
	 * @param b un conflit
	 * @return valeur booleenne 
	 * 
	 * Cette méthode prend un conflit en paramétre et renvoie vrai si il est dans la liste des conflits détéctés, faux sinon
	 */
	public boolean confExist(Conflit b) {
		for (Conflit a : lesconflits) {
			if (a.equals(b)) {
				return true;
			}
		}
		return false;
	}

	

	/**
	 * @param planning liste de plannings de type : Chirurgien, Bloc, ChirurgienBloc
	 * @param date : date
	 * @param ressource : une ressource  (Un chirurgien, un bloc ou un couple de chirurgienbloc)
	 * @param <R> : une ressource (Un chirurgien, un bloc ou un couple de chirurgienbloc)
	 * @return boolean : une valeur booleenne qui indique si une ressource est mobilisé à un moment précis 
	 */
	public <R extends Ressource> boolean tupleExist(List<Planning<R>> planning, Date date, R ressource) {
		boolean exist = false;
		for (Planning<R> lpc : planning) {
			if (lpc.getJour().equals(date) && lpc.getRessource().equals(ressource)) {
				exist = true;
				break;
			}
		}
		return exist;
	}

	/**
	 * @return le nombre de chirugies de la base de données 
	 */
	public int getNombreChirurgies() {
		return this.meschirurgies.size();
	}

	/**
	 * @return le nombre de jours ou un chirurgien est planifié 
	 */
	public int getNombrePlanningChir() {
		return this.pc.size();
	}

	/**
	 * @return le nombre de jours ou un bloc est planifié 
	 */
	public int getNombrePlanningBloc() {
		return this.pb.size();
	}

	/**
	 * @return le nombre de fois ou un chirurgien et un bloc sont planifiés ensemble  
	 */
	public int getNombrePlanningCB() {
		return this.pcb.size();
	}

	public static void main(String[] args) {
		Hopital a = new Hopital("Chirurgies_v2.csv");
		
	}
}
