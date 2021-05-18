package pt.isec.a2019134744.jogo.logica.dados.jogadores;

import pt.isec.a2019134744.jogo.logica.dados.Peca;

public class Virtual extends Player {

    public Virtual(int nJogador, Peca peca) {
        super("Computador" + nJogador, peca);
    }

}
