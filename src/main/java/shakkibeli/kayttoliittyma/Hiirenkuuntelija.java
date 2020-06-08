package shakkibeli.kayttoliittyma;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import shakkibeli.logiikka.Siirto;
import shakkibeli.logiikka.Pelilogiikka;
import shakkibeli.nappula.Arvo;
import static shakkibeli.nappula.Arvo.*;
import shakkibeli.nappula.Nappula;

public class Hiirenkuuntelija implements MouseListener {

    private Pelilogiikka logiikka;
    private Nappula valittuNappula;
    private int ruudunSivunPituus;
    boolean valittu;

    public Hiirenkuuntelija(Pelilogiikka logiikka, int ruudunSivunPituus) {
        this.logiikka = logiikka;
        this.ruudunSivunPituus = ruudunSivunPituus;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            if (valittuNappula.getKorotettu() == true && valittuNappula.getArvo() == KUNINGATAR) {
                valittuNappula.setArvo(TORNI);
            } else if (valittuNappula.getKorotettu() == true && valittuNappula.getArvo() == TORNI) {
                valittuNappula.setArvo(HEVONEN);
            } else if (valittuNappula.getKorotettu() == true && valittuNappula.getArvo() == HEVONEN) {
                valittuNappula.setArvo(LAHETTI);
            } else if (valittuNappula.getKorotettu() == true && valittuNappula.getArvo() == LAHETTI) {
                valittuNappula.setArvo(KUNINGATAR);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int hiiriX = e.getX() - 8;
        int hiiriY = e.getY() - 30;
        double hiirenSijaintiX = (1.0 * hiiriX) / (1.0 * ruudunSivunPituus);
        double hiirenSijaintiY = (1.0 * hiiriY) / (1.0 * ruudunSivunPituus);
        int x = (int) hiirenSijaintiX;
        int y = (int) hiirenSijaintiY;

        if (valittu == false) {
            valittuNappula = logiikka.getNappula(x, y);
            if (valittuNappula != null) {
                valittuNappula.valitse(true);
                valittu = true;
            }
        } else if (logiikka.liikutaNappulaa(new Siirto(valittuNappula.getX(), valittuNappula.getY(), x, y, valittuNappula)) == true) {
            valittu = false;
        } else {
            valittuNappula.valitse(false);
            valittu = false;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!logiikka.isValkoisenVuoro() && logiikka.getPelaajia() == 1) {
            logiikka.suoritaTekoaly();
        } 
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
