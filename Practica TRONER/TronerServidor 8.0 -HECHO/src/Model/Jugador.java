package Model;

import Network.ServerDedicat;

/**
 * Classe jugador del joc, la utilizada a la vista del joc principal
 */
public class Jugador {

    private int direccio;
    private int posx;
    private int posy;
    private boolean viu;
    private int wins;
    private Usuari usuari;
    // NUEVO
    private ServerDedicat server;

    // nuevo

    public Jugador() {
    }

    public Jugador(int direccio, int posx, int posy, Usuari usuari){
        this.direccio = direccio;
        this.posx = posx;
        this.posy = posy;
        this.wins = 0;
        this.usuari = usuari;
        this.viu = true;
    }

    public int getWins(){
        return wins;
    }

    public void setDireccio(int direccio) {
        this.direccio = direccio;
    }

    public int getDireccio() {
        return direccio;
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * Getter de l'usuari
     * @return el nostre usuari
     */
    public Usuari getUsuari() {
        return usuari;
    }

    /**
     * Metode per actualitzar l'uusari en qüestió
     * @param usuari nou usuari
     */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public ServerDedicat getServer() {
        return server;
    }

    public void setServer(ServerDedicat server) {
        this.server = server;
    }

    /**
     * Metode que reseteja els jugadors
     * @param i index del jugador
     * @param rows nombre de files de la matriu
     * @param cols nombre de columnes de la matriu
     */
    public void resetPosicioIDireccio(int i, int rows, int cols, Jugador player) {
        switch (i) {
            case 0:
                player.direccio = 4;
                player.posx = 5;
                player.posy = rows/2;
                break;
            case 1:
                player.direccio = 3;
                player.posx = cols-5;
                player.posy = rows/2;
                break;
            case 2:
                player.direccio = 2;
                player.posx = cols/2;
                player.posy = 5;
                break;
            case 3:
                player.direccio = 1;
                player.posx = cols/2;
                player.posy = rows-5;
                break;

        }
    }
}
