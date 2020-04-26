package View;

import Controller.ControllerGrafica;

import javax.swing.*;
import java.awt.*;

public class ViewOpcioGrafica extends JFrame{
    private JComboBox<String> jcbUsuaris;
    private JComboBox<String> jcbModalitat;
    private JButton jbSortir;
    private JButton jbFerGrafica;

    public ViewOpcioGrafica(){
        setLocationRelativeTo(null);
        this.setUndecorated(true);
        setVisible(false);

        JPanel jpCenter = new JPanel(new GridLayout(1,2));
        jcbUsuaris = new JComboBox<String>();
        jcbModalitat = new JComboBox<String>();
        jcbUsuaris.setBackground(Color.BLACK);
        jcbModalitat.setBackground(Color.BLACK);
        jpCenter.add(jcbUsuaris);
        jpCenter.add(jcbModalitat);
        this.add(jpCenter);
        Font fontTitol = new Font("OCR A Extended", Font.BOLD,15);
        jbSortir = new JButton(" -> Tancar finestra <- ");
        jbSortir.setBackground(Color.BLACK);
        jbSortir.setForeground(Color.CYAN);
        jbSortir.setFont(fontTitol);
        jbFerGrafica = new JButton("-> Fer Grafica <- ");
        jbFerGrafica.setBackground(Color.BLACK);
        jbFerGrafica.setForeground(Color.CYAN);
        jbFerGrafica.setFont(fontTitol);
        JPanel jpSortir = new JPanel(new GridLayout(2,1));
        jpSortir.setBackground(Color.BLACK);
        jpSortir.add(jbFerGrafica);
        jpSortir.add(jbSortir);
        this.add(jpSortir,BorderLayout.SOUTH);
        pack();
    }

    public void registraController(ControllerGrafica e){
        jbSortir.setActionCommand("TANCAR");
        jbSortir.addActionListener(e);
        jbFerGrafica.setActionCommand("FER QUERY");
        jbFerGrafica.addActionListener(e);
    }

    public void setJComboboxUsuari(String[] usuaris){
        Font fontTitol = new Font("OCR A Extended", Font.BOLD,15);
        for(String e :usuaris){
            jcbUsuaris.addItem(e);
            jcbUsuaris.setForeground(Color.CYAN);
            jcbUsuaris.setFont(fontTitol);
        }
    }
    public void buidaJCombobox(){
        int itemCount = jcbUsuaris.getItemCount();

        for(int i=0;i<itemCount;i++){
            jcbUsuaris.removeItemAt(0);
        }
        int itemCount2 = jcbModalitat.getItemCount();

        for(int i=0;i<itemCount2;i++){
            jcbModalitat.removeItemAt(0);
        }
    }

    public String getComboboxUsuari(){
        return (String)jcbUsuaris.getSelectedItem();
    }
    public void setJComboboxModalitat(String[] modalitat){
        Font fontTitol = new Font("OCR A Extended", Font.BOLD,15);
        for(String e :modalitat){
            jcbModalitat.addItem(e);
            jcbModalitat.setForeground(Color.CYAN);
            jcbModalitat.setFont(fontTitol);
        }
    }

    public int getComboboxModalitat(){
        String modalitat = "";
        modalitat = (String)jcbModalitat.getSelectedItem();
        switch(modalitat){
            case "Mode V2":
                return 2;
            case "Mode V4":
                return 4;
            case "Mode Torneig":
                return 0;
        }
        return -1;
    }

}
