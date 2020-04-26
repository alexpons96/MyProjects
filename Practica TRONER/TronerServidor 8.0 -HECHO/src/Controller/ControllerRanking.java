package Controller;

import Model.DBInfo;
import View.ViewRanking;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerRanking implements ActionListener{
    ViewRanking vistaRanking;
    DBInfo database;

    // CONSTRUCTOR -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    public ControllerRanking(ViewRanking vista, DBInfo dbinfo) {
        this.vistaRanking = vista;
        this.database = dbinfo;
    }

    // GETTERS I SETTERS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    public ViewRanking getVistaRanking() {
        return vistaRanking;
    }

    public void setVistaRanking(ViewRanking vistaRanking) {
        this.vistaRanking = vistaRanking;
    }

    public DBInfo getDatabase() {
        return database;
    }

    public void setDatabase(DBInfo database) {
        this.database = database;
    }

    // INNER-METHODS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Metode que actualitza el ranking del total de punts dels jugadors de la base de dades
     * @throws SQLException
     */
    public void actualitzaRanking() throws SQLException {
        vistaRanking.netejaRanking();
        String query = "select nom, punts_totals, data_ultim_acces from usuari order by punts_totals desc;";
        ResultSet usuaris = database.controllerDBInfo.selectQuery(query);
        while (usuaris.next()) {
            vistaRanking.afegeixUsuariRanking(usuaris.getString("nom"),
                    usuaris.getLong("punts_totals"),
                    usuaris.getDate("data_ultim_acces"));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "TANCAR":
                vistaRanking.setVisible(false);
                break;
        }
    }
}
