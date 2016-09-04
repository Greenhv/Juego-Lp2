package com.mycompany.juegolp2;

/**
 *
 * @author pmvb
 */
public class Avatar
{
    private String nombre;
    private int nivel;
    private Saco saco;
    private Position position;

    public Avatar(String nombre)
    {
        this.nombre = nombre;
        this.nivel = 1;
        this.position = new Position(0, 0);
    }

    public void move(Direction dir)
    {
        dir.move(this.getPosition());
    }

    public Position getPosition()
    {
        return this.position;
    }

}

