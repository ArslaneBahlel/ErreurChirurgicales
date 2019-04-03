package projet;

/**
 * @author Bahlel
 *
 * Cette classe modélise un conflit précis entre deux chirurgies qui est causé par un chirurgien 
 */
public class ConflitChirurgien extends Conflit {
	
	/**
	 * @param un   la première chirurgie que concerne le conflit
	 * @param deux la deuxième chirurgie que concerne le conflit
	 * 
	 * le constructeur fait appel à celui de la super classe pour instancier les deux attributs et le conflit entre les deux chirurgies est causé par un chirurgien  
	 */
	public ConflitChirurgien(Chirurgie un, Chirurgie deux) {
		super(un, deux);
	}

	/**
	 * Redéfinition de la méthode abstraite solve
	 * Cette méthode va tout simplement retourner la chirurgie corrigé
	 * @param sol : Une ressource (Chirurgien, Bloc ou couple de ChirurgienBloc)
	 * @param <R> : Une ressource (Chirurgien, Bloc ou couple de ChirurgienBloc)
	 * @return Chirurgie : Retourne la chirurgie avec la correction (Le bon Chirurgien)
	 */
	@Override
	public <R extends Ressource> Chirurgie solve(R sol) {
		Chirurgie lachir;
		lachir=this.getUn();
		lachir.setChirurgien((Chirurgien) sol);
		return  lachir;
	}
}
