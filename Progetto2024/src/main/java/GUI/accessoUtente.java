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
        buttonAccess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(accessoUtente.this.textName.getText().isBlank() || accessoUtente.this.textCognome.getText().isBlank())
                {
                    JOptionPane.showMessageDialog(accessoUtente.frame, "Compila tutti i campi");
                }
                else
                {
                    Boolean ctr = controller.accessUtente(accessoUtente.this.textName.getText(),accessoUtente.this.textCognome.getText());
                    if (ctr)
                    {
                        SchermataUtente schermatautente = new SchermataUtente(controller,accessoUtente.frame);
                        accessoUtente.frame.setVisible(false);
                        SchermataUtente.frame.setVisible(true);

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(accessoUtente.frame, "Utente non presente");
                    }

                }
            }
        });
    }
}

