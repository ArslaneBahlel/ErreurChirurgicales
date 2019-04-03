package projet;

/**
 * @author Bahlel
 *
 * Cette classe modélise une ressource praticulière qui est le chirurgien
 */
public class Chirurgien implements Ressource {
	private String nom;
	/**
	 * Constructeur de la classe Chirurgien
	 * @param nom : nom du chirurgien
	 */
	public Chirurgien(String nom) {
		this.nom = nom;
	}
	/**
	 * @return String : Le nom du Chirurgien
	 */
	public String toString() {
		return nom;
	}
	/**
	 * Redéfinition de la méthode equals
	 * Deux chirurgien sont égaux si ils ont le même nom
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Chirurgien))
			return false;
		Chirurgien c = (Chirurgien) o;
		if (this.toString().equals(c.toString()))
			return true;
		else
			return false;

	}
	/**
	 * Cette méthode va nous permettre de crée un Conflit de type chirurgien entre deux chirurgies
	 * @param un : Première chirurgie du conflit
	 * @param deux	: Deuxième chirurgie du conflit
	 * @return Conflit : Un Conflit entre les deux chirurgies
	 */
	@Override
	public ConflitChirurgien createConflit(Chirurgie un, Chirurgie deux) {
		ConflitChirurgien a = new ConflitChirurgien(un, deux);

		return a;
	}
}
