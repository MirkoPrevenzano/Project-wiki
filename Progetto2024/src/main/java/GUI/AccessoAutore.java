package GUI;

import controllerPackage.Controller;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccessoAutore {
    public JFrame frame;

    private JTextField textUsername;
    private JPasswordField textPassword;
    private JButton buttonAccess;
    private JButton buttonReturn;
    private JPanel panel1;
    private JPanel panelUsername;
    private JLabel labelUsername;
    private JPanel panelReturn;
    private JPanel panelPassword;
    private JPanel panelAccess;
    private JLabel labelPassword;

    public AccessoAutore(final Controller controller, final JFrame frameChiamante) {

       this.frame = new JFrame("Accesso autore");
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
                    char[] passwordChars = textPassword.getPassword();
                    String password = new String(passwordChars);
                    int ctr = controller.accessAutore(textUsername.getText(), password);

                    if(ctr==1)
                    {
                        frame.setVisible(false);
                        SchermataAutore schermataAutore=new SchermataAutore(controller,AccessoAutore.this.frame);
                        schermataAutore.frame.setVisible(true);
                        AccessoAutore.this.frame.setVisible(false);


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