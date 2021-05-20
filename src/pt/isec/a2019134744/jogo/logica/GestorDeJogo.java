package pt.isec.a2019134744.jogo.logica;

import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.logica.memento.CareTaker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class GestorDeJogo {

    private final String REPLAYS_PATH = "./res/replays";

    private MaquinaEstados maquinaEstados;
    private final CareTaker careTaker;

    public GestorDeJogo() {
        this.maquinaEstados = new MaquinaEstados();
        this.careTaker = new CareTaker(maquinaEstados);
    }

    /* FUNCOES DO CARETAKER */
    public void undo(int nJogadas) {
        careTaker.undo(nJogadas);
    }

    /* FUNCOES DA MAQUINA DE ESTADOS */
    public void comeca() {
        maquinaEstados.comeca();
    }

    public void comeca(String... jogadores) {
        maquinaEstados.comeca(jogadores);
    }

    public void termina() {
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

    public void desisteMinijogo() {
        maquinaEstados.desisteMinijogo();
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
        return maquinaEstados.getSituacao();
    }

    /* FUNCOES DO JOGO */
    public String getInfoJogo() {
        return maquinaEstados.getInfoJogo();
    }

    public String getResultadoJogo() { return maquinaEstados.getResultadoJogo(); }

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

        String older = "";
        long lastMod = Long.MAX_VALUE;
        for(File f : Objects.requireNonNull(dir.listFiles())) {
            if(f.getName().contains(".txt")) {
                lista.add(f.getName());
                if(f.lastModified() < lastMod) {
                    older = new String(f.getName());
                    lastMod = f.lastModified();
                }
            }
        }

        lista.add(older);
        return lista;
    }

    public void gravaReplay() {
        List<String> infoReplay = maquinaEstados.getReplay();
        List<String> listaReplays = getListaReplays();

        File pasta = new File(REPLAYS_PATH);
        if(!pasta.exists())
            pasta.mkdirs();

        if(listaReplays.size() >= 6) {
            new File(REPLAYS_PATH + "/" + listaReplays.get(listaReplays.size() - 1)).delete();
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        File f = new File(REPLAYS_PATH + "/jogo_" + dtf.format(now) + ".txt");

        try {
            FileWriter fw = new FileWriter(f, false);
            BufferedWriter bw = new BufferedWriter(fw);

            for (String s : infoReplay)
                bw.write(s);

            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public String listaReplays() {

        return "Aqui";
    }

    public List<String> verReplay(String ficheiro) {
        List<String> replay = new ArrayList<>();
        replay.add("Replay do ficheiro: " + ficheiro + "\n");

        try {
            File fich = new File(REPLAYS_PATH + "/" + ficheiro);
            FileReader fr = new FileReader(fich);
            Scanner sc = new Scanner(fr);

            while(sc.hasNextLine())
                replay.add(sc.nextLine());

            sc.close();
            return replay;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String gravaJogo(String fich) {
        
        return "Aguarde!";
    }
}
