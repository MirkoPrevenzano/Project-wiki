package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    private static JFrame frame;
    private JButton Accesso_Autore;
    private JButton Accesso_Utente;
    private JButton Iscriviti;
    private JPanel panel1;

    public Home() {
        Accesso_Autore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Hai cliccato il pulsante!");
            }
        });
    }

    public static void main(String[] args)
    {
        frame = new JFrame("Home");
        Home home = new Home();
        frame.setContentPane(home.panel1);
        frame.setDefaultCloseOperation(3);
        frame.pack();
        frame.setVisible(true);

    }

}
