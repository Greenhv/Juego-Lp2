package lp2.juegolp2.Mundo;

import lp2.juegolp2.Artefactos.*;
import lp2.juegolp2.Facilidades.*;
import java.util.*;
import java.util.stream.IntStream;

/**
 *
 * @author pmvb
 */
public class GestorLaberinto
{
    private List<Laberinto> laberintos;
    private ImageLoader imgLoader;
    
    public GestorLaberinto(ImageLoader imgLoader)
    {
        this.laberintos = new ArrayList<Laberinto>();
        this.imgLoader = imgLoader;
    }
    
    public Laberinto get(int n)
    {
        return this.laberintos.get(n);
    }
    
    public Laberinto Crear(int M, int N, int[] niveles)
    {
        // pct_enemigo: Minimo 5% - Maximo 20%
        double pct = ((Math.random() * 100) % 11 + 5) / 100;
        Laberinto lab = new Laberinto(2*M+1, 2*N+1, pct, niveles, imgLoader);
        configLaberinto(lab);
        return lab;
    }
    
    public void crearLaberintos(int numLaberintos, int enemy_range)
    {
        for (int i = 0; i < numLaberintos; ++i) {
            int M = (int) ((Math.random()*6) + 5);
            int N = (int) ((Math.random()*6) + 5);
            int[] niveles = IntStream.rangeClosed(i+1, (i+1)*enemy_range).toArray();
            this.laberintos.add(this.Crear(M, N, niveles));
        }
    }
    /**
     * 
     * Agrega lo necesario para completar el laberinto (celdas anterior, siguiente,
     * artefactos y enemigos).
     * Crea lista de celdas libres y luego elige donde poner lo necesario
     * aleatoriamente.
     * 
     * @param lab Laberinto
     */
    private void configLaberinto(Laberinto lab)
    {
        // Crea lista de celdas libres
        ArrayList<Position> libres = lab.getCeldasLibres();
        
        // Agrega anterior
        int index = (int) (Math.random()*libres.size());
        lab.setAnterior(libres.get(index));
        libres.remove(index);
        
        // Agrega siguiente
        index = (int) (Math.random()*100) % libres.size();
        lab.setSiguiente(libres.get(index));
        libres.remove(index);
        
        // Agrega enemigos y artefactos
        for (int i = 0; i < libres.size(); ++i) {
            if (Math.random() <= lab.getPctEnemigo()) {
                lab.addEnemigo(libres.get(i));
            } else if (Math.random() <= 0.1) {
                Artefacto artefacto = Artefacto.random(this.imgLoader);
                lab.addArtefacto(artefacto, libres.get(i));
            }
        }
    }
    
    public int size()
    {
        return laberintos.size();
    }
    
    public void addAliado(Aliado aliado)
    {
        int labIndex = (int) (Math.random() * this.size());
        this.get(labIndex).addAliado(aliado);
    }
}
