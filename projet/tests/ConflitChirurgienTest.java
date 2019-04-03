package tests;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import projet.Bloc;
import projet.Chirurgie;
import projet.Chirurgien;
import projet.ConflitChirurgien;
import projet.Creneau;
/**
 * Classe de test pour la Classe ConflitChirurgien
 */
public class ConflitChirurgienTest {
	Chirurgien c1, c2;
	Bloc b1, b2;
	Chirurgie chir, chir2;
	ConflitChirurgien f;
	@Before
	public void setUp() throws Exception {
		c1= new Chirurgien("Premier");
		c2= new Chirurgien("Deuxieme");
		
		b1= new Bloc("Bloc1");
		b2= new Bloc("Bloc2");
		
		chir= new Chirurgie(0, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"), new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00")), b1, c1);
		chir2 = new Chirurgie(1, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"), new Creneau(new SimpleDateFormat("HH:mm:ss").parse("9:00:00"), new SimpleDateFormat("HH:mm:ss").parse("11:00:00")), b2, c1);
		
		f = new ConflitChirurgien(chir, chir2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSolve() {
		Chirurgien c3 = new Chirurgien("Trois");
		chir=f.solve(c3);
		assertEquals(c3, chir.getChirurgien());
	}

}