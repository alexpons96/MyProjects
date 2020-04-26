package View;

import Controller.Controller;
import Model.JPanelBackground;
import Model.ModelMenu;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewMenu extends JFrame {
    private GridBagConstraints constraints;
    private Controller controller;

    private JLabel jlTitol;
    private JLabel jlMode;
    private JButton jbOpcio1;
    private JButton jbOpcio2;
    private JButton jbOpcio3;
    private JButton jbOpcio4;
    private JButton jbOpcio5;
    private JButton jbOpcioSortir;

    // CONSTRUCTOR
    public ViewMenu(Controller controller) {
        this.controller = controller;
        constraints = new GridBagConstraints();

        // CONFIGURACIO DEL MENU:
        setTitle("Troner");
        setLayout(new BorderLayout());
        setSize(1280,720);
        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // CREACIO DELS ELEMENTS DEL MENU:

        // 0. indicador de la sessió: ADMIN
        jlMode = new JLabel(controller.getModelMenuPrincipal().getMODE());
        jlMode.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        jlMode.setForeground(Color.CYAN);
        // 1. titol
        jlTitol = new JLabel(controller.getModelMenuPrincipal().getTITLE());
        jlTitol.setFont(new Font("OCR A Extended", Font.BOLD, 120));
        jlTitol.setForeground(Color.WHITE);
        // 2. opcions
        jbOpcio1 = new JButton(controller.getModelMenuPrincipal().getOPCIO1());
        jbOpcio1.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        jbOpcio1.setForeground(Color.white);
        jbOpcio1.setBackground(Color.black);
        jbOpcio2 = new JButton(controller.getModelMenuPrincipal().getOPCIO2());
        jbOpcio2.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        jbOpcio2.setForeground(Color.white);
        jbOpcio2.setBackground(Color.black);
        jbOpcio3 = new JButton(controller.getModelMenuPrincipal().getOPCIO3());
        jbOpcio3.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        jbOpcio3.setForeground(Color.white);
        jbOpcio3.setBackground(Color.black);
        jbOpcio4 = new JButton(controller.getModelMenuPrincipal().getOPCIO4());
        jbOpcio4.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        jbOpcio4.setForeground(Color.white);
        jbOpcio4.setBackground(Color.black);
        jbOpcio5 = new JButton(controller.getModelMenuPrincipal().getOPCIO5());
        jbOpcio5.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        jbOpcio5.setForeground(Color.white);
        jbOpcio5.setBackground(Color.black);
        jbOpcioSortir = new JButton(controller.getModelMenuPrincipal().getOPCIOSORTIR());
        jbOpcioSortir.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        jbOpcioSortir.setForeground(Color.white);
        jbOpcioSortir.setBackground(Color.black);


        // INSERCIO DELS ELEMENTS AL MENU:

        // -> Añadimos el background:
        JPanelBackground backgroundMainMenu = new JPanelBackground();
        backgroundMainMenu.setBackground("assets/tron_menu.jpg");
        getContentPane().add(backgroundMainMenu, BorderLayout.CENTER);
        // -> Añadimos el modo de runeo del juego
        getContentPane().add(jlMode, BorderLayout.NORTH);
        // -> Añadimos todos los elementos (opciones y titulo del menu).
        generarMenu(backgroundMainMenu);

    }

    private void generarMenu(JPanelBackground backgroundMainMenu) {
        backgroundMainMenu.setLayout(new GridBagLayout());

        // -> Mode de la sessió:
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        backgroundMainMenu.add(jlMode, constraints);

        // -> Títol:
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.NORTHEAST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        backgroundMainMenu.add(jlTitol, constraints);

        constraints.weighty = 0.05;

        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.gridwidth = 2;
        // -> Opcio1:
        constraints.gridy = 1;
        backgroundMainMenu.add(jbOpcio1, constraints);
        // -> Opcio2:
        constraints.gridy = 2;
        backgroundMainMenu.add(jbOpcio2, constraints);
        // -> Opcio3:
        constraints.gridy = 3;
        backgroundMainMenu.add(jbOpcio3, constraints);
        // -> Opcio4:
        constraints.gridy = 4;
        backgroundMainMenu.add(jbOpcio4, constraints);
        // -> Opcio5:
        constraints.gridy = 5;
        backgroundMainMenu.add(jbOpcio5, constraints);
        // -> OpcioSortir:
        constraints.gridy = 6;
        backgroundMainMenu.add(jbOpcioSortir, constraints);

    }

    // GETTERS I SETTERS
    public Controller getControladorMenuPrincipal() {
        return controller;
    }

    public void setControladorMenuPrincipal(Controller controller) {
        this.controller = controller;
    }

    /**
     * Modificacio del setter del jlMode, que permet canviar entre avisar si el servidor està connectat (currently on-line/off-line)
     * @param online
     */
    public void setJlMode(boolean online) {
        if (online) {
            this.jlMode.setForeground(Color.GREEN);
            this.jlMode.setText(this.getControladorMenuPrincipal().getModelMenuPrincipal().getMODE()+" >>> Currently ONLINE");
        } else {
            this.jlMode.setForeground(Color.RED);
            this.jlMode.setText(this.getControladorMenuPrincipal().getModelMenuPrincipal().getMODE()+" >>> Currently OFFLINE");
        }
    }

    // METHODS

    /**
     * Proc. que registra els actionsEvents dels botons del menu principal (les opcions del joc per part de l'admin)
     * @param controlador controlador del menu principal
     */
    public void registraControladorBotonsMenu(ActionListener controlador) {
        // Opcio 1  -> registrar nou usuari
        jbOpcio1.setActionCommand(ModelMenu.BTN_OPCIO1);
        jbOpcio1.addActionListener(controlador);
        // Opcio 2  -> gestionar usuaris
        jbOpcio2.setActionCommand(ModelMenu.BTN_OPCIO2);
        jbOpcio2.addActionListener(controlador);
        // Opcio 3  -> configurar el sistema
        jbOpcio3.setActionCommand(ModelMenu.BTN_OPCIO3);
        jbOpcio3.addActionListener(controlador);
        // Opcio 4  -> visualitzar ranking
        jbOpcio4.setActionCommand(ModelMenu.BTN_OPCIO4);
        jbOpcio4.addActionListener(controlador);
        // Opcio 5  -> visualitzar grafic
        jbOpcio5.setActionCommand(ModelMenu.BTN_OPCIO5);
        jbOpcio5.addActionListener(controlador);
        // Opcio SORTIDA    -> sortir -.-
        jbOpcioSortir.setActionCommand(ModelMenu.BTN_OPCIOSORTIR);
        jbOpcioSortir.addActionListener(controlador);
    }
}
