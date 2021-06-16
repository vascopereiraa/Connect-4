package pt.isec.a2019134744.jogo.logica;

import pt.isec.a2019134744.jogo.logica.dados.Tabuleiro;
import pt.isec.a2019134744.jogo.logica.dados.jogadores.JogadorAtivo;
import pt.isec.a2019134744.jogo.logica.dados.jogadores.Player;
import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.logica.memento.CareTaker;
import pt.isec.a2019134744.jogo.logica.memento.Memento;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GestorDeJogo {

    public static final String REPLAYS_PATH = "." + File.separator + "res" + File.separator + "replays";
    public static final String SAVES_PATH = "." + File.separator + "res" + File.separator + "saves";
    public static final String RES_PATH = "." + File.separator + "res";

    private MaquinaEstados maquinaEstados;
    private final CareTaker careTaker;

    private CareTaker leitorReplays;

    boolean flagEncerraJogo = false;

    public GestorDeJogo() {
        this.maquinaEstados = new MaquinaEstados();
        this.careTaker = new CareTaker(maquinaEstados);
        this.leitorReplays = null;
    }

    /* FUNCOES DO CARETAKER */
    public void undo(int nJogadas) {
        if(nJogadas > 0)
            careTaker.undo(nJogadas);
    }

    public int getJogadasGravadas() {
        return careTaker.getJogadasGravadas();
    }

    /* FUNCOES DA MAQUINA DE ESTADOS */
    public void inicia() {
        maquinaEstados.inicia();
    }

    public boolean comeca(String... jogadores) {
        boolean res = maquinaEstados.comeca(jogadores);
        if(!res)
            careTaker.gravaMemento();
        return res;
    }

    public void termina() {
        flagEncerraJogo = true;
        maquinaEstados.termina();
    }

    public void joga(int nColuna) {
        if(maquinaEstados.joga(nColuna))
            careTaker.gravaMemento();
    }

    public void jogaEspecial(int nColuna) {
        if(maquinaEstados.jogaEspecial(nColuna))
            careTaker.gravaMemento();
    }

    public void desiste() {
        maquinaEstados.desiste();
    }

    public void jogaMinijogo() {
        maquinaEstados.jogaMinijogo();
    }

    public void respondeMinijogo(String resposta) {
        maquinaEstados.respondeMinijogo(resposta);
    }

    public void recomeca() {
        careTaker.reset();
        maquinaEstados.recomeca();
    }

    public Situacao getSituacao() {
        Situacao situacao = maquinaEstados.getSituacao();
        if (situacao == Situacao.FimJogo && !flagEncerraJogo)
            gravaReplay();
        return situacao;
    }

    /* FUNCOES DO JOGO */
    public String getInfoJogo() {
        return maquinaEstados.getInfoJogo();
    }

    public String getContexto() {
        return maquinaEstados.getContexto();
    }

    public boolean isHumano() { return maquinaEstados.isHumano(); }

    public String getTabuleiro() {
        return maquinaEstados.getTabuleiro();
    }

    public String getNomeJogador() { return maquinaEstados.getNomeJogador(); }

    public String getDadosJogador() {
        return maquinaEstados.getDadosJogador();
    }

    public int getCreditosJogAtivo() {
        return maquinaEstados.getCreditosJogAtivo();
    }

    public String getNomeMinijogo() {
        return maquinaEstados.getNomeMinijogo();
    }

    public int getNPecasEspeciais() {
        return maquinaEstados.getNPecasEspeciais();
    }

    public int getSegundos() {
        return maquinaEstados.getSegundos();
    }

    /* FUNCOES DOS MINIJOGOS */
    public String getPerguntaMinijogo() {
        return maquinaEstados.getPerguntaMinijogo();
    }

    public void iniciaMinijogos() {
        maquinaEstados.iniciaMinijogos();
    }

    /* FUNCOES DE SAVES */
    public List<String> getListaReplays() {
        List<String> lista = new ArrayList<>();
        File dir = new File(REPLAYS_PATH);

        if(!dir.exists())
            return lista;

        String older = "";
        long lastMod = Long.MAX_VALUE;
        for(File f : Objects.requireNonNull(dir.listFiles())) {
            if(f.getName().contains(".dat")) {
                lista.add(f.getName());
                if(f.lastModified() < lastMod) {
                    older = f.getName();
                    lastMod = f.lastModified();
                }
            }
        }

        Collections.sort(lista);

        lista.add(older);
        return lista;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void gravaReplay() {
        List<String> infoReplay = maquinaEstados.getReplay();
        List<String> listaReplays = getListaReplays();

        File pasta = new File(REPLAYS_PATH);
        if(!pasta.exists()) {
            pasta.mkdirs();
        }

        if(listaReplays.size() >= 6) {
            new File(REPLAYS_PATH + File.separator + listaReplays.get(listaReplays.size() - 1)).delete();
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        File f = new File(REPLAYS_PATH + File.separator + "jogo_" + dtf.format(now) + ".dat");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {

            oos.writeObject(careTaker);

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public String verReplay(String ficheiro) {

        if(leitorReplays == null) {
            File fich = new File(REPLAYS_PATH + File.separator + ficheiro);

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fich))) {

                leitorReplays = (CareTaker) ois.readObject();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        Object[] array = (Object[]) leitorReplays.getLastMemento().getSnapshot();
        if(array == null) {
            leitorReplays = null;
            return null;
        }
        JogadorAtivo jogadorAtivo = (JogadorAtivo) array[2];
        Player jogador;
        if(jogadorAtivo == JogadorAtivo.jogador1)
            jogador = (Player) array[0];
        else
            jogador = (Player) array[1];
        Tabuleiro tab = (Tabuleiro) array[3];

        return jogador.toString() + "\n" + tab.imprimeTab() + "\n";
    }

    public boolean carregaReplay(File hFile) {

        if(leitorReplays == null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(hFile))) {

                leitorReplays = (CareTaker) ois.readObject();
                return true;

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Object> getReplay() {
        Memento lastMemento = leitorReplays.getLastMemento();
        if(lastMemento == null) {
            leitorReplays = null;
            return null;
        }
        Object[] array = (Object[]) lastMemento.getSnapshot();
        if(array == null) {
            leitorReplays = null;
            return null;
        }
        JogadorAtivo jogadorAtivo = (JogadorAtivo) array[2];
        Player jogador;
        if(jogadorAtivo == JogadorAtivo.jogador1)
            jogador = (Player) array[0];
        else
            jogador = (Player) array[1];
        Tabuleiro tab = (Tabuleiro) array[3];

        List<Object> dados = new ArrayList<>();
        dados.add(tab);
        dados.add(jogador);
        return dados;
    }

    public boolean getLeitorReplaysEstado() {
        return leitorReplays.isLastMementoEmpty();
    }

    public void resetReplays() {
        leitorReplays = null;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public String gravaJogo(String nomeSave) {

        File pasta = new File(SAVES_PATH);
        if(!pasta.exists()) {
            pasta.mkdirs();
        }

        if(nomeSave.toLowerCase().contains("."))
            nomeSave = nomeSave.split("\\.")[0];


        return gravajogo(new File(SAVES_PATH + File.separator + nomeSave + ".dat"));
    }

    public String gravajogo(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

            oos.writeObject(maquinaEstados);

        } catch (IOException e) {
            return "Nao foi possivel efetuar a gravação do jogo";
        }

        return "Jogo gravado com o nome " + file.getName() + " com sucesso!";
    }

    public List<String> getListaSaves() {
        List<String> lista = new ArrayList<>();
        File dir = new File(SAVES_PATH);

        if(!dir.exists())
            return lista;

        for(File f : Objects.requireNonNull(dir.listFiles())) {
            if(f.getName().contains(".dat")) {
                lista.add(f.getName());
            }
        }

        Collections.sort(lista);

        return lista;
    }

    public String carregaJogo(String nomeSave) {
        return carregaJogo(new File(SAVES_PATH + File.separator + nomeSave));
    }

    public String carregaJogo(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            maquinaEstados = (MaquinaEstados) ois.readObject();
            if(maquinaEstados != null)
                careTaker.reset();
            return "";

        } catch (IOException | ClassNotFoundException e) {
            return "Não foi possivel carregar o save " + file.getName() + " indicado!";
        }
    }
}
