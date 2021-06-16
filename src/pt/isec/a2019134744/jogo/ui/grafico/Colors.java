package pt.isec.a2019134744.jogo.ui.grafico;

import javafx.scene.paint.Color;

public class Colors {

    private static int value;
    private static char next;

    static {
        value = 0;
    }

    private Colors() {

    }

    public static void setValue(int a) {
        value = a;
    }

    public static void setNext(char n) {
        next = n;
    }

    public static Color getColor1() {
        return switch(value) {
            case 0 -> Color.YELLOW;
            case 1 -> Color.WHITE;
            default -> Color.LIGHTGRAY;
        };
    }

    public static Color getColor2() {
        return switch(value) {
            case 0 -> Color.RED;
            case 1 -> Color.BLACK;
            default -> Color.LIGHTGRAY;
        };
    }

    public static String getColor() {
        if(value == 0) {
            return switch (next) {
                case 'R' -> "Vermelha";
                case 'Y' -> "Amarela";
                default -> "Cinzenta";
            };
        }
        return switch (next) {
            case 'R' -> "Preta";
            case 'Y' -> "Branca";
            default -> "Cinzenta";
        };
    }

    public static Color getColorHover() {
        if(value == 0) {
            return switch (next) {
                case 'R' -> Color.LIGHTPINK;
                case 'Y' -> Color.LIGHTYELLOW;
                default -> Color.LIGHTGRAY;
            };
        }
        return switch (next) {
            case 'R' -> Color.GRAY;
            case 'Y' -> Color.WHITE;
            default -> Color.LIGHTGRAY;
        };
    }

    public static Color getDefaultColor() {
        return Color.LIGHTGRAY;
    }


}
