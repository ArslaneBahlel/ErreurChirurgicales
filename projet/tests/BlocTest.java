package tests;

/**
 * Classe de test pour la Classe Bloc
 */
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
import projet.ConflitBloc;
import projet.Creneau;
public class BlocTest {
	private Bloc un;
	private Bloc deux;
	@Before
	public void setUp() throws Exception {
		un= new Bloc("UnBloc");
		deux = new Bloc("DeuxBloc");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToString() {
		String s= un.toString();
		assertEquals("UnBloc", s);
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
			chir= new Chirurgie(0, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"), new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00")), un, new Chirurgien("Chirur"));
			chir2 = new Chirurgie(1, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"), new Creneau(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"), new SimpleDateFormat("HH:mm:ss").parse("10:00:00")), un, new Chirurgien("Chiruru"));
			ConflitBloc cb = new ConflitBloc(chir, chir2);
			assertEquals(cb, un.createConflit(chir, chir2));	
	}
}
