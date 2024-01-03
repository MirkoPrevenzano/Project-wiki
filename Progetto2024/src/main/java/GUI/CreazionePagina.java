package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CreazionePagina {

    public JFrame frame;
    private JPanel panel1;
    private JPanel panelTextArea;
    private JTextField textTitolo;
    private JButton creaButton;
    private JButton returnButton;
    private JButton buttonSave;
    private JTextArea textArea;
    private JPanel panelTitolo;
    private JPanel panelButton;
    private JLabel labelTitolo;


    public CreazionePagina(final Controller controller, final JFrame frameChiamante, final String usernameAutore, List<String> listTitoli) {
        this.frame = new JFrame("Creazione nuova pagina" );
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(3);
        this.frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);// Imposta la posizione di default (centrato sullo schermo)
        frame.setResizable(false); // Imposta la finestra come ridimensionabile
        buttonSave.setVisible(false);
        panelTextArea.setVisible(false);

        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textTitolo.getText().isBlank()) {
                    Boolean ctr = controller.addPage(textTitolo.getText(), usernameAutore);
                    if (!ctr) {
                        JOptionPane.showMessageDialog(frame, "Hai già una pagina con questo titolo");
                    } else {
                        textTitolo.setEnabled(false);
                        buttonSave.setVisible(true);
                        panelTextArea.setVisible(true);
                        creaButton.setVisible(false);
                        returnButton.setVisible(false);
                        labelTitolo.setVisible(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Inserisci un titolo");
                }
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                frameChiamante.setVisible(true);
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String testo = textArea.getText();

                if (!testo.isBlank()) {
                    if (!testo.endsWith("\n")) {
                        testo += "\n";
                    }
                    int supp = 0;
                    int positions = 0;
                    do {
                        String frase;
                        positions = testo.indexOf("\n", supp + 1);
                        if (positions != -1) {
                            if (supp == 0) {
                                frase = testo.substring(supp, positions + 1);
                            } else {
                                frase = testo.substring(supp + 1, positions + 1);
                            }
                            //if(frase.length()>1)
                            controller.gestioneTestoPage(frase, usernameAutore, textTitolo.getText());
                            supp = positions;
                        }
                    } while (testo.indexOf("\n", positions + 1) != -1);
                }

                listTitoli.add(textTitolo.getText());
                JOptionPane.showMessageDialog(frame, "Pagina creata con successo");
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });


    }
}



