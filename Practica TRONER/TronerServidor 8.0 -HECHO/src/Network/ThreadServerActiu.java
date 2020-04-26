package Network;

import Model.*;

import javax.print.attribute.standard.JobKOctetsProcessed;
import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ThreadServerActiu extends Thread {  //AIXO SEMPRE ESTARÀ ACTIU, I ANIRÀ REBENT USUARIS.
    private DBInfo dbinfo;
    private ServerSocket sServer;
    private ArrayList<ServerDedicat> llistaServers;    // és com una llista d'usuaris que estan connectats al servidor

    private boolean servidorObert;

    // NUEVO
    private Lobby[] llistaLobbies;

    // CONSTRUCTOR
    public ThreadServerActiu(DBInfo dbinfo, Lobby[] llistaLobbies) {
        this.dbinfo= dbinfo;
        this.servidorObert = false;
        this.llistaServers = new ArrayList<>();
        this.llistaLobbies = llistaLobbies;
    }

    // GETTERS I SETTERS
    public DBInfo getDbinfo() {
        return dbinfo;
    }

    public void setDbinfo(DBInfo dbinfo) {
        this.dbinfo = dbinfo;
    }

    public ServerSocket getsServer() {
        return sServer;
    }

    public void setsServer(ServerSocket sServer) {
        this.sServer = sServer;
    }

    public ArrayList<ServerDedicat> getLlistaSockets() {
        return llistaServers;
    }

    public void setLlistaSockets(ArrayList<ServerDedicat> llistaSockets) {
        this.llistaServers = llistaSockets;
    }

    public boolean isServidorObert() {
        return servidorObert;
    }

    public void setServidorObert(boolean servidorObert) {
        this.servidorObert = servidorObert;
    }

    public ArrayList<ServerDedicat> getLlistaServers() {
        return llistaServers;
    }

    public void setLlistaServers(ArrayList<ServerDedicat> llistaServers) {
        this.llistaServers = llistaServers;
    }

    public Lobby[] getLlistaLobbies() {
        return llistaLobbies;
    }

    public void setLlistaLobbies(Lobby[] llistaLobbies) {
        this.llistaLobbies = llistaLobbies;
    }

    // METHODS

    @Override
    public void run() {
        try {
            // CHIVATO
            //System.out.println("DBInfo: " + dbinfo.toString());
            sServer = new ServerSocket(dbinfo.getPort_client()); //rep el port llegit al .json

            // CHIVATO
            System.out.println("Servidor obert!");
            this.servidorObert = true;

            while (servidorObert) {
                System.out.print("Esperant usuari pel port " + dbinfo.getPort_client() + "...\t");
                // 1. Creem el servidor dedicat, que espera que el client es connecti per poder acceptar-lo:
                Socket sUsuari = sServer.accept();
                ServerDedicat sDedicat = new ServerDedicat(sUsuari, dbinfo, llistaLobbies, this);
                // 2. El runejem
                sDedicat.start();
                // 3. L'afegim a la llista de ServidorsDedicats
                llistaServers.add(sDedicat);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Espera d'usuaris detinguda, servidor tancat!", "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Metode que tanca el servidor dedicat enviat per parametre
     * @param serverDedicat servidorDedicat a treure de la llista.
     */
    public void tancaServidorDedicat(ServerDedicat serverDedicat) {
        try {
            sleep(250);
            serverDedicat.getSocket().close();
            this.llistaServers.remove(serverDedicat);
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Error al tancar el servidor dedicat!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al actualitzar la llista de servidors dedicats!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }
}
