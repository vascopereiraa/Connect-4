package pt.isec.a2019134744.jogo.logica.estados;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;
import pt.isec.a2019134744.jogo.logica.dados.jogadores.JogadorAtivo;

public class AguardaJogada extends EstadoAdapter {

    public AguardaJogada(JogoConnect4 jogoConnect4) {
        super(jogoConnect4);
    }

    @Override
    public IEstado joga(int nColuna) {
        // Vê se consegue jogar a peça
        if(super.getJogo().jogaPeca(nColuna)) {
            // Verifica se é o vencedor
            if (super.getJogo().isVencedor() != JogadorAtivo.none)
                // Se for o vencedor termina o jogo
                return new FimJogo(super.getJogo());
            super.getJogo().switchJogadorAtivo();
            if((super.getJogo().getNJogadaHumano() % 5) == 0) {
                // Se for a jogada de um humano lança DecisaoMiniJogo
                super.getJogo().switchMinijogoExecucao();
                return new DecisaoMinijogo(super.getJogo());
            }
            // Continua jogo
            return new AguardaJogada(super.getJogo());
        }
        // Se não conseguir jogar a peça
        return this;
    }

    @Override
    public IEstado jogaEspecial(int nColuna) {
        if(super.getJogo().jogaPecaEspecial(nColuna)) {
            super.getJogo().switchJogadorAtivo();
            if(super.getJogo().getNJogadaHumano() % 5 == 0)
                // Se for a jogada de um humano lança DecisaoMiniJogo
                return new DecisaoMinijogo(super.getJogo());
            // Continua jogo
            return new AguardaJogada(super.getJogo());
        }
        // Se não conseguir jogar a peça
        return this;
    }

    @Override
    public IEstado termina() {
        super.getJogo().desiste();
        return new FimJogo(super.getJogo());
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.AguardaJogada;
    }
}
