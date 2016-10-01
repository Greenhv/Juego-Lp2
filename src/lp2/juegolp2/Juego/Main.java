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
        Juego juego = Juego.getInstance();
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
        }
    }
}
