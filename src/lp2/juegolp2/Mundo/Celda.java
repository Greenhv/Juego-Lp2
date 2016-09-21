package lp2.juegolp2.Mundo;

/**
 *
 * @author pmvb
 */
public class Celda
{
    private int fila;
    private int columna;
    private Tipo tipo;
    private Contenido contenido;

    public Celda()
    {
        this.tipo = Tipo.PARED;
        this.contenido = Contenido.PARED;
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
        this.contenido = contenido;
    }
    
    public void setTipo(Tipo tipo)
    {
        this.tipo = tipo;
    }
    
    public Tipo getTipo()
    {
        return this.tipo;
    }

    public void setContenido(Contenido contenido)
    {
        this.contenido = contenido;
    }
    
    public Contenido getContenido()
    {
        return this.contenido;
    }
    
    public void draw()
    {
        System.out.print(this.contenido.asChar());
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
        this.setContenido(Contenido.LIBRE);
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

    /**
     * Contenidos posibles de una celda
     *
     */
    public enum Contenido {
        PARED('#'),
        LIBRE(' '), 
        ARTEFACTO('A'), 
        ENEMIGO('E'), 
        SIGUIENTE('+'),
        ANTERIOR('-'), 
        JUGADOR('*');

        public char asChar() {
            return asChar;
        }
        private final char asChar;

        private Contenido(char asChar) {
            this.asChar = asChar;
        }
    }
    
    public enum Tipo
    {
        PARED,
        ADENTRO,
        AFUERA,
        ANTERIOR,
        SIGUIENTE,
    }
}
