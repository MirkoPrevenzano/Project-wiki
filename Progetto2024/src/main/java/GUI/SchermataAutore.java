package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchermataAutore {
    static JFrame frame;

    private JPanel panelReturn;
    private JPanel panel1;
    private JButton buttonReturn;

    public SchermataAutore( final Controller controller, final JFrame frameChiamante) {
         frame=new JFrame("Profilo");
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(3);
        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);
        this.frame.setVisible(true);
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
