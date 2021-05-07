package pt.isec.a2019134744.jogo.logica.estados;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;

import java.util.Scanner;

public class JogaMinijogo extends EstadoAdapter {

    public JogaMinijogo(JogoConnect4 jogoConnect4) {
        super(jogoConnect4);
        super.getJogo().lancaMinijogo();
    }

    public String getPerguntaMinijogo() {
        return super.getJogo().getPerguntaMinijogo();
    }

    public String setRespostaMinijogo(Scanner sc) {
        return super.getJogo().setRespostaMinijogo(sc);
    }

    @Override
    public IEstado terminaMinijogo() {
        if(super.getJogo().isFinishedMinijogo()) {
            if(super.getJogo().isVencedorMinijogo()) {
                super.getJogo().ganhaPecaEspecial();
                super.getJogo().switchMinijogo();
                return new AguardaJogada(super.getJogo());
            }
            super.getJogo().switchJogadorAtivo();
            super.getJogo().switchMinijogo();
            return new AguardaJogada(super.getJogo());
        }
        return this;
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.JogaMinijogo;
    }1
}
