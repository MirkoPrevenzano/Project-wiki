package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class accessoUtente {
    static JFrame frame;
    private JPanel Name;
    private JPanel Cognome;
    private JLabel NameLabel;
    private JTextField textName;
    private JLabel labelCognome;
    private JTextField textCognome;
    private JPanel Accesso;
    private JButton buttonAccess;
    private JPanel Return;
    private JButton buttonReturn;
    private JPanel panel1;

    public accessoUtente(final Controller controller, final JFrame frameChiamante) {
        this.frame = new JFrame("Accesso utente");
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(3);
        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);
        this.frame.setVisible(true);

        buttonReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accessoUtente.this.frame.setVisible(false); //nascondo frame iscrizione
                frameChiamante.setVisible(true);//riattivo frame home
                accessoUtente.this.frame.dispose();
            }
        });
    }
}
