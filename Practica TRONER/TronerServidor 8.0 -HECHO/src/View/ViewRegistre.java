package View;


import Controller.Controller;
import Model.ModelRegistre;
import Model.Usuari;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewRegistre extends JDialog {
    private JTextField jtfRGT_nick;
    private JPasswordField jtfRGT_pass1;
    private JPasswordField jtfRGT_pass2;
    private JTextField jtfRGT_email;

    private JButton jbRegister;
    private JButton jbCancel;

    private GridBagConstraints c;

    private Controller controller;

    public ViewRegistre(Controller controller) {
        this.c = new GridBagConstraints();   // inicialitzem la variable de les constants del GridBagLayout
        this.controller = controller;

        this.setTitle("Registre");
        this.setSize(400,400);
        this.setLocationRelativeTo(null);
        //this.setUndecorated(true);      //para que no aparezca la barra superior del JDialog -lo quitamos, queda feo
        this.setDefaultCloseOperation(0);
        this.getContentPane().setBackground(Color.BLACK);

        this.setLayout(new GridBagLayout());

        // Creem les instàncies de la finestra:
        // 1. Els atributs de la classe (caselles per escriure):
        jtfRGT_nick = new JTextField();
        jtfRGT_pass1 = new JPasswordField();
        jtfRGT_pass2 = new JPasswordField();
        jtfRGT_email = new JTextField();

        // 2. Les fonts que farem servir:
        Font fontTitol = new Font("OCR A Extended", Font.BOLD,15);  // creem el tipus de font que farem servir
        Font fontText = new Font("OCR A Extended", Font.PLAIN,15);  // creem el tipus de font que farem servir

        this.generarRegistre(fontTitol, fontText);
        // Registrem els diferents botons de la finestra:
        //this.registraControladorBotonsRegistre();
    }

    private void generarRegistre(Font fTitol, Font fText) {
        JLabel jlRGT_nick = new JLabel(controller.getModelRegistre().getRgt_nick());
        JLabel jlRGT_pass1 = new JLabel(controller.getModelRegistre().getRgt_pass1());
        JLabel jlRGT_pass2 = new JLabel(controller.getModelRegistre().getRgt_pass2());
        JLabel jlRGT_correu = new JLabel(controller.getModelRegistre().getRgt_correu());
        jlRGT_nick.setFont(fText);
        jlRGT_pass1.setFont(fText);
        jlRGT_pass2.setFont(fText);
        jlRGT_correu.setFont(fText);
        jlRGT_nick.setForeground(Color.white);
        jlRGT_pass1.setForeground(Color.white);
        jlRGT_pass2.setForeground(Color.white);
        jlRGT_correu.setForeground(Color.white);

        JLabel jlRegistre = new JLabel("REGISTRA'T aquí");
        jlRegistre.setForeground(Color.LIGHT_GRAY);
        jlRegistre.setFont(fTitol);
        jlRegistre.setForeground(Color.YELLOW);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        this.getContentPane().add(jlRegistre, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        // 2.2. Nick Registre
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.gridy = 5;
        c.anchor = GridBagConstraints.WEST;
        this.getContentPane().add(jlRGT_nick, c);
        // 2.3. Pass1 Registre
        c.gridy = 6;
        this.getContentPane().add(jlRGT_pass1, c);
        // 2.4. Pass2 Registre
        c.gridy = 7;
        this.getContentPane().add(jlRGT_pass2, c);
        // 2.5. Correu Registre
        c.gridy = 8;
        this.getContentPane().add(jlRGT_correu, c);
        // 2.6. Nick TextField Registre
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.getContentPane().add(jtfRGT_nick, c);
        // 2.7. Password TextField Registre
        c.gridy = 6;
        this.getContentPane().add(jtfRGT_pass1, c);
        // 2.8. Password again TextField Registre
        c.gridy = 7;
        this.getContentPane().add(jtfRGT_pass2, c);
        // 2.9. Correu TextField Registre
        c.gridy = 8;
        this.getContentPane().add(jtfRGT_email, c);
        // 2.10. Botó Registre
        jbRegister = new JButton("REGISTRE");
        jbRegister.setBackground(Color.white);
        jbRegister.setFont(fTitol);
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        jbRegister.setEnabled(false);
        this.getContentPane().add(jbRegister, c);

        // 3. Botó CANCEL
        jbCancel = new JButton("SORTIR");
        jbCancel.setBackground(Color.LIGHT_GRAY);
        jbCancel.setFont(fTitol);
        c.gridy = 10;
        c.fill = GridBagConstraints.NONE;
        this.getContentPane().add(jbCancel, c);
    }

    // GETTERS I SETTERS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    public JTextField getJtfRGT_nick() {
        return jtfRGT_nick;
    }

    public void setJtfRGT_nick(JTextField jtfRGT_nick) {
        this.jtfRGT_nick = jtfRGT_nick;
    }

    public JPasswordField getJtfRGT_pass1() {
        return jtfRGT_pass1;
    }

    public void setJtfRGT_pass1(JPasswordField jtfRGT_pass1) {
        this.jtfRGT_pass1 = jtfRGT_pass1;
    }

    public JPasswordField getJtfRGT_pass2() {
        return jtfRGT_pass2;
    }

    public void setJtfRGT_pass2(JPasswordField jtfRGT_pass2) {
        this.jtfRGT_pass2 = jtfRGT_pass2;
    }

    public JTextField getJtfRGT_email() {
        return jtfRGT_email;
    }

    public void setJtfRGT_email(JTextField jtfRGT_email) {
        this.jtfRGT_email = jtfRGT_email;
    }


    public Controller getController() {
        return controller;
    }
    public void setController(Controller controller) {
        this.controller = controller;
    }
    public JButton getJbRegister() {
        return jbRegister;
    }

    public JButton getJbCancel() {
        return jbCancel;
    }

    public void setJbRegister(JButton jbRegister) {
        this.jbRegister = jbRegister;
    }

    public void setJbCancel(JButton jbCancel) {
        this.jbCancel = jbCancel;
    }

    /**
     * Metode que registra el controlador que gestiona quins JButtons s'han pulsat
     */
    public void registraControladorBotonsRegistre() {
        jbRegister.setActionCommand(ModelRegistre.LGN_REGISTRE);
        jbRegister.addActionListener(controller);
        jbCancel.setActionCommand(ModelRegistre.LGN_CANCEL);
        jbCancel.addActionListener(controller);
    }

    /**
     * Metode que registra el controlador que gestiona el JButton de Enviar si els camps son correctes
     * @param controladorCamps controlador
     */
    public void registraControladorCampsRegistre(DocumentListener controladorCamps) {
        jtfRGT_nick.getDocument().addDocumentListener(controladorCamps);
        jtfRGT_pass1.getDocument().addDocumentListener(controladorCamps);
        jtfRGT_pass2.getDocument().addDocumentListener(controladorCamps);
        jtfRGT_email.getDocument().addDocumentListener(controladorCamps);
    }

    /**
     * Omple l'usuari amb la informació dels camps de la finestra de login
     * @param usuari usuari creat
     * @return usuari actualitzat
     */
    public Usuari ompleUsuari(Usuari usuari) {
        usuari.setNickname(getJtfRGT_nick().getText());
        usuari.setCorreu(getJtfRGT_email().getText());
        String pass = "";
        // el password no el podem llegir tal cual, perque es char[], hem de generar l'String char a char:
        for (int i = 0; i < getJtfRGT_pass1().getPassword().length; i++) {
            pass += getJtfRGT_pass1().getPassword()[i];
        }
        usuari.setPassword(pass);
        usuari.actualitzaDataRegistre();
        usuari.actualitzaUltimAcces();

        return usuari;
    }

    /**
     * Metode que buida els camps de la finestra de registre local
     */
    public void buidaCamps() {
        this.jtfRGT_nick.setText("");
        this.jtfRGT_pass1.setText("");
        this.jtfRGT_pass2.setText("");
        this.jtfRGT_email.setText("");
    }
}