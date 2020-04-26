package View;
import javax.swing.*;
import java.awt.*;

/**
 * Classe que mostra la gràfica de la opcion corresponent
 */
public class ViewGrafica extends JDialog{
    private int max; // puntuacion max
    private int numPartidas; // numero de partidas
    private Integer[] grafica;
    //private Integer[] grafica ={50,100,150,200,75,20,80,100,60,180}; //valores de partidas

    /**
     *
     */
    public ViewGrafica(){
        max = 0;
        numPartidas = 0;
        setTitle("Grafica");
        setLocationRelativeTo(null);
        setSize(500,500);

    }
    public void paint (Graphics g)
    {
        super.paint(g);

        g.setColor (Color.BLACK);

        g.drawLine (75, 425, 75, 0);
        g.drawString("0", 60, 435);
        int i = 0;
        int despla = 0;
        int count = 0;
        while(count < max){
            i++;
            count = count + max/10;
            despla = 435- i*35;
            g.drawString(String.valueOf(count), 50, despla);


        }

        int x = 0;
        int count2 = 0;
        if(numPartidas < 10){
            while(x < numPartidas ){
                g.drawString("Partida"+String.valueOf(x+1), 80*x+100, 435);
                x++;
            }

        } else {

            while (count2 < numPartidas) {
                g.drawString("Partida" + String.valueOf(count2+1), 80*x+100, 435);
                count2= count2 +numPartidas/10;
                x++;
            }
        }
        g.drawLine (75, 425, 80*x+100, 425);
        setSize(80*x+100,500);
        g.setColor (Color.BLACK);

        g.setColor (Color.red);
        int escala = 0; int p = 0;
        float coordPunt = 0;


        while(p < numPartidas){
            if (max >=100){
                coordPunt = (float)(425-despla)/max;
                escala = (int) (((max - grafica[p])*coordPunt));
                g.fillRect(90+p*80, despla +escala,65, 425-escala-despla);
            } else {
                coordPunt = (float)(425-50)/max;
                escala = (int) (((max - grafica[p])*coordPunt));
                escala = (int) (((count - grafica[p])*coordPunt));
                g.fillRect(90+p*80, 50+escala,65, 425-escala-50);
            }


            p++;
        }

    }
    public void limitPunts(int e){
        max = e;
    }
    public void inserirNumPartides(int np){
        numPartidas = np;
    }
    public void valorRectangles(Integer[] valors) {
        grafica = valors;
    }
}
