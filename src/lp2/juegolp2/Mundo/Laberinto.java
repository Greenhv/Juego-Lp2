package lp2.juegolp2.Mundo;

import lp2.juegolp2.Artefactos.*;
import java.util.*;

/**
 *
 * @author pmvb
 */
public class Laberinto
{
    /*
     * Ancho de un laberinto
     */
    private int ancho;

    /*
     * Alto de un laberinto
     */
    private int alto;

    /*
     * El laberinto mismo
     */
    public Celda[][] laberinto;

    /*
     * La probabilidad de que un enemigo aparezca en una celda
     */
    private double pct_enemigo;

    /*
     * Niveles posibles de enmigos en el laberinto
     */
    private int[] niveles;
    
    /**
     * Posicion de la celda para acceder al siguiente laberinto
     * 
     */
    private Position anterior;
    
    /**
     * Posicion de la celda para acceder al siguiente laberinto
     * 
     */
    private Position siguiente;
    
    /**
     * Artefactos en el laberinto
     */
    private HashMap<Position, Artefacto> artefactos;
    
    /**
     * Enemigos en el laberinto
     */
    private ArrayList<Enemigo> enemigos;
    
    public Laberinto(int ancho, int alto, double pct_enemigo, int[] niveles)
    {
        this.setPctEnemigo(pct_enemigo);
        this.setAncho(ancho);
        this.setAlto(alto);
        this.artefactos = new HashMap<>();
        this.enemigos = new ArrayList<>();
        laberinto = new Celda[alto][ancho];
        this.niveles = Arrays.copyOfRange(niveles, 0, niveles.length);
        
        for (int i = 0; i < alto; i++){
          for (int j = 0; j < ancho; j++){
                laberinto[i][j] = new Celda(i,j);
            }
        }
        this.init();
        generar_ruta();
    }
    
    /* Generador de Camino para el Laberinto */
    private void init()
    {
        boolean fila_flag = true;
        boolean columna_flag = false;
        for (int i = 1; i < alto-1; i++){
            for (int j = 1; j < ancho-1; j++){
                if(fila_flag){
                    laberinto[i][j].markAsOutside();
                }
                fila_flag = !fila_flag;
            }
            columna_flag = !columna_flag;
            fila_flag = true;
        }
    }
    
    private void generar_ruta()
    {
        Stack<Celda> cells_stack = new Stack<>();
        Celda starting_cell = laberinto[1][1]; //just a test starting point
        starting_cell.markAsInside();
        cells_stack.push(starting_cell);
        
        while(!cells_stack.empty()) {
            Celda current_cell = cells_stack.peek();
            
            HashMap<Integer, Celda> close_cells = new HashMap<>();
            int fila = current_cell.getFila();
            int columna = current_cell.getColumna();
            if(labyrinth_free_space(fila, columna-2)){
                close_cells.put(0, laberinto[fila][columna-2]);
            }
            if(labyrinth_free_space(fila-2, columna)){
                close_cells.put(1, laberinto[fila-2][columna]);
            }
            if(labyrinth_free_space(fila, columna+2)){
                close_cells.put(2, laberinto[fila][columna+2]);
            }
            if(labyrinth_free_space(fila+2, columna)){ 
                close_cells.put(3, laberinto[fila+2][columna]);
            }
            
            //if current_cell has adjacent cells that are ADENTRO
            if(nearby_empty_cell(close_cells)) {
                //pick a random_cell
                Celda random_cell = get_random_cell(close_cells);
                //make a path from current to random cell
                make_path(current_cell, random_cell);
                random_cell.markAsInside();
                cells_stack.push(random_cell);
            }
            else
                cells_stack.pop();
        }
    }
    
    
    private boolean labyrinth_free_space(int fila, int columna)
    {
        if( (fila < 0) || (columna < 0) || (fila >= alto) || (columna >= ancho)){
            return false;
        }
        return laberinto[fila][columna].isFree();
    }
    
    private boolean nearby_empty_cell(HashMap<Integer, Celda> close_cells)
    {
        return close_cells.size() > 0;
    }
    
    private Celda get_random_cell(HashMap<Integer, Celda> close_cells)
    {
        while(true){
            int random_number = (int) (Math.random() * 10) % 4;
            if(close_cells.get(random_number) != null)
                return close_cells.get(random_number);
            //return null;
        }
    }
    
    private void make_path(Celda start_cell, Celda end_cell)
    {
        if(start_cell.getFila() == end_cell.getFila()){
            if(start_cell.getColumna() < end_cell.getColumna())
                laberinto[start_cell.getFila()][start_cell.getColumna()+1].markAsInside();
            else
                laberinto[start_cell.getFila()][start_cell.getColumna()-1].markAsInside();
        }
        if(start_cell.getColumna() == end_cell.getColumna()){
            if(start_cell.getFila() < end_cell.getFila())
                laberinto[start_cell.getFila()+1][start_cell.getColumna()].markAsInside();
            else
                laberinto[start_cell.getFila()-1][start_cell.getColumna()].markAsInside();
        }
    }    

    /* Fin del generador de laberinto */
    
    public void draw()
    {
        for (int i = 0; i < this.getAlto(); ++i) {
            for (int j = 0; j < this.getAncho(); ++j) {
                laberinto[i][j].draw();
            }
            System.out.println();
        }
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        if (ancho <= 0)
            throw new IllegalArgumentException("Ancho debe ser mayor que cero");
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        if (alto <= 0)
            throw new IllegalArgumentException("Alto debe ser mayor que cero");
        this.alto = alto;
    }
    
    public Celda get(Position pos)
    {
        int x = pos.getX();
        int y = pos.getY();
        // Por como se manejan los arreglos, 'x' corresponde al alto y 'y' al ancho
        if (!this.inBounds(x, y))
            throw new IndexOutOfBoundsException(
                "Coordenadas fuera de rango en Laberinto.get(Position)"
                + "pos: " + pos.toString());
        return laberinto[x][y];
    }
    
    public Celda get(int x, int y)
    {
        // Por como se manejan los arreglos, 'x' corresponde al alto y 'y' al ancho
        if (!this.inBounds(x, y))
            throw new IndexOutOfBoundsException(
                "Coordenadas fuera de rango en Laberinto.get(int, int): "
                + "x: " + Integer.toString(x) + ", y: " + Integer.toString(y));
        return laberinto[x][y];
    }

    /**
     * @return the anterior
     */
    public Position getAnterior() 
    {
        return anterior.copy();
    }

    /**
     * @param anterior the anterior to set
     */
    public void setAnterior(Position anterior) 
    {
        this.anterior = anterior;
        this.get(anterior).setContenido(Celda.Contenido.ANTERIOR);
    }

    /**
     * @return the siguiente
     */
    public Position getSiguiente() 
    {
        return siguiente.copy();
    }

    /**
     * @param siguiente the siguiente to set
     */
    public void setSiguiente(Position sig)
    {
        this.siguiente = sig;
        this.get(sig).setContenido(Celda.Contenido.SIGUIENTE);
    }
    
    public void actualizarJugador(int X, int Y)
    {
        this.get(X, Y).setContenido(Celda.Contenido.JUGADOR);
    }
    
    public boolean inBounds(int x, int y)
    {
        return !(x < 0 || y < 0 || x >= getAlto() || y >= getAncho());
    }
    
    public boolean inBounds(Position pos)
    {
        int x = pos.getX();
        int y = pos.getY();
        return !(x < 0 || y < 0 || x >= getAlto() || y >= getAncho());
    }
    
    public boolean validPlayerPosition(int x, int y)
    {
        if (x < 1 || y < 1 || x >= getAlto()-1 || y >= getAncho()-1){
            return false;
        }
        return (this.get(x, y).getContenido() == Celda.Contenido.LIBRE.asChar() ||
                this.get(x, y).getContenido() == Celda.Contenido.ANTERIOR.asChar() ||
                this.get(x, y).getContenido() == Celda.Contenido.SIGUIENTE.asChar());
                //this.get(x, y).getContenido() == Contenido.ENEMIGO.asChar() Para que pueda pasar encima de el
    }
    
    public boolean validPlayerPosition(Position pos)
    {
        int x = pos.getX();
        int y = pos.getY();
        return validPlayerPosition(x, y);
    }
    
    public void addEnemigo(Position pos)
    {   
        int nivel = this.niveles[(int) (Math.random() % niveles.length)];
        Enemigo enemigo = Enemigo.random(nivel);
        enemigo.setPosition(pos);
        this.enemigos.add(enemigo);
        this.get(pos).setContenido(Celda.Contenido.ENEMIGO);
    }
    
    public double getPctEnemigo()
    {
        return this.pct_enemigo;
    }
    
    private void setPctEnemigo(double pct)
    {
        if (pct < 0 || pct > 1)
            throw new IllegalArgumentException(
                "La probabilidad de aparicion de enemigos debe estar entre 0 y 1 (inclusivo)");
        this.pct_enemigo = pct;
    }
    
    public void moverEnemigos()
    {
        Direction[] dirs = Direction.values();
        for (int i = 0; i < this.enemigos.size(); ++i) {
            int index = (int) (Math.random() * 4);
            Direction dir = dirs[index];
            // Si es una posición válida, mueve el enemigo y termina
            if (validPlayerPosition(enemigos.get(i).getPosition().copy().move(dir))) {
                Enemigo enemy = this.enemigos.get(i);
                this.get(enemy.getPosition()).setContenido(Celda.Contenido.LIBRE);
                enemy.move(dir);
                this.get(enemy.getPosition()).setContenido(Celda.Contenido.ENEMIGO);
            }
        }
    }
    
    public Artefacto getArtefacto(Position pos)
    {
        return this.artefactos.get(pos);
    }
    
    public void removeArtefacto(Position pos)
    {
        this.artefactos.remove(pos);
    }
    
    public Enemigo getEnemigo(Position pos)
    {
        for (int i = 0; i < enemigos.size(); ++i)
            if (enemigos.get(i).getPosition().equals(pos))
                return enemigos.get(i);
        return null;
    }
    
    public void removeEnemigo(Position pos)
    {
        for (int i = 0; i < enemigos.size(); ++i)
            if (enemigos.get(i).getPosition().equals(pos))
                enemigos.remove(i);
    }
}