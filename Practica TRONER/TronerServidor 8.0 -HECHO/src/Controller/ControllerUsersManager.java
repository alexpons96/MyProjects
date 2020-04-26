package Controller;

import Model.DBInfo;
import View.ViewUsersManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerUsersManager implements ActionListener {
    ViewUsersManager vistaTaulaUsuaris;
    DBInfo database;

    // CONSTRUCTOR -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    public ControllerUsersManager(ViewUsersManager vista, DBInfo dbinfo) {
        this.vistaTaulaUsuaris = vista;
        this.database = dbinfo;
    }


    // GETTERS I SETTERS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    public ViewUsersManager getVistaTaulaUsuaris() {
        return vistaTaulaUsuaris;
    }

    public void setVistaTaulaUsuaris(ViewUsersManager vistaTaulaUsuaris) {
        this.vistaTaulaUsuaris = vistaTaulaUsuaris;
    }

    public DBInfo getDatabase() {
        return database;
    }

    public void setDatabase(DBInfo database) {
        this.database = database;
    }


    // INNER-METHODS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Invoked when an action occurs.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "TANCAR":
                vistaTaulaUsuaris.setVisible(false);
                break;
            case "ESBORRAR":
                vistaTaulaUsuaris.eliminaUsuariSeleccionat(this);
                break;
        }
    }

    /**
     * Mètode que actualitza la taula d'usuaris.
     * @throws SQLException si hi ha cap problema al fer la comanda al mysql
     */
    public void actualitzaTaula() throws SQLException{
        vistaTaulaUsuaris.netejaTaula();
        String query = "select nom, punts_totals, data_registre, data_ultim_acces from usuari";
        ResultSet usuaris = database.controllerDBInfo.selectQuery(query);
        while (usuaris.next()) {
            vistaTaulaUsuaris.afegeixUsuari(usuaris.getString("nom"),
                                            usuaris.getLong("punts_totals"),
                                            usuaris.getDate("data_registre"),
                                            usuaris.getDate("data_ultim_acces"));
        }
    }

}
