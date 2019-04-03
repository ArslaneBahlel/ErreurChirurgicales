package tests;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import projet.Bloc;
import projet.Chirurgie;
import projet.Chirurgien;
import projet.ChirurgienBloc;
import projet.ConflitChirurgienBloc;
import projet.Creneau;
/**
 * Classe de test pour la Classe ChirurgienBloc
 */
public class ChirurgienBlocTest {
	ChirurgienBloc un, deux;
	Bloc b1, b2;
	Chirurgien c1, c2;
	@Before
	public void setUp() throws Exception {
		c1= new Chirurgien("Premier");
		c2= new Chirurgien("Deuxieme");
		
		b1= new Bloc("Bloc1");
		b2= new Bloc("Bloc2");
		
		un=new ChirurgienBloc(c1, b1);
		deux=new ChirurgienBloc(c2, b2);
		
		un.setCreneau(new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00")));
		deux.setCreneau(new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00")));
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testToString() {
		assertEquals("Premier AND Bloc1", un.toString());
	}
	
	@Test
	public void testGetBloc() {
		assertEquals(b1, un.getBloc());
		assertEquals(b2, deux.getBloc());
	}
	
	@Test
	public void testGetChirurgien() {
		assertEquals(c1, un.getChirurgien());
		assertEquals(c2, deux.getChirurgien());
	}
	
	@Test
	public void testGetCreneau() throws ParseException {
		Creneau cren = new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00"));
		assertEquals(cren, un.getCreneau());
	}
	
	@Test
	public void testSetChirurgien() {
		un.setChirurgien(c2);
		assertEquals(c2, un.getChirurgien());
	}
	
	@Test
	public void testSetBloc() {
		un.setBloc(b2);
		assertEquals(b2, un.getBloc());
	}
	
	@Test
	public void testSetCreneau() throws ParseException {
		Creneau cren = new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:10:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00"));
		un.setCreneau(cren);
		assertEquals(cren, un.getCreneau());
	}
	
	@Test
	public void testCreateConflit() throws ParseException {
		Chirurgie chir, chir2;
			chir= new Chirurgie(0, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"), new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00")), b1, c1);
			chir2 = new Chirurgie(1, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"), new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00")), b1, c1);
			ConflitChirurgienBloc cb = new ConflitChirurgienBloc(chir, chir2);
			assertEquals(cb, un.createConflit(chir, chir2));	
	}
}
