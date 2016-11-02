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
    private static String[] allyTypes = {
        "redguy", 
        "blondegirl",
        "blueguy",
        "redgirl",
        "brownbear",
        "blackbear",
        "brunettegirl",
        "silverguy",
    };
    
    private ArrayList<Consejo> consejos;
    
    public Aliado(String nombre, Position pos, ImageLoader imgLoader)
    {
        super(nombre, pos, imgLoader);
        consejos = new ArrayList<>();
        this.sprite.setImage(allyTypes[(int) (Math.random()*allyTypes.length)] + "StopDown");
    }
    
    public Aliado(String nombre, ArrayList<Consejo> consejos, ImageLoader imgLoader)
    {
        super(nombre, imgLoader);
        this.consejos = consejos;
        Collections.sort(consejos);
        this.sprite.setImage(allyTypes[(int) (Math.random()*allyTypes.length)] + "StopDown");
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
    
    public static Aliado readAliadoFromString(String line, ImageLoader imgLoader)
    {
        String strAliado = line.split("ALIADO:")[1];
        String[] datosAliado = strAliado.split("/");
        // Obten nombre aliado
        String nomAliado = datosAliado[0];
               
        // Crea lista de consejos
        ArrayList<Consejo> listaConsejos = new ArrayList<>();
                
        // Obten consejos aliado
        String strConsejos = datosAliado[1];
        String[] datosConsejos = strConsejos.split(":")[1].split("@");
        int numConsejos = Integer.parseInt(datosConsejos[0]);
        for (int j = 1; j <= numConsejos; ++j) {
            String[] consejo = datosConsejos[j].split("\\.");
            String strConsejo = consejo[0];
            int nivelConsejo = Integer.parseInt(consejo[1]);
            Consejo objConsejo = new Consejo(strConsejo, nivelConsejo);
            listaConsejos.add(objConsejo);
        }

        return new Aliado(nomAliado, listaConsejos, imgLoader);
    }
}
