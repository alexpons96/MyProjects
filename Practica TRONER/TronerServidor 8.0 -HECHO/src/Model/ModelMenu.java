package Model;

public class ModelMenu {
    // constants per als ActionCommands (no fan falta, pero queda millor organitzat)
    public static final String BTN_OPCIO1 = "OPCIO1";
    public static final String BTN_OPCIO2 = "OPCIO2";
    public static final String BTN_OPCIO3 = "OPCIO3";
    public static final String BTN_OPCIO4 = "OPCIO4";
    public static final String BTN_OPCIO5 = "OPCIO5";
    public static final String BTN_OPCIOSORTIR = "SORTIR";

    private final String TITLE = "TRONER ";
    private final String MODE = "[admin/host_mode]";
    private final String OPCIO1 = "   Registrar nou usuari";
    private final String OPCIO2 = "   Gestionar usuaris";
    private final String OPCIO3 = "   Configurar el sistema";
    private final String OPCIO4 = "   Visualitzar ranking";
    private final String OPCIO5 = "   Visualitzar gràfic jugador";
    private final String OPCIOSORTIR = "   Sortir de Troner";

    // CONSTRUCTOR
    public ModelMenu() {
    }

    // GETTERS (no setters)
    public String getTITLE() {
        return TITLE;
    }

    public String getMODE() {
        return MODE;
    }

    public String getOPCIO1() {
        return OPCIO1;
    }

    public String getOPCIO2() {
        return OPCIO2;
    }

    public String getOPCIO3() {
        return OPCIO3;
    }

    public String getOPCIO4() {
        return OPCIO4;
    }

    public String getOPCIO5() {
        return OPCIO5;
    }

    public String getOPCIOSORTIR() {
        return OPCIOSORTIR;
    }
}
