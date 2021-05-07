package pt.isec.a2019134744.jogo.logica.dados;

import pt.isec.a2019134744.jogo.logica.dados.jogadores.Humano;
import pt.isec.a2019134744.jogo.logica.dados.jogadores.Player;
import pt.isec.a2019134744.jogo.logica.dados.jogadores.Virtual;
import pt.isec.a2019134744.jogo.logica.dados.minijogos.IJogo;
import pt.isec.a2019134744.jogo.logica.dados.minijogos.JogoCalculos;
import pt.isec.a2019134744.jogo.logica.dados.minijogos.JogoPalavras;

import java.util.Scanner;

public class JogoConnect4 {

    private Player jogador1;
    private Player jogador2;
    private Player jogadorAtivo;
    private final Tabuleiro tabuleiro;
    private boolean isJogoTerminado;

    // Minijogos
    private final IJogo jogoCalculos;
    private final IJogo jogoPalavras;
    private IJogo jogoAtivo;

    public JogoConnect4() {
        this.tabuleiro = new Tabuleiro();
        this.jogoCalculos = new JogoCalculos();
        this.jogoPalavras = new JogoPalavras();
        resetJogo();
    }

    public void resetJogo() {
        tabuleiro.resetTabuleiro();
        jogador1 = null;
        jogador2 = null;
        isJogoTerminado = false;
        jogoAtivo = jogoCalculos;
    }

    // Apenas sao pedidos os nomes dos jogadores humanos
    public boolean comecaJogo(String... jogadores) {
        if(jogadores.length > 2)
            return false;
        switch(jogadores.length) {
            case 0 -> adicionaJogadores();
            case 1 -> adicionaJogadores(jogadores[0]);
            default -> adicionaJogadores(jogadores[0], jogadores[1]);
        }
        // Sortear o primeiro jogador
        int random = (int) (Math.random() * 2) + 1;
        if(random == 1)
            jogadorAtivo = jogador1;
        else
            jogadorAtivo = jogador2;

        return true;
    }

    private void adicionaJogadores(String jogador1, String jogador2) {
        this.jogador1 = new Humano(jogador1, Peca.Vermelha);
        this.jogador2 = new Humano(jogador2, Peca.Amarela);
    }

    private void adicionaJogadores(String jogador1) {
        this.jogador1 = new Humano(jogador1, Peca.Vermelha);
        this.jogador2 = new Virtual(2, Peca.Amarela);
    }

    private void adicionaJogadores() {
        this.jogador1 = new Virtual(1, Peca.Vermelha);
        this.jogador2 = new Virtual(2, Peca.Amarela);
    }

    public void switchJogadorAtivo() {
        if(jogadorAtivo == jogador1)
            jogadorAtivo = jogador2;
        else
            jogadorAtivo = jogador1;
    }

    public String imprimeTabuleiroJogo() {
        return tabuleiro.imprimeTab();
    }

    public boolean jogaPeca(int nColuna) {
        if(!tabuleiro.introduzPeca(nColuna, jogadorAtivo.getPeca()))
            return false;      // Nao conseguiu jogar a peça -> pede novamente

        // Consegue jogar peça
        // Se for humano vai incrementar contador de jogada
        if(isHumano()) {
            Humano a = (Humano) jogadorAtivo;
            a.incNJogada();
        }
        switchJogadorAtivo();
        return true;
    }

    public boolean jogaPecaEspecial(int nColuna) {
        if(!jogadorAtivo.jogaPecaEspecial())
            return false;
        tabuleiro.removeColuna(nColuna);
        switchJogadorAtivo();
        return true;
    }

    public Player isVencedor() {
        if(tabuleiro.verificaVencedor(jogadorAtivo.getPeca())) {
            isJogoTerminado = true;
            return jogadorAtivo;
        }
        return null;
    }

    public int getNJogadaHumano() {
        if(isHumano()) {
            Humano a = (Humano) jogadorAtivo;
            return a.getnJogada();
        }
        return -1;
    }

    public boolean isFinished() {
        return isJogoTerminado;
    }

    public void desiste() {
        isJogoTerminado = true;
        switchJogadorAtivo();   // Jogador que ganhou é o ativo
    }

    public boolean isHumano() {
        return jogadorAtivo instanceof Humano;
    }

    @Override
    public String toString() {
        return "JogoConnect4{" +
                "jogador1=" + jogador1 +
                ", jogador2=" + jogador2 +
                ", jogadorAtivo=" + jogadorAtivo +
                ", tabuleiro=" + tabuleiro +
                ", isJogoTerminado=" + isJogoTerminado +
                ", jogoCalculos=" + jogoCalculos +
                ", jogoPalavras=" + jogoPalavras +
                ", jogoAtivo=" + jogoAtivo +
                '}';
    }

    /* Métodos de controlo do Minijogo */

    public void lancaMinijogo() {
        jogoAtivo.inicializaJogo();
    }

    public String getPerguntaMinijogo() {
        return jogoAtivo.getPergunta();
    }

    public String setRespostaMinijogo(Scanner sc) {
        return jogoAtivo.setResposta(sc);
    }

    public boolean isFinishedMinijogo() {
        return jogoAtivo.isFinished();
    }

    public boolean isVencedorMinijogo() {
        return jogoAtivo.isVencedor();
    }

    public void switchMinijogo() {
        if(jogoAtivo instanceof JogoCalculos)
            jogoAtivo = jogoPalavras;
        else
            jogoAtivo = jogoCalculos;
    }

    public void ganhaPecaEspecial() {
        Humano a = (Humano) jogadorAtivo;
        a.ganhaPecaEspecial();
    }
}
