package projet;


/**
 * @author Bahlel
 *
 * Cette classe modélise une ressource praticulière qui est le Bloc et le chirurgien à la fois 
 */
public class ChirurgienBloc implements Ressource {
	private Chirurgien chirurgien;
	private Bloc bloc;
	private Creneau creneau;

	/**
	 * @param chirurgien un chirurgien
	 * @param bloc un bloc 
	 */
	public ChirurgienBloc(Chirurgien chirurgien, Bloc bloc) {
		this.chirurgien = chirurgien;
		this.bloc = bloc;
	}
	/**
	 * 
	 * @return Bloc : le Bloc du couple ChirurgienBloc
	 */
	public Bloc getBloc() {
		return bloc;
	}
	/**
	 * 
	 * @return Chirurgien : le Chirurgien du couple ChirurgienBloc
	 */
	public Chirurgien getChirurgien() {
		return chirurgien;
	}
	/**
	 * 
	 * @param creneau : le creneau du couple ChirurgienBloc
	 */
	public void setCreneau(Creneau creneau) {
		this.creneau = creneau;
	}
/**
 * 
 * @return Creneau : le creneau du couple ChirurgienBloc
 */
	public Creneau getCreneau() {
		return this.creneau;
	}
	/**
	 * 
	 * @param chirurgien : Le chirurgien du couple ChirurgienBloc
	 */
	public void setChirurgien(Chirurgien chirurgien) {
		this.chirurgien = chirurgien;
	}
	/**
	 * 
	 * @param bloc : le Bloc  du couple ChirurgienBloc
	 */
	public void setBloc(Bloc bloc) {
		this.bloc = bloc;
	}
	/**
	 * @return String : Le couple ChirurgienBloc en String
	 */
	public String toString() {
		return chirurgien.toString() + " AND " + bloc.toString();
	}
	/**
	 * Cette méthode va nous permettre de crée un Conflit de type ChirurgienBloc entre deux chirurgies
	 * @param un : Première chirurgie du conflit
	 * @param deux	: Deuxième chirurgie du conflit
	 * @return Conflit : Un Conflit entre les deux chirurgies
	 */
	@Override
	public ConflitChirurgienBloc createConflit(Chirurgie un, Chirurgie deux) {
		ConflitChirurgienBloc a = new ConflitChirurgienBloc(un, deux);

		return a;
	}
	/**
	 * Redéfinition de la méthode equals
	 * Deux objets de type ChirurgienBloc sont égaux si ils ont le même chirurgien et le même bloc
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ChirurgienBloc))
			return false;
		ChirurgienBloc c = (ChirurgienBloc) o;
		if (this.bloc.equals(c.getBloc()) && this.chirurgien.equals(c.getChirurgien())) {
			return true;
		} else {
			return false;
		}

	}
}
