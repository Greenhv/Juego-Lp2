package com.mycompany.juegolp2;

/**
 * Contenidos posibles de una celda
 *
 */
public enum ContenidoCeldas
{
    PARED('#'),
    LIBRE(' '),
    ARTEFACTO('A'),
    ENEMIGO('E'),
    SIGUIENTE('+'),
    ANTERIOR('-'),
    JUGADOR('*');

    public char asChar()
    {
        return asChar;
    }

    private final char asChar;

    private ContenidoCeldas(char asChar)
    {
        this.asChar = asChar;
    }
}