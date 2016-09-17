package lp2.Mundo;

import lp2.Artefactos.*;
/**
 *
 * @author pmvb
 */
public class Avatar extends Entidad
{
    private int nivel;
    private Arma arma;
    private Armadura armadura;
    private Saco saco;
    
    public Avatar(String nombre, Position pos)
    {
        super(nombre, pos);
        this.nivel = 1;
        this.saco = new Saco();
        setMaxHP(nivel*10);
        super.initHP();
    }
    
    public void pickupItem(Artefacto item)
    {
        if (item.type() == Artefacto.Tipo.ARMA)
            this.setArma((Arma) item);
        else if (item.type() == Artefacto.Tipo.ARMADURA)
            this.armadura = (Armadura) item;
        else
            this.getSaco().addItem(item);
    }
    
    @Override
    public int getNivel()
    {
        return this.nivel;
    }
    
    public Arma getArma()
    {
        return this.arma;
    }
    
    public Armadura getArmadura()
    {
        return this.armadura;
    }
       
    public Saco getSaco()
    {
        return this.saco;
    }
    
    @Override
    public String toString()
    {
        String str = "Nombre: " + getNombre() + " - HP: " + getCurrentHP() + "/" + getMaxHP() + "\n";
        str += "Mirando hacia: " + Direction.direcciones.get(this.getFacingDir()) + "\n";
        str += "Nivel: " + getNivel() + "\n";
        str += "Arma: " + ((getArma() == null) ? "Ninguna" : getArma().toString()) + "\n";
        str += "Armadura: " + ((getArmadura() == null) ? "Ninguna" : getArmadura().toString()) + "\n";
        str += "Saco: \n";
        str += ((getSaco().empty()) ? "Vacío" : getSaco().toString());
        
        return str;
    }

    /**
     * @param arma the arma to set
     */
    public void setArma(Arma arma) {
        this.arma = arma;
    }
}

