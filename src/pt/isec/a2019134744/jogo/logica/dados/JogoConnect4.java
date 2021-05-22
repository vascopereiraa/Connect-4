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

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JogoConnect4 implements IMementoOriginator, Serializable {

    // Info Jogo
    private String infoJogo;
    private final List<String> infoReplay;

    // Dados Jogadores
    private Player jogador1;
    private Player jogador2;
    private JogadorAtivo jogadorAtivo;

    // Dados Tabuleiro Jogo
    private Tabuleiro tabuleiro;
    private boolean isJogoTerminado;

    // Minijogos
    private transient IJogo jogoCalculos;
    private transient IJogo jogoPalavras;
    private transient IJogo jogoAtivo;
    private transient boolean isMinijogoDecorrer;
    private transient int repeatMinijogo;

    public JogoConnect4() {
        this.infoReplay = new ArrayList<>();
        this.tabuleiro = new Tabuleiro();
        iniciaMinijogos();
        resetJogo();
    }

    public void resetJogo() {
        infoReplay.clear();
        tabuleiro.resetTabuleiro();
        isJogoTerminado = false;
        jogador1 = null;
        jogador2 = null;
        jogadorAtivo = JogadorAtivo.none;
    }

    // Apenas sao pedidos os nomes dos jogadores humanos
    public boolean comecaJogo(String... jogadores) {
        if(jogadores.length > 2)
            return false;
        if(jogadores[0].equalsIgnoreCase(""))
            adicionaJogadores();
        else
            switch(jogadores.length) {
                case 1 -> adicionaJogadores(jogadores[0]);
                case 2 -> adicionaJogadores(jogadores[0], jogadores[1]);
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

        infoReplay.add(ConsoleColors.PURPLE + "2 Jogadores Humanos:\n" + ConsoleColors.RESET + this.jogador1 + "\n" + this.jogador2 + "\n");
    }

    private void adicionaJogadores(String jogador1) {
        this.jogador1 = new Humano(jogador1, Peca.Vermelha);
        this.jogador2 = new Virtual(2, Peca.Amarela);

        infoReplay.add("1 Jogador Humano e 1 Jogador Virtual:\n" + this.jogador1 + "\n\n" + this.jogador2 + "\n");
    }

    private void adicionaJogadores() {
        this.jogador1 = new Virtual(1, Peca.Vermelha);
        this.jogador2 = new Virtual(2, Peca.Amarela);

        infoReplay.add("2 Jogadores Virtuais:\n" + this.jogador1 + "\n\n" + this.jogador2 + "\n");
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
            infoJogo = String.format(ConsoleColors.PURPLE + "Foi introduzida uma peça " + getJogadorAtivo().getPeca().toString() +
                            " na posicao %d\n" + ConsoleColors.RESET + "%s", nColuna, imprimeTabuleiroJogo());
            infoReplay.add(infoJogo);
            infoReplay.add("\nespera\n");
            return true;
        }
        // Nao conseguiu jogar a peça -> pede novamente
        infoJogo = String.format(ConsoleColors.PURPLE + "Tentou introduzir uma peça " + getJogadorAtivo().getPeca().toString() +
                " numa posição inválida!\n" + ConsoleColors.RESET + "%s", imprimeTabuleiroJogo());
        infoReplay.add(infoJogo);
        infoReplay.add("\nespera\n");

        return false;
    }

    public boolean jogaPecaEspecial(int nColuna) {
        if(!getJogadorAtivo().jogaPecaEspecial()) {
            infoJogo = String.format(ConsoleColors.PURPLE + "Tentou jogar uma peça especial na posicao " + nColuna +
                    " mas nao tem nenhuma peça especial no seu bolso!\n" + ConsoleColors.RESET + "%s",
                    imprimeTabuleiroJogo());
            infoReplay.add(infoJogo);
            infoReplay.add("\nespera\n");
            return false;
        }
        if(!tabuleiro.removeColuna(nColuna)) {
            getJogadorAtivo().ganhaPecaEspecial();
            infoJogo = String.format(ConsoleColors.PURPLE + "Nao foi possivel limpar a coluna " + nColuna +
                            "! A peça especial foi reposta no seu bolso :P\n" + ConsoleColors.RESET + "%s",
                    imprimeTabuleiroJogo());
            return false;
        }
        if (isHumano())
            ((Humano) getJogadorAtivo()).incNJogada();
        infoJogo = String.format(ConsoleColors.PURPLE + "Foi jogada uma peça especial na coluna " + nColuna +
                        " que a deixou vazia!\n" + ConsoleColors.RESET + "%s", imprimeTabuleiroJogo());
        infoReplay.add(infoJogo);
        infoReplay.add("\nespera\n");
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
        infoJogo = String.format("O jogador " + ConsoleColors.GREEN + "%s" + ConsoleColors.RESET +
                " desistiu... ;-;\n%s", getJogadorAtivo().getNome(), imprimeTabuleiroJogo());
        infoReplay.add(infoJogo);
        infoReplay.add("\nespera\n");
        isJogoTerminado = true;
        switchJogadorAtivo();   // Jogador que ganhou é o ativo
    }

    public boolean isHumano() {
        return getJogadorAtivo() instanceof Humano;
    }

    public boolean isHumano(Player jogador) {
        return jogador instanceof Humano;
    }

    public String getNomeJogador() { return getJogadorAtivo().getNome(); }

    @Override
    public String toString() {
        if(isMinijogoDecorrer)
            return getJogadorAtivo().toString() + "\n" + jogoAtivo.toString();
        String feedback;
        if(isJogoTerminado) {
            feedback = String.format("O jogador " +
                    ConsoleColors.GREEN + getJogadorAtivo().getNome() + ConsoleColors.RESET +
                    " é o grande Vencedor!!\n%s", imprimeTabuleiroJogo());
            infoReplay.add(feedback);
            infoReplay.add("\nespera\n");
            return feedback;
        }
        infoReplay.add("\n" + ConsoleColors.GREEN + "Jogador Ativo:\n" + ConsoleColors.RESET);
        feedback = getJogadorAtivo().toString() + "\n";
        infoReplay.add(feedback);
        return feedback;
    }

    public String getResultadoJogo() { return infoJogo; }

    /* Métodos de controlo do Minijogo */
    public void lancaMinijogo() {
        if(jogoAtivo == null)
            iniciaMinijogos();
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

        // Garantir que o mesmo minijogo não é jogado 2x pelo msm Humano
        if(isHumano(jogador1) && isHumano(jogador2)) {
            repeatMinijogo++;
            if (repeatMinijogo == 2) {
                repeatMinijogo = -1;
                switchMinijogo();
            }
        }
    }

    public void switchMinijogoExecucao() {
        isMinijogoDecorrer = !isMinijogoDecorrer;
    }

    private void iniciaMinijogos() {
        jogoCalculos = new JogoCalculos();
        jogoPalavras = new JogoPalavras();
        jogoAtivo = jogoCalculos;
        isMinijogoDecorrer = false;
        repeatMinijogo = 0;
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

    /* GRAVAÇÃO DOS REPLAYS DO JOGO */
    public List<String> getReplay() {
        return infoReplay;
    }
}
