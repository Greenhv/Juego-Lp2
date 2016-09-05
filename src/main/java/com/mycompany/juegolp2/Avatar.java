package com.mycompany.juegolp2;

/**
 *
 * @author pmvb
 */
public class Avatar
{
    private String nombre;
    private int nivel;
    private int maxHP;
    private int hp;
    private Arma arma;
    private Armadura armadura;
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
        this.getPosition().move(dir);
    }

    public Position getPosition()
    {
        return this.position;
    }

}

