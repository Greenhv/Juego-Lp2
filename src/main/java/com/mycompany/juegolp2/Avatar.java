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
        if (item.type() == Artefacto.Tipo.ARMA)
            this.arma = (Arma) item;
        else if (item.type() == Artefacto.Tipo.ARMADURA)
            this.armadura = (Armadura) item;
        else
            this.saco.addItem(item);
    }
    
    @Override
    public int getNivel()
    {
        return this.nivel;
    }
    
    public Arma getArma()
    {
        return this.arma;
    }
    
    public Armadura getArmadura()
    {
        return this.armadura;
    }
    
    public Saco getSaco()
    {
        return this.saco;
    }
}

