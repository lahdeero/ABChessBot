package shakkibeli.nappula;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import static shakkibeli.nappula.Arvo.*;
import static shakkibeli.nappula.Vari.*;

public class Nappula {

    private int x;
    private int y;
    private int edellinenX;
    private int edellinenY;
    private int testiEdellinenX;
    private int testiEdellinenY;
    private Arvo arvo;
    private Vari vari;
    private int variKoodi;
    private boolean valittu;
    private boolean aloitusKaytetty;
    private boolean syoty;
    private boolean korotettu;
    private Muuntaja muuntaja;

    /**
     * Luokan konstruktori.
     *
     * @param x Nappulan sijainti x-akselilla.
     * @param y Nappulan sijainti y-akselilla.
     * @param arvo Nappulan arvo. Esim. HEVONEN.
     * @param vari Nappulan vari. Esim VALKOINEN.
     */
    public Nappula(int x, int y, Arvo arvo, Vari vari) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            this.x = x;
            this.y = y;
            this.edellinenX = x;
            this.edellinenY = y;
            this.arvo = arvo;
            this.vari = vari;
        }
        this.korotettu = false;
        this.aloitusKaytetty = false;
        this.syoty = false;
        this.muuntaja = new Muuntaja();
    }

    /**
     * Muuntaja muuntaa muotoon: Qh6
     * @return 
     */
    @Override
    public String toString() {
        return muuntaja.muunna(x,y,this);
    }

    /**
     * Lähinnä (omassa) testauksessa käytetty metodi.
     *
     * @return Nappulan sijainnin, arvon ja värin merkkijonona.
     */
    public String info() {
        return "x: " + this.x + " y: " + this.y + " Arvo: " + this.arvo
                + " Väri: " + this.vari;
    }

    /**
     * Käyttöliittymän apumetodi, tämän avulla piirretään punainen neliö valitun
     * nappulan ympärille.
     *
     * @param valittu Hiirellä klikattu nappula.
     */
    public void valitse(boolean valittu) {
        this.valittu = valittu;
    }

    /**
     * Käyttöliittymän apumetodi, tarkistetaan onko kyseinen nappula
     * valittu(viimeksi kosketettu vuorossa oleva).
     *
     * @return true jos nappula on valittu, muuten false.
     */
    public boolean onkoValittu() {
        return this.valittu;
    }

    /**
     * ABCB stuff
     *
     * @return WHITE: P,R,N,B,Q,K, BLACK: p,r,n,b,q,k
     */
    public char getCharacter() {
        if (this.vari == VALKOINEN) {
            switch (arvo) {
                case SOTILAS:
                    return 'P';
                case TORNI:
                    return 'R';
                case HEVONEN:
                    return 'N';
                case LAHETTI:
                    return 'B';
                case KUNINGATAR:
                    return 'Q';
                case KUNINGAS:
                    return 'K';
                default:
                    Logger.getLogger(Nappula.class.getName()).log(Level.SEVERE, null, "Valkoisen nappulan merkkiä ei saatu");
            }
        } else {
            switch (arvo) {
                case SOTILAS:
                    return 'p';
                case TORNI:
                    return 'r';
                case HEVONEN:
                    return 'n';
                case LAHETTI:
                    return 'b';
                case KUNINGATAR:
                    return 'q';
                case KUNINGAS:
                    return 'k';
                default:
                    Logger.getLogger(Nappula.class.getName()).log(Level.SEVERE, null, "Mustan nappulan merkkiä ei saatu");
            }
        }
        Logger.getLogger(Nappula.class.getName()).log(Level.SEVERE, null, "Nappulan merkkiä ei saatu");
        return '.';
    }

    /**
     * ABCB stuff
     *
     * @return WHITE: P,R,N,B,Q,K, BLACK: p,r,n,b,q,k
     */
    public int getIntegerValue() {
        if (this.vari == VALKOINEN) {
            switch (arvo) {
                case SOTILAS:
                    return 15;
                case TORNI:
                    return 14;
                case HEVONEN:
                    return 13;
                case LAHETTI:
                    return 12;
                case KUNINGATAR:
                    return 11;
                case KUNINGAS:
                    return 10;
                default:
                    Logger.getLogger(Nappula.class.getName()).log(Level.SEVERE, null, "Valkoisen nappulan numeroarvoa ei saatu");
            }
        } else {
            switch (arvo) {
                case SOTILAS:
                    return 25;
                case TORNI:
                    return 24;
                case HEVONEN:
                    return 23;
                case LAHETTI:
                    return 22;
                case KUNINGATAR:
                    return 21;
                case KUNINGAS:
                    return 20;
                default:
                    Logger.getLogger(Nappula.class.getName()).log(Level.SEVERE, null, "Mustan nappulan numeroarvoa ei saatu");
            }
        }
        Logger.getLogger(Nappula.class.getName()).log(Level.SEVERE, null, "Nappulan numeroarvoa ei saatu");
        return 0;
    }

    /**
     * Liikuttaa nappulaa, eli antaa sille uudet x(nx)- ja y(ny)-koordinaatit.
     *
     * @param nx Uusi X.
     * @param ny Uusi Y.
     */
    public void liiku(int nx, int ny) {
        if (nx >= 0 && nx < 8 && ny >= 0 && ny < 8) {
            this.edellinenX = x;
            this.edellinenY = y;
            this.x = nx;
            this.y = ny;
            this.aloitusKaytetty = true;
        }
        if (this.arvo == SOTILAS && this.y == 7 || this.arvo == SOTILAS && this.y == 0) {
            korotettu = true;
        } else {
            korotettu = false;
        }
    }

    /**
     * Liikuttaa nappulan takaisin edelliseen sijaintiinsa.
     */
    public void liikuTakaisin() {
        this.x = edellinenX;
        this.y = edellinenY;
    }

    /**
     * Logiikan käyttämä apumetodi, esim. kun tarkistetaan voiko uhkauksen
     * torjua.
     *
     * @param nx Testiliikkeen määränpää x-akselilla.
     * @param ny Testiliikkeen määränpää y-akselilla.
     */
    public void testiLiiku(int nx, int ny) {
        if (nx >= 0 && nx < 8 && ny >= 0 && ny < 8) {
            this.testiEdellinenX = x;
            this.testiEdellinenY = y;
            this.x = nx;
            this.y = ny;
        }
    }

    /**
     * Kumoaa testiLiiku(int nx, int ny) -metodin toimet.
     */
    public void testiLiikuTakaisin() {
        this.x = testiEdellinenX;
        this.y = testiEdellinenY;
    }

    public boolean getAloitusKaytetty() {
        return this.aloitusKaytetty;
    }

    /**
     * Asettaa nappulalle uuden arvon, käytetään sotilaan saapuessa
     * (vastustajan) päätyyn.
     *
     * @param uusi Uusi arvo.
     */
    public void setArvo(Arvo uusi) {
        this.korotettu = true;
        this.arvo = uusi;
    }

    public void setVariKoodi(int koodi) {
        this.variKoodi = koodi;
    }

    /**
     * Saattaa olla turha, käyettty Vari enumin korvaajana jossain(hyvin
     * harvoissa) tilanteissa joskus.
     *
     * @return 1 jos nappula on valkoinen, mustalla nappulalla 2.
     */
    public int getVariKoodi() {
        if (this.variKoodi == 0) {
            if (this.vari == VALKOINEN) {
                return 1;
            }
            return 2;
        }
        return this.variKoodi;
    }

    public Arvo getArvo() {
        return this.arvo;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getEdellinenX() {
        return this.edellinenX;
    }

    public int getEdellinenY() {
        return this.edellinenY;
    }

    public Vari getVari() {
        return this.vari;
    }

    public boolean getKorotettu() {
        return this.korotettu;
    }

    /**
     * Tekoäly sai käsiinsä joskus laudalta jo poistettuja nappuloita, tällä
     * yritetty korjata tilanne(saattaa olla jo turha).
     */
    public void setSyodyksi() {
        this.syoty = true;
    }

    /**
     * Tekoäly sai käsiinsä joskus laudalta jo poistettuja nappuloita, tällä
     * yritetty korjata tilanne(saattaa olla jo turha).
     *
     * @return Onko nappula syöty eli poistettu laudalta.
     */
    public boolean onkoSyoty() {
        return this.syoty;
    }

    public int getArvoKoodi() {
        if (this.vari == VALKOINEN) {
            if (this.arvo == KUNINGAS) {
                return 1;
            } else if (this.arvo == KUNINGATAR) {
                return 2;
            } else if (this.arvo == TORNI) {
                return 3;
            } else if (this.arvo == HEVONEN) {
                return 5;
            } else if (this.arvo == LAHETTI) {
                return 4;
            } else if (this.arvo == SOTILAS) {
                return 6;
            }
        } else {
            if (this.arvo == KUNINGAS) {
                return 7;
            } else if (this.arvo == KUNINGATAR) {
                return 8;
            } else if (this.arvo == TORNI) {
                return 9;
            } else if (this.arvo == HEVONEN) {
                return 11;
            } else if (this.arvo == LAHETTI) {
                return 10;
            } else if (this.arvo == SOTILAS) {
                return 12;
            }
        }

        Logger.getLogger(Nappula.class.getName()).log(Level.SEVERE, null, "Sotilaan arvokoodia ei saatu");

        return 0;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + this.x;
        hash = 11 * hash + this.y;
        hash = 11 * hash + Objects.hashCode(this.arvo);
        hash = 11 * hash + Objects.hashCode(this.vari);
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
        final Nappula other = (Nappula) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.arvo != other.arvo) {
            return false;
        }
        if (this.vari != other.vari) {
            return false;
        }
        return true;
    }

}
