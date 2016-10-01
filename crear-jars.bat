#!/bin/sh

#Windows
#call compilar-fuentes.bat

#Linux
source ./compilar-fuentes.bat

echo "Creando jars"

jar cf dist/lp2.juegolp2.Artefactos.jar -C build/classes lp2/juegolp2/Artefactos/
jar cf dist/lp2.juegolp2.Facilidades.jar -C build/classes lp2/juegolp2/Facilidades/
jar cf dist/lp2.juegolp2.Mundo.jar -C build/classes lp2/juegolp2/Mundo/
jar cf dist/lp2.juegolp2.Interfaz.jar -C build/classes lp2/juegolp2/Interfaz/

# Crea jar del juego completo
jar cfm dist/lp2.juegolp2.Juego.jar Manifest-Juego.txt \
-C build/classes lp2/juegolp2/Artefactos/ \
-C build/classes lp2/juegolp2/Interfaz/ \
-C build/classes lp2/juegolp2/Facilidades/ \
-C build/classes lp2/juegolp2/Mundo/ \
-C build/classes lp2/juegolp2/Juego/

echo "Listo!"