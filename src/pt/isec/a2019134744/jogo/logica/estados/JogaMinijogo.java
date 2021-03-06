package pt.isec.a2019134744.jogo.logica.estados;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;

import java.util.Scanner;

public class JogaMinijogo extends EstadoAdapter {

    public JogaMinijogo(JogoConnect4 jogoConnect4) {
        super(jogoConnect4);
        super.getJogo().lancaMinijogo();
    }

    @Override
    public IEstado respondeMinijogo(String resposta) {
        super.getJogo().setRespostaMinijogo(resposta);
        if(super.getJogo().isFinishedMinijogo()) {
            // Verifica se ganhou o minijogo
            if(!super.getJogo().isVencedorMinijogo())
                super.getJogo().switchJogadorAtivo();
            return new AguardaJogada(super.getJogo());
        }
        return this;
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.JogaMinijogo;
    }
}
