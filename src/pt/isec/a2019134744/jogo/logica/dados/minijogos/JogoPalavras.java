package pt.isec.a2019134744.jogo.logica.dados.minijogos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JogoPalavras implements IJogo {

    private static final List<String> PALAVRAS_DATASET = new ArrayList<>();

    static {
        File f = new File("palavras.txt");
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                PALAVRAS_DATASET.add(sc.nextLine().toLowerCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> palavras;
    private int maxSegundos;
    private int nCertas;
    private long start;
    private long end;

    public JogoPalavras() {
        inicializaJogo();
    }

    public void inicializaJogo() {
        this.palavras = new ArrayList<>();
        this.nCertas = 0;
        this.start = System.currentTimeMillis();
        getPalavras();
    }

    private void getPalavras() {
        // Selecionar palavras do Dataset
        while (palavras.size() < 5) {
            String palavra = PALAVRAS_DATASET.get(getRandomNum(PALAVRAS_DATASET.size()));
            if (!palavras.contains(palavra)) {
                palavras.add(palavra);
                maxSegundos += palavra.length();
            }
        }

        // Numero de espacos em branco entre as palavras
        maxSegundos += 4;

        // Tempo = 1/2 * nr_caracteres (incl. espacos)
        maxSegundos /= 2;
    }

    private int getRandomNum(int max) {
        return (int)(Math.random() * max);
    }

    @Override
    public String getPergunta() {
        StringBuilder sb = new StringBuilder("Escreva corretamente as seguintes" +
                " palavras em " + maxSegundos + " segundos:\n");

        for(String palavra : palavras)
            sb.append(palavra).append(" ");

        // Eliminar o utlimo espaço do conjunto de palavras
        sb.deleteCharAt(sb.toString().length() - 1);

        return sb.append("\n").toString();
    }

    @Override
    public String setResposta(Scanner sc) {
        String[] input = sc.nextLine().toLowerCase().split("\\s");
        for(String palavra : input)
            if(palavras.contains(palavra)) {
                palavras.remove(palavra);
                nCertas++;
            }
        if(nCertas != 0)
            return String.format("Ja acertou %d palavras e passaram-se %d segundos\n",
                    nCertas, (System.currentTimeMillis() - start) / 1000);
        return String.format("Ainda nao acertou em nenhuma palavra e passaram-se %d " +
                "segundos\n", (System.currentTimeMillis() - start) / 1000);
    }

    @Override
    public boolean isFinished() {
        end = System.currentTimeMillis();
        return nCertas == 5;
    }

    @Override
    public boolean isVencedor() {
        return nCertas == 5 && palavras.size() == 0 &&
                end-start <= (maxSegundos * 1000L);
    }

    @Override
    public String toString() {
        return """
                Minijogo das Palavras
                Reescreva as seguintes palavras antes que o tempo de esgote para ganhar uma
                peca especial
                """;
    }

}
