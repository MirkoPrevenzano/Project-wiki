package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewPaginaUtente {

    public JFrame frame;

    private JPanel panel1;
    private JList listFrasi;
    private JPanel panelReturn;
    private JButton buttonReturn;
    private JButton buttonProposta;
    private JPanel panelProposta;
    private JPanel PanelSave;
    private JButton ButtonSave;
    private JPanel panelList;

    public ViewPaginaUtente(final Controller controller, final JFrame frameChiamante, List<String> frasi, String titolo) {

        this.frame = new JFrame("Frasi");
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(3);
        this.frame.pack();
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
