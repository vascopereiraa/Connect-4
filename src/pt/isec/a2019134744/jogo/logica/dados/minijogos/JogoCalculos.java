package pt.isec.a2019134744.jogo.logica.dados.minijogos;

import java.util.Scanner;

public class JogoCalculos implements IJogo {

    private long start;
    private long end;
    private int nAcertos;
    private int resultado;
    private int nQuestao;

    private String questao;

    public JogoCalculos() {
        inicializaJogo();
    }

    public void inicializaJogo() {
        this.start = System.currentTimeMillis();
        this.end = System.currentTimeMillis();
        this.resultado = 0;
        this.nAcertos = 0;
        this.nQuestao = 1;
        setPergunta();
    }

    private void setPergunta() {
        int num1 = getRandomNum();
        int num2 = getRandomNum();
        char operador;
        switch ((int)(Math.random() * 4 + 1)) {
            case 1 -> { operador = '+'; resultado = num1 + num2; }
            case 2 -> { operador = '-'; resultado = num1 - num2; }
            case 3 -> { operador = '*'; resultado = num1 * num2; }
            default -> { operador = '/'; resultado = num1 / num2; }
        }
        questao = "Q" + nQuestao++ + ": Introduza o resultado de " + num1 + " " + operador + " " + num2 + ": ";
    }

    @Override
    public String getPergunta() {
        System.err.println(questao);
        return questao;
    }

    @Override
    public String setResposta(String resposta) {
        Integer number;
        try {
            assert resposta != null;
            number = Integer.parseInt(resposta);
        } catch (NumberFormatException nfe) {
            number = null;
        }

        if(number != null && number == resultado) {
            nAcertos++;
            setPergunta();
            return String.format("Resposta Certa!\nJá acertou em %d questões e passaram-se %d segundos\n",
                    nAcertos, (System.currentTimeMillis() - start) / 1000);
        }
        if(nAcertos != 0) {
            setPergunta();
            return String.format("Resposta Incorreta!\nJá acertou em %d questões e passaram-se %d segundos\n",
                    nAcertos, (System.currentTimeMillis() - start) / 1000);
        }
        setPergunta();
        return String.format("Resposta Incorreta!\nAinda não acertou nenhum calculo e passaram-se %d segundos\n",
                (System.currentTimeMillis() - start) / 1000);
    }

    @Override
    public boolean isFinished () {
        end = System.currentTimeMillis();
        return nAcertos == 5 || end - start > 30000;
    }

    @Override
    public boolean isVencedor() {
        return isFinished() && (end-start) <= 30000;
    }

    private int getRandomNum() {
        return (int)(Math.random() * 10) + 1;
        // return (int)(Math.random() * 100) + 1;
    }

    @Override
    public String toString() {
        return """
                Minijogo de Contas
                Acerte em 5 calculos em menos de 30 segundos para ganhar uma peça especial
                """;
    }

    public int getSegundos() {
        return 30;
    }

}
