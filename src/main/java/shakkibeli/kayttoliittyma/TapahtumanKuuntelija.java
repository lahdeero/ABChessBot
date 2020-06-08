package shakkibeli.kayttoliittyma;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class TapahtumanKuuntelija implements ActionListener {
    private Kayttoliittyma kayttoliittyma;
    private JTextField rsp;
    private JButton kaksinpeli;
    private JButton yksinpeli;
    private JButton katsoja;

    public TapahtumanKuuntelija(Kayttoliittyma kayttoliittyma, JTextField rsp, List<JButton> valinnat) {
        this.kayttoliittyma = kayttoliittyma;
        this.rsp = rsp;
        kaksinpeli = valinnat.get(0);
        yksinpeli = valinnat.get(1);
        katsoja = valinnat.get(2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == kaksinpeli) {
            int valittuRsp = muutaRspNumeroiksi();
            kayttoliittyma.prerun(valittuRsp, 2);
        } else if (e.getSource() == yksinpeli) {
            int valittuRsp = muutaRspNumeroiksi();
            kayttoliittyma.prerun(valittuRsp, 1);
        } else if (e.getSource() == katsoja) {
            int valittuRsp = muutaRspNumeroiksi();
            kayttoliittyma.prerun(valittuRsp, 0);
        }
    }
    private int muutaRspNumeroiksi() {
        String teksti = rsp.getText();
        int luku;
        try {
            luku = Integer.parseInt(teksti);
        } catch (NumberFormatException e) {
            luku = 100;
        }
        return luku;
    }
    
}
