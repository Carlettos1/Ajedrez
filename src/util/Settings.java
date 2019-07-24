package util;
public final class Settings {
    public static final int X = 16;
    public static final int Y = 17;
    public static final int TILE_SIZE = 50;
    public static final String TITULO = "Ajedrex";
    public static final int ANCHO_BOTON = 150;
    public static final int ANCHURA_JUGADOR = 400;
    public static final int ALTURA = Y * TILE_SIZE + 45;
    public static final int ANCHURA_TABLERO = X * TILE_SIZE + 15 + ANCHO_BOTON + 20;
    public static final int ANCHURA_VENTANA = ANCHURA_TABLERO + ANCHURA_JUGADOR * 2;
    public static final int CIRCULO = 15;
    public static final int INICIO_BLANCO =  ANCHURA_JUGADOR + ANCHURA_TABLERO;
    public static final int SEED = 381_654_729;
    public static final int ALTO_CARTAS_ESCAQUE = 150;
}