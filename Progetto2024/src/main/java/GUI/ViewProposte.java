package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewProposte {
    private JPanel panel1;
    private JList listProposte;
    private JButton aggiornaButton;
    private JButton returnButton;
    private JPanel panelList;
    public JFrame frame;

    public ViewProposte(final Controller controller, final JFrame frameChiamante, final String usernameAutore) {
        this.frame = new JFrame("Proposta");
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(3);
        this.frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);// Imposta la posizione di default (centrato sullo schermo)
        frame.setResizable(false); // Imposta la finestra come ridimensionabile
        List<String> listTitoli;
        listTitoli = controller.caricaTitoli();
        this.listProposte.setModel(new DefaultListModel());
        DefaultListModel model = (DefaultListModel) this.listProposte.getModel();
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false); //nascondo frame iscrizione
                frameChiamante.setVisible(true);//riattivo frame home
                frame.dispose();//rilascio le risorse

            }
        });
    }
}
