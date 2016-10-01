#!/bin/sh

echo "Compilando Fuentes"

# Compila Artefactos
javac -d build/classes src/lp2/juegolp2/Artefactos/*.java

# Compila Facilidades
javac -cp src -d build/classes src/lp2/juegolp2/Facilidades/*.java

# Compila Mundo
javac -cp src -d build/classes src/lp2/juegolp2/Mundo/*.java

# Compila Interfaz
javac -cp src -d build/classes src/lp2/juegolp2/Interfaz/*.java

# Compila Juego
javac -cp src -d build/classes src/lp2/juegolp2/Juego/*.java

echo "Listo!"