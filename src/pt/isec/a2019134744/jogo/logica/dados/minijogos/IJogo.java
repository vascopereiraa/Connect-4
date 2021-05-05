package pt.isec.a2019134744.jogo.logica.dados.minijogos;

import java.util.Scanner;

public interface IJogo {

    public void inicializaJogo();
    public String getPergunta();
    public String setResposta(Scanner sc);
    public boolean isFinished ();
    public boolean isVencedor();

}
