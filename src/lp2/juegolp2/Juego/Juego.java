package lp2.juegolp2.Juego;

import com.thoughtworks.xstream.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.Timer;
import lp2.juegolp2.Mundo.*;
import lp2.juegolp2.Artefactos.*;
import lp2.juegolp2.Interfaz.*;
import lp2.juegolp2.Facilidades.*;
import lp2.juegolp2.Entidades.*;

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
    private static final int enemyLevelRange = 2;
    
    private Avatar jugador;
    
    private GestorLaberinto gestorLaberinto;
    private Dibujador dibujador;
    private int currentLabIndex;
    private int numLaberintos;
    private XStream xmlSerializer;
    private Timer timer;
    
    private Juego()
    {
        this.dibujador = new Dibujador(this);
        this.gestorLaberinto = new GestorLaberinto(this.dibujador.getImageLoader());
        this.currentLabIndex = 0;
        xmlSerializer = new XStream();
        xmlSerializer.omitField(new WorldObject(dibujador.getImageLoader()).getClass(), "imgLoader");
        xmlSerializer.omitField(new WorldObject(dibujador.getImageLoader()).getClass(), "sprite");
        
        this.init();
        this.historia();
    }
    
    public static Juego getInstance()
    {
        return Juego.INSTANCE;
    }
    
    // Introducción al juego      
    public void historia(){
        String nJugador = this.jugador.getNombre();        
        String story = 
            "A través del tiempo y el espacio se abren puertas.\n"
            + "Mundo paralelos se crean todos los días con acciones pequeñas.\n"
            + "Hay mundos maravillosos con historias y leyendas nunca antes contadas\n"
            + "Sin embargo...\n"
            + "No todos los mundos son amigables.\n"
            + "Un día normal de su vida, {playerName} "
            + "es transportado hacia el fantástico mundo de Aether.\n"
            + "Aether está dominado por el demonio Azazel. "
            + "Azazel planea unir los mundos y convertirse en el amo supremo\n"
            + "{playerName} lo detendrá, no porque lo desee, sino porque es el único que puede hacerlo.\n"
            + "Avanza, {playerName}\n";
        this.dibujador.showStory(story, nJugador);
        //this.pauseScreen();
    }
    
    // Configura lo necesario para jugar
    public void init()
    {
        this.initArtefactos();
        this.initMap();
        // Obten datos y crea jugador
        this.initPlayer();
        this.initAliados();
        this.timer = new Timer(2000, new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
        	moverEntidades(getLaberintoActual());
                updateStage();
            }
        });
        this.timer.start();
    }

    public void play()
    {
        this.startGame();
        /*
        Result result = Result.PLAYING;
        while(result == Result.PLAYING) {
            //String input = this.dibujador.showInputPrompt("Ingrese su siguiente movimiento (help para ver los comandos disponibles): ");
            //result = this.processInput(input);
            
            this.updateStage();
        }
        this.processResult(result);
        */
    }
    
    private void processResult(Result result)
    {
        switch (result) {
            case QUIT:
                this.dibujador.showMessage(
                    "Has decidido terminar el juego. Gracias por jugar."
                );
                break;
            case WIN:
                this.dibujador.showMessage("Has ganado !");
                break;
            case LOSE:
                this.dibujador.showMessage("Has perdido :(");
                break;
        }
        if (result == Result.PLAYING) {
            this.updateStage();
        } else {
            this.endGame();
        }
    }
    
    private String[] getCommandFromString(String line)
    {
        if (line == null)
            return null;
        // Remueve espacios al inicio y al final
        // Luego reemplaza espacios en medio de la cadena con ";"
        // Finalmente, separa tokens por ";"
        String[] cmd = line.trim().replaceAll("\\s+", ";").split(";");
        if (cmd[0].equals("interactuar") && cmd.length == 1) {
            cmd = new String[] {
                "interactuar",
                this.jugador.getFacingDir().toString(),
            };
        }
        return cmd;
    }
    
    private boolean verifyCommand(String[] cmd)
    {
        // Si no ha ingresado ningun comando
        if (cmd == null || cmd.length <= 0)
            return false;
        // Limpia la entrada
        cmd[0] = cmd[0].toLowerCase();
        // Si el comando no está disponible
        if (!Arrays.asList(availableCommands).contains(cmd[0].toLowerCase()))
            return false;
        // Comandos que requieren argumento direccion
        if (cmd[0].equals("mover") || cmd[0].equals("interactuar")) {
            if (cmd.length != 2)
                return false;
            cmd[1] = cmd[1].toUpperCase();
            if (!Direction.contains(cmd[1]))
                return false;
        }
        return true;
    }
    
    public void showHelp()
    {
        String help = "Comandos disponibles: \n";
        /**
         * Ayuda
         */
        help += "help: Muestra este mensaje de ayuda\n";
        /**
         * Mover
         */
        help += "mover <dir>: Mueve al jugador en la direccion dir\n";
        help += "Donde dir puede ser:\n";
        help += "'UP': arriba\n";
        help += "'DOWN': abajo\n";
        help += "'RIGHT': derecha\n";
        help += "'LEFT': izquierda\n";
        /**
         * Interactuar
         */
        help += "interactuar <dir>:\tInteractua con la celda adyacente en la direccion dir\n";
        help += "\t\t\tDonde dir puede tener los mismos valores que al mover el jugador\n";
        /**
         * Usar
         */
        help += "usar:\t\t\tUsa un artefacto de tu saco\n";
        /**
         * Salir
         */
        help += "salir:\t\t\tTermina el juego inmediatamente.\n";
        
        this.dibujador.showMessage("Ayuda", help);
    }
    
    private void initPlayer()
    {
        String nombre;
        do {
            nombre = this.dibujador.showInputPopup("Ingrese su nombre: ");
            if (nombre == null) {
                this.dibujador.showError("Debe escribir un nombre");
            }
        } while (nombre == null);
        Laberinto currentLab = this.gestorLaberinto.get(this.currentLabIndex);
        Position avatarPos = new Position(currentLab.getAnterior());
        Arma armaIni = Arma.armasDisp.get(0);
        Armadura armaduraIni = Armadura.armadurasDisp.get(0);
        
        this.jugador = new Avatar(nombre, avatarPos, this.dibujador.getImageLoader());
        this.jugador.setArma(armaIni);
        this.jugador.setArmadura(armaduraIni);
        this.gestorLaberinto.get(currentLabIndex).agregaPlayer(jugador);
    }
    
    private void initAliados()
    {
        ArrayList<Aliado> aliados = readAliadosTxt("aliados.txt");
        
        for (int i = 0; i < aliados.size(); ++i) {
            /**
             * Llena el saco del aliado
             */
            int numArt = (int) (Math.random() * 6) + 5;
            for (int j = 0; j < numArt; ++j) {
                aliados.get(i).pickupItem(Artefacto.random(this.dibujador.getImageLoader()));
            }
            /**
            * Agrega aliados a los laberintos
            */
            this.gestorLaberinto.addAliado(aliados.get(i));
        }   
    }
    
    private ArrayList<Aliado> readAliadosTxt(String filename)
    {
        ArrayList<Aliado> aliados = new ArrayList<>();
        try {
            RandomAccessFile file = new RandomAccessFile(filename, "r");
            String line = file.readLine();
            int numAliados = Integer.parseInt(line.split(":")[1]);
            for (int i = 0; i < numAliados; ++i) {
                line = file.readLine();
                Aliado aliado = Aliado.readAliadoFromString(line, this.dibujador.getImageLoader());
                aliados.add(aliado);
            }
        } catch (Exception ex) {
            this.dibujador.showError(ex.toString());
            ex.printStackTrace();
            System.exit(1);
        }
        return aliados;
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
            laberintoActual.removePlayer(jugador);
            if(++this.currentLabIndex == this.gestorLaberinto.size()) {
                res = Result.WIN;
            } else {
                laberintoActual = this.gestorLaberinto.get(this.currentLabIndex);
                jugador.setPosition(laberintoActual.getAnterior());
                laberintoActual.agregaPlayer(jugador);
            }
        } else if(this.jugador.getPosition().equals(laberintoActual.getAnterior())){
            if(this.currentLabIndex >= 1){
                this.currentLabIndex--;
                laberintoActual = this.gestorLaberinto.get(this.currentLabIndex);
                this.jugador.setPosition(laberintoActual.getSiguiente());
                laberintoActual.agregaPlayer(jugador);
            }
        }
        //this.moverEntidades(laberintoActual);
        return res;
    }
    
    private void moverAvatar(String mov, Laberinto laberintoActual) 
    {
        Direction dir = Direction.valueOf(mov);
        Position newPos = this.jugador.getPosition().copy().move(dir);
        // Si no se puede mover a la posición seleccionada, mostramos un mensaje
        if (!laberintoActual.validPlayerPosition(newPos)) {
            this.jugador.setFacingDir(dir);
            //this.dibujador.showError("No se puede mover a esa posición");
            //pauseScreen();
            return;
        }
        laberintoActual.moverEntidad(jugador, dir);
        Aliado aliado = laberintoActual.getAliado(newPos);
        if (aliado != null) {
            laberintoActual.moverEntidad(aliado, dir.opposite());
        }
        
        Artefacto artefacto = laberintoActual.getArtefacto(newPos);
        if (artefacto != null) {
            this.jugador.pickupItem(artefacto);
            laberintoActual.removeArtefacto(newPos);
            this.dibujador.showMessage("Has recogido un artefacto: " + artefacto);
        }
    }
    
    private void moverEntidades(Laberinto lab)
    {
        System.out.println("Moviendo entidades");
        lab.moverEnemigos(this.jugador.getPosition());
        lab.moverAliados();
    }
    
    private Result interactuar(String mov, Laberinto laberintoActual)
    {
        Direction dir = Direction.valueOf(mov);
        Position pos = this.jugador.getPosition().copy().move(dir);
        // Si no se puede interactuar con la posición seleccionada, se envía un mensaje
        if (laberintoActual.get(pos).getContenido().contains(Celda.Contenido.PARED)) {
            this.dibujador.showError("No se puede interactuar con esa celda");
            //this.pauseScreen();
            return Result.PLAYING;
        }
        Result res = this.interactuar(laberintoActual, pos);
        //this.pauseScreen();
        return res;
    }
    
    private Result interactuar(Laberinto laberintoActual, Position pos)
    {
        // Verifico si hay un enemigo en esa posicion
        Enemigo enemigo = laberintoActual.getEnemigo(pos);
        if (enemigo != null) {
            Result res = this.battle(this.jugador, enemigo);
            if (res == Result.PLAYING && enemigo.getCurrentHP() <= 0) {
                laberintoActual.removeEnemigo(enemigo.getPosition());
            }
            return res;
        }
        
        // Verifico si hay un artefacto
        Artefacto artefacto = laberintoActual.getArtefacto(pos);
        if (artefacto != null) {
            this.jugador.pickupItem(artefacto);
            laberintoActual.removeArtefacto(pos);
            this.dibujador.showMessage("Has recogido un artefacto: " + artefacto);
            return Result.PLAYING;
        }
        
        Aliado aliado = laberintoActual.getAliado(pos);
        if (aliado != null) {
            this.dibujador.showMessage("Consejo de tu aliado: " + aliado.getConsejo());
        }
        return Result.PLAYING;
    }
    
    private Result battle(Avatar jugador, Entidad enemigo)
    {
        Result res = Result.PLAYING;
        while(res == Result.PLAYING) {
            jugador.setInBattle(true);
            enemigo.setInBattle(true);
            this.dibujador.showBattleInterface(jugador, enemigo);
            String input = this.dibujador.showInputPrompt(
                "Accion a tomar (Ingrese help para ver las acciones disponibles): "
            );
            String[] cmd = getCommandFromString(input);
            if (!this.verifyBattleCommand(cmd)) {
                this.dibujador.showError("No se ha ingresado un comando válido");
                this.showBattleHelp();
                continue;
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
                    this.dibujador.showMessage("Has huido del enemigo");
                    res = Result.BATTLE_RUN;
                    break;
                case "usar":
                    // Mostrar el inventario, luego pedir indice.
                    this.useItem();
                    break;
            }
            // El enemigo murio luego de la accion del jugador ?
            if(enemigo.getCurrentHP() == 0) {
                this.dibujador.showMessage("El enemigo ha sido derrotado!");
                res = Result.BATTLE_WIN;
            } else if (attacked) {
                // Accion del Enemigo atacar o curarse ( por ahora solo atacara )
                jugador.damage(enemigo.getArma().damage());
            }
            
            // El jugador murio luego de la accion del Enemigo ?
            if(jugador.getCurrentHP() == 0)
                res = Result.LOSE;
        }
        jugador.setInBattle(false);
        enemigo.setInBattle(false);
        if (res != Result.LOSE) {
            res = Result.PLAYING;
        }
        this.dibujador.hideBattleInterface();
        return res;
    }
    
    public void showBattleHelp()
    {
        String help = "Comandos de batalla disponibles: \n";
        /**
         * Ayuda
         */
        help += "help:\t\tMuestra este mensaje de ayuda\n";
        /**
         * Atacar
         */
        help += "atacar:\t\tAtacar al enemigo con tu arma equipada\n";
        /**
         * Usar
         */
        help += "usar:\t\tUsar un artefacto de tu inventorio\n";
        /**
         * Huir
         */
        help += "huir:\t\tTermina la batalla inmediatamente\n";
        
        this.dibujador.showMessage("Ayuda", help);
    }
    
    public boolean verifyBattleCommand(String[] cmd)
    {
        // Si no ha ingresado ningun comando
        if (cmd == null || cmd.length <= 0)
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
            //pauseScreen();
            return;
        }
        System.out.println("Saco:");
        System.out.println(this.jugador.getSaco());
        boolean validChoice;
        int choice = -1;
        do {
            validChoice = true;
            String input = this.dibujador.showInputPrompt(
                "Ingrese el artefacto a utilizar ('q' para salir): "
            );
            if (input == null) {
                input = "";
            }
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > numItems)
                    validChoice = false;
            } catch (NumberFormatException ex) {
                input = input.trim();
                if (input.length() > 0) {
                    choice = input.charAt(0);
                }
                if (choice != 'q')
                    validChoice = false;
            }
        } while (!validChoice);

        if (choice == 'q')
            return;
        
        this.dibujador.showMessage(
            "Has usado el objeto " + this.jugador.getArtefacto(choice-1).getNombre()
        );
        this.jugador.useItem(choice-1);
    }
    
    private void pauseScreen()
    {
        this.dibujador.showPauseScreen();
    }
    
    public enum Result
    {
        QUIT,
        LOSE,
        WIN,
        PLAYING,
        BATTLE_WIN,
        BATTLE_RUN,
    }
    
    private void initArtefactos()
    {
        try {
            PocionCuracion.loadXML(xmlSerializer);
            Arma.loadXML(xmlSerializer);
            Armadura.loadXML(xmlSerializer);
        } catch (FileNotFoundException e) {
            this.dibujador.showError(e.toString());
            System.exit(1);
        } catch (IOException e) {
            this.dibujador.showError(e.toString());
            System.exit(1);
        }
    }
    
    private void serializeArtefactos()
    {
        this.serializePociones();
        this.serializeArmas();
        this.serializeArmaduras();
    }
    
    private void serializeArmaduras()
    {
        try {
            FileWriter fw = new FileWriter("armaduras.xml");
            xmlSerializer.toXML(Armadura.armadurasDisp, fw);
            fw.close();
        } catch (IOException e) {
            this.dibujador.showError(e.toString());
        }
    }
    
    private void serializeArmas()
    {
        try {
            FileWriter fw = new FileWriter("armas.xml");
            xmlSerializer.toXML(Arma.armasDisp, fw);
            fw.close();
        } catch (IOException e) {
            this.dibujador.showError(e.toString());
        }
    }
    
    private void serializePociones()
    {
        try {
            FileWriter fw = new FileWriter("pociones.xml");
            xmlSerializer.toXML(PocionCuracion.pocionesDisp, fw);
            fw.close();
        } catch (IOException e) {
            this.dibujador.showError(e.toString());
        }
    }
    
    private Laberinto getLaberintoActual()
    {
        return gestorLaberinto.get(currentLabIndex);
    }
    
    private Result processInput(String input)
    {
        Result result = Result.PLAYING;
        String[] cmd = getCommandFromString(input);
        Laberinto laberintoActual = gestorLaberinto.get(currentLabIndex);
        if (!verifyCommand(cmd)) {
            dibujador.showError("No se ha ingresado un comando válido");
            showHelp();
        } else {
            switch (cmd[0]) {
                case "help":
                    showHelp();
                    break;
                case "interactuar":
                    result = interactuar(cmd[1], laberintoActual);
                    break;
                case "mover":
                    result = move(cmd[1], laberintoActual);
                    break;
                case "usar":
                    useItem();
                    break;
                case "salir":
                    result = Result.QUIT;
                    break;
            }
        }
        this.processResult(result);
        return result;
    }
    
    private void endGame()
    {
        timer.stop();
        dibujador.closeWindow();
        serializeArtefactos();
    }
    
    private void startGame()
    {
        dibujador.startGame();
    }
    
    private Avatar getJugador()
    {
        return this.jugador;
    }
    
    private void keyPressed(java.awt.event.KeyEvent evt)
    {
        String command = "";
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_Q:
                command = "salir";
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                command = "mover up";
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                command = "mover right";
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                command = "mover left";
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                command = "mover down";
                break;
            case KeyEvent.VK_U:
                command = "usar";
                break;
            case KeyEvent.VK_I:
                command = "interactuar " + this.jugador.getFacingDir();
                break;
        }
        if (!command.equals("")) {
            this.processInput(command);
        }
    }
    
    public class GameShared
    {
        private GameShared(){}
        
        public Laberinto getLaberintoActual()
        {
            return Juego.getInstance().getLaberintoActual();
        }
    
        public Result processInput(String input)
        {
            return Juego.getInstance().processInput(input);
        }
    
        public void endGame()
        {
            Juego.getInstance().endGame();
        }
    
        public void startGame()
        {
            Juego.getInstance().startGame();
        }
        
        public Avatar getJugador()
        {
            return Juego.getInstance().getJugador();
        }
        
        public void keyPressed(java.awt.event.KeyEvent evt)
        {
            Juego.getInstance().keyPressed(evt);
        }
    }
    
    public void giveKeyTo(Dibujador dibujador)
    {
        dibujador.receiveGameKey(new GameShared());
    }
    
    private void updateStage()
    {
        this.dibujador.dibujarLaberinto();
        this.dibujador.dibujarInfoJugador();
    }
}
