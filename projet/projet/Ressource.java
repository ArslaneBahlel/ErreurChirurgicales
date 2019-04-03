package projet;

public interface Ressource {
	/**
	 * Méthode abstraite que nous allons redéfinir dans les classes filles
	 * Cette méthode va nous permettre de crée un Conflit entre deux chirurgies
	 * @param un : Première chirurgie du conflit
	 * @param deux	: Deuxième chirurgie du conflit
	 * @return Conflit : Un Conflit entre les deux chirurgies
	 */
	public abstract Conflit createConflit(Chirurgie un, Chirurgie deux);

}
