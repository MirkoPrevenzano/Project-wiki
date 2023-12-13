package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchermataAutore {
    static JFrame frame;

    private JPanel panelReturn;
    private JPanel panel1;
    private JButton buttonReturn;
    private JButton buttonCreatePage;
    private JPanel panelCreatePage;

    public SchermataAutore(final Controller controller, final JFrame frameChiamante) {
         frame=new JFrame("Profilo");
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(3);
        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);
        this.frame.setVisible(true);
        buttonReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                frameChiamante.setVisible(true);
            }
        });
        buttonCreatePage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreazionePagina creazionePagina = new CreazionePagina(controller, SchermataAutore.this.frame);
                SchermataAutore.frame.setVisible(false);
                creazionePagina.frame.setVisible(true);

            }
        });
    }

}
