package projet;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Bahlel
 *
 * Cette classe modélise une chirurgie 
 */
public class Chirurgie {
	private int id;
	private Date date;
	private Creneau creneau;
	private Bloc bloc;
	private Chirurgien chirurgien;

	/**
	 * @param id         identifiant de la chirugie
	 * @param date       heure de début et de fin de la chirugie
	 * @param creneau    jour de la chirurgie
	 * @param bloc       le bloc ou est faite la chirurgie
	 * @param chirurgien le chirurgien qui fait la chirurgie
	 */
	public Chirurgie(int id, Date date, Creneau creneau, Bloc bloc, Chirurgien chirurgien) {
		this.id = id;
		this.date = date;
		this.creneau = creneau;
		this.bloc = bloc;
		this.chirurgien = chirurgien;
	}
	/**
	 * @return String : Renvoie la chirurgie en string
	 */
	public String toString() {
		return id + ";" + new SimpleDateFormat("dd/MM/yyyy").format(date).toString() + ";" + creneau.toString() + ";"
				+ bloc.toString() + ";" + chirurgien.toString();
	}
	/**
	 * Redéfinition de la méthode equals
	 * Deux chirurgies sont égales si elles ont le même ID
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Chirurgie))
			return false;
		Chirurgie c = (Chirurgie) o;
		if (this.id == c.getId()) {
			return true;
		} else {
			return false;
		}

	}
	/**
	 * 
	 * @return Date : Renvoie la date de la chirurgie
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * 
	 * @param date : la date de la chirurgie
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * 
	 * @param creneau : le creneau horaire de la chirurgie
	 */
	public void setCreneau(Creneau creneau) {
		this.creneau = creneau;
	}
	/**
	 * 
	 * @return Creneau : le creneau horaire de la chirurgie
	 */
	public Creneau getCreneau() {
		return this.creneau;
	}
	/**
	 * 
	 * @return Bloc : le bloc ou a lieu la chirurgie
	 */
	public Bloc getBloc() {
		return bloc;
	}
	/**
	 * 
	 * @return Chirurgien : le chirurgien qui effectue la chirurgie
	 */
	public Chirurgien getChirurgien() {
		return chirurgien;
	}
	/**
	 * 
	 * @param chirurgien : le chirurgien qui effectue la chirurgie
	 */
	public void setChirurgien(Chirurgien chirurgien) {
		this.chirurgien = chirurgien;
	}
	/**
	 * 
	 * @param bloc : le bloc ou a lieu la chirurgie
	 */
	public void setBloc(Bloc bloc) {
		this.bloc = bloc;
	}
	/**
	 * 
	 * @return int : L'ID de la chirurgie
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param id : l'ID de la chirurgie
	 */
	public void setId(int id) {
		this.id = id;
	}

}
