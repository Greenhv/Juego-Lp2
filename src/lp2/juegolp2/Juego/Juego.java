package lp2.juegolp2.Juego;

import java.util.*;
import lp2.juegolp2.Mundo.*;
import lp2.juegolp2.Artefactos.*;
import lp2.juegolp2.Interfaz.*;
/**
 * Singleton, no debería haber más de una instancia de Juego
 * 
 * @author pmvb
 */
public class Juego {
    private static final Juego INSTANCE = new Juego();
    private static final String[] availableCommands = {
        "help",
        "interactuar",
        "mirar",
        "mover",
        "salir"
    };
    // Rango de niveles de enemigos de un laberinto
    private static final int enemyLevelRange = 5;
    
    private Avatar jugador;
    private GestorLaberinto gestorLaberinto;
    private Dibujador dibujador;
    private int currentLabIndex;
    private int numLaberintos;
    
    private Juego()
    {
        this.gestorLaberinto = new GestorLaberinto();
        this.dibujador = new Dibujador();
        this.currentLabIndex = 0;
    }
    
    public static Juego getInstance()
    {
        return Juego.INSTANCE;
    }
    
    // Introducción al juego      
    public void historia(){
        String nJugador = this.jugador.getNombre();        
        System.out.println("A través del tiempo y el espacio se abren puertas.");
        System.out.println("Mundo paralelos se crean todos los días con acciones pequeñas.");
        System.out.println("Hay mundos maravillosos con historias y leyendas nunca antes contadas");
        System.out.println("Sin embargo...");
        System.out.println("No todos los mundos son amigables.");
        System.out.print("Un día normal de su vida, ");
        System.out.print(nJugador);
        System.out.println(" es transportado hacia el fantástico mundo de Aether.");
        System.out.println("Aether está dominado por el demonio Azazel");
        System.out.println("Azazel planea unir los mundos y convertirse en el amo supremo");
        System.out.print(nJugador);
        System.out.println(" lo detendrá, no porque lo desee, sino porque es el único que puede hacerlo.");
        System.out.printf("Avanza, %s\n",nJugador);
        this.pauseScreen();
    }
    
    // Configura lo necesario para jugar
    public void init()
    {
        this.initMap();
        // Obten datos y crea jugador
        this.initPlayer();
        
    }

    public Result play()
    {
        Scanner scan = new Scanner(System.in);
        Laberinto laberintoActual = this.gestorLaberinto.get(this.currentLabIndex);
        while(true){
            this.dibujador.dibujarLaberinto(laberintoActual, this.jugador.getPosition());
            this.dibujador.dibujarInfoJugador(this.jugador);
            System.out.print("Ingrese su siguiente movimiento (Ingrese help para ver los comandos disponibles) : ");
            
            String[] cmd = this.getCommandFromString(scan.nextLine());
            if (!this.verifyCommand(cmd)) {
                this.dibujador.showError("No se ha ingresado un comando válido");
                this.showHelp();
            } else {
                switch (cmd[0]) {
                    case "help":
                        this.showHelp();
                        break;
                    case "interactuar":
                        this.interactuar(cmd[1], laberintoActual);                        
                        break;
                    case "mover":
                        Result res = this.move(cmd[1], laberintoActual);
                        // Si el juego termina
                        if (res != null)
                            return res;
                        break;
                    case "mirar":
                        this.playerFaceDirection(cmd[1]);
                        break;
                    case "salir":
                        return Result.QUIT;
                }
            }
        }
    }
    
    private String[] getCommandFromString(String line)
    {
        return line.split(" ");
    }
    
    private boolean verifyCommand(String[] cmd)
    {
        // Si no ha ingresado ningun comando
        if (cmd.length <= 0)
            return false;
        // Limpia la entrada
        cmd[0] = cmd[0].toLowerCase();
        // Si el comando no está disponible
        if (!Arrays.asList(availableCommands).contains(cmd[0].toLowerCase()))
            return false;
        // Si trata de moverse, pero la direccion no es valida
        if (cmd[0].equals("mover")
            || cmd[0].equals("mirar")
            || cmd[0].equals("interactuar")) {
            if (cmd.length < 2)
                return false;
            cmd[1] = cmd[1].toUpperCase();
            if (!Direction.contains(cmd[1]))
                return false;
        }
        return true;
    }
    
    public void showHelp()
    {
        System.out.println("Comandos disponibles: ");
        /**
         * Ayuda
         */
        System.out.println("help:\t\tMuestra este mensaje de ayuda");
        /**
         * Mover
         */
        System.out.println("mover <dir>:\t\tMueve al jugador en la direccion dir");
        System.out.println("\t\t\tDonde dir puede ser:");
        System.out.println("\t\t\t'UP': arriba");
        System.out.println("\t\t\t'DOWN': abajo");
        System.out.println("\t\t\t'RIGHT': derecha");
        System.out.println("\t\t\t'LEFT': izquierda");
        /**
         * Mirar
         */
        System.out.println("mirar <dir>:\t\tMira en la direccion dir");
        System.out.println("\t\t\tDonde dir puede tener los mismos valores que al mover el jugador");
        /**
         * Interactuar
         */
        System.out.println("interactuar <dir>:\tInteractua con la celda adyacente en la direccion dir");
        System.out.println("\t\t\tDonde dir puede tener los mismos valores que al mover el jugador");
        /**
         * Salir
         */
        System.out.println("salir:\t\t\tTermina el juego inmediatamente.");
        /**
         * Pause the screen
         */
        this.pauseScreen();
    }
    
    public Result result()
    {
        // Verifica si el jugador ha perdido o gano, o si sigue jugando
        /**
         * Si el jugador está en la posición siguiente del último laberinto, 
         * ha ganado
         */
        if (this.currentLabIndex == this.gestorLaberinto.size()-1
            &&
            this.jugador.getPosition().equals(this.gestorLaberinto.get(numLaberintos).getSiguiente())) {
            return Result.WIN; 
        }
        if (this.jugador.getCurrentHP() == 0) {
            return Result.LOSE;
        }
        return Result.WIN;
    }
    
    private void initPlayer()
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("Ingrese su nombre: ");
        String nombre = scan.nextLine();
        Laberinto currentLab = this.gestorLaberinto.get(this.currentLabIndex);
        Position avatarPos = new Position(currentLab.getAnterior());
        Arma armaInicial = new Arma(1, 5);
        this.jugador = new Avatar(nombre, avatarPos);
        this.jugador.setArma(armaInicial);
        this.gestorLaberinto.agregaPlayer(jugador);
    }

    private void initMap()
    {
        this.numLaberintos = (int) (Math.random() * 6) + 5;
        this.gestorLaberinto.crearLaberintos(numLaberintos, enemyLevelRange);
    }
    
    private Result move(String mov, Laberinto laberintoActual)
    {
        Result res = null;
        this.moverAvatar(mov, laberintoActual);
        if(this.jugador.getPosition().equals(laberintoActual.getSiguiente())){
            if(++this.currentLabIndex == this.gestorLaberinto.size()) {
                res = Result.WIN;
            } else {
                laberintoActual = this.gestorLaberinto.get(this.currentLabIndex);
                this.jugador.setPosition(laberintoActual.getAnterior());
            }
        } else if(this.jugador.getPosition().equals(laberintoActual.getAnterior())){
            if(this.currentLabIndex >= 1){
                this.currentLabIndex--;
                laberintoActual = this.gestorLaberinto.get(this.currentLabIndex);
                this.jugador.setPosition(laberintoActual.getSiguiente());
            }
        }
        this.moverEnemigos(laberintoActual);
        return res;
    }
    
    private void moverAvatar(String mov, Laberinto laberintoActual) 
    {
        Direction dir = Direction.valueOf(mov);
        // Si no se puede mover a la posición seleccionada, se envía un mensaje
        if (!laberintoActual.validPlayerPosition(this.jugador.getPosition().copy().move(dir))) {
            this.dibujador.showError("No se puede mover a esa posición");
            pauseScreen();
            return;
        }
        Position playerPos = this.jugador.getPosition();
        Celda currCell = laberintoActual.get(playerPos);
        if (playerPos.equals(laberintoActual.getAnterior())) {
            // Si está sobre la celda ANTERIOR antes de moverse, lo pinta de nuevo
            currCell.setContenido(Celda.Contenido.ANTERIOR.asChar());
        } else if (playerPos.equals(laberintoActual.getSiguiente())) {
            // Si está sobre la celda SIGUIENTE antes de moverse, lo pinta de nuevo
            currCell.setContenido(Celda.Contenido.SIGUIENTE.asChar());
        } else {
            currCell.setContenido(Celda.Contenido.LIBRE.asChar());
        }
        this.jugador.move(dir);
    }
    
    private void moverEnemigos(Laberinto lab)
    {
        lab.moverEnemigos();
    }
    
    private void interactuar(String mov, Laberinto laberintoActual)
    {
        Direction dir = Direction.valueOf(mov);
        Position pos = this.jugador.getPosition().copy().move(dir);
        // Si no se puede mover a la posición seleccionada, se envía un mensaje
        if (laberintoActual.get(pos).getContenido() == Celda.Contenido.PARED.asChar()) {
            this.dibujador.showError("No se puede interactuar con esa celda");
            this.pauseScreen();
            return;
        }
        this.interactuar(laberintoActual, pos);
        this.pauseScreen();
    }
    
    private void interactuar(Laberinto laberintoActual, Position pos)
    {
        System.out.println("Entra a la condicion");
        // Verifico si hay un artefacto
        Artefacto artefacto = laberintoActual.getArtefacto(pos);
        if (artefacto != null) {
            this.jugador.pickupItem(artefacto);
            laberintoActual.removeArtefacto(pos);
            return;
        }
        // Verifico si hay un enemigo en esa posicion
        Enemigo enemigo = laberintoActual.getEnemigo(pos);
        if (enemigo != null) {
            this.battle(this.jugador, enemigo);
            return;
        }
    }
    
    private void battle(Avatar jugador, Entidad enemigo)
    {
        System.out.println("Batalla");
        this.pauseScreen();
    }
    
    private void playerFaceDirection(String mov)
    {
        Direction dir = Direction.valueOf(mov);
        this.jugador.setFacingDir(dir);
    }
    
    private void pauseScreen()
    {
        System.out.println("Presione Enter para continuar...");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }
    
    public enum Result
    {
        QUIT,
        LOSE,
        WIN,
        PLAYING
    }
}
