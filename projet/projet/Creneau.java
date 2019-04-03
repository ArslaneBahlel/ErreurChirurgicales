package projet;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author Bahlel
 *
 * Cette classe modélise un crénau horraire qui est donc une heure de début et de fin 
 */
public class Creneau{
	
	private Date heure_debut;
	private Date heure_fin;
	
	/**
	 * @param heure_debut indique l'heure de début du créneau
	 * @param heure_fin indique l'heure de fin du créneau
	 */
	public Creneau(Date heure_debut, Date heure_fin) {
		this.heure_debut=heure_debut;
		this.heure_fin=heure_fin;
	}
	/**
	 * @return String : renvoie le créneau horaire en String
	 */
	public String toString() {
		return new SimpleDateFormat("HH:mm:ss").format(heure_debut).toString()+";"+new SimpleDateFormat("HH:mm:ss").format(heure_fin).toString();
	}
	
	
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Creneau))
			return false;	
			Creneau c = (Creneau) o;
			if(this.heure_debut.equals(c.getHD()) && this.heure_fin.equals(c.getHF()))
				return true;
			else 
				return false;
			
		
	}
	
	public Date getHD() {
		return heure_debut;
	}
	
	public Date getHF() {
		return heure_fin;
	}

	public void setHD(Date heure_debut) {
		this.heure_debut=heure_debut;
	}
	public void setHF(Date heure_fin) {
		this.heure_fin=heure_fin;
	}
	/**
	 * @return la durée de la chirurgie 
	 */
	public long getDuree() {
	    long diff = Math.abs(this.heure_fin.getTime() - this.heure_debut.getTime());
	    return diff;
	}

	
	/**
	 * @param creneau créneau avec une heure de début et de fin 
	 * @return une valeur booleenne qui indique si il y a conflit entre deux crénaux
	 */
	public boolean conflit(Creneau creneau) {
		if (this.heure_debut.compareTo(creneau.getHD())<=0 && (creneau.getHD().compareTo(this.heure_fin)<=0) )
            return true;
		if (this.heure_debut.compareTo(creneau.getHF())<=0 && (creneau.getHF().compareTo(this.heure_fin)<=0) )
            return true;

        return false;
	}
}
