package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;



public class SchermataAutore {
    static JFrame frame;

    private JPanel panelReturn;
    private JPanel panel1;
    private JButton buttonReturn;
    private JButton buttonCreatePage;
    private JPanel panelCreatePage;
    private JPanel panelList;
    private JList<String> listPage;
    private JButton aggiornaButton;

    public SchermataAutore(final Controller controller, final JFrame frameChiamante, final String usernameAutore) {
        frame = new JFrame("Profilo");
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        aggiornaButton.setSize(3,4);
        this.frame.setLocationRelativeTo(frameChiamante);
        this.frame.setVisible(true);
        frame.setLayout(null);
        frame.add(new JScrollPane(listPage));
        List<String> listTitoli;
        listTitoli = controller.caricaTitoli(usernameAutore);






        DefaultListModel<String> listModel = new DefaultListModel<>();
        listPage.setModel(listModel);
        JScrollPane scrollPane = new JScrollPane(listPage);

        panelList.setLayout(new BoxLayout(panelList, BoxLayout.Y_AXIS));
        panelList.add(scrollPane);


        frame.add(panelList, BorderLayout.CENTER);
        //frame.pack();


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
                SchermataAutore.frame.setVisible(false);
                creazionePagina.frame.setVisible(true);
            }
        });

        aggiornaButton.addActionListener(new ActionListener() {
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
        frame.setVisible(true);
        listPage.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !listPage.isSelectionEmpty())
                {
                    List<String> listaFrasi=controller.getTestoPage(listPage.getSelectedValue(),usernameAutore);
                    ViewPagina viewPagina=new ViewPagina(controller,frame,usernameAutore,listaFrasi,listPage.getSelectedValue());
                    viewPagina.frame.setVisible(true);
                    SchermataAutore.frame.setVisible(false);
                    listPage.clearSelection();
                }

            }
        });
    }

}

