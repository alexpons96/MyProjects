package Controller;

import Model.DBInfo;
import View.ViewGrafica;
import View.ViewOpcioGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ControllerGrafica implements ActionListener {
    private ViewOpcioGrafica vistaOpcioGrafica;
    private ViewGrafica vistaGrafica;
    private DBInfo dbInfo;

    public ControllerGrafica(ViewOpcioGrafica vistaOpcioGrafica, DBInfo dbinfo,ViewGrafica vistaGrafica){
        this.vistaOpcioGrafica = vistaOpcioGrafica;
        this.vistaGrafica = vistaGrafica;
        this.dbInfo = dbinfo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "TANCAR":
                vistaOpcioGrafica.setVisible(false);
                vistaOpcioGrafica.buidaJCombobox();
                break;
            case "FER QUERY":
                String q = "";
                q = montarQuery();
                //System.out.println(q);
                try {
                    int i = 0;
                    Integer[] grafica = dbInfo.controllerDBInfo.buscarInfoGrafica(q);
                    i = buscarMaxPuntuacio(grafica);
                    vistaGrafica.limitPunts(i);
                    vistaGrafica.inserirNumPartides(contarParitdas(grafica));
                    vistaGrafica.valorRectangles(grafica);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                vistaGrafica.setVisible(true);
                break;
        }
    }

    public void omplirComboboxUsuaris() throws SQLException {
        vistaOpcioGrafica.setJComboboxUsuari(dbInfo.controllerDBInfo.buscarUsuaris());
    }

    public void omplirComboboxModalitat() {
        String[] modalitat = new String[3];
        modalitat[0] = "Mode V2";
        modalitat[1] = "Mode V4";
        modalitat[2] = "Mode Torneig";
        vistaOpcioGrafica.setJComboboxModalitat(modalitat);
    }

    public String montarQuery(){
        String q = "";
        q += "SELECT punts_partida FROM juga AS j, partida WHERE j.id_partida = partida.id_partida AND nom_usuari LIKE '" + vistaOpcioGrafica.getComboboxUsuari() + "' AND tipus =" + vistaOpcioGrafica.getComboboxModalitat()+ ";";
        return q;
    }

    public int buscarMaxPuntuacio(Integer[] grafica){
        int max = 0;
        for(int i = 0; i < grafica.length; i++){
            if(max < grafica[i]){
                max = grafica[i];
            }
        }
        return max;
    }
    public int contarParitdas(Integer[] s){
        int numP = 0;
        while(numP < s.length){
            numP++;
        }
        return numP;
    }
}