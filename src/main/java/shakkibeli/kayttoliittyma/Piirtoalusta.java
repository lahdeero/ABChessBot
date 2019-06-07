package shakkibeli.kayttoliittyma;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import shakkibeli.logiikka.Pelilogiikka;
import static shakkibeli.nappula.Arvo.*;
import shakkibeli.nappula.Nappula;
import static shakkibeli.nappula.Vari.*;

public class Piirtoalusta extends JPanel {

    private Pelilogiikka logiikka;
    private int ruudunSivunPituus;
    private ImageIcon image;
    private Font font;
    private int odota;

    private ImageIcon mustaSotilas;
    private ImageIcon valkoinenSotilas;
    private ImageIcon mustaTorni;
    private ImageIcon valkoinenTorni;
    private ImageIcon mustaHevonen;
    private ImageIcon valkoinenHevonen;
    private ImageIcon mustaLahetti;
    private ImageIcon valkoinenLahetti;
    private ImageIcon mustaKuningatar;
    private ImageIcon valkoinenKuningatar;
    private ImageIcon mustaKuningas;
    private ImageIcon valkoinenKuningas;

    public Piirtoalusta(Pelilogiikka logiikka, int ruudunSivunPituus) throws IOException {

        this.logiikka = logiikka;
        this.ruudunSivunPituus = ruudunSivunPituus;
        this.font = new Font("TimesRoman", Font.PLAIN, ruudunSivunPituus / 4);
        this.odota = 10;
        
        String alihakemisto = "/kuvat/";
        mustaSotilas = new ImageIcon(this.getClass().getResource(alihakemisto + "mustaSotilas.png"));
        valkoinenSotilas = new ImageIcon(this.getClass().getResource(alihakemisto + "valkoinenSotilas.png"));
        mustaTorni = new ImageIcon(this.getClass().getResource(alihakemisto + "mustaTorni.png"));
        valkoinenTorni = new ImageIcon(this.getClass().getResource(alihakemisto + "valkoinenTorni.png"));
        mustaHevonen = new ImageIcon(this.getClass().getResource(alihakemisto + "mustaHevonen.png"));
        valkoinenHevonen = new ImageIcon(this.getClass().getResource(alihakemisto + "valkoinenHevonen.png"));
        mustaLahetti = new ImageIcon(this.getClass().getResource(alihakemisto + "mustaLahetti.png"));
        valkoinenLahetti = new ImageIcon(this.getClass().getResource(alihakemisto + "valkoinenLahetti.png"));
        mustaKuningatar = new ImageIcon(this.getClass().getResource(alihakemisto + "mustaKuningatar.png"));
        valkoinenKuningatar = new ImageIcon(this.getClass().getResource(alihakemisto + "valkoinenKuningatar.png"));
        mustaKuningas = new ImageIcon(this.getClass().getResource(alihakemisto + "mustaKuningas.png"));
        valkoinenKuningas = new ImageIcon(this.getClass().getResource(alihakemisto + "valkoinenKuningas.png"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(font);
        piirraLauta(g);
        try {
            piirraNapit(g);
        } catch (IOException ex) {
            Logger.getLogger(Piirtoalusta.class.getName()).log(Level.SEVERE, null, ex);
        }
        paivita();

    }

    public void piirraLauta(Graphics g) {
        int edellinenX = -1;
        int edellinenY = -1;
        int uusiSiirtoX = -1;
        int uusiSiirtoY = -1;
        boolean valkoinen = true;
        
        if (logiikka.getLauta().getHistoria().getHistoriaKoko() > 0) {
            edellinenX = logiikka.getLauta().getHistoria().getViimeinenSiirto().getX();
            edellinenY = logiikka.getLauta().getHistoria().getViimeinenSiirto().getY();
            uusiSiirtoX = logiikka.getLauta().getHistoria().getViimeinenSiirto().getUusiX();
            uusiSiirtoY = logiikka.getLauta().getHistoria().getViimeinenSiirto().getUusiY();
        }
        
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (x == edellinenX && y == edellinenY) {
                    piirraEdellisenSiirronRuutu(x, y, g);
                } else if (x == uusiSiirtoX && y == uusiSiirtoY) {
                    piirraUudenSiirronRuutu(x, y, g);
                } else if (x % 2 == 1 && y % 2 == 0 || x % 2 == 0 && y % 2 == 1) {
                    g.setColor(Color.decode("#CC6600"));
                    valkoinen = true;
                } else {
                    g.setColor(Color.decode("#FFCC99"));
                    valkoinen = false;
                }
                g.fillRect(x * ruudunSivunPituus, y * ruudunSivunPituus, ruudunSivunPituus, ruudunSivunPituus);
                if (valkoinen) {
                    g.setColor(Color.decode("#FFCC99"));
                } else {
                    g.setColor(Color.decode("#CC6600"));
                }
                if (y == 7) {
                    int a = 65 + x;
                    char c = (char) a;
                    String s = "";
                    s += c;
                    g.drawString(s, x * ruudunSivunPituus + (ruudunSivunPituus / 10 * 8), y * ruudunSivunPituus + (ruudunSivunPituus / 10 * 9));
                }
                if (x == 0) {
                    String s = Integer.toString(8 - y);
                    g.drawString(s, x * ruudunSivunPituus, y * ruudunSivunPituus + (ruudunSivunPituus / 4));
                }
            }
        }
        
        if (logiikka.getJatkuu() == 2) {
            piirraTeksti(g, "shakkimatti!");
        } else if (logiikka.getJatkuu() == 3) {
            piirraTeksti(g, "tasapeli!");
        } else if (logiikka.getPelaajia() == 0 && odota <= 0) {
            odota = 100;
            logiikka.suoritaTekoaly();
        } else if (logiikka.getPelaajia() == 0 && !logiikka.odotaTekoalya()) {
            odota--;
        }
    }

    public void piirraNapit(Graphics g) throws IOException {

        List<Nappula> nappulat = logiikka.getNappulaLista();

        for (Nappula nappula : nappulat) {
            image = palautaNappulanKuvatiedosto(nappula);
            int x = nappula.getX();
            int y = nappula.getY();
            g.drawImage(image.getImage(), x * ruudunSivunPituus, y * ruudunSivunPituus, ruudunSivunPituus, ruudunSivunPituus, this);

            if (nappula.onkoValittu() == true) {
                piirraPunainenNelio(x, y, g);
            }

        }

        paivita();
    }
    public void piirraTeksti(Graphics g, String teksti) {
        g.setColor(Color.blue);
        g.setFont(new Font(null,Font.BOLD,ruudunSivunPituus));
        g.drawString(teksti, ruudunSivunPituus/2, 4*ruudunSivunPituus);
        g.setFont(font);
    }
    public void piirraPunainenNelio(int x, int y, Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        double thickness = 10.0;
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke((float) thickness));
        g2.setColor(Color.red);
        g2.drawRect(x * ruudunSivunPituus, y * ruudunSivunPituus, ruudunSivunPituus, ruudunSivunPituus);
        g2.setStroke(oldStroke);
    }

    public void piirraEdellisenSiirronRuutu(int x, int y, Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        double thickness = 10.0;
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke((float) thickness));
        g2.setColor(Color.yellow);
        g2.fillRect(x * ruudunSivunPituus, y * ruudunSivunPituus, ruudunSivunPituus, ruudunSivunPituus);
        g2.setStroke(oldStroke);
    }

    public void piirraUudenSiirronRuutu(int x, int y, Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        double thickness = 10.0;
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke((float) thickness));
        g2.setColor(Color.orange);
        g2.fillRect(x * ruudunSivunPituus, y * ruudunSivunPituus, ruudunSivunPituus, ruudunSivunPituus);
        g2.setStroke(oldStroke);
    }

    public ImageIcon palautaNappulanKuvatiedosto(Nappula nappi) {
        if (nappi.getVari() == MUSTA && nappi.getArvo() == SOTILAS) {
            return mustaSotilas;
        } else if (nappi.getVari() == VALKOINEN && nappi.getArvo() == SOTILAS) {
            return valkoinenSotilas;
        } else if (nappi.getVari() == MUSTA && nappi.getArvo() == TORNI) {
            return mustaTorni;
        } else if (nappi.getVari() == VALKOINEN && nappi.getArvo() == TORNI) {
            return valkoinenTorni;
        } else if (nappi.getVari() == MUSTA && nappi.getArvo() == HEVONEN) {
            return mustaHevonen;
        } else if (nappi.getVari() == VALKOINEN && nappi.getArvo() == HEVONEN) {
            return valkoinenHevonen;
        } else if (nappi.getVari() == MUSTA && nappi.getArvo() == LAHETTI) {
            return mustaLahetti;
        } else if (nappi.getVari() == VALKOINEN && nappi.getArvo() == LAHETTI) {
            return valkoinenLahetti;
        } else if (nappi.getVari() == MUSTA && nappi.getArvo() == KUNINGATAR) {
            return mustaKuningatar;
        } else if (nappi.getVari() == VALKOINEN && nappi.getArvo() == KUNINGATAR) {
            return valkoinenKuningatar;
        } else if (nappi.getVari() == MUSTA && nappi.getArvo() == KUNINGAS) {
            return mustaKuningas;
        } else if (nappi.getVari() == VALKOINEN && nappi.getArvo() == KUNINGAS) {
            return valkoinenKuningas;
        }
        return valkoinenSotilas;
    }

    public void paivita() {
        super.repaint();
    }
}
