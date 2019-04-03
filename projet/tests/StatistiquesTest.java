package tests;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import projet.Statistiques;
/**
 * Classe de test pour la Classe Statistiques
 */
public class StatistiquesTest {

	Statistiques stat;
	@Before
	public void setUp() throws Exception {
		stat = new Statistiques(343, 3);
		stat.addTemps(2000);
		stat.addTemps(4000);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetReussite() {
		assertEquals("99.13", stat.getReussite().toString());
	}
	
	@Test
	public void testNbrConflitsResolu() {
		assertEquals(340, stat.nbrConflitsResout());
	}
	
	@Test
	public void testMoyenne() {
		assertEquals(3000, stat.moyenne());
	}
	
	@Test
	public void testDuree() {
		assertEquals(6000, stat.durree());
	}

}
