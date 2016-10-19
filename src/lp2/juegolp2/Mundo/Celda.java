package lp2.juegolp2.Mundo;

import java.util.*;

/**
 *
 * @author pmvb
 */
public class Celda
{
    private int fila;
    private int columna;
    private Tipo tipo;
    private TreeSet<Contenido> contenido;

    public Celda()
    {
        this.tipo = Tipo.PARED;
        this.contenido = new TreeSet<>(new ComparadorContenidoCelda());
        this.addContenido(Celda.Contenido.PARED);
    }
    
    public Celda(int fila, int columna)
    {
        this();
        this.fila = fila;
        this.columna = columna;
    }

    public Celda(Tipo tipo, Contenido contenido)
    {
        this.tipo = tipo;
        this.contenido.add(contenido);
    }
    
    public void setTipo(Tipo tipo)
    {
        this.tipo = tipo;
    }
    
    public Tipo getTipo()
    {
        return this.tipo;
    }

    public boolean addContenido(Contenido contenido)
    {
        if (contenido == Contenido.PARED) {
            this.contenido.clear();
        } else {
            this.contenido.remove(Contenido.PARED);
        }
        return this.contenido.add(contenido);
    }
    
    public TreeSet<Contenido> getContenido()
    {
        return this.contenido;
    }
    
    public boolean removeContenido(Contenido contenido)
    {
        return this.contenido.remove(contenido);
    }
    
    /**
     * Este método dibujará el contenido de una celda.
     * Cuando implementemos los gráficos, deberá dibujar solo la celda,
     * su contenido de dibujará por separado
     */
    public void draw()
    {
        System.out.print(this.contenido.first().asChar());
    }

    /**
     * @return the fila
     */
    public int getFila() {
        return fila;
    }

    /**
     * @param fila the fila to set
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna)
    {
        this.columna = columna;
    }
    
    public void markAsInside()
    {
        this.setTipo(Tipo.ADENTRO);
        this.addContenido(Contenido.LIBRE);
    }
    
    public void markAsOutside()
    {
        this.setTipo(Tipo.AFUERA);
    }
    
    public boolean isFree()
    {
        return this.getTipo() == Tipo.AFUERA;
    }
    
    public boolean esPared()
    {
        return this.getTipo() == Tipo.PARED;
    }
    
    public boolean esLibre()
    {
        return this.contenido.first() == Celda.Contenido.LIBRE;
    }

    public enum Tipo
    {
        PARED,
        ADENTRO,
        AFUERA,
        ANTERIOR,
        SIGUIENTE,
    }
    
    /**
     * Contenidos posibles de una celda
     *
     */
    public enum Contenido {
        PARED('#', 0),
        LIBRE(' ', 1), 
        SIGUIENTE('+', 2),
        ANTERIOR('-', 2), 
        ARTEFACTO('A', 3), 
        ENEMIGO('E', 4), 
        ALIADO('F', 4),
        JUGADOR('O', 5);

        private final char asChar;
        private final int priority;
        
        private Contenido(char asChar, int priority) {
            this.asChar = asChar;
            this.priority = priority;
        }
        
        public char asChar() {
            return asChar;
        }
        
        public int priority()
        {
            return priority;
        }
        
        @Override
        public String toString()
        {
            return "Contenido: '" + asChar + "' - Prioridad: " + priority;
        }
    }
}

class ComparadorContenidoCelda implements Comparator<Celda.Contenido>
{
    public int compare(Celda.Contenido cont1, Celda.Contenido cont2)
    {
        int res = -(cont1.priority() - cont2.priority());
        return (res != 0) ? res : cont1.compareTo(cont2);
    }
}