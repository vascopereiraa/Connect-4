package pt.isec.a2019134744.jogo;

import pt.isec.a2019134744.jogo.logica.GestorDeJogo;
import pt.isec.a2019134744.jogo.logica.MaquinaEstados;
import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;
import pt.isec.a2019134744.jogo.ui.texto.UIConnect4;

public class Connect4 {
    public static void main(String[] args) {
        GestorDeJogo gestorDeJogo = new GestorDeJogo();
        UIConnect4 uiConnect4 = new UIConnect4(gestorDeJogo);
        uiConnect4.start();
    }
}