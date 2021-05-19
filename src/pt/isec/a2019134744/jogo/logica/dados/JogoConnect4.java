package pt.isec.a2019134744.jogo.logica.dados;

import pt.isec.a2019134744.jogo.logica.dados.jogadores.Humano;
import pt.isec.a2019134744.jogo.logica.dados.jogadores.JogadorAtivo;
import pt.isec.a2019134744.jogo.logica.dados.jogadores.Player;
import pt.isec.a2019134744.jogo.logica.dados.jogadores.Virtual;
import pt.isec.a2019134744.jogo.logica.dados.minijogos.IJogo;
import pt.isec.a2019134744.jogo.logica.dados.minijogos.JogoCalculos;
import pt.isec.a2019134744.jogo.logica.dados.minijogos.JogoPalavras;
import pt.isec.a2019134744.jogo.logica.memento.IMementoOriginator;
import pt.isec.a2019134744.jogo.logica.memento.Memento;
import pt.isec.a2019134744.jogo.utils.ConsoleColors;

import java.io.Serializable;
import java.util.Scanner;

public class JogoConnect4 implements IMementoOriginator, Serializable {

    // Info Jogo
    private String infoJogo;

    // Dados Jogadores
    private Player jogador1;
    private Player jogador2;
    private JogadorAtivo jogadorAtivo;

    // Dados Tabuleiro Jogo
    private Tabuleiro tabuleiro;
    private boolean isJogoTerminado;

    // Minijogos
    private final IJogo jogoCalculos;
    private final IJogo jogoPalavras;
    private IJogo jogoAtivo;
    private boolean isMinijogoDecorrer;

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
        jogadorAtivo = JogadorAtivo.none;
        isJogoTerminado = false;
        jogoAtivo = jogoCalculos;
        isMinijogoDecorrer = false;
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
            jogadorAtivo = JogadorAtivo.jogador1;
        else
            jogadorAtivo = JogadorAtivo.jogador2;

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
        if(jogadorAtivo == JogadorAtivo.jogador1)
            jogadorAtivo = JogadorAtivo.jogador2;
        else
            jogadorAtivo = JogadorAtivo.jogador1;
    }

    private Player getJogadorAtivo() { return jogadorAtivo == JogadorAtivo.jogador1 ? jogador1 : jogador2; }

    public String imprimeTabuleiroJogo() {
        return tabuleiro.imprimeTab();
    }

    public boolean jogaPeca(int nColuna) {
        // Consegue jogar peça
        if(tabuleiro.introduzPeca(nColuna, getJogadorAtivo().getPeca())) {
            // Se for humano vai incrementar contador de jogada
            if (isHumano())
                ((Humano) getJogadorAtivo()).incNJogada();
            infoJogo = (String.format("Foi introduzida uma peça " + getJogadorAtivo().getPeca().toString() +
                            " na posicao %d", nColuna));
            return true;
        }
        // Nao conseguiu jogar a peça -> pede novamente
        infoJogo = "Tentou introduzir uma peça " + getJogadorAtivo().getPeca().toString() +
                " numa posição inválida!";

        return false;
    }

    public boolean jogaPecaEspecial(int nColuna) {
        if(!getJogadorAtivo().jogaPecaEspecial())
            return false;
        if(!tabuleiro.removeColuna(nColuna)) {
            getJogadorAtivo().ganhaPecaEspecial();
            return false;
        }
        if (isHumano())
            ((Humano) getJogadorAtivo()).incNJogada();
        return true;
    }

    public JogadorAtivo isVencedor() {
        if(tabuleiro.verificaVencedor(getJogadorAtivo().getPeca())) {
            isJogoTerminado = true;
            return jogadorAtivo;
        }
        return JogadorAtivo.none;
    }

    public int getNJogadaHumano() {
        if(isHumano()) {
            Humano a = (Humano) getJogadorAtivo();
            return a.getnJogada();
        }
        return -1;
    }

    public void desiste() {
        infoJogo = String.format("O jogador %s desistiu... ;-;\n", getJogadorAtivo().getNome());
        isJogoTerminado = true;
        switchJogadorAtivo();   // Jogador que ganhou é o ativo
    }

    public boolean isHumano() {
        return getJogadorAtivo() instanceof Humano;
    }

    public String getNomeJogador() { return getJogadorAtivo().getNome(); }

    @Override
    public String toString() {
        if(isMinijogoDecorrer)
            return getJogadorAtivo().toString() + "\n" + jogoAtivo.toString();
        if(isJogoTerminado)
            return "O jogador " + ConsoleColors.GREEN + getJogadorAtivo().getNome() + ConsoleColors.RESET +
                    " é o grande Vencedor!!";
        return getJogadorAtivo().toString() + "\n" + imprimeTabuleiroJogo();
    }

    public String getResultadoJogo() { return infoJogo; }

    /* Métodos de controlo do Minijogo */
    public void lancaMinijogo() {
        jogoAtivo.inicializaJogo();
    }

    public String getPerguntaMinijogo() {
        return jogoAtivo.getPergunta();
    }

    public void setRespostaMinijogo(Scanner sc) {
        infoJogo = jogoAtivo.setResposta(sc);
    }

    public boolean isFinishedMinijogo() {
        if(jogoAtivo.isFinished()) {
            if (jogoAtivo.isVencedor())
                getJogadorAtivo().ganhaPecaEspecial();
            switchMinijogo();
            isMinijogoDecorrer = false;
            return true;
        }
        return false;
    }

    public void switchMinijogo() {
        if(jogoAtivo instanceof JogoCalculos)
            jogoAtivo = jogoPalavras;
        else
            jogoAtivo = jogoCalculos;
    }

    public void switchMinijogoExecucao() {
        isMinijogoDecorrer = !isMinijogoDecorrer;
    }

    /* METODOS PARA A FUNCIONALIDADE DE 'UNDO' */
    @Override
    public Memento getMemento() {
        return new Memento(new Object[] {
            jogador1, jogador2, jogadorAtivo, tabuleiro
        });
    }

    @Override
    public boolean setMemento(Memento m, int nJogadas) {
        if (getJogadorAtivo().usaCreditos(nJogadas)) {

            int creditosJogador1 = jogador1.getCreditos();
            int creditosJogador2 = jogador2.getCreditos();

            Object[] array = (Object[]) m.getSnapshot();
            jogador1 = (Player) array[0];
            jogador2 = (Player) array[1];
            jogadorAtivo = (JogadorAtivo) array[2];
            tabuleiro = (Tabuleiro) array[3];

            jogador1.setCreditos(creditosJogador1);
            jogador2.setCreditos(creditosJogador2);

            return true;
        }
        return false;
    }
}
