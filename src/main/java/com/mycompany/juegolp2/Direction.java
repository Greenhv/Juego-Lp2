package com.mycompany.juegolp2;

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
}