package Controller;

import Model.*;
import Network.*;
import View.*;
import com.mysql.jdbc.jmx.LoadBalanceConnectionGroupManagerMBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.function.LongBinaryOperator;

import static java.lang.Thread.sleep;

public class Controller implements ActionListener {
    private ModelMenu modelMenuPrincipal;
    private ModelRegistre modelRegistre;
    private ViewMenu vistaMenuPrincipal;
    private ViewRegistre vistaRegistre;
    private ViewUsersManager vistaTaulaUsuaris; // NUEVO
    private ViewRanking vistaRanking;
    private ViewOpcioGrafica vistaOpcioGrafica;
    private ViewGrafica vistaGrafica;

    private ControllerTextAreaRegistre controllerTextAreaRegistre;
    private ControllerUsersManager controllerTaulaUsuaris;  // NUEVO
    private ControllerRanking controllerRanking;
    private ControllerGrafica controllerGrafica;


    private ThreadServerActiu threadServerActiu;

    // NUEVO:
    private Lobby[] llistaLobbies;   /** array de lobbies */

    // información de la base de datos a la que nos conectaremos al principio de la sesión.
    private DBInfo dbinfo;

    // CONSTRUCTOR
    public Controller(ModelMenu modelMenu, ModelRegistre mRegistre) {
        this.modelMenuPrincipal = modelMenu;
        this.modelRegistre = mRegistre;
        this.vistaMenuPrincipal = new ViewMenu(this);
        this.vistaRegistre = new ViewRegistre(this);

        this.controllerTextAreaRegistre = new ControllerTextAreaRegistre(this,this.vistaRegistre);
        this.vistaRegistre.registraControladorCampsRegistre(controllerTextAreaRegistre);
        this.vistaRegistre.registraControladorBotonsRegistre();

        this.dbinfo = new DBInfo();

        this.llistaLobbies = new Lobby[3];

        this.vistaTaulaUsuaris = new ViewUsersManager();
        this.controllerTaulaUsuaris = new ControllerUsersManager(vistaTaulaUsuaris, dbinfo);
        this.vistaTaulaUsuaris.registraControlador(controllerTaulaUsuaris);
        this.vistaTaulaUsuaris.setVisible(false);
        this.vistaRanking = new ViewRanking();
        this.controllerRanking = new ControllerRanking(vistaRanking,dbinfo);
        this.vistaRanking.registraControlador(controllerRanking);
        this.vistaRanking.setVisible(false);
        vistaOpcioGrafica = new ViewOpcioGrafica();
        vistaGrafica = new ViewGrafica();
        controllerGrafica = new ControllerGrafica(vistaOpcioGrafica, dbinfo, vistaGrafica);
        vistaOpcioGrafica.registraController(controllerGrafica);
        vistaOpcioGrafica.setVisible(false);

        // Perque tant aviat comenci sempre ha de carregar la base de dades.
        this.carregaDB();

        threadServerActiu = new ThreadServerActiu(dbinfo, llistaLobbies);
        threadServerActiu.setServidorObert(false);  // nuevo
        vistaMenuPrincipal.setJlMode(false);
    }


    // GETTERS I SETTERS

    public ModelMenu getModelMenuPrincipal() {
        return modelMenuPrincipal;
    }

    public void setModelMenuPrincipal(ModelMenu modelMenuPrincipal) {
        this.modelMenuPrincipal = modelMenuPrincipal;
    }

    public ViewMenu getVistaMenuPrincipal() {
        return vistaMenuPrincipal;
    }

    public void setVistaMenuPrincipal(ViewMenu vistaMenuPrincipal) {
        this.vistaMenuPrincipal = vistaMenuPrincipal;
    }

    public ViewRegistre getVistaRegistre(){
        return vistaRegistre;
    }

    public void setVistaRegistre(ViewRegistre vistaRegistre){
        this.vistaRegistre = vistaRegistre;
    }

    public ModelRegistre getModelRegistre(){
        return modelRegistre;
    }

    public void setModelRegistre(ModelRegistre modelRegistre){
        this.modelRegistre = modelRegistre;
    }

    public DBInfo getDbinfo() {
        return dbinfo;
    }

    public void setDbinfo(DBInfo dbinfo) {
        this.dbinfo = dbinfo;
    }

    public ThreadServerActiu getThreadServerActiu() {
        return threadServerActiu;
    }

    public void setThreadServerActiu(ThreadServerActiu threadServerActiu) {
        this.threadServerActiu = threadServerActiu;
    }

    public ViewUsersManager getVistaTaulaUsuaris() {
        return vistaTaulaUsuaris;
    }

    public void setVistaTaulaUsuaris(ViewUsersManager vistaTaulaUsuaris) {
        this.vistaTaulaUsuaris = vistaTaulaUsuaris;
    }

    public ViewRanking getVistaRanking() {
        return vistaRanking;
    }

    public void setVistaRanking(ViewRanking vistaRanking) {
        this.vistaRanking = vistaRanking;
    }

    public Lobby[] getLlistaLobbies() {
        return llistaLobbies;
    }

    public void setLlistaLobbies(Lobby[] llistaLobbies) {
        this.llistaLobbies = llistaLobbies;
    }

    public ViewOpcioGrafica getVistaOpcioGrafica() {
        return vistaOpcioGrafica;
    }

    public void setVistaOpcioGrafica(ViewOpcioGrafica vistaOpcioGrafica) {
        this.vistaOpcioGrafica = vistaOpcioGrafica;
    }

    public ViewGrafica getVistaGrafica() {
        return vistaGrafica;
    }

    public void setVistaGrafica(ViewGrafica vistaGrafica) {
        this.vistaGrafica = vistaGrafica;
    }

// METHODS

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        try {
            switch (command) {
                case ModelMenu.BTN_OPCIO1:  // registrar nou usuari
                    vistaRegistre.setVisible(true);
                    break;

                case ModelMenu.BTN_OPCIO2:  // gestionar usuaris
                    controllerTaulaUsuaris.actualitzaTaula();   //actualitzem la taula
                    vistaTaulaUsuaris.setVisible(true);         //obrim la finestra
                    break;

                case ModelMenu.BTN_OPCIO3:  // configurar el sistema
                    this.configurarSistema();
                    break;

                case ModelMenu.BTN_OPCIO4:  // visualitzar ranking
                    controllerRanking.actualitzaRanking();
                    vistaRanking.setVisible(true);
                    break;

                case ModelMenu.BTN_OPCIO5:  // visualitzar grafic
                    controllerGrafica.omplirComboboxUsuaris();
                    controllerGrafica.omplirComboboxModalitat();
                    vistaOpcioGrafica.setVisible(true);
                    break;

                case ModelMenu.BTN_OPCIOSORTIR: // tancar tots els processos i sortir del joc
                    // chivato:
                    System.out.println("Cridant a la " + ModelMenu.BTN_OPCIOSORTIR + " (bySWITCH)!\n");
                    // Terminem tota execució en segon pla del programa
                    terminarExecucio();
                    break;

                case ModelRegistre.LGN_REGISTRE:
                    System.out.println("Cridant a la " + ModelRegistre.LGN_REGISTRE + "(bySWITCH)!\n");
                    this.ferRegistreServidor();
                    vistaRegistre.buidaCamps();
                    vistaRegistre.setVisible(false);
                    break;

                case ModelRegistre.LGN_CANCEL:
                    System.out.println("Cridant a la " + ModelRegistre.LGN_CANCEL + "(bySWITCH)!\n");
                    vistaRegistre.setVisible(false);
                    break;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vistaMenuPrincipal,"Error al enviar una ordre a la base de dades!","ERROR", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuPrincipal, "Alguna opció ha fallat: ¡execució cancel·lada! :(", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metode que configura el sistema del servidor (mostra la finestra i espera que l'usuari esculli una opció)
     * @throws InterruptedException
     */
    private void configurarSistema() throws InterruptedException {
        Object[] options = { "Iniciar Servidor", "Modificar el port",
                "Aturar Servidor" };

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        JLabel jlAux = new JLabel("Port de connexió al servidor (actual): ");
        jlAux.setForeground(Color.CYAN);
        panel.add(jlAux);
        JTextField port = new JTextField(10);
        port.setText(dbinfo.getPort_client()+"");
        panel.add(port);

        int result = JOptionPane.showOptionDialog(null, panel, "Configuració del Sistema",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, null);
        switch (result) {
            case 0: // obrir servidor
                if (!threadServerActiu.isServidorObert()) {
                    this.carregaLobby();
                    System.out.println("Ha creat els lobbies!");
                    this.carregaServidor();
                    System.out.println("Ha carregat el servidor!");
                    JOptionPane.showMessageDialog(null, "Servidor obert pel port "+dbinfo.getPort_client()+"!");
                } else {
                    JOptionPane.showMessageDialog(null, "El servidor ja està obert!");
                }
                break;

            case 1: // modificar port
                if (threadServerActiu.isServidorObert()) {
                    //System.out.println("Error amb el port!");
                    JOptionPane.showMessageDialog(null, "El port no es pot actualitzar mentre està obert!", "WARNING", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (this.portIncorrecte(port.getText())) {
                        JOptionPane.showMessageDialog(null, "Port no vàlid! Introdueix un numero entre 1024 i 65535", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        dbinfo.setPort_client(Integer.parseInt(port.getText()));
                        dbinfo.controllerDBInfo.canviaPortClient();
                        JOptionPane.showMessageDialog(null, "El port s'ha actualitzat correctament al " + dbinfo.getPort_client() + "!");
                    }
                }
                break;

            case 2: // tancar servidor
                if (threadServerActiu.isServidorObert()) {
                    this.tancaLobby();
                    this.tancaServidor();
                } else {
                    JOptionPane.showMessageDialog(null, "No hi ha cap servidor obert!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
                break;
        }
    }

    /**
     * Mètode que diu si el port introduit a la opcio de configuracio no es vàlid
     * @param port port a comprovar
     * @return boolea que indica si el port es correcte
     */
    private boolean portIncorrecte(String port) {
        try {
            if (Integer.parseInt(port) > 1023 && Integer.parseInt(port) <= 65535) {
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error.");
        }
        return true;
    }

    /**
     * Metode que realitza tots els passos per crear un nou usuari sense iniciar sessio amb ningú.
     */
    private void ferRegistreServidor() {
        Usuari usuari = new Usuari();
        boolean found = false;
        // Llegim la info de la finestra i creem l'usuari.
        usuari.actualitzaUsuari(vistaRegistre.ompleUsuari(usuari));
        usuari.setNou_usuari(true);
        // El busquem a la base de dades.
        found = dbinfo.getControllerDBInfo().buscarUsuari(usuari);
        // L'inserim si no hi ha hagut cap error
        if (!found) {
            dbinfo.getControllerDBInfo().creaUsuari(usuari);
            JOptionPane.showMessageDialog(vistaRegistre, "Usuari registrat amb éxit!");
        } else {
            JOptionPane.showMessageDialog(vistaRegistre, "No s'ha pogut registrar aquest usuari!", "WARNING", JOptionPane.WARNING_MESSAGE);
        }

    }

    /**
     * Carrega la base de dades
     */
    private void carregaDB() {
        dbinfo = dbinfo.getControllerDBInfo().loadModelDBInfo(); // enviem les dades, llegides del json
        dbinfo.getControllerDBInfo().connect();  // ens connectem a la base de dades
    }

    /**
     * Mètode que realitza tots elspassos per iniciar el servidor
     */
    private void carregaServidor() throws InterruptedException {
        sleep(250);
        threadServerActiu.setLlistaLobbies(this.llistaLobbies);
        threadServerActiu.start();
        this.vistaMenuPrincipal.setJlMode(true);

        // Esperem a que el threadServerActiu encengui el servidor, i iniciem els lobbies:
        while (!this.threadServerActiu.isServidorObert());
        /* no es un chivato, pero hay que borrarlo!
        this.llistaLobbies[0].start();
        this.llistaLobbies[1].start();
        this.llistaLobbies[2].start();
        */
    }

    /**
     * Metode que carrega i prepara els lobbies (sales d'espera)
     * @throws InterruptedException
     */
    private void carregaLobby() throws InterruptedException {
        // Creem els lobbies del servidor per a cada modalitat de joc:
        this.llistaLobbies[0] = new Lobby(2);   // lobby per a cua de partides de 2 jugadors (casual)
        this.llistaLobbies[1] = new Lobby(4);   // lobby per a cua de partides de 4 jugadors (casual)
        this.llistaLobbies[2] = new Lobby(0);   // lobby per a cua de partides de 4 jugadors en mode torneig

        // Com que sempre hi haurà una classe ThreadServerActiu creada, passem l'actual:
        this.llistaLobbies[0].setServerGeneral(this.threadServerActiu);
        this.llistaLobbies[1].setServerGeneral(this.threadServerActiu);
        this.llistaLobbies[2].setServerGeneral(this.threadServerActiu);
    }

    /**
     * Metode que tanca tots els sockets oberts i elimina els servidors de la llista de servers dedicats del threadServerActiu.
     */
    private void tancaServidor() {
        try {
            if (threadServerActiu.isServidorObert()) {
                int max = threadServerActiu.getLlistaSockets().size();
                //System.out.println("NOMBRE DE SOCKETS OBERTS: " + max);
                for (int i = 0; i < max; i++) {
                    System.out.print("Tancant socket " + (i + 1) + "...\t");
                    threadServerActiu.getLlistaSockets().get(i).getSocket().close();
                    threadServerActiu.getLlistaSockets().remove(i);
                    System.out.println("TANCAT!");
                }
                threadServerActiu.getsServer().close();

                // Hem de reinicar el servidor:
                threadServerActiu = null;
                threadServerActiu = new ThreadServerActiu(dbinfo, llistaLobbies);
                threadServerActiu.setServidorObert(false);
                this.vistaMenuPrincipal.setJlMode(false);

            } else {
                JOptionPane.showMessageDialog(null, "No hi ha cap servidor obert!");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Sockets tancats!");
        }
    }

    /**
     * Metode que tanca els tres lobbies (sales d'espera)
     */
    private void tancaLobby() {
        System.out.println("nPlayers al lobby 0: "+this.llistaLobbies[0].getCuaJugadors().size());
        System.out.println("nPlayers al lobby 1: "+this.llistaLobbies[1].getCuaJugadors().size());
        System.out.println("nPlayers al lobby 2: "+this.llistaLobbies[2].getCuaJugadors().size());

        int nplayers = this.llistaLobbies[0].getCuaJugadors().size();   // todo esto tal vez explote cuando lo pongamos en práctica
        for (int i = 0; i < nplayers; i++) {
            this.llistaLobbies[0].getCuaJugadors().get(i).setPlaying(false);    //ja no estàn jugant
            this.llistaLobbies[0].remPlayer(this.llistaLobbies[0].getCuaJugadors().get(i));
        }
        this.llistaLobbies[0] = null;

        nplayers = this.llistaLobbies[1].getCuaJugadors().size();   // todo esto tal vez explote cuando lo pongamos en práctica
        for (int i = 0; i < nplayers; i++) {
            this.llistaLobbies[0].getCuaJugadors().get(i).setPlaying(false);
            this.llistaLobbies[1].remPlayer(this.llistaLobbies[1].getCuaJugadors().get(i));
        }
        this.llistaLobbies[1] = null;

        nplayers = this.llistaLobbies[2].getCuaJugadors().size();   // todo esto tal vez explote cuando lo pongamos en práctica
        for (int i = 0; i < nplayers; i++) {
            this.llistaLobbies[0].getCuaJugadors().get(i).setPlaying(false);
            this.llistaLobbies[2].remPlayer(this.llistaLobbies[2].getCuaJugadors().get(i));
        }
        this.llistaLobbies[2] = null;
    }

    /**
     * Termina tots els processos que es poden detenir sobre l'execució del programa
     * @throws IOException
     */
    public void terminarExecucio() throws IOException {    // aixo tancarà tots els processos
        System.out.println("...Terminant execució!");
        System.out.println("Servidor obert: " + threadServerActiu.isServidorObert());   // <--- CHIVATO
        if (threadServerActiu.isServidorObert()) {
            System.out.println("Tamany llista de sockets: " + threadServerActiu.getLlistaSockets().size());   // <--- CHIVATO
            // Comprovem si hi ha cap socket d'un client obert (alguna connexio activa al moment de tancar):
            if (threadServerActiu.getLlistaSockets().size() > 0) {  // Si hi ha connexions actives, les tanquem totes:
                for (int i = 0; i < threadServerActiu.getLlistaSockets().size(); i++) {
                    threadServerActiu.getLlistaSockets().get(i).getSocket().close();
                    System.out.println("Esborrant el nombre: " + i);
                    threadServerActiu.getLlistaSockets().remove(i);
                }
            }
            // Ara tanquem el socket del servidor:
            threadServerActiu.getsServer().close();     // esto hace que lo otro pete, porque está esperando un 'accept' en el momento en que lo cerramos
            threadServerActiu.setServidorObert(false);
        }

        // tanquem la connexio amb la base de dades
        dbinfo.getControllerDBInfo().disconnect();
        System.exit(0);
    }
}
