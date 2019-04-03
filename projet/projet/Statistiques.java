package projet;
/**
 * @author Bahlel
 */
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Statistiques {
	private int num_stat_avant;
	private int num_stat_apres;
	public List<Long> tempsresolution;
/**
 * 
 * @param num_stat_avant : nombre de conflits avant l'exécution de l'algorithme de résolution
 * @param num_stat_apres : nombre de conflits après l'exécution de l'algorithme de résolution
 * 
 * Constructeur statistiques
 */
	public Statistiques(int num_stat_avant, int num_stat_apres) {
		tempsresolution = new ArrayList<Long>();
		this.num_stat_avant = num_stat_avant;
		this.num_stat_apres = num_stat_apres;
	}
/**
 * 
 * @return long:  moyenne de la liste tempsresolution
 */
	public long moyenne() {
		long ret = 0;
		for (long i : tempsresolution) {
			ret += i;
		}
		return ret / tempsresolution.size();
	}
	/**
	 * 
	 * @return BigDecimal : le Pourcentage de réussite le pourcentage de conflits résolu
	 */
	public BigDecimal getReussite() {
		BigDecimal bd = new BigDecimal(this.num_stat_apres);
		BigDecimal cent = new BigDecimal(100);
		bd = bd.multiply(new BigDecimal(100));
		bd = bd.divide(new BigDecimal(this.num_stat_avant), 2, RoundingMode.HALF_UP);
		cent = cent.subtract(bd);
		return cent;
	}

	/**
	 * 
	 * @return int : le nombre de conflits résolu
	 */
	public int nbrConflitsResout() {
		return this.num_stat_avant - this.num_stat_apres;
	}
	/**
	 * 
	 * @param num_stat_apres : le nombre de conflits trouvé après execution de l'algorithme de résolution
	 */
	public void setStatsApres(int num_stat_apres) {
		this.num_stat_apres = num_stat_apres;
	}
	/**
	 * 
	 * @param num : ajout d'une valeur à la liste tempsresolution
	 */
	public void addTemps(long num) {
		tempsresolution.add(num);
	}
	/**
	 * 
	 * @return long : temps de résolution total des conflits
	 */
	public long durree() {
		long s = 0;
		for (long a : tempsresolution) {
			s += a;
		}
		return s;
	}
}
