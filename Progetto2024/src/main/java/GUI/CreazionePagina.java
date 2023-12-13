package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreazionePagina {
    public JFrame frame;

    private JPanel panelReturn;
    private JButton buttonReturn;
    private JPanel panelTitolo;
    private JTextField textTitolo;
    private JPanel panel1;

    public CreazionePagina(final Controller controller, final JFrame frameChiamante)
    {
        frame=new JFrame("Creazione Pagina");
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
