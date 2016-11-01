package lp2.juegolp2.Artefactos;

import com.thoughtworks.xstream.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import lp2.juegolp2.Facilidades.ImageLoader;

public class PocionCuracion extends Artefacto
{
    public static ArrayList<PocionCuracion> pocionesDisp;
    
    private int puntos_vida;
    
    public PocionCuracion(int puntos_vida, String nombre, ImageLoader imgLoader)
    {
        super(nombre, imgLoader);
        this.setHP(puntos_vida);
        this.sprite.setImage(getNombre().toLowerCase().replace(' ', '_'));
    }

    public PocionCuracion(PocionCuracion pocion)
    {
        this(pocion.getHP(), pocion.getNombre(), pocion.getImageLoader());
    }
    
    public int getHP()
    {
        return this.puntos_vida;
    }
    
    public void setHP(int puntos_vida)
    {
        if (puntos_vida <= 0)
            throw new IllegalArgumentException(
                "Los puntos de vida de una poción no pueden ser negativos"
            );
        this.puntos_vida = puntos_vida;
    }
    
    public static PocionCuracion random(ImageLoader imgLoader)
    {
        PocionCuracion potion = pocionesDisp.get((int) (Math.random() * pocionesDisp.size()));
        if (potion.getImageLoader() == null) {
            potion.setImageLoader(imgLoader);
        }
        return potion.copy();
    }
    
    public PocionCuracion copy()
    {
        return new PocionCuracion(this);
    }
    
    @Override
    public Artefacto.Tipo type()
    {
        return Artefacto.Tipo.POCION;
    }
    
    @Override
    public String toString()
    {
        String str = getNombre() + " - " + Integer.toString(puntos_vida) + "HP";
        return str;
    }
    public static void loadXML(XStream xstream) 
        throws FileNotFoundException, IOException
    {
        FileReader reader = new FileReader("pociones.xml");
        pocionesDisp = (ArrayList<PocionCuracion>)xstream.fromXML(reader);
        reader.close();
    }
}
