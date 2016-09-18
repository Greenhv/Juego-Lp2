package lp2.juegolp2.Artefactos;

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
    public Artefacto.Tipo type()
    {
        return Artefacto.Tipo.ARMADURA;
    }
    
    @Override
    public String toString()
    {
        String str = "Armadura: \n";
        str += "Defensa: " + Integer.toString(defensa) + "\n";
        return str;
    }
}
