package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccessoUtente {
    public JFrame frame;
    private JPanel panel1;
    private JTextField textName;
    private JTextField textCognome;
    private JButton buttonReturn;
    private JButton buttonAccess;

    public AccessoUtente(final Controller controller, final JFrame frameChiamante) {
        this.frame = new JFrame("Accesso utente");
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(3);
        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);
        this.frame.setVisible(true);

        buttonReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccessoUtente.this.frame.setVisible(false); //nascondo frame iscrizione
                frameChiamante.setVisible(true);//riattivo frame home
                AccessoUtente.this.frame.dispose();
            }

        });
        buttonAccess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(AccessoUtente.this.textName.getText().isBlank() || AccessoUtente.this.textCognome.getText().isBlank())
                {
                    JOptionPane.showMessageDialog(AccessoUtente.this.frame, "Compila tutti i campi");
                }
                else
                {
                    Boolean ctr = controller.accessUtente(AccessoUtente.this.textName.getText(),AccessoUtente.this.textCognome.getText());
                    if (ctr)
                    {
                        SchermataUtente schermataUtente = new SchermataUtente(controller,AccessoUtente.this.frame);
                        AccessoUtente.this.frame.setVisible(false);
                        schermataUtente.frame.setVisible(true);

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(AccessoUtente.this.frame, "Utente non presente");
                    }

                }
            }
        });
    }
}
