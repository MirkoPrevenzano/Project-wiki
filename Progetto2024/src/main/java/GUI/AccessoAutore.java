package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccessoAutore {
    static JFrame frame;
    private JPanel panelUsername;
    private JPanel panelPassword;
    private JPanel panelReturn;
    private JPanel panelAccess;
    private JTextField textUsername;
    private JLabel labelUsername;
    private JPasswordField textPassword;
    private JLabel labelPassword;
    private JButton buttonAccess;
    private JButton buttonReturn;
    private JPanel panel1;

    public AccessoAutore(final Controller controller, final JFrame frameChiamante) {
        frame = new JFrame("Accesso autore");
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(3);
        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);
        this.frame.setVisible(true);

        buttonReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccessoAutore.this.frame.setVisible(false); //nascondo frame iscrizione
                frameChiamante.setVisible(true);//riattivo frame home
                AccessoAutore.this.frame.dispose();
            }
        });
        buttonAccess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AccessoAutore.this.textUsername.getText().isBlank() || AccessoAutore.this.textPassword.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(Iscrizione.frame, "Compila tutti i campi");
                } else {

                    int ctr=controller.accessAutore(textUsername.getText(), textPassword.getText());
                    if(ctr==1)
                    {
                        frame.setVisible(false);
                        SchermataAutore schermataAutore=new SchermataAutore(controller,AccessoAutore.frame);
                        schermataAutore.frame.setVisible(true);
                        AccessoAutore.frame.setVisible(false);


                    }
                    else if(ctr==2)
                    {
                        JOptionPane.showMessageDialog(frame,"Password errata, riprova");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame, "Autore non trovato");
                    }
                }

            }

        });
    }
}