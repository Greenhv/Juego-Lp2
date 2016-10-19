package lp2.juegolp2.Entidades;

import lp2.juegolp2.Artefactos.*;
import lp2.juegolp2.Mundo.Celda;
import lp2.juegolp2.Mundo.Position;
/**
 *
 * @author pmvb
 */
public class Avatar extends Entidad
{
    private int nivel;
    private Saco saco;
    
    public Avatar(String nombre)
    {
        super(nombre);
        init();
    }
    
    public Avatar(String nombre, Position pos)
    {
        super(nombre, pos);
        init();
    }
    
    private void init()
    {
        this.nivel = 5;
        this.saco = new Saco();
        setMaxHP(nivel*10);
        this.initHP();
    }
    
    public void pickupItem(Artefacto item)
    {
        if (item == null)
            return;
        if (item.type() == Artefacto.Tipo.ARMA && this.getArma() == null) {
            this.setArma((Arma) item);
        } else if (item.type() == Artefacto.Tipo.ARMADURA && this.getArmadura() == null) {
            this.setArmadura((Armadura) item);
        } else {
            this.getSaco().addItem(item);
        }
    }
    
    @Override
    public int getNivel()
    {
        return this.nivel;
    }
       
    public Saco getSaco()
    {
        return this.saco;
    }
    
    @Override
    public String toString()
    {
        String str = "Nombre: " + getNombre() + " - HP: " + getCurrentHP() + "/" + getMaxHP() + "\n";
        str += "Nivel: " + getNivel() + "\n";
        str += "Arma: " + ((getArma() == null) ? "Ninguna" : getArma().toString()) + "\n";
        str += "Armadura: " + ((getArmadura() == null) ? "Ninguna" : getArmadura().toString()) + "\n";
        str += "Saco: \n";
        str += ((getSaco().empty()) ? "Vacío" : getSaco().toString());
        
        return str;
    }
    
    public int getNumItems()
    {
        return this.getSaco().size();
    }
    
    public Artefacto getArtefacto(int index)
    {
        return this.getSaco().getItem(index);
    }
    
    public Artefacto dropItem(int index)
    {
        return this.getSaco().removeItem(index);
    }
    
    public void levelUp()
    {
        ++this.nivel;
        this.setMaxHP(nivel*10);
        this.initHP();
    }
    
    @Override
    public Celda.Contenido getContenidoCelda()
    {
        return Celda.Contenido.JUGADOR;
    }
}

