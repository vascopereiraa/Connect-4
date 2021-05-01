package pt.isec.a2019134744.jogo;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;
import pt.isec.a2019134744.jogo.ui.texto.UIConnect4;

public class Connect4 {
    public static void main(String[] args) {
        JogoConnect4 jogoConnect4 = new JogoConnect4();
        UIConnect4 uiConnect4 = new UIConnect4(jogoConnect4);
        uiConnect4.start();
    }
}