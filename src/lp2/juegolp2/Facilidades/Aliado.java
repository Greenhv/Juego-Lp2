package lp2.juegolp2.Facilidades;

import java.util.*;
import lp2.juegolp2.Entidades.Avatar;
import lp2.juegolp2.Artefactos.*;
import lp2.juegolp2.Mundo.*;

/**
 *
 * @author alulab
 */
public class Aliado extends Avatar
{
    private ArrayList<Consejo> consejos;
    
    public Aliado(String nombre)
    {
        super(nombre);
        consejos = new ArrayList<>();
    }
    
    public Aliado(String nombre, Position pos)
    {
        super(nombre, pos);
        consejos = new ArrayList<>();
    }
    
    public Aliado(String nombre, ArrayList<Consejo> consejos)
    {
        this(nombre);
        this.consejos = consejos;
        Collections.sort(consejos);
    }
    
    public String getConsejo()
    {
        int index = (int) (Math.random() * consejos.size());
        return consejos.get(index).getConsejo();
    }
    
    public void addConsejo(Consejo consejo)
    {
        this.consejos.add(consejo);
        Collections.sort(consejos);
    }
    
    @Override
    public Celda.Contenido getContenidoCelda()
    {
        return Celda.Contenido.ALIADO;
    }
}
