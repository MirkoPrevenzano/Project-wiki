package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;



public class SchermataAutore {
    public JFrame frame;

    private JPanel panel1;
    private JButton buttonReturn;
    private JButton buttonCreatePage;
    private JButton aggiornaButton;
    private JList<String> listPage;
    private JPanel panelCreatePage;
    private JPanel panelList;
    private JPanel panelReturn;
    private JPanel panelCreateP;
    private JPanel panelAggiorna;

    public SchermataAutore(final Controller controller, final JFrame frameChiamante, final String usernameAutore) {
        this.frame = new JFrame("Profilo:" + usernameAutore);
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(3);
        this.frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);// Imposta la posizione di default (centrato sullo schermo)
        frame.setResizable(false); // Imposta la finestra come ridimensionabile

        List<String> listTitoli = controller.caricaTitoli(usernameAutore);
        this.listPage.setModel(new DefaultListModel());
        DefaultListModel model = (DefaultListModel) this.listPage.getModel();
        if (listTitoli != null) {
            for (int i = 0; i < listTitoli.size(); ++i) {
                model.addElement(listTitoli.get(i));
            }
        /*frame = new JFrame("Profilo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //inserisco modello
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Tasto crea pagina
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel1.add(buttonCreatePage, gbc);

        // Pulsante aggiorna lista
        gbc.gridy++;
        gbc.gridwidth = 2;
        aggiornaButton.getMaximumSize();
        panel1.add(aggiornaButton, gbc);

        // Lista

        gbc.gridy++;
        gbc.gridwidth = 2;
        listPage.setFixedCellWidth(200);  // Imposta la larghezza preferita della lista
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listPage.setModel(listModel);

// Aggiungi la lista al pannello
        gbc.gridy++;
        gbc.gridwidth = 2;
// Aggiungi uno scrollPane per supportare la visualizzazione di eventuali elementi in eccesso
        JScrollPane scrollPane = new JScrollPane(listPage);
        panel1.add(scrollPane, gbc);


        //creo listModel

        //popolo la lista

        for (String titolo : listTitoli) {
            listModel.addElement(titolo);
        }

        // Button return
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel1.add(buttonReturn, gbc);

        frame.getContentPane().add(panel1);*/

            buttonReturn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    frame.dispose();
                    frameChiamante.setVisible(true);
                }
            });

            buttonCreatePage.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {


                    CreazionePagina creazionePagina = new CreazionePagina(controller, SchermataAutore.this.frame, usernameAutore, listTitoli);
                    SchermataAutore.this.frame.setVisible(false);
                    creazionePagina.frame.setVisible(true);
                }
            });

            aggiornaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.clear();
                    listPage.setVisible(true);
                    for (int i = 0; i < listTitoli.size(); i++) {
                        model.addElement(listTitoli.get(i));
                    }
                }
            });

            listPage.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting() && !listPage.isSelectionEmpty()) {
                        List<String> listaFrasi = controller.getTestoPage(listPage.getSelectedValue(), usernameAutore);
                        ViewPagina viewPagina = new ViewPagina(controller, frame, usernameAutore, listaFrasi, listPage.getSelectedValue());
                        viewPagina.frame.setVisible(true);
                        SchermataAutore.this.frame.setVisible(false);
                        listPage.clearSelection();
                    }
                }
            });

            frame.setSize(400, 300);
            frame.setResizable(false);
            frame.setLocationRelativeTo(frameChiamante);
            frame.setVisible(true);
        }
    }
}

