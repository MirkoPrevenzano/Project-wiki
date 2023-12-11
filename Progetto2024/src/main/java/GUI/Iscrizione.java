package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Iscrizione {
    static JFrame frame;
    private JLabel Name;
    private JPanel panelName;
    private JPanel panelCognome;
    private JLabel Cognome;
    private JPanel panelUsername;
    private JLabel Username;
    private JPanel panelPassword;
    private JLabel Password;
    private JTextField textCognome;
    private JTextField textUsername;
    private JTextField textPassword;
    private JTextField textName;
    private JPanel panel1;
    private JPanel panelReturn;
    private JButton returnButton;
    private JButton completaIscrizione;
    private JPanel panelCompleta;
    private JPanel panelRadio;
    private JRadioButton buttomAutore;

    public Iscrizione(final Controller controller, final JFrame frameChiamante) {
        this.frame = new JFrame("Iscrizione");
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(3);
        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);
        this.frame.setVisible(true);
        this.textPassword.setVisible(false);
        this.textUsername.setVisible(false);
        this.Username.setVisible(false);
        this.Password.setVisible(false);
        frameChiamante.setVisible(false);

        //Tasto return per tornare indietro
        this.returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Iscrizione.this.frame.setVisible(false); //nascondo frame iscrizione
                frameChiamante.setVisible(true);//riattivo frame home
                Iscrizione.this.frame.dispose();//rilascio le risorse

            }
        });
        //actionListener per verificare se tutti i campi sono stati compilati e poi manda ad un metodo del controller che effettuerà le iscrizioni
        completaIscrizione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (Iscrizione.this.buttomAutore.isSelected()) {
                    if (Iscrizione.this.textName.getText().isBlank() || Iscrizione.this.textCognome.getText().isBlank() || Iscrizione.this.textUsername.getText().isBlank() || Iscrizione.this.textPassword.getText().isBlank()) {
                        JOptionPane.showMessageDialog(Iscrizione.frame, "Compila tutti i campi");
                    } else {
                        int ctr = controller.addNewAuthor(Iscrizione.this.textName.getText(), Iscrizione.this.textCognome.getText(), Iscrizione.this.textUsername.getText(), Iscrizione.this.textPassword.getText());
                        if (ctr==1) {
                            JOptionPane.showMessageDialog(Iscrizione.frame, "Iscrizione completata con successo");
                        } else if(ctr==2){
                            JOptionPane.showMessageDialog(Iscrizione.frame, "Autore già iscritto");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(Iscrizione.frame, "Iscrizione fallita");
                        }
                    }
                } else {
                    if (Iscrizione.this.textName.getText().isBlank() || Iscrizione.this.textCognome.getText().isBlank()) {
                        JOptionPane.showMessageDialog(Iscrizione.frame, "Compila tutti i campi");
                    } else {
                        int ctr = controller.addNewUtente(Iscrizione.this.textName.getText(), Iscrizione.this.textCognome.getText());
                        if (ctr==1) {
                            JOptionPane.showMessageDialog(Iscrizione.frame, "Iscrizione completata con successo");
                        } else if(ctr==2){
                            JOptionPane.showMessageDialog(Iscrizione.frame, "Utente già iscritto");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(Iscrizione.frame, "Iscrizione fallita");
                        }
                    }

                }

            }
        });
        buttomAutore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Iscrizione.this.buttomAutore.isSelected()) {
                    Iscrizione.this.textUsername.setVisible(true);
                    Iscrizione.this.textPassword.setVisible(true);
                    Iscrizione.this.Username.setVisible(true);
                    Iscrizione.this.Password.setVisible(true);
                }
                else
                {
                    Iscrizione.this.textUsername.setVisible(false);
                    Iscrizione.this.textPassword.setVisible(false);
                    Iscrizione.this.Username.setVisible(false);
                    Iscrizione.this.Password.setVisible(false);
                }

            }
        });
    }


}
