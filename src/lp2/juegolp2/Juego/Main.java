package lp2.juegolp2.Juego;

import lp2.juegolp2.Artefactos.*;
import lp2.juegolp2.Mundo.*;
import java.util.*;
import java.util.stream.IntStream;
/**
 *
 * @author pmvb
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] niveles ={0};
        Laberinto l = new Laberinto(10,10,0.5,niveles);
        l.addArtefacto();
        /*Juego juego = Juego.getInstance();
        juego.init();
        juego.historia();
        Juego.Result res = juego.play();
        switch (res) {
            case QUIT:
                System.out.print("Has decidido terminar el juego. ");
                System.out.println("Gracias por jugar.");
                break;
            case WIN:
                System.out.println("Has ganado !");
                break;
            case LOSE:
                System.out.println("Has perdido :(");
                break;
        }*/
    }
}
