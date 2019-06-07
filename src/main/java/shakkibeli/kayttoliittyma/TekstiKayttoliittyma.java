package shakkibeli.kayttoliittyma;

import shakkibeli.lauta.Lauta;
import static shakkibeli.nappula.Arvo.*;
import shakkibeli.nappula.Nappula;
import shakkibeli.nappula.Vari;
import static shakkibeli.nappula.Vari.VALKOINEN;
/**
 * Käytetty lähinnä logiikan testaamiseen komentorivillä.
 * @author Eero
 */
public class TekstiKayttoliittyma {

    private Lauta lauta;

    public TekstiKayttoliittyma() {
    }
    
    public TekstiKayttoliittyma(Lauta lauta) {
        this.lauta = lauta;
    }

    public void tulostaLauta() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (lauta.getNappula(x, y) == null) {
                    System.out.print(".");
                    continue;
                }
                Nappula tulostettava = lauta.getNappula(x, y);
                Vari vari = tulostettava.getVari();
                if (null != tulostettava.getArvo()) switch (tulostettava.getArvo()) {
                    case SOTILAS:
                        if (vari == VALKOINEN) {
                            System.out.print("P");
                        } else {
                            System.out.print("p");
                        } 
                        break;
                    case TORNI:
                        if (vari == VALKOINEN) {
                            System.out.print("R");
                        } else {
                            System.out.print("r");
                        } 
                        break;
                    case HEVONEN:
                        if (vari == VALKOINEN) {
                            System.out.print("N");
                        } else {
                            System.out.print("n");
                        } 
                        break;
                    case LAHETTI:
                        if (vari == VALKOINEN) {
                            System.out.print("B");
                        } else {
                            System.out.print("b");
                        } 
                        break;
                    case KUNINGAS:
                        if (vari == VALKOINEN) {
                            System.out.print("K");
                        } else {
                            System.out.print("k");
                        } 
                        break;
                    case KUNINGATAR:
                        if (vari == VALKOINEN) {
                            System.out.print("Q");
                        } else {
                            System.out.print("q");
                        } 
                        break;
                    default:
                        break;
                }
            }
            System.out.println("");
        }
    }
    
    public void tulostaTaulukko(int[][] taulukko) {
        for (int y = 0; y < taulukko.length; y++) {
            for (int x = 0; x < taulukko[y].length; x++) {
                int n = taulukko[y][x];
                switch (n) {
                    case 0:
                        System.out.print(".");
                        break;
                    case 1:
                        System.out.print("k");
                        break;
                    case 2:
                        System.out.print("q");
                        break;
                    case 3:
                        System.out.print("t");
                        break;
                    case 4:
                        System.out.print("l");
                        break;
                    case 5:
                        System.out.print("r");
                        break;
                    case 6:
                        System.out.print("s");
                        break;
                    case 7:
                        System.out.print("K");
                        break;
                    case 8:
                        System.out.print("Q");
                        break;
                    case 9:
                        System.out.print("T");
                        break;
                    case 10:
                        System.out.print("L");
                        break;
                    case 11:
                        System.out.print("R");
                        break;
                    case 12:
                        System.out.print("S");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
    }
}
