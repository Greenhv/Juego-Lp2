package lp2.juegolp2.Mundo;

import java.util.*;

/**
 * Created by pmvb on 01/09/16.
 */
public enum Direction
{
    UP, //UP
    RIGHT, //RIGHT
    DOWN, //DOWN
    LEFT; // LEFT
    
    public static boolean contains(String test) {
        for (Direction c : Direction.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
    
    public static final Map<Direction, String> direcciones;
    static
    {
        direcciones = new HashMap<>();
        direcciones.put(UP, "Arriba");
        direcciones.put(RIGHT, "Derecha");
        direcciones.put(DOWN, "Abajo");
        direcciones.put(LEFT, "Izquierda");
    }
}