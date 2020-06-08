package shakkibeli.nappula;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static shakkibeli.nappula.Arvo.HEVONEN;
import static shakkibeli.nappula.Arvo.SOTILAS;
import static shakkibeli.nappula.Arvo.TORNI;
import static shakkibeli.nappula.Vari.MUSTA;
import static shakkibeli.nappula.Vari.VALKOINEN;

public class MuuntajaTest {
    Nappula heppa;
    Nappula torni;
    Nappula sotilas;
    public MuuntajaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        heppa = new Nappula(1,7,HEVONEN,VALKOINEN);
        torni = new Nappula(0,0,TORNI,MUSTA);
        sotilas = new Nappula(7,6,SOTILAS,VALKOINEN);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testaaHevosenToString() {
        heppa.liiku(0, 5);
        assertEquals("Na3", heppa.toString());
    }
    @Test
    public void testaaTorninToString() {
        torni.liiku(7,0);
        assertEquals("Rh8", torni.toString());
    }
    @Test
    public void testaaSotilaanToString() {
        sotilas.liiku(7,4);
        assertEquals("h4", sotilas.toString());
    }
}
