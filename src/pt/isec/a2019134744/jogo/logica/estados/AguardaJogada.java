package pt.isec.a2019134744.jogo.logica.estados;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;

public class AguardaJogada extends EstadoAdapter {

    public AguardaJogada(JogoConnect4 jogoConnect4) {
        super(jogoConnect4);
    }

    @Override
    public IEstado joga(int nColuna) {

        // todo: joga peca normal
        // todo: verifica se ganha

        // caso seja a jogada nr 4 de cada jogador tem de ver para lancar o Decisao Minijogo

        return new AguardaJogada(getJogo());
    }

    @Override
    public IEstado jogaEspecial(int nColuna) {

        // todo: joga peca especial
        // todo: verifica se ganha

        // caso seja a jogada nr 4 de cada jogador tem de ver para lancar o Decisao Minijogo


        return new AguardaJogada(super.getJogo());
    }

    @Override
    public IEstado termina() {
        // super.getJogo().desiste();
        return new FimJogo(super.getJogo());
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.AguardaJogada;
    }
}
