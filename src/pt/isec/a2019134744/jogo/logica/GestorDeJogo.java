package pt.isec.a2019134744.jogo.logica;

import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.logica.memento.CareTaker;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GestorDeJogo {

    private final String REPLAYS_PATH = "." + File.separator + "res" + File.separator + "replays";
    private final String SAVES_PATH = "." + File.separator + "res" + File.separator + "saves";

    private MaquinaEstados maquinaEstados;
    private final CareTaker careTaker;

    boolean flagEncerraJogo = false;

    public GestorDeJogo() {
        this.maquinaEstados = new MaquinaEstados();
        this.careTaker = new CareTaker(maquinaEstados);
    }

    /* FUNCOES DO CARETAKER */
    public void undo(int nJogadas) {
        if(nJogadas > 0)
            careTaker.undo(nJogadas);
    }

    /* FUNCOES DA MAQUINA DE ESTADOS */
    public void inicia() {
        maquinaEstados.inicia();
    }

    public void comeca(String... jogadores) {
        maquinaEstados.comeca(jogadores);
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

    public void respondeMinijogo(Scanner sc) {
        maquinaEstados.respondeMinijogo(sc);
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

    /* FUNCOES DOS MINIJOGOS */
    public String getPerguntaMinijogo() {
        return maquinaEstados.getPerguntaMinijogo();
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
            if(f.getName().contains(".txt")) {
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
        File f = new File(REPLAYS_PATH + File.separator + "jogo_" + dtf.format(now) + ".txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, false))) {

            for (String s : infoReplay)
                bw.write(s);

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public List<String> verReplay(String ficheiro) {
        List<String> replay = new ArrayList<>();
        replay.add("Replay do ficheiro: " + ficheiro + "\n");

        File fich = new File(REPLAYS_PATH + File.separator + ficheiro);

        try (Scanner sc = new Scanner(new FileReader(fich))) {

            while(sc.hasNextLine())
                replay.add(sc.nextLine());

            return replay;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public String gravaJogo(String nomeSave) {

        File pasta = new File(SAVES_PATH);
        if(!pasta.exists()) {
            pasta.mkdirs();
        }

        if(nomeSave.toLowerCase().contains("."))
            nomeSave = nomeSave.split("\\.")[0];


        File f = new File(SAVES_PATH + File.separator + nomeSave + ".dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {

            oos.writeObject(maquinaEstados);

        } catch (IOException e) {
            return "Nao foi possivel efetuar a gravação do jogo";
        }

        return "Jogo gravado com o nome " + nomeSave + " com sucesso!";
    }

    public String carregaJogo(String nomeSave) {

        File f = new File(SAVES_PATH + File.separator + nomeSave + ".dat");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {

            maquinaEstados = (MaquinaEstados) ois.readObject();
            if(maquinaEstados != null)
                careTaker.reset();

        } catch (IOException | ClassNotFoundException e) {
            return "Não foi possivel carregar o save " + nomeSave + " indicado!";
        }

        return "";
    }
}
