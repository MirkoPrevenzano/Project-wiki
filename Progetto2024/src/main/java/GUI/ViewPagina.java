package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ViewPagina {
    public JFrame frame;
    private JPanel panel1;
    private JList listFrasi;
    private JPanel panelButton;
    private JButton modificaFraseButton;
    private JButton eliminaEliminaButton;
    private JButton saveReturnButton;
    private JButton buttonProposta;
    private JPanel panelList;
    private JPanel panelProposta;
    private JPanel panelModify;
    private JPanel panelElimina;
    private JPanel panelReturn;

    private List<String> frasiSelezionate = new ArrayList<>();

    private List<String> modifiche = new ArrayList<>();

    public ViewPagina(final Controller controller, final JFrame frameChiamante, final String usernameAutore, List<String> frasi, String titolo) {
        this.frame = new JFrame(titolo);
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(3);
        this.frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);// Imposta la posizione di default (centrato sullo schermo)
        frame.setResizable(false); // Imposta la finestra come ridimensionabile

        this.listFrasi.setModel(new DefaultListModel());
        DefaultListModel model = (DefaultListModel) this.listFrasi.getModel();
        if (frasi != null) {
            for (int i = 0; i < frasi.size(); ++i) {
                model.addElement(frasi.get(i));
            }

            buttonProposta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewProposte viewProposte = new ViewProposte(controller, frame, usernameAutore);
                    viewProposte.frame.setVisible(true);
                    ViewPagina.this.frame.setVisible(false);
                }
            });

            modificaFraseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (listFrasi.isSelectionEmpty()) {
                        JOptionPane.showMessageDialog(ViewPagina.this.frame, "Selezione una frase");
                    } else {
                        String f = (String) listFrasi.getSelectedValue();
                        listFrasi.clearSelection();
                        String nuovaFrase = JOptionPane.showInputDialog("Nuova frase:");
                        if (!nuovaFrase.isEmpty()) {
                            int index = frasi.indexOf(f);
                            frasi.set(index, nuovaFrase);
                            // controller.modificaTesto(frasi, titolo, usernameAutore);
                            model.clear();
                            for (int i = 0; i < frasi.size(); i++) {
                                model.addElement(frasi.get(i));
                            }
                            int ctr = 0;
                            for (String search : modifiche
                            ) {
                                if (search.equals(f))
                                    ctr = 1;
                            }
                            if (ctr == 1) {
                                int i = modifiche.indexOf(f);
                                modifiche.set(i, nuovaFrase);
                            } else {
                                frasiSelezionate.add(f);
                                modifiche.add(nuovaFrase);
                            }
                            listFrasi.revalidate();

                        }

                    }

                }
            });
            eliminaEliminaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (listFrasi.isSelectionEmpty()) {
                        JOptionPane.showMessageDialog(ViewPagina.this.frame, "Selezione una frase");
                    } else {
                        String f = (String) listFrasi.getSelectedValue();
                        listFrasi.clearSelection();
                        frasi.remove(f);

                        model.clear();
                        for (int i = 0; i < frasi.size(); i++) {
                            model.addElement(frasi.get(i));
                        }
                        int ctr = 0;
                        for (String search : modifiche
                        ) {
                            if (search.equals(f))
                                ctr = 1;
                        }
                        if (ctr == 1) {
                            int i = modifiche.indexOf(f);
                            modifiche.set(i, "");
                        } else {
                            frasiSelezionate.add(f);
                            modifiche.add("");
                        }
                        // controller.modificaTesto(frasi, titolo, usernameAutore);
                        listFrasi.revalidate();
                    }

                }

            });
            saveReturnButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.saveProposta(frasiSelezionate, modifiche, usernameAutore, titolo);
                    controller.modificaTesto(frasiSelezionate, modifiche, usernameAutore, titolo, usernameAutore);
                    /*_______________________*/
                    frame.setVisible(false);
                    frame.dispose();
                    frameChiamante.setVisible(true);
                }
            });
        }
    }
}
