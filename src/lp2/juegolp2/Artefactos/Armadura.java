package lp2.juegolp2.Artefactos;

import java.util.*;

/**
 * Created by pmvb on 01/09/16.
 */
public class Armadura extends Artefacto
{
    public static ArrayList<Armadura> armadurasDisp;
    static {
        armadurasDisp = new ArrayList<>();
        armadurasDisp.add(new Armadura(5, "Armadura de Cuero"));
        armadurasDisp.add(new Armadura(10, "Armadura de Metal"));
        armadurasDisp.add(new Armadura(15, "Armadura Oscura"));
    }
    private int defensa;

    public Armadura(int def)
    {
        super();
        this.defensa = def;
    }
    
    public Armadura(int def, String nombre)
    {
        this(def);
        setNombre(nombre);
    }
    
    public int getDefensa()
    {
        return defensa;
    }

    public static Armadura random()
    {
        return armadurasDisp.get((int) (Math.random() * armadurasDisp.size()));
    }
    
    @Override
    public Artefacto.Tipo type()
    {
        return Artefacto.Tipo.ARMADURA;
    }
    
    @Override
    public String toString()
    {
        String str = getNombre() + " - " + Integer.toString(defensa);
        return str;
    }
}
