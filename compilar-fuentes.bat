#!/bin/sh

echo "Compilando Fuentes"

# Compila Artefactos
javac -sourcepath src -classpath "lib/\*" -d build/classes src/lp2/juegolp2/Artefactos/*.java

# Compila Facilidades
#javac -sourcepath src -d build/classes src/lp2/juegolp2/Facilidades/*.java

# Compila Mundo
#javac -sourcepath src -d build/classes src/lp2/juegolp2/Mundo/*.java

# Compila Interfaz
#javac -sourcepath src -d build/classes src/lp2/juegolp2/Interfaz/*.java

# Compila Juego
#javac -sourcepath src -classpath "lib/\*" -d build/classes src/lp2/juegolp2/Juego/*.java

echo "Listo!"