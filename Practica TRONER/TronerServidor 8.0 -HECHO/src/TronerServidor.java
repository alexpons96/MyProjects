import Controller.Controller;
import Model.ModelMenu;
import Model.ModelRegistre;
import Network.ThreadServerActiu;

import java.awt.event.KeyEvent;
import java.time.Instant;
import java.util.Date;

public class TronerServidor {
    public static void main(String[] args) {
        try {
            // todo LO PRÓXIMO ES VER CÓMO PODEMOS APAGAR EL PUÑETERO SERVER SIN QUE SALTE LA EXCEPCIÓN

            // 1) Creamos el modelo del menú y el controlador de éste, para las opciones que escoja ejecutar el admin.
            ModelMenu modelMenu = new ModelMenu();
            ModelRegistre modelRegistre = new ModelRegistre();

            // 2) Creem la classe controller, que maneja tota la lògica del joc i la gestió de la sessio i les partides dels usuaris.
            Controller controller = new Controller(modelMenu, modelRegistre);

            // 3) Registramos el controlador del menú principal:
            controller.getVistaMenuPrincipal().registraControladorBotonsMenu(controller);

            // 4) Mostramos la vista del menú al haber cargado todos los anteriores pasos
            controller.getVistaMenuPrincipal().setVisible(true); //todo setVisible del menu.

            // EXTRA:
            //ReproductorMP3 gameSong = new ReproductorMP3(); //empieza a sonar la canción (por ahora lo quitamos).

        } catch (Exception e) {
            System.out.println("ERROR A L'EXECUCIÓ DEL JOC!");
        }
    }
}
