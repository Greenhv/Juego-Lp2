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
    
    public Direction opposite()
    {
        Direction[] values = Direction.values();
        int index = (this.ordinal() + values.length/2) % values.length;
        return values[index];
    }
}