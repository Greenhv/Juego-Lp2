package lp2.juegolp2.Facilidades;

/**
 *
 * @author alulab
 */
public class Consejo implements Comparable<Consejo>
{
    private String consejo;
    int nivel;

    public Consejo(String consejo, int nivel)
    {
        this.setConsejo(consejo);
        this.setNivel(nivel);
    }
    
    public int getNivel()
    {
        return this.nivel;
    }
    
    public String getConsejo()
    {
        return this.consejo;
    }
    
    public void setNivel(int nivel)
    {
        if (nivel <= 0)
            throw new IllegalArgumentException(
                "El nivel de un consejo no puede ser menor que cero"
            );
        this.nivel = nivel;
    }
    
    public void setConsejo(String consejo)
    {
        this.consejo = consejo;
    }
    
    @Override
    public int compareTo(Consejo cons) {
        return this.getNivel() - cons.getNivel();
    }
    
    @Override
    public String toString()
    {
        return this.getConsejo();
    }
}
