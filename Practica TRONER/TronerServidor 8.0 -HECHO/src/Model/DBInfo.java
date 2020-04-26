package Model;

import Controller.ControlDBInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;

/**
 * Clase que contendrá los datos leídos del Json.
 */
public class DBInfo {
    private int port_bbdd;
    private String ip_server;
    private String nom_bbdd;
    private String user_bbdd;
    private String pass_bbdd;
    private int port_client;

    public ControlDBInfo controllerDBInfo;

    // CONSTRUCTOR

    public DBInfo() {
        this.controllerDBInfo = new ControlDBInfo(this);
    }
/*
    public DBInfo(int port_bbdd, String ip_bbdd, String nom_bbdd, String user_bbdd, String pass_bbdd, int port_client) {
        this.port_bbdd = port_bbdd;
        this.ip_bbdd = ip_bbdd;
        this.nom_bbdd = nom_bbdd;
        this.user_bbdd = user_bbdd;
        this.pass_bbdd = pass_bbdd;
        this.port_client = port_client;
    }
*/

    // GETTERS I SETTERS

    public int getPort_bbdd() {
        return port_bbdd;
    }

    public String getIp_server() {
        return ip_server;
    }

    public String getNom_bbdd() {
        return nom_bbdd;
    }

    public String getUser_bbdd() {
        return user_bbdd;
    }

    public String getPass_bbdd() {
        return pass_bbdd;
    }

    public int getPort_client() {
        return port_client;
    }

    public void setPort_bbdd(int port_bbdd) {
        this.port_bbdd = port_bbdd;
    }

    public void setIp_server(String ip_server) {
        this.ip_server = ip_server;
    }

    public void setNom_bbdd(String nom_bbdd) {
        this.nom_bbdd = nom_bbdd;
    }

    public void setUser_bbdd(String user_bbdd) {
        this.user_bbdd = user_bbdd;
    }

    public void setPass_bbdd(String pass_bbdd) {
        this.pass_bbdd = pass_bbdd;
    }

    public void setPort_client(int port_client) {
        this.port_client = port_client;
    }

    public ControlDBInfo getControllerDBInfo() {
        return controllerDBInfo;
    }

    public void setControllerDBInfo(ControlDBInfo controllerDBInfo) {
        this.controllerDBInfo = controllerDBInfo;
    }

    // METHODS


}
