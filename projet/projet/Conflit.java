package projet;
/**
 * @author Bahlel
 *
 * Cette classe modélise un conflit entre deux chirurgies
 */
public abstract class Conflit {
	private Chirurgie un;
	private Chirurgie deux;

	/**
	 * @param un   la première chirurgie que concerne le conflit
	 * @param deux la deuxième chirurgie que concerne le conflit
	 * 
	 * le constructeur instancie les deux chirugies 
	 */
	public Conflit(Chirurgie un, Chirurgie deux) {
		this.un = un;
		this.deux = deux;

	}
	/**
	 * Méthode abstraite qui va être redéfini dans les méthodes filles
	 * Cette méthode va tout simplement retourner la chirurgie corrigé
	 * @param sol : Une ressource (Chirurgien, Bloc ou couple de ChirurgienBloc)
	 * @param <R> : Une ressource (Chirurgien, Bloc ou couple de ChirurgienBloc)
	 * @return Chirurgie : Retourne la chirurgie avec la correction (Le bon chirurgien, bloc ou créneau)
	 */
	public abstract <R extends Ressource> Chirurgie solve(R sol);

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Conflit))
			return false;
		Conflit c = (Conflit) o;
		if (this.un.getId() == c.getUn().getId() && this.deux.getId() == c.getDeux().getId())
			return true;
		if (this.un.getId() == c.getDeux().getId() && this.deux.getId() == c.getUn().getId())
			return true;
		else
			return false;

	}

	public Chirurgie getUn() {
		return un;
	}

	public Chirurgie getDeux() {
		return deux;
	}

	public String toString() {
		return (un.getId() + " et " + deux.getId());
	}
}
