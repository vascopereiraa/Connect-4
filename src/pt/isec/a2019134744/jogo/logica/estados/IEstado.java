package pt.isec.a2019134744.jogo.logica.estados;

import java.util.Scanner;

public interface IEstado {

    // Transicoes de estado
    IEstado comeca();
    IEstado comeca(String... jogadores);
    IEstado termina();
    IEstado joga(int nColuna);
    IEstado jogaEspecial(int nColuna);
    IEstado desisteMinijogo();
    IEstado jogaMinijogo();
    IEstado respondeMinijogo(Scanner sc);
    IEstado recomeca();

    Situacao getSituacao();
}
