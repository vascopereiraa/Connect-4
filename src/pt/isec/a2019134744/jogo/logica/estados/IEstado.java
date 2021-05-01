package pt.isec.a2019134744.jogo.logica.estados;

public interface IEstado {

    // Transicoes de estado
    IEstado comeca();
    IEstado comeca(String... jogadores);
    IEstado termina();
    IEstado joga(int nColuna);
    IEstado jogaEspecial(int nColuna);
    IEstado desisteMinijogo();
    IEstado jogaMinijogo();
    IEstado terminaMinijogo();
    IEstado recomeca();

    Situacao getSituacao();
}
