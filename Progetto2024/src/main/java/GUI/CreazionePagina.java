package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreazionePagina {
    public JFrame frame;

    private JButton buttonReturn;
    private JPanel panelTitolo;
    private JTextField textTitolo;
    private JPanel panel1;
    private JPanel panelTextArea;
    private JTextArea textArea;
    private JButton creaButton;
    private JPanel panelSave;
    private JButton buttonSave;
    private JButton returnButton;

    public CreazionePagina(final Controller controller, final JFrame frameChiamante, final String usernameAutore) {
        frame = new JFrame("Creazione Pagina");
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(3);
        //this.frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);// Imposta la posizione di default (centrato sullo schermo)
        frame.setResizable(false); // Imposta la finestra come ridimensionabile
        this.frame.setLocationRelativeTo(frameChiamante);
        this.frame.setVisible(true);
        panelTextArea.setVisible(false);
        buttonSave.setVisible(false);


        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textTitolo.getText() != null) {
                    buttonSave.setVisible(true);
                    panelTextArea.setVisible(true);
                    controller.addPage(textTitolo.getText(), usernameAutore);
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
        //controlla le operazioni fatte nella textArea
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkForNewLine(e, textArea, controller);
            }

            private void checkForNewLine(DocumentEvent e, JTextArea textArea, Controller controller) {
                String frase = null;
                try {
                    javax.swing.text.Document doc = e.getDocument();
                    String text = doc.getText(0, doc.getLength());

                    // Verifica se l'ultimo carattere inserito è un carattere di a capo
                    if (text.endsWith("\n")) {
                        String testo = textArea.getText();

                        // Trova l'indice dell'ultimo carattere di a capo
                        int lastIndex = testo.lastIndexOf("\n");
                        // Trova l'indice del carattere di a capo prima di quello trovato sopra
                        int secondLastIndex = testo.lastIndexOf("\n", lastIndex - 1);

                         frase=testo.substring(secondLastIndex+1,lastIndex+1);

                        }
                    controller.gestioneTestoPage(frase);

                        // Aggiorna la JTextArea con la nuova frase (se necessario)
                        // textArea.setText(nuovaFrase);
                    
                } catch (javax.swing.text.BadLocationException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Implementa se necessario
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Implementa se necessario
            }
        });

    }
}
