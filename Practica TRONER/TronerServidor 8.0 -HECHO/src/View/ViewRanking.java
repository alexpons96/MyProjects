package View;

import Controller.ControllerRanking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class ViewRanking extends JDialog{
    private final static String[] COLUMNS = {"Nickname",
            "Punts acumulats",
            "Últim accés"};
    private JTable jtUsuaris;    /** Taula d'usuaris */
    private DefaultTableModel dtmUsuaris;
    private JButton jbSortir;

    public ViewRanking(){
        this.setTitle("Taula Ranking");
        this.setSize(700, 400);
        this.setUndecorated(true);

        // 2. Creem la taula i les opcions i models:
        jtUsuaris = new JTable();
        this.setBackground(Color.BLACK);
        jtUsuaris.setBackground(Color.BLACK);
        jtUsuaris.setForeground(Color.CYAN);
        dtmUsuaris = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }};

        dtmUsuaris.setColumnIdentifiers(COLUMNS);
        jtUsuaris.setModel(dtmUsuaris);

        JScrollPane jspTaula = new JScrollPane();
        getContentPane().add(jspTaula, BorderLayout.CENTER);
        jspTaula.setViewportView(jtUsuaris);

        // [3. Creem el panel south]
        JPanel jpSud = new JPanel(new GridLayout(1,1));
        getContentPane().add(jpSud, BorderLayout.SOUTH);

        Font fontTitol = new Font("OCR A Extended", Font.BOLD,15);  // creem el tipus de font que farem servir
        jbSortir = new JButton(" -> Tancar finestra <- ");
        jbSortir.setBackground(Color.BLACK);
        jbSortir.setForeground(Color.CYAN);
        jbSortir.setFont(fontTitol);
        jpSud.add(jbSortir);

        this.setLocationRelativeTo(null);
    }

    public void registraControlador(ControllerRanking e) {
        jbSortir.setActionCommand("TANCAR");
        jbSortir.addActionListener(e);


    }
    public void afegeixUsuariRanking(String nom, long punts, Date ultim_acces) {
        dtmUsuaris.addRow(new Object[] {nom, punts, ultim_acces});
    }

    public void netejaRanking() {
        System.out.println("Nombre de files: " + dtmUsuaris.getRowCount());
        int i = 0;
        while (i < dtmUsuaris.getRowCount()) dtmUsuaris.removeRow(i);
    }
}
