package lp2.juegolp2.Artefactos;

import com.thoughtworks.xstream.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import lp2.juegolp2.Facilidades.ImageLoader;

/**
 * Created by pmvb on 01/09/16.
 */
public class Armadura extends Artefacto
{
    public static ArrayList<Armadura> armadurasDisp;
    
    private int defensa;

    public Armadura(int def, String nombre, ImageLoader imgLoader)
    {
        super(nombre, imgLoader);
        setDefensa(def);
        this.sprite.setImage(getNombre().toLowerCase().replace(' ', '_'));
    }
    
    public Armadura(Armadura armor)
    {
        this(armor.getDefensa(), armor.getNombre(), armor.getImageLoader());
    }
    
    public int getDefensa()
    {
        return defensa;
    }
    
    public void setDefensa(int def)
    {
        if (def <= 0)
            throw new IllegalArgumentException(
                "La defensa de una armadura debe ser mayor que cero"
            );
        this.defensa = def;
    }

    public static Armadura random(ImageLoader imgLoader)
    {
        Armadura armor = armadurasDisp.get((int) (Math.random() * armadurasDisp.size()));
        if (armor.getImageLoader() == null) {
            armor.setImageLoader(imgLoader);
        }
        return armor.copy();
    }
    
    public Armadura copy()
    {
        return new Armadura(this);
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
    
    public static void loadXML(XStream xstream) 
        throws FileNotFoundException, IOException
    {
        FileReader reader = new FileReader("armaduras.xml");
        armadurasDisp = (ArrayList<Armadura>)xstream.fromXML(reader);
        reader.close();
    }
}
