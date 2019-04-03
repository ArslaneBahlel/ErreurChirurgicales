package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import projet.Bloc;
import projet.Chirurgie;
import projet.Chirurgien;
import projet.Creneau;
import projet.Planning;
/**
 * Classe de test pour la Classe Planning
 */
public class PlanningTest {
	Planning<Chirurgien> pc;
	Date d;
	Chirurgien c;
	Bloc b;
	Chirurgie chir;
	Creneau cren;
	@Before
	public void setUp() throws Exception {
		d = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019");
		c= new Chirurgien("DrMaboul");
		b= new Bloc("Bloc");
		cren=new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00"));
		chir= new Chirurgie(0, d, cren, b, c);
		pc=new Planning<Chirurgien>(d, c);
		pc.add(chir);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetJour() {
		assertEquals(d, pc.getJour());
	}

	@Test
	public void testGetRessource() {
		assertEquals(c, pc.getRessource());
	}

	@Test
	public void testChangerUneChirurgie() {
		chir.setChirurgien(new Chirurgien("House"));
		pc.changeUneChirurgie(chir, chir.getChirurgien());
		assertEquals(chir, pc.getCreneau().get(0));
	}

	@Test
	public void testDeleteUneChirurgie() {
		pc.deleteUneChirurgie(chir);
		assertEquals(0, pc.getCreneau().size());
	}
	
	@Test
	public void testConflitCreneau() throws ParseException {
		Creneau c = new Creneau(new SimpleDateFormat("HH:mm:ss").parse("09:00:00"), new SimpleDateFormat("HH:mm:ss").parse("11:00:00"));
		assertTrue(pc.conflitCreneau(c));
	}
	
	@Test
	public void testIsConflit() throws ParseException {
		Creneau cr = new Creneau(new SimpleDateFormat("HH:mm:ss").parse("09:00:00"), new SimpleDateFormat("HH:mm:ss").parse("11:00:00"));
		Chirurgie chir2 = new Chirurgie(2, d, cr, b, c);
		chir2.setCreneau(cr);
		pc.add(chir2);
		assertEquals(chir, pc.isConflit(chir2));
	}
	
	@Test
	public void testConflit() throws ParseException {
		Creneau cr = new Creneau(new SimpleDateFormat("HH:mm:ss").parse("09:00:00"), new SimpleDateFormat("HH:mm:ss").parse("11:00:00"));
		Chirurgie chir2 = new Chirurgie(2, d, cr, b, c);
		chir2.setCreneau(cr);
		pc.add(chir2);
		assertTrue(pc.conflit());
	}
	
	@Test
	public void testContains() {
		assertTrue(pc.containsChirurgie(chir));
	}
}
