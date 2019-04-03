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

import projet.Creneau;
/**
 * Classe de test pour la Classe Creneau
 */
public class CreneauTest {
	Creneau cren, cren1;
	@Before
	public void setUp() throws Exception {
		cren = new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00"));
		cren1 = new Creneau(new SimpleDateFormat("HH:mm:ss").parse("09:00:00"), new SimpleDateFormat("HH:mm:ss").parse("12:00:00"));

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToString() {
		assertEquals("08:00:00;10:00:00", cren.toString());
	}
	
	@Test
	public void testEquals() {
		assertTrue(cren.equals(cren));
		assertFalse(cren.equals(cren1));
	}
	
	@Test
	public void testGetHd() throws ParseException {
		Date d = new SimpleDateFormat("HH:mm:ss").parse("08:00:00");
		assertEquals(d, cren.getHD());
	}
	
	@Test
	public void testGetHf() throws ParseException {
		Date d = new SimpleDateFormat("HH:mm:ss").parse("10:00:00");
		assertEquals(d, cren.getHF());
	}
	
	@Test
	public void testSetHd() throws ParseException {
		Date d = new SimpleDateFormat("HH:mm:ss").parse("09:00:00");
		cren.setHD(d);
		assertEquals(d, cren.getHD());
	}
	
	@Test
	public void testSetHg() throws ParseException {
		Date d = new SimpleDateFormat("HH:mm:ss").parse("12:00:00");
		cren.setHF(d);
		assertEquals(d, cren.getHF());
	}
	
	@Test
	public void testGeDuree() {
		long duree= 7200000;
		assertEquals(duree, cren.getDuree());
	}
	
	@Test
	public void testConflit() throws ParseException {
		assertTrue(cren.conflit(cren1));
		cren1.setHD(new SimpleDateFormat("HH:mm:ss").parse("10:00:01"));
		cren1.setHF(new SimpleDateFormat("HH:mm:ss").parse("12:00:00"));
		assertFalse(cren.conflit(cren1));
		
	}
}
