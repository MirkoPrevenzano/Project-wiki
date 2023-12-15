package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchermataUtente {
    static JFrame frame;
    private JPanel panel1;
    private JPanel panelReturn;
    private JButton returnButton;
    public SchermataUtente(final Controller controller, final JFrame frameChiamante)
    {

        frame = new JFrame("Schermata Utente");
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(3);
        //this.frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);// Imposta la posizione di default (centrato sullo schermo)
        frame.setResizable(false); // Imposta la finestra come ridimensionabile
        this.frame.setLocationRelativeTo(frameChiamante);
        this.frame.setVisible(true);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SchermataUtente.this.frame.setVisible(false); //nascondo frame iscrizione
                frameChiamante.setVisible(true);//riattivo frame home
                SchermataUtente.this.frame.dispose();
            }
        });
    }
}
