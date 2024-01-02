package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import java.awt.*;
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
    private JPanel panelSave;


    public CreazionePagina(final Controller controller, final JFrame frameChiamante, final String usernameAutore, List<String> listTitoli) {
        frame = new JFrame("Creazione Pagina");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel1.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Titolo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel1.add(textTitolo, gbc);

        // Crea Pulsante
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel1.add(creaButton, gbc);

        // TextArea
        panelTextArea.setLayout(new BorderLayout());
        panelTextArea.setVisible(false);


        JScrollPane scrollPane = new JScrollPane(textArea);
        panelTextArea.add(scrollPane, BorderLayout.CENTER);

        gbc.gridy++;
        gbc.gridwidth = 2;
        panel1.add(panelTextArea, gbc);

        // Salva Pulsante
        gbc.gridy++;
        gbc.gridwidth = 2;
        buttonSave.setVisible(false);
        panel1.add(buttonSave, gbc);

        // Torna Indietro Pulsante
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel1.add(returnButton, gbc);

        frame.getContentPane().add(panel1);

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

        frame.setSize(400, 300);
        frame.setResizable(false);
        frame.setLocationRelativeTo(frameChiamante);
        frame.setVisible(true);
    }
}



