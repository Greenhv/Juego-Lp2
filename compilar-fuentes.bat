#!/bin/sh

echo "Compilando Fuentes"
mkdir build
mkdir build/sclasses

# Compila Artefactos
javac -sourcepath src -cp 'lib/*' -d build/classes src/lp2/juegolp2/Artefactos/*.java

# Compila Entidades
javac -sourcepath src -cp 'lib/*' -d build/classes src/lp2/juegolp2/Entidades/*.java

# Compila Facilidades
javac -sourcepath src -cp 'lib/*' -d build/classes src/lp2/juegolp2/Facilidades/*.java

# Compila Mundo
javac -sourcepath src -cp 'lib/*' -d build/classes src/lp2/juegolp2/Mundo/*.java

# Compila Interfaz
javac -sourcepath src -cp 'lib/*' -d build/classes src/lp2/juegolp2/Interfaz/*.java

# Compila Juego
javac -sourcepath src -cp 'lib/*' -d build/classes src/lp2/juegolp2/Juego/*.java

echo "Listo!"