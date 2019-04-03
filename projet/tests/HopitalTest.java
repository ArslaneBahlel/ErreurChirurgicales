package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import projet.Hopital;

//TODO : Finir la classe Test de Hopital
/**
 * Classe de test pour la Classe Hopital
 */

public class HopitalTest {
	Hopital h;
	@Before
	public void setUp() throws Exception {
		h=new Hopital("MiniBase.csv");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testChirurgies() {
		assertEquals(25, h.getNombreChirurgies());
	}

	@Test
	public void testPlanChir() {
		assertEquals(12, h.getNombrePlanningChir());
	}
	
	@Test
	public void testPlanBloc() {
		assertEquals(13, h.getNombrePlanningBloc());
	}
	
	@Test
	public void testPlanCB() {
		assertEquals(17, h.getNombrePlanningCB());
	}
	
	@Test
	public void testConflit() {
		assertEquals(6, h.getNombreConflits());
	}
	@Test
	public void testPlanCorrection() {
		h.correction();
		assertEquals(0, h.getNombreConflits());
	}
}
