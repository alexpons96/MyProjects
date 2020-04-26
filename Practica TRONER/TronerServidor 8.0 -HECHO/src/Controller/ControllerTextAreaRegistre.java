package Controller;

import View.ViewRegistre;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ControllerTextAreaRegistre implements DocumentListener {
    private Controller controller;
    private ViewRegistre  viewRegistre;

    // CONSTRUCTOR -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    public ControllerTextAreaRegistre(Controller controller, ViewRegistre viewRegisre) {
        this.controller = controller;
        this.viewRegistre = viewRegisre;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        comprovarCampsRegistre();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        comprovarCampsRegistre();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        comprovarCampsRegistre();
    }


    // GETTERS I SETTERS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public ViewRegistre getViewLogin() {
        return viewRegistre;
    }

    public void setViewLogin(ViewRegistre  viewLogin) {
        this.viewRegistre = viewLogin;
    }


    // METHODS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-


    public void comprovarCampsRegistre() {
        if (viewRegistre.getJtfRGT_nick().getText().length() >= 3
                && passwordValid(viewRegistre.getJtfRGT_pass1().getPassword())
                && passwordValid(viewRegistre.getJtfRGT_pass2().getPassword())
                && passwordsCoincideixen(viewRegistre.getJtfRGT_pass1().getPassword(), viewRegistre.getJtfRGT_pass2().getPassword())
                && emailValid(viewRegistre.getJtfRGT_email().getText())) {
            viewRegistre.getJbRegister().setEnabled(true);
        } else {
            viewRegistre.getJbRegister().setEnabled(false);
        }
    }

    public boolean passwordValid(char[] password) {
        boolean passCorrecte = false;
        if (password.length > 5) {  // la contrassenya deu tenir 6 o més caràcters
            for (int i = 0; i < password.length && !passCorrecte; i++) { // ha de tenir, com a mínim, una minúscula
                if (password[i] >= 'a' && password[i] <= 'z') { // si té minúscula, comprovem que tingui també una majúscula
                    for (int j = 0; j < password.length && !passCorrecte; j++) {
                        if (password[j] >= 'A' && password[j] <= 'Z') { // si té minúscula, comprovem que tingui també una majúscula
                            for (int z = 0; z < password.length; z++) {
                                if (password[z] >= '0' && password[z] <= '9') {
                                    passCorrecte = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return passCorrecte;
    }

    public boolean passwordsCoincideixen(char[] pass1, char[] pass2) {
        if (pass1.length != pass2.length) {
            return false;
        } else {
            for (int i = 0; i < pass1.length; i++) {
                if (pass1[i] != pass2[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean emailValid(String email) {
        boolean arrovaTrobada = false;
        int indexArrova = 0;

        for (int i = 0; i < email.length(); i++) {
            if (!arrovaTrobada && i != 0 && email.charAt(i) == '@') { // si _@
                arrovaTrobada = true;
                indexArrova = i;
            }
            if (arrovaTrobada
                    && email.charAt(i) == '.'
                    && (i - indexArrova > 1)    // hi ha, com a mínim, un caracter entre l''@' i el '.'
                    && ++i < email.length()) {  // hi ha, com a mínim, un caracter després del '.' (.com, per exemple)
                return true;
            }
        }
        return false;
    }
}
