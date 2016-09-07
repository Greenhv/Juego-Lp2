package com.mycompany.juegolp2;

/**
 *
 * @author pmvb
 */
public class Avatar extends Entidad
{
    private int nivel;
    private Arma arma;
    private Armadura armadura;
    
    public Avatar(String nombre, Position pos)
    {
        super(nombre, pos);
        this.nivel = 1;
        setMaxHP(nivel*10);
        super.initHP();
    }
    
    public void pickupItem(Artefacto item)
    {
        if (item.type() == Artefacto.Tipo.ARMA)
            this.arma = (Arma) item;
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
}

