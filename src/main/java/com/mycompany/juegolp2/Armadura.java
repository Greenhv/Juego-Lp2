package com.mycompany.juegolp2;

/**
 * Created by pmvb on 01/09/16.
 */
public class Armadura extends Artefacto
{
    private int defensa;

    public Armadura(int def)
    {
        super();
        this.defensa = def;
    }

    public int getDefensa()
    {
        return defensa;
    }

    @Override
    public Artefacto.Tipo getType() {
        return Artefacto.Tipo.ARMADURA;
    }
}
