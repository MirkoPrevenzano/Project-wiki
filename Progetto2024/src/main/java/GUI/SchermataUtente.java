package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SchermataUtente {
    static JFrame frame;
    private JPanel panel1;
    private JPanel panelReturn;
    private JButton returnButton;
    private JPanel panelTest;
    private JTextField textTitolo;
    private JPanel panelAggiorna;
    private JButton buttonAggiorna;
    private JPanel panelList;
    private JList listPage;

    public SchermataUtente(final Controller controller, final JFrame frameChiamante)
    {
        frame = new JFrame("Schermata Utente");
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(3);
        //this.frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);// Imposta la posizione di default (centrato sullo schermo)
        frame.setResizable(false); // Imposta la finestra come ridimensionabile
        this.frame.setLocationRelativeTo(frameChiamante);
        this.frame.setVisible(true);
        List<String> listTitoli;
        listTitoli = controller.caricaTitoli();



        DefaultListModel<String> listModel = new DefaultListModel<>();
        listPage.setModel(listModel);
        JScrollPane scrollPane = new JScrollPane(listPage);
        panelList.setLayout(new BoxLayout(panelList, BoxLayout.Y_AXIS));
        panelList.add(scrollPane);

        //  frame.add(panelList, BorderLayout.CENTER);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SchermataUtente.this.frame.setVisible(false); //nascondo frame iscrizione
                frameChiamante.setVisible(true);//riattivo frame home
                SchermataUtente.this.frame.dispose();
            }
        });
        buttonAggiorna.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Se vuoi aggiornare la lista, assicurati di svuotarla prima di aggiungere nuovi elementi
                listModel.clear();
                listPage.setVisible(true);
                for (int i = 0; i < listTitoli.size(); i++) {
                    listModel.addElement(listTitoli.get(i));
                }
            }
        });

        listPage.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !listPage.isSelectionEmpty()) {
                    String titolo = (String) listPage.getSelectedValue();
                    List<String> listaFrasi = controller.getTestoPage(titolo);
                    ViewPaginaUtente viewPaginaUtente = new ViewPaginaUtente(controller, frame, listaFrasi, titolo);
                    viewPaginaUtente.frame.setVisible(true);
                    SchermataUtente.this.frame.setVisible(false);
                    listPage.clearSelection();
                }
            }
        });

    }
}

