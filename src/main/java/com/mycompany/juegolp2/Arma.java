package com.mycompany.juegolp2;

/**
 * Created by pmvb on 01/09/16.
 */
public class Arma extends Artefacto
{
    private int dmg_min;
    private int dmg_max;

    public Arma(int dmg_min, int dmg_max)
    {
        super();
        if (dmg_min > dmg_max)
            throw new ArithmeticException("Minimum weapon damage can't be lower than maximum damage");
        this.dmg_min = dmg_min;
        this.dmg_max = dmg_max;
    }

    public int damage()
    {
        return (int) (Math.random() * (dmg_max-dmg_min+1) + dmg_min);
    }

    @Override
    public Artefacto.Tipo type()
    {
        return Artefacto.Tipo.ARMA;
    }
    
    @Override
    public String toString()
    {
        String str = "Arma: \n";
        str += "Ataque mínimo: " + Integer.toString(dmg_min) + "\n";
        str += "Ataque máximo: " + Integer.toString(dmg_max) + "\n";
        return str;
    }
}
