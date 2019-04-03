package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
/**
 * Classe de test pour la Classe Chirurgie
 */
public class ChirurgieTest {
	Chirurgie chir, chir2;
	Bloc b1, b2;
	Chirurgien c1, c2;
	
	@Before
	public void setUp() throws Exception {
		c1= new Chirurgien("Premier");
		c2= new Chirurgien("Deuxieme");
		
		b1= new Bloc("Bloc1");
		b2= new Bloc("Bloc2");
		
		chir= new Chirurgie(0, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"), new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00")), b1, c1);
		chir2 = new Chirurgie(1, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"), new Creneau(new SimpleDateFormat("HH:mm:ss").parse("9:00:00"), new SimpleDateFormat("HH:mm:ss").parse("11:00:00")), b2, c2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToString() {
		assertEquals("0;01/01/2019;08:00:00;10:00:00;Bloc1;Premier", chir.toString());
	}
	
	@Test
	public void testEquals() {
		assertTrue(chir.equals(chir));
		assertFalse(chir.equals(chir2));
	}
	
	@Test
	public void testGetDate() throws ParseException {
		assertEquals(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"), chir.getDate());
	}
	
	@Test
	public void testSetDate() throws ParseException {
		Date d = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019");
		chir.setDate(d);
		assertEquals(d, chir.getDate());
	}
	
	@Test
	public void testSetCreneau() throws ParseException {
		Creneau cren = new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("12:00:00"));
		chir.setCreneau(cren);
		assertEquals(cren, chir.getCreneau());
	}
	
	@Test
	public void testGetCreneau() throws ParseException {
		Creneau cren = new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00"));
		assertEquals(cren, chir.getCreneau());
	}
	
	@Test
	public void testGetBloc() {
		assertEquals(new Bloc("Bloc1"), chir.getBloc());
	}
	
	@Test
	public void testGetChirurgien() {
		assertEquals(new Chirurgien("Premier"), chir.getChirurgien());
	}
	
	@Test
	public void testSetChirurgien() {
		chir.setChirurgien(new Chirurgien("Haha"));
		assertEquals(new Chirurgien("Haha"), chir.getChirurgien());
	}
	
	@Test
	public void testSetBloc() {
		chir.setBloc(new Bloc("Haha"));
		assertEquals(new Bloc("Haha"), chir.getBloc());
	}
	
	@Test
	public void testGetId() {
		assertEquals(0, chir.getId());
	}
}
