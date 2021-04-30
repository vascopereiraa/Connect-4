package pt.isec.a2019134744.jogo.logica.dados;

import pt.isec.a2019134744.jogo.logica.dados.jogadores.Humano;
import pt.isec.a2019134744.jogo.logica.dados.jogadores.Player;
import pt.isec.a2019134744.jogo.logica.dados.jogadores.Virtual;

public class JogoConnect4 {

    private final int NR_LINHAS = 7;
    private final int NR_COLUNAS = 6;
    private Player jogador1;
    private Player jogador2;

    public JogoConnect4() {
        resetJogo();
    }

    public void resetJogo() {
        jogador1 = null;
        jogador2 = null;
    }

    // Apenas sao pedidos os nomes dos jogadores humanos
    public void comecaJogo(String... jogadores) {
        switch(jogadores.length) {
            case 0 -> adicionaJogadores();
            case 1 -> adicionaJogadores(jogadores[0]);
            default -> adicionaJogadores(jogadores[0], jogadores[1]);
        }
    }

    private void adicionaJogadores(String jogador1, String jogador2) {
        this.jogador1 = new Humano(jogador1);
        this.jogador2 = new Humano(jogador2);
    }

    private void adicionaJogadores(String jogador1) {
        this.jogador1 = new Humano(jogador1);
        this.jogador2 = new Virtual(2);
    }

    private void adicionaJogadores() {
        this.jogador1 = new Virtual(1);
        this.jogador2 = new Virtual(2);
    }



    // todo: tabuleiro


}
