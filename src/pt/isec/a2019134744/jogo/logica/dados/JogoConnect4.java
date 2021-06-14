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

public class JogoConnect4 implements IMementoOriginator, Serializable {

    // Versao da Serializacao
    @Serial
    private static final long serialVersionUID = 10;

    // Info Jogo
    private String contexto;
    private StringBuilder contextoJogo;
    private List<String> infoReplay;

    // Dados Jogadores
    private Player jogador1;
    private Player jogador2;
    private JogadorAtivo jogadorAtivo;

    // Dados Tabuleiro Jogo
    private Tabuleiro tabuleiro;
    private boolean isJogoTerminado;
    private boolean isJogoEncerrado;

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
        isJogoEncerrado = false;
        jogador1 = null;
        jogador2 = null;
        jogadorAtivo = JogadorAtivo.none;
    }

    // Apenas sao pedidos os nomes dos jogadores humanos
    public boolean comecaJogo(String... jogadores) {
        // Verifica o numero de nomes de jogador
        if(jogadores.length > 2) {
            contexto = " Numero de jogadores inválido!";
            return false;
        }

        // Verifica se os nomes sao iguais -> se forem manda para trás
        if(jogadores.length == 2 && jogadores[0].equalsIgnoreCase(jogadores[1])) {
            contexto = " Os nomes introduzidos são iguais!\nIntroduza nomes diferentes para os 2 jogadores";
            return false;
        }

        StringBuilder sb = new StringBuilder();

        switch(jogadores.length) {
            case 1 -> {
                if (jogadores[0].equalsIgnoreCase("")) adicionaJogadores(sb);
                else adicionaJogadores(jogadores[0], sb);
            }
            case 2 -> adicionaJogadores(jogadores[0], jogadores[1], sb);
        }

        // Sortear o primeiro jogador
        int random = (int) (Math.random() * 2) + 1;
        if(random == 1)
            jogadorAtivo = JogadorAtivo.jogador1;
        else
            jogadorAtivo = JogadorAtivo.jogador2;

        sb.append("O primeiro a jogar é o jogador ").append(getJogadorAtivo().getNome()).append("!!\n");

        contexto = sb.toString();
        infoReplay.add(contexto);
        infoReplay.add("\nEspera\n");
        return true;
    }

    private void adicionaJogadores(String jogador1, String jogador2, StringBuilder sb) {
        this.jogador1 = new Humano(jogador1, Peca.Vermelha);
        this.jogador2 = new Humano(jogador2, Peca.Amarela);

        sb.append(ConsoleColors.PURPLE + "2 Jogadores Humanos:\n" + ConsoleColors.RESET).append(this.jogador1)
                .append("\n").append(this.jogador2).append("\n");
    }

    private void adicionaJogadores(String jogador1, StringBuilder sb) {
        this.jogador1 = new Humano(jogador1, Peca.Vermelha);
        this.jogador2 = new Virtual(2, Peca.Amarela);

        sb.append(ConsoleColors.PURPLE + "1 Jogador Humano e 1 Jogador Virtual:\n" + ConsoleColors.RESET)
                .append(this.jogador1).append("\n\n").append(this.jogador2).append("\n");
    }

    private void adicionaJogadores(StringBuilder sb) {
        this.jogador1 = new Virtual(1, Peca.Vermelha);
        this.jogador2 = new Virtual(2, Peca.Amarela);

        sb.append(ConsoleColors.PURPLE + "2 Jogadores Virtuais:\n" + ConsoleColors.RESET).append(this.jogador1)
                .append("\n\n").append(this.jogador2).append("\n");
    }

    public void switchJogadorAtivo() {
        if(jogadorAtivo == JogadorAtivo.jogador1)
            jogadorAtivo = JogadorAtivo.jogador2;
        else
            jogadorAtivo = JogadorAtivo.jogador1;
    }

    private Player getJogadorAtivo() {
        if(jogador1 == null || jogador2 == null)
            return new Humano("null", Peca.none);
        return jogadorAtivo == JogadorAtivo.jogador1 ? jogador1 : jogador2;
    }

    public String imprimeTabuleiroJogo() {
        return tabuleiro.imprimeTab();
    }

    public boolean jogaPeca(int nColuna) {
        // Consegue jogar peça
        if(tabuleiro.introduzPeca(nColuna, getJogadorAtivo().getPeca())) {
            // Se for humano vai incrementar contador de jogada
            if (isHumano())
                getJogadorAtivo().incJogada();
            contexto = String.format(ConsoleColors.PURPLE + "Foi introduzida uma peça " + getJogadorAtivo().getPeca().toString() +
                            " na posicao %d\n" + ConsoleColors.RESET + "%s", nColuna, imprimeTabuleiroJogo());
            infoReplay.add(contexto);
            infoReplay.add("\nespera\n");
            return true;
        }
        // Nao conseguiu jogar a peça -> pede novamente
        contexto = String.format(ConsoleColors.PURPLE + "Tentou introduzir uma peça " + getJogadorAtivo().getPeca().toString() +
                " numa posição inválida!\n" + ConsoleColors.RESET + "%s", imprimeTabuleiroJogo());
        infoReplay.add(contexto);
        infoReplay.add("\nespera\n");

        return false;
    }

    public boolean jogaPecaEspecial(int nColuna) {
        if(!getJogadorAtivo().jogaPecaEspecial()) {
            contexto = String.format(ConsoleColors.PURPLE + "Tentou jogar uma peça especial na posicao " + nColuna +
                    " mas nao tem nenhuma peça especial no seu bolso!\n" + ConsoleColors.RESET + "%s",
                    imprimeTabuleiroJogo());
            infoReplay.add(contexto);
            infoReplay.add("\nespera\n");
            return false;
        }
        if(!tabuleiro.removeColuna(nColuna)) {
            getJogadorAtivo().ganhaPecaEspecial();
            contexto = String.format(ConsoleColors.PURPLE + "Nao foi possivel limpar a coluna " + nColuna +
                            "! A peça especial foi reposta no seu bolso :P\n" + ConsoleColors.RESET + "%s",
                    imprimeTabuleiroJogo());
            return false;
        }
        if (isHumano())
            getJogadorAtivo().incJogada();
        contexto = String.format(ConsoleColors.PURPLE + "Foi jogada uma peça especial na coluna " + nColuna +
                        " que a deixou vazia!\n" + ConsoleColors.RESET + "%s", imprimeTabuleiroJogo());
        infoReplay.add(contexto);
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
            return a.getJogada();
        }
        return -1;
    }

    public void desiste() {
        contexto = String.format("O jogador " + ConsoleColors.GREEN + "%s" + ConsoleColors.RESET +
                " desistiu... ;-;\n%s\n\n", getJogadorAtivo().getNome(), imprimeTabuleiroJogo());
        infoReplay.add(contexto);
        // infoReplay.add("\nespera\n");
        isJogoTerminado = true;
        switchJogadorAtivo();   // Jogador que ganhou é o ativo
    }

    public boolean isHumano() {
        return getJogadorAtivo() instanceof Humano;
    }

    public boolean isHumano(Player jogador) {
        return jogador instanceof Humano;
    }

    public String getNomeJogador() {
        if(jogador1 == null || jogador2 == null)
            return "";
        return getJogadorAtivo().getNome();
    }

    public String getDadosJogador() {
        if(jogador1 == null || jogador2 == null)
            return "";

        if(jogadorAtivo == JogadorAtivo.jogador1)
            return jogador1.toString();
        return jogador2.toString();
    }

    @Override
    public String toString() {
        String feedback;
        if(isJogoTerminado) {
            feedback = String.format(ConsoleColors.PURPLE + "O jogador " +
                    getJogadorAtivo().getNome() + ConsoleColors.PURPLE +
                    " é o grande Vencedor!!\n"+ ConsoleColors.RESET + "%s", imprimeTabuleiroJogo());
            infoReplay.add(feedback);
            return feedback;
        }
        if(isJogoEncerrado) {
            return "O jogador " + getJogadorAtivo().getNome() + " encerrou o jogo!\n";
        }
        if(isMinijogoDecorrer) {
            if (jogoAtivo == null) {
                iniciaMinijogos();
            }
            return getJogadorAtivo().toString() + "\n" + jogoAtivo.toString();
        }
        infoReplay.add("\n" + ConsoleColors.GREEN + "Jogador Ativo:\n" + ConsoleColors.RESET);
        feedback = getJogadorAtivo().toString() + "\n";
        infoReplay.add(feedback);
        return feedback;

    }

    public String getContexto() { return contexto; }

    public int getCreditosJogAtivo() {
        if(jogador1 == null || jogador2 == null)
            return 0;
        return getJogadorAtivo().getCreditos();
    }

    public int getNPecasEspeciais() {
        return getJogadorAtivo().getnPecasEspeciais();
    }

    /* Métodos de controlo do Minijogo */
    public void lancaMinijogo() {
        if(jogoAtivo == null)
            iniciaMinijogos();
        jogoAtivo.inicializaJogo();
    }

    public String getPerguntaMinijogo() {
        return jogoAtivo.getPergunta();
    }

    public void setRespostaMinijogo(String resposta) {
        contextoJogo = new StringBuilder();
        contextoJogo.append(jogoAtivo.setResposta(resposta));
    }

    public boolean isFinishedMinijogo() {
        if(jogoAtivo.isFinished()) {
            if (isVencedorMinijogo()) {
                getJogadorAtivo().ganhaPecaEspecial();
                contexto = contextoJogo.append("Ganhou o Minijogo e consequentemente 1 peça especial!\n").toString();
                isMinijogoDecorrer = false;
                return true;
            }
            isMinijogoDecorrer = false;
            getJogadorAtivo().incJogada();
            contexto = contextoJogo.append("Infelizmente não foi desta que leva a peça especial, melhor sorte para a próxima!\n").toString();
            return true;
        }
        contexto = contextoJogo.toString();
        return false;
    }

    public boolean isVencedorMinijogo() {
        return jogoAtivo.isVencedor();
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

    public String getNomeMinijogo() {
        if(jogoAtivo == null)
            return "";
        if(jogoAtivo instanceof JogoCalculos)
            return "Jogo dos Cálculos";
        else
            return "Jogo das Palavras";
    }

    public void switchExecucaoMinijogo() {
        isMinijogoDecorrer = !isMinijogoDecorrer;
    }

    public void iniciaMinijogos() {
        jogoCalculos = new JogoCalculos();
        jogoPalavras = new JogoPalavras();
        jogoAtivo = jogoCalculos;
        isMinijogoDecorrer = false;
        repeatMinijogo = -1;
    }

    /* METODOS PARA A FUNCIONALIDADE DE 'UNDO' */
    @Override
    public Memento getMemento() {
        return new Memento(new Object[] {
            jogador1, jogador2, jogadorAtivo, tabuleiro, infoReplay
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setMemento(Memento m, int nJogadas) {
        if (getJogadorAtivo().usaCreditos(nJogadas)) {

            JogadorAtivo jogador = jogadorAtivo;
            int pecasEspJogador1 = jogador1.getnPecasEspeciais();
            int pecasEspJogador2 = jogador2.getnPecasEspeciais();
            int creditosJogador1 = jogador1.getCreditos();
            int creditosJogador2 = jogador2.getCreditos();

            Object[] array = (Object[]) m.getSnapshot();
            jogador1 = (Player) array[0];
            jogador2 = (Player) array[1];
            jogadorAtivo = (JogadorAtivo) array[2];
            tabuleiro = (Tabuleiro) array[3];
            infoReplay = (ArrayList<String>) array[4];

            jogador1.setCreditos(creditosJogador1);
            jogador2.setCreditos(creditosJogador2);
            jogador1.setnPecasEspeciais(pecasEspJogador1);
            jogador2.setnPecasEspeciais(pecasEspJogador2);
            int jogadas;
            if(jogador == JogadorAtivo.jogador1) {
                jogador1.resetJogada();
                jogador2.setNJogada(Math.max(jogador2.getJogada() - nJogadas, 0));
            } else {
                jogador2.resetJogada();
                jogador1.setNJogada(Math.max(jogador1.getJogada() - nJogadas, 0));
            }

            contexto = "Foram revertidas " + nJogadas + " jogadas!\n";

            return true;
        }
        return false;
    }

    /* GRAVAÇÃO DOS REPLAYS DO JOGO */
    public List<String> getReplay() {
        return infoReplay;
    }

    public void encerraJogo() {
        isJogoEncerrado = true;
        contexto = "O jogo foi encerrado pelo jogador " + getJogadorAtivo().getNome() + "\n";
    }
}
