package com.mycompany.juegolp2;

/**
 *
 * @author pmvb
 */
public class Avatar extends Entidad
{
    private int nivel;
    private Arma arma;
    private Armadura armadura;
    private Saco saco;

    public Avatar(String nombre)
    {
        super(nombre);
        this.nivel = 1;
        this.saco = new Saco();
    }
    
    public Avatar(String nombre, Position pos)
    {
        super(nombre, pos);
        this.nivel = 1;
        this.saco = new Saco();
    }
    
    public void pickupItem(Artefacto item)
    {
        this.saco.addItem(item);
    }
    
    public void useItem(int index)
    {
        Artefacto item = this.saco.getItem(index);
        this.saco.removeItem(index);
        // Qué hacer
    }
}

