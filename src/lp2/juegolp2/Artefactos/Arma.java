package lp2.juegolp2.Artefactos;

import com.thoughtworks.xstream.XStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by pmvb on 01/09/16.
 */
public class Arma extends Artefacto
{
    public static ArrayList<Arma> armasDisp;
    
    private int dmg_min;
    private int dmg_max;

    public Arma(int dmg_min, int dmg_max)
    {
        super();
        if (dmg_min > dmg_max)
            throw new ArithmeticException(
                "El daño máximo de un arma no puede ser menor que el mínimo");
        this.dmg_min = dmg_min;
        this.dmg_max = dmg_max;
    }
    
    public Arma(int dmg_min, int dmg_max, String nombre)
    {
        this(dmg_min, dmg_max);
        this.setNombre(nombre);
    }
    
    public Arma(Arma arma)
    {
        this(arma.dmg_min, arma.dmg_max, arma.getNombre());
    }

    public int damage()
    {
        return (int) (Math.random() * (dmg_max-dmg_min+1) + dmg_min);
    }

    public static Arma random()
    {
        return armasDisp.get((int) (Math.random() * armasDisp.size())).copy();
    }
    
    public Arma copy()
    {
        return new Arma(this);
    }
    
    @Override
    public Artefacto.Tipo type()
    {
        return Artefacto.Tipo.ARMA;
    }
    
    @Override
    public String toString()
    {
        // Se imprime el daño promedio
        String str = getNombre() + " - " + Integer.toString((dmg_min+dmg_max)/2);
        return str;
    }
    
    public static void loadXML(XStream xstream) 
        throws FileNotFoundException, IOException
    {
        FileReader reader = new FileReader("armas.xml");
        armasDisp = (ArrayList<Arma>)xstream.fromXML(reader);
        reader.close();
    }
}
