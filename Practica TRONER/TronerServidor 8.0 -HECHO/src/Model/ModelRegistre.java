package Model;

public class ModelRegistre {

    public static final String LGN_REGISTRE = "LGN_REGISTRE";
    public static final String LGN_CANCEL = "LGN_CANCEL";

    private final String rgt_nick = "_Nickname: ";
    private final String rgt_pass1 = "_Password: ";
    private final String rgt_pass2 = "_Password (again): ";
    private final String rgt_correu = "_Email: ";

    public ModelRegistre(){}

    public static String getLgnRegistre() {
        return LGN_REGISTRE;
    }

    public static String getLgnCancel() {
        return LGN_CANCEL;
    }

    public String getRgt_nick() {
        return rgt_nick;
    }

    public String getRgt_pass1() {
        return rgt_pass1;
    }

    public String getRgt_pass2() {
        return rgt_pass2;
    }

    public String getRgt_correu() {
        return rgt_correu;
    }


}