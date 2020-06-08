package shakkibeli.logiikka;

import abcb.simulate.Position;
import java.util.List;
import java.util.Objects;
import shakkibeli.nappula.Muuntaja;
import shakkibeli.nappula.Nappula;

public class Siirto {

    private int x;
    private int y;
    private int uusix;
    private int uusiy;
    private Nappula liikuteltava;
    private Muuntaja muuntaja;
    private boolean testi;
    private boolean eats;

    /**
     * Kun tarvitaan tieto siitä, mitä halutaan siirtää ja mihin paikalle,
     * käyettään tämän luokan ilmentymää. Viittaus siirrettävään nappula-olioon
     * annetaan myös, jotta tiedetään mitä nappulaa oikeasti ollaan
     * liikuttamassa.
     *
     *
     * @param x Nykyinen sijainti x-akselilla.
     * @param y Nykyinen sijainti y-akselilla.
     * @param uusix Määränpää x-akselilla.
     * @param uusiy Määränpää y-akselilla.
     * @param liikuteltava Liikuteltava Nappula-olio.
     */
    public Siirto(int x, int y, int uusix, int uusiy, Nappula liikuteltava) {
        this.x = x;
        this.y = y;
        this.uusix = uusix;
        this.uusiy = uusiy;
        this.liikuteltava = liikuteltava;
        this.testi = false;
        this.muuntaja = new Muuntaja();
    }

    /**
     * Liikuteltavan nappulan sijainnin saa myös Nappulasta, joten tämän
     * vähemmän parametreja vaativan konstruktorin käyttö voi olla
     * käytännöllisempää usein.
     *
     * @param uusix Määränpää x-akselilla.
     * @param uusiy Määränpää y-akselilla.
     * @param liikutettava Liikuteltava Nappula-olio.
     */
    public Siirto(int uusix, int uusiy, Nappula liikutettava) {
        this.x = liikutettava.getX();
        this.y = liikutettava.getY();
        this.uusix = uusix;
        this.uusiy = uusiy;
        this.liikuteltava = liikutettava;
        this.muuntaja = new Muuntaja();

    }

    /**
     * AB-Botti käyttää tätä. HUOM! nappulalistalla syötyjä nappuloita
     *
     * @param oldPosition
     * @param newPosition
     * @param nappulat
     */
    public Siirto(Position oldPosition, Position newPosition, List<Nappula> nappulat) {
        this.muuntaja = new Muuntaja();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (oldPosition.board[y][x] != newPosition.board[y][x]) {
                    if (newPosition.board[y][x] == 0) {
                        this.x = x;
                        this.y = y;
                    } else {
                        this.uusix = x;
                        this.uusiy = y;
                        this.eats = oldPosition.board[y][x] != 0;
                    }
                }
            }
        }
        for (Nappula nappi : nappulat) {
            if (!nappi.onkoSyoty() && nappi.getX() == x && nappi.getY() == y) {
                this.liikuteltava = nappi;
                break;
            }
        }
    }

    @Override
    public String toString() {
        return muuntaja.muunnaSiirtoShakkimuotoon(this.x, this.y, this.uusix, this.uusiy);
    }
    
    public String toChessNotation() {
        return muuntaja.muunna(this.uusix, this.uusiy, this.liikuteltava, eats);
    }

    public void setTesti(boolean testataanko) {
        this.testi = testataanko;
    }

    /**
     * Kun halutaan tietää onko kyseessä testisiirto vai ei.
     *
     * @return Testisiirto palauttaa true, normaali siirto false.
     */
    public boolean onkoTesti() {
        return this.testi;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getUusiX() {
        return this.uusix;
    }

    public int getUusiY() {
        return this.uusiy;
    }

    public Nappula getNappula() {
        return this.liikuteltava;
    }

    public Nappula getPaivitettyNappula() {
        return new Nappula(this.uusix, this.uusiy, liikuteltava.getArvo(), liikuteltava.getVari());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.x;
        hash = 37 * hash + this.y;
        hash = 37 * hash + this.uusix;
        hash = 37 * hash + this.uusiy;
        hash = 37 * hash + this.liikuteltava.getArvoKoodi();
        hash = 37 * hash + this.liikuteltava.getVariKoodi();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Siirto other = (Siirto) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.uusix != other.uusix) {
            return false;
        }
        if (this.uusiy != other.uusiy) {
            return false;
        }
        if (!Objects.equals(this.liikuteltava, other.liikuteltava)) {
            return false;
        }
        return true;
    }

}
