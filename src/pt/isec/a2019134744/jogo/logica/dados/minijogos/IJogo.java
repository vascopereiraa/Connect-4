package pt.isec.a2019134744.jogo.logica.dados.minijogos;

import java.util.Scanner;

public interface IJogo {

    void inicializaJogo();
    String getPergunta();
    String setResposta(String resposta);
    boolean isVencedor();
    boolean isFinished ();

}
