package pt.isec.a2019134744.jogo.logica.estados;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;

import java.util.Scanner;

public class JogaMinijogo extends EstadoAdapter {

    public JogaMinijogo(JogoConnect4 jogoConnect4) {
        super(jogoConnect4);
        super.getJogo().lancaMinijogo();
    }

    @Override
    public IEstado respondeMinijogo(Scanner sc) {
        super.getJogo().setRespostaMinijogo(sc);
        if(super.getJogo().isFinishedMinijogo())
            return new AguardaJogada(super.getJogo());
        return this;
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.JogaMinijogo;
    }
}
