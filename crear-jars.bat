#!/bin/sh

#Windows
#call compilar-fuentes.bat

#Linux
source ./compilar-fuentes.bat

echo "Creando jars"

jar cf lib/lp2.juegolp2.Artefactos.jar -C build/classes lp2/juegolp2/Artefactos/ lib/xstream-1.4.9.jar

jar cf lib/lp2.juegolp2.Entidades.jar -C build/classes lp2/juegolp2/Entidades/

jar cf lib/lp2.juegolp2.Facilidades.jar -C build/classes lp2/juegolp2/Facilidades/

jar cf lib/lp2.juegolp2.Mundo.jar -C build/classes lp2/juegolp2/Mundo/

jar cf lib/lp2.juegolp2.Interfaz.jar -C build/classes lp2/juegolp2/Interfaz/

# Crea jar del juego completo
jar cfm lp2.juegolp2.Juego.jar Manifest-Juego.txt \
-C build/classes lp2/juegolp2/Artefactos/ \
-C build/classes lp2/juegolp2/Entidades/ \
-C build/classes lp2/juegolp2/Interfaz/ \
-C build/classes lp2/juegolp2/Facilidades/ \
-C build/classes lp2/juegolp2/Mundo/ \
lib/xpp3_min-1.1.4c.jar \
lib/xstream-1.4.9.jar \
lib/xmlpull-1.1.3.1.jar \
-C build/classes lp2/juegolp2/Juego/

echo "Listo!"