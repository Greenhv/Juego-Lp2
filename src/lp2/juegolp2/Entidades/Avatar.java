package lp2.juegolp2.Entidades;

import lp2.juegolp2.Artefactos.*;
import lp2.juegolp2.Facilidades.ImageLoader;
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
    
    public Avatar(String nombre, ImageLoader imgLoader)
    {
        super(nombre, imgLoader);
        init();
    }
    
    public Avatar(String nombre, Position pos, ImageLoader imgLoader)
    {
        super(nombre, pos, imgLoader);
        init();
    }
    
    private void init()
    {
        this.nivel = 5;
        this.saco = new Saco();
        setMaxHP(nivel*10);
        this.initHP();
        this.sprite.setImage("brownbearStopDown");
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
    
    public void useItem(int index)
    {
        Artefacto artefacto = this.getArtefacto(index);
        /**
         * Usa artefacto
         * 
         * Si es un arma o armadura, lo cambia por los que tiene actualmente
         * Si es una pocion, la utiliza
         */
        switch (artefacto.type()) {
            case ARMA:
                Arma arma = (Arma) artefacto;
                this.pickupItem(this.getArma());
                this.setArma(arma);
                break;
            case ARMADURA:
                Armadura armadura = (Armadura) artefacto;
                this.pickupItem(this.getArmadura());
                this.setArmadura(armadura);
                break;
            case POCION:
                PocionCuracion pocion = (PocionCuracion) artefacto;
                this.heal(pocion);
                break;
        }
        this.dropItem(index);
    }
}

