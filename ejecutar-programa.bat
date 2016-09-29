#!/bin/sh

#Windows
#call crear-jars.bat

#Linux
source ./crear-jars.bat

cd dist && java -jar lp2.juegolp2.Juego.jar; cd ../
