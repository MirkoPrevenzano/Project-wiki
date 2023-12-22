package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewPagina {
    public JFrame frame;
    private JPanel panel1;
    private JList listFrasi;
    private JPanel panelList;
    private JButton buttonReturn;
    private JButton modificaFraseButton;
    private JButton eliminaEliminaButton;
    private JButton modificaTestoButton;

    public ViewPagina(final Controller controller, final JFrame frameChiamante, final String usernameAutore, List<String> frasi,String titolo)
    {
        frame = new JFrame(titolo);
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        this.frame.setLocationRelativeTo(frameChiamante);
        this.frame.setVisible(true);
       frame.setLayout(new BorderLayout());
       // frame.add(new JScrollPane(listFrasi));
        listFrasi.setSize(500,500);
        frame.add(panelList,BorderLayout.NORTH);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listFrasi.setModel(listModel);
        JScrollPane scrollPane = new JScrollPane(listFrasi);
        panelList.setLayout(new BoxLayout(panelList, BoxLayout.Y_AXIS));
        listFrasi.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));
        panelList.add(scrollPane);
       // frame.add(panelList, BorderLayout.CENTER);
        frame.add(buttonReturn,BorderLayout.SOUTH);
        listModel.clear();
        listFrasi.setVisible(true);
        for (int i = 0; i < frasi.size(); i++) {
            listModel.addElement(frasi.get(i));
        }
        frame.pack();

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
