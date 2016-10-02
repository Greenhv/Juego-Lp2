package lp2.juegolp2.Juego;

import java.util.*;
import lp2.juegolp2.Mundo.*;
import lp2.juegolp2.Artefactos.*;
import lp2.juegolp2.Interfaz.*;
import lp2.juegolp2.Facilidades.*;

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
        "mover",
        "usar",
        "salir"
    };
    private static final String[] battleCommands = {
        "help",
        "atacar",
        "huir",
        "usar"
    };
    // Rango de niveles de enemigos de un laberinto
    private static final int enemyLevelRange = 5;
    
    private Avatar jugador;
    private Aliado aliado;
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
        System.out.print("Un día normal de su vida, " + nJugador);
        System.out.println(" es transportado hacia el fantástico mundo de Aether.");
        System.out.println("Aether está dominado por el demonio Azazel");
        System.out.println("Azazel planea unir los mundos y convertirse en el amo supremo");
        System.out.println(nJugador + " lo detendrá, no porque lo desee, sino porque es el único que puede hacerlo.");
        System.out.println("Avanza, " + nJugador);
        this.pauseScreen();
    }
    
    // Configura lo necesario para jugar
    public void init()
    {
        this.initMap();
        // Obten datos y crea jugador
        this.initPlayer();
        this.initAliado();
    }

    public Result play()
    {
        Scanner scan = new Scanner(System.in);
        Laberinto laberintoActual = this.gestorLaberinto.get(this.currentLabIndex);
        while(true){
            this.dibujador.dibujarLaberinto(laberintoActual, this.jugador.getPosition());
            this.dibujador.dibujarInfoJugador(this.jugador);
            System.out.println("ALIADO:");
            this.dibujador.dibujarInfoJugador(this.aliado);
            System.out.print("Ingrese su siguiente movimiento (Ingrese help para ver los comandos disponibles) : ");
            
            String[] cmd = this.getCommandFromString(scan.nextLine());
            Result result = Result.PLAYING;
            if (!this.verifyCommand(cmd)) {
                this.dibujador.showError("No se ha ingresado un comando válido");
                this.showHelp();
            } else {
                switch (cmd[0]) {
                    case "help":
                        this.showHelp();
                        break;
                    case "interactuar":
                        result = this.interactuar(cmd[1], laberintoActual);
                        break;
                    case "mover":
                        result = this.move(cmd[1], laberintoActual);
                        break;
                    case "usar":
                        this.useItem();
                        break;
                    case "salir":
                        result = Result.QUIT;
                        break;
                }
            }
            if (result != Result.PLAYING)
                return result;
        }
    }
    
    private String[] getCommandFromString(String line)
    {
        return line.trim().split(" ");
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
        // Comandos que requieren argumento direccion
        if (cmd[0].equals("mover")
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
         * Interactuar
         */
        System.out.println("interactuar <dir>:\tInteractua con la celda adyacente en la direccion dir");
        System.out.println("\t\t\tDonde dir puede tener los mismos valores que al mover el jugador");
        /**
         * Usar
         */
        System.out.println("usar:\t\t\tUsa un artefacto de tu saco");
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
        Arma armaIni = Arma.armasDisp.get(0);
        Armadura armaduraIni = Armadura.armadurasDisp.get(0);
        
        this.jugador = new Avatar(nombre, avatarPos);
        this.jugador.setArma(armaIni);
        this.jugador.setArmadura(armaduraIni);
        this.gestorLaberinto.agregaPlayer(jugador);
    }
    
    /**
     * Modificación: Función que inicializa el aliado
     */
    private void initAliado()
    {
        /**
         * Encuentra una posición inicial para el aliado
         */
        Position posAliado = new Position(this.jugador.getPosition());
        Laberinto labActual = this.gestorLaberinto.get(currentLabIndex);
        for (Direction dir : Direction.values()) {
            Position newPos = posAliado.copy().move(dir);
            if (labActual.validPlayerPosition(newPos)) {
                posAliado.move(dir);
                break;
            }
        }
        aliado = new Aliado("ALIADO", posAliado);
        this.gestorLaberinto.get(currentLabIndex).get(posAliado).setContenido(Celda.Contenido.ALIADO);
        /**
         * Llena el saco del aliado
         */
        int numArt = (int) (Math.random() * 6) + 5;
        for (int i = 0; i < numArt; ++i) {
            this.aliado.pickupItem(Artefacto.random());
        }
    }
    
    private void initMap()
    {
        this.numLaberintos = (int) (Math.random() * 6) + 5;
        this.gestorLaberinto.crearLaberintos(numLaberintos, enemyLevelRange);
    }
    
    private Result move(String mov, Laberinto laberintoActual)
    {
        Result res = Result.PLAYING;
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
        laberintoActual.moverEntidad(aliado);
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
            currCell.setContenido(Celda.Contenido.ANTERIOR);
        } else if (playerPos.equals(laberintoActual.getSiguiente())) {
            // Si está sobre la celda SIGUIENTE antes de moverse, lo pinta de nuevo
            currCell.setContenido(Celda.Contenido.SIGUIENTE);
        } else {
            currCell.setContenido(Celda.Contenido.LIBRE);
        }
        this.jugador.move(dir);
        laberintoActual.actualizarJugador(this.jugador.getPosition());
    }
    
    private void moverEnemigos(Laberinto lab)
    {
        lab.moverEnemigos();
    }
    
    private Result interactuar(String mov, Laberinto laberintoActual)
    {
        Direction dir = Direction.valueOf(mov);
        Position pos = this.jugador.getPosition().copy().move(dir);
        // Si no se puede interactuar con la posición seleccionada, se envía un mensaje
        if (laberintoActual.get(pos).getContenido() == Celda.Contenido.PARED) {
            this.dibujador.showError("No se puede interactuar con esa celda");
            this.pauseScreen();
            return Result.PLAYING;
        }
        Result res = this.interactuar(laberintoActual, pos);
        this.pauseScreen();
        return res;
    }
    
    private Result interactuar(Laberinto laberintoActual, Position pos)
    {
        // Verifico si hay un artefacto
        Artefacto artefacto = laberintoActual.getArtefacto(pos);
        if (artefacto != null) {
            this.jugador.pickupItem(artefacto);
            laberintoActual.removeArtefacto(pos);
            this.dibujador.showMessage("Has recogido un artefacto.");
            return Result.PLAYING;
        }
        // Verifico si hay un enemigo en esa posicion
        Enemigo enemigo = laberintoActual.getEnemigo(pos);
        if (enemigo != null) {
            Result res = this.battle(this.jugador, enemigo);
            if (res == Result.PLAYING && enemigo.getCurrentHP() <= 0) {
                laberintoActual.removeEnemigo(enemigo.getPosition());
            }
            return res;
        }
        if (aliado.getPosition().equals(pos)) {
            this.dibujador.showMessage("Consejo de tu aliado: " + aliado.getConsejo());
        }
        return Result.PLAYING;
    }
    
    private Result battle(Avatar jugador, Entidad enemigo)
    {   
        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.print("Heroe: " + jugador.getNombre());
            System.out.print(" - Vida Actual: " + jugador.getCurrentHP());
            System.out.print(" \t\t vs \t\tEnemigo: " + enemigo.getNombre());
            System.out.println(" - Vida Actual: " + enemigo.getCurrentHP());
            System.out.println("Acciones disponibles: *help *atacar *huir *usar");
            System.out.print("Accion a tomar: ");
            String[] cmd = getCommandFromString(scan.nextLine());
            if (!this.verifyBattleCommand(cmd)) {
                this.dibujador.showError("No se ha ingresado un comando válido");
                this.showBattleHelp();
            }
            boolean attacked = false;
            // Accion del Avatar
            switch(cmd[0]) {
                case "help":
                    this.showBattleHelp();
                    break;
                case "atacar":
                    enemigo.damage(jugador.getArma().damage());
                    attacked = true;
                    break;
                case "huir":
                    return Result.PLAYING;
                case "usar":
                    // Mostrar el inventario, luego pedir indice.
                    this.useItem();
                    break;
            }
            // El enemigo murio luego de la accion del jugador ?
            if(enemigo.getCurrentHP() == 0) {
                System.out.println("El " + enemigo.getNombre() + " a sido derrotado!");
                return Result.PLAYING;
            }
            
            // Accion del Enemigo atacar o curarse ( por ahora solo atacara )
            if (attacked)
                jugador.damage(enemigo.getArma().damage());
            
            // El jugador murio luego de la accion del Enemigo ?
            if(jugador.getCurrentHP() == 0)
                return Result.LOSE;
        }
    }
    
    public void showBattleHelp()
    {
        System.out.println("Comandos de batalla disponibles: ");
        /**
         * Ayuda
         */
        System.out.println("help:\t\tMuestra este mensaje de ayuda");
        /**
         * Atacar
         */
        System.out.println("atacar:\t\tAtacar al enemigo con tu arma equipada");
        /**
         * Usar
         */
        System.out.println("usar:\t\tUsar un artefacto de tu inventorio");
        /**
         * Huir
         */
        System.out.println("huir:\t\tTermina la batalla inmediatamente");
        this.pauseScreen();
    }
    
    public boolean verifyBattleCommand(String[] cmd)
    {
        // Si no ha ingresado ningun comando
        if (cmd.length <= 0)
            return false;
        // Limpia la entrada
        cmd[0] = cmd[0].toLowerCase();
        // Si el comando no está disponible
        if (!Arrays.asList(battleCommands).contains(cmd[0].toLowerCase()))
            return false;
        return true;
    }
    
    public void useItem()
    {
        int numItems = this.jugador.getNumItems();
        if (numItems == 0) {
            this.dibujador.showError("No tiene artefactos en su saco.");
            pauseScreen();
            return;
        }
        System.out.println("Saco:");
        System.out.println(this.jugador.getSaco());
        boolean validChoice;
        int choice;
        Scanner scan = new Scanner(System.in);
        do {
            validChoice = true;
            System.out.print("Ingrese el artefacto a utilizar ('q' para salir): ");
            try {
                choice = scan.nextInt();
                if (choice < 1 || choice > numItems)
                    validChoice = false;
            } catch (InputMismatchException ex) {
                choice = scan.next().charAt(0);
                if (choice != 'q')
                    validChoice = false;
            }
        } while (!validChoice);

        if (choice == 'q')
            return;
        
        Artefacto artefacto = this.jugador.getArtefacto(choice-1);
        /**
         * Usa artefacto
         * 
         * Si es un arma o armadura, lo cambia por los que tiene actualmente
         * Si es una pocion, la utiliza
         */
        switch (artefacto.type()) {
            case ARMA:
                Arma arma = (Arma) artefacto;
                this.jugador.pickupItem(this.jugador.getArma());
                this.jugador.setArma(arma);
                break;
            case ARMADURA:
                Armadura armadura = (Armadura) artefacto;
                this.jugador.pickupItem(this.jugador.getArmadura());
                this.jugador.setArmadura(armadura);
                break;
            case POCION:
                PocionCuracion pocion = (PocionCuracion) artefacto;
                this.jugador.heal(pocion);
                break;
        }
        this.jugador.dropItem(choice-1);
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
