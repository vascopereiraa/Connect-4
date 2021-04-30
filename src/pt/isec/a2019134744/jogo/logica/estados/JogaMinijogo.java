package pt.isec.a2019134744.jogo.logica.estados;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;

public class JogaMinijogo extends EstadoAdapter {

    public JogaMinijogo(JogoConnect4 jogoConnect4) {
        super(jogoConnect4);
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.JogaMinijogo;
    }
}
