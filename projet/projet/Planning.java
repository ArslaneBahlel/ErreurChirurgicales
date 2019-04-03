package projet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Bahlel
 *
 *         Cette classe modélise un planning de ressource : sur une ressource et une journée précise on aura une liste de toutes les chirurgies qui concernent cettre ressource
 */
public class Planning<R extends Ressource> {

	private Date jour;
	private R ressource;
	private List<Chirurgie> creneau = new ArrayList<Chirurgie>();

	/**
	 * @param jour      le jour du planning en question
	 * @param ressource la ressource que concerne le planning
	 * 
	 *                  constructeur qui instancie le jour et la ressource du
	 *                  planning
	 */
	public Planning(Date jour, R ressource) {
		this.ressource = ressource;
		this.jour = jour;
	}

	/**
	 * @param chirurgie La chirurgie à ajouter au créneau
	 * 
	 *                  méthode qui permet d'ajouter une chirurgie à un planning
	 */
	public void add(Chirurgie chirurgie) {
		this.creneau.add(chirurgie);
	}

	public String toString() {
		String a = "[";
		for (Chirurgie b : creneau) {
			a += "(" + b.getId() + ") " + b.getCreneau().toString() + ", ";
		}
		return ressource.toString() + "( " + new SimpleDateFormat("dd/MM/yyyy").format(jour).toString() + " ) => " + a
				+ "]";
	}

	/**
	 * @return Le jour du planning
	 */
	public Date getJour() {
		return jour;
	}

	/**
	 * @return La ressource que concerne le planning
	 */
	public R getRessource() {
		return ressource;
	}

	/**
	 * @param chir chirugie à modifier
	 * @param ress la ressource à modifier dans la chirugie
	 * @param <Z> : : une ressource (Un chirurgien, un bloc ou un couple de chirurgienbloc)
	 *cette méthode permet de modifier la ressource d'une chirugie
	 */
	public <Z extends Ressource> void changeUneChirurgie(Chirurgie chir, Z ress) {
		Iterator<Chirurgie> it = creneau.iterator();
		Chirurgie c;
		int id;
		while (it.hasNext()) {
			c = it.next();
			id = creneau.indexOf(c);
			if (c.equals(chir)) {
				if (ress instanceof Chirurgien) {
					c.setChirurgien(chir.getChirurgien());
				} else if (ress instanceof Bloc) {
					c.setBloc(chir.getBloc());
				} else {
					c.setCreneau(chir.getCreneau());
					c.setDate(chir.getDate());
				}
				creneau.set(id, c);
			}
		}
	}

	/**
	 * @param chir chirurgie
	 * 
	 * Cette méthode supprime du planning la chirurgie prise en paramètre
	 */
	public void deleteUneChirurgie(Chirurgie chir) {
		Iterator<Chirurgie> it = creneau.iterator();
		Chirurgie c;
		while (it.hasNext()) {
			c = it.next();
			if (c.equals(chir)) {
				it.remove();
			}
		}
	}

	/**
	 * @return la liste des chirurgies du planning
	 */
	public List<Chirurgie> getCreneau() {
		return creneau;
	}

	/**
	 * @param c chirurgie
	 * @return une valeur booleenne qui indique si une chirurgie pose conflit dans
	 *         le planning
	 */
	public Chirurgie isConflit(Chirurgie c) {
		int id1, id2;
		id1 = c.getId();
		for (Chirurgie c2 : this.creneau) {
			id2 = c2.getId();
			if (id1 != id2 && c.getCreneau().conflit(c2.getCreneau()))
				return c2;
		}

		return null;

	}

	/**
	 * @param c un créneau horraire
	 * @return une variable booleenne qui indique si il y a conflit pour le planning
	 *         en question lors du créneau horraire pris en paramètre
	 */
	public boolean conflitCreneau(Creneau c) {
		for (Chirurgie c2 : this.creneau) {
			if (c.conflit(c2.getCreneau()))
				return true;
		}

		return false;

	}

	/**
	 * @return une valeur booleenne qui indique si il y a au moins un conflit entre
	 *         deux chirugies dans le planning
	 */
	public boolean conflit() {
		int id1, id2;
		for (Chirurgie c : creneau) {
			id1 = c.getId();
			for (Chirurgie c2 : creneau) {
				id2 = c2.getId();
				if (id1 != id2 && c.getCreneau().conflit(c2.getCreneau()))
					return true;
			}
		}

		return false;
	}

	/**
	 * @param chir une chirurgie
	 * @return une valeur booleenne qui indique si la chirugie prise en paramètre
	 *         existe dans le planning
	 */
	public boolean containsChirurgie(Chirurgie chir) {
		for (Chirurgie c : creneau) {
			if (chir.getId() == (c.getId())) {
				return true;
			}
		}
		return false;
	}

}
