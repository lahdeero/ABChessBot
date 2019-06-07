package shakkibeli.kayttoliittyma;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import shakkibeli.logiikka.Pelilogiikka;

public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private Piirtoalusta piirtoalusta;
    private int ruudunSivunPituus;
    private Pelilogiikka logiikka;
    private int valinta;
    private boolean vika;

    public Kayttoliittyma() {

    }

    public Kayttoliittyma(int ruudunSivunPituus) {
        this.ruudunSivunPituus = ruudunSivunPituus;
    }

    public void menu() {
        frame = new JFrame("menu");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(640, 480));

        luoEsikomponentit(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    public void prerun(int rsp, int valinta) {
        this.valinta = valinta;
        this.ruudunSivunPituus = rsp;
        if (frame != null) {
            frame.dispose();
        }
        run();
    }

    @Override
    public void run() {
        frame = new JFrame("shakkibeli");

        int leveys = 8 * ruudunSivunPituus + 16;
        int korkeus = 8 * ruudunSivunPituus + 38;

        frame.setPreferredSize(new Dimension(leveys, korkeus));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        if (vika == false) {
            try {
                luoKomponentit(frame.getContentPane());
            } catch (IOException ex) {
                Logger.getLogger(Kayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
            }
            vika = true;
        } 

        frame.pack();
        frame.setVisible(true);
    }

    public void luoEsikomponentit(Container container) {

        GridLayout layout = new GridLayout(5, 1);
        container.setLayout(layout);

        JLabel otsikko = new JLabel("shakkibeli", SwingConstants.CENTER);
        otsikko.setFont(new java.awt.Font("Algerian", 0, 24));

        JTextField asetaRsp = new JTextField("100");
        JButton yksinpeli = new JButton("yksinpeli");
        JButton kaksinpeli = new JButton("kaksinpeli");
        JButton katsoja = new JButton("katsoja");
        
        List<JButton> lista = new ArrayList<>();
        lista.addAll(Arrays.asList(kaksinpeli, yksinpeli, katsoja));
        TapahtumanKuuntelija taku = new TapahtumanKuuntelija(this, asetaRsp, lista);
        kaksinpeli.addActionListener(taku);
        yksinpeli.addActionListener(taku);
        katsoja.addActionListener(taku);

        container.add(otsikko);
        container.add(yksinpeli);
        container.add(kaksinpeli);
        container.add(katsoja);
        container.add(asetaRsp);
    }

    public void luoKomponentit(Container container) throws IOException {
        if (valinta == 2) {
            this.logiikka = new Pelilogiikka();
        } else if (valinta == 1) {
            this.logiikka = new Pelilogiikka(1);
        } else if (valinta == 0) {
            this.logiikka = new Pelilogiikka(0);
        }

        piirtoalusta = new Piirtoalusta(logiikka, ruudunSivunPituus);

        container.add(piirtoalusta);

        frame.addMouseListener(new Hiirenkuuntelija(logiikka, ruudunSivunPituus));
    }

    public Piirtoalusta getPiirtoalusta() {
        return this.piirtoalusta;
    }

    public JFrame getFrame() {
        return frame;
    }

}
