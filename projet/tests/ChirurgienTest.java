package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
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
 * Classe de test pour la Classe Chirurgien
 */
public class ChirurgienTest {
	Chirurgien un, deux;
	@Before
	public void setUp() throws Exception {
		un = new Chirurgien("LePremier");
		deux = new Chirurgien("LeDeuxieme");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToString() {
		String s= un.toString();
		assertEquals("LePremier", s);
	}
	
	@Test
	public void testEqualsFalse() {
		assertFalse(un.equals(deux));
	}
	
	@Test
	public void testEqualsTrue() {
		assertTrue(deux.equals(deux));
	}

	@Test
	public void testCreateConflit() throws ParseException {
		Chirurgie chir, chir2;
			chir= new Chirurgie(0, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"), new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00")), new Bloc("Bloc"), un);
			chir2 = new Chirurgie(1, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"), new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00")), new Bloc("Bloc2"), un);
			ConflitChirurgien cb = new ConflitChirurgien(chir, chir2);
			assertEquals(cb, un.createConflit(chir, chir2));		
	}

}
