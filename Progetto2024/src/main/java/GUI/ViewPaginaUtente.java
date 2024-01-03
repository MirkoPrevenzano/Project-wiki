package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewPaginaUtente {
    public JFrame frame;
    private JPanel panel1;
    private JPanel panelReturn;
    private JButton buttonReturn;
    private JPanel panelSave;
    private JButton buttonSave;
    private JPanel panelProposta;
    private JButton buttonProposta;
    private JPanel panelList;
    private JList listFrasi;
    private JPanel panelButton;
    private JScrollPane scroll;

    public ViewPaginaUtente(final Controller controller, final JFrame frameChiamante, List<String> frasi, String titolo) {

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

            buttonReturn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    frame.dispose();

                    frameChiamante.setVisible(true);
                }
            });

        }
    }
}
