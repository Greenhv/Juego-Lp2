package lp2.juegolp2.Facilidades;

/**
 *
 * @author pmvb
 */
public class Consejo
{
    private String consejo;
    private int nivel;
    
    public Consejo(String consejo, int nivel)
    {
        this.setConsejo(consejo);
        this.setNivel(nivel);
    }
    
    public void setNivel(int nivel)
    {
        if (nivel <= 0)
            throw new IllegalArgumentException(
                "El nivel de un consejo no puede ser menor que cero");
        this.nivel = nivel;
    }
    
    public void setConsejo(String consejo)
    {
        this.consejo = consejo;
    }
    
    public String getConsejo()
    {
        return this.consejo;
    }
    
    public int getNivel()
    {
        return this.nivel;
    }
    
    @Override
    public String toString()
    {
        return this.getConsejo();
    }
}