package lp2.juegolp2.Juego;

import java.awt.*;
import java.util.*;
import java.util.stream.IntStream;
import javax.swing.*;
import lp2.juegolp2.Artefactos.*;
import lp2.juegolp2.Mundo.*;

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
        juego.play();
    }
}
