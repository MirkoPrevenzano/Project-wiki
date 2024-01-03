package GUI;

import controllerPackage.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ViewPagina {
    public JFrame frame;
    private JPanel panel1;
    private JList listFrasi;
    private JPanel panelList;
    private JButton buttonReturn;
    private JButton modificaFraseButton;
    private JButton eliminaEliminaButton;
    private JButton saveReturnButton;
    private List<String> frasiSelezionate=new ArrayList<>();
    private List<String> modifiche=new ArrayList<>();

    public ViewPagina(final Controller controller, final JFrame frameChiamante, final String usernameAutore, List<String> frasi,String titolo)
    {
        frame = new JFrame(titolo);
        this.frame.setContentPane(this.panel1);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setResizable(false);
        this.frame.setLocationRelativeTo(frameChiamante);
        this.frame.setVisible(true);
        frame.setLayout(new BorderLayout());

        listFrasi.setSize(500,500);
        frame.add(panelList,BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listFrasi.setModel(listModel);

        JScrollPane scrollPane = new JScrollPane(listFrasi);
        panelList.setLayout(new BoxLayout(panelList, BoxLayout.Y_AXIS));
        listFrasi.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));
        panelList.add(scrollPane);

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

        modificaFraseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(listFrasi.isSelectionEmpty())
                {
                    JOptionPane.showMessageDialog(ViewPagina.this.frame, "Selezione una frase");
                }
                else
                {
                    String f= (String) listFrasi.getSelectedValue();
                    String nuovaFrase = JOptionPane.showInputDialog("Nuova frase:");
                    if(!nuovaFrase.isEmpty())
                    {
                        int index=frasi.indexOf(f);
                        frasi.set(index,nuovaFrase);
                       // controller.modificaTesto(frasi, titolo, usernameAutore);
                        listModel.clear();
                        for (int i = 0; i < frasi.size(); i++) {
                            listModel.addElement(frasi.get(i));
                        }
                        int ctr=0;
                        for (String search:modifiche
                             ) {
                            if(search.equals(f))
                               ctr=1;
                        }
                        if(ctr==1)
                        {
                            int i=modifiche.indexOf(f);
                            modifiche.set(i,nuovaFrase);
                        }
                        else
                        {
                            frasiSelezionate.add(f);
                            System.out.println(frasiSelezionate.size());
                            modifiche.add(nuovaFrase);
                        }
                        listFrasi.revalidate();

                    }

                }

            }
        });
        eliminaEliminaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(listFrasi.isSelectionEmpty())
                {
                    JOptionPane.showMessageDialog(ViewPagina.this.frame, "Selezione una frase");
                }
                else {
                    String f = (String) listFrasi.getSelectedValue();
                    frasi.remove(f);

                    listModel.clear();
                    for (int i = 0; i < frasi.size(); i++) {
                        listModel.addElement(frasi.get(i));
                    }
                    int ctr=0;
                    for (String search:modifiche
                    ) {
                        if(search.equals(f))
                            ctr=1;
                    }
                    if(ctr==1)
                    {
                        int i=modifiche.indexOf(f);
                        modifiche.set(i,"");
                    }
                    else
                    {
                        frasiSelezionate.add(f);
                        modifiche.add("");
                    }
                   // controller.modificaTesto(frasi, titolo, usernameAutore);
                    listFrasi.revalidate();
                }

            }

        });
        saveReturnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveProposta(frasiSelezionate, modifiche, usernameAutore, titolo);
                controller.modificaTesto(frasiSelezionate, modifiche, usernameAutore, titolo, usernameAutore);
                /*_______________________*/
                frame.setVisible(false);
                frame.dispose();
                frameChiamante.setVisible(true);
            }
        });
    }
}
