package projet;

/**
 * @author Bahlel
 *
 * Cette classe modélise une ressource praticulière qui est le Bloc
 */
public class Bloc implements Ressource {

	private String nom;

	/**
	 * Constructeur de la classe Bloc
	 * @param nom : Nom du Bloc
	 */
	public Bloc(String nom) {
		this.nom = nom;
	}
	/**
	 * @return String : Le nom du bloc
	 */
	public String toString() {
		return nom;
	}
	/**
	 * Redéfinition de la méthode equals
	 * Deux bloc sont égaux si ils ont le même nom
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Bloc))
			return false;
		Bloc c = (Bloc) o;
		if (this.toString().equals(c.toString()))
			return true;
		else
			return false;
	}
	/**
	 * Cette méthode va nous permettre de crée un Conflit de type Bloc entre deux chirurgies
	 * @param un : Première chirurgie du conflit
	 * @param deux	: Deuxième chirurgie du conflit
	 * @return Conflit : Un Conflit entre les deux chirurgies
	 */
	@Override
	public ConflitBloc createConflit(Chirurgie un, Chirurgie deux) {
		ConflitBloc a = new ConflitBloc(un, deux);

		return a;
	}

}
