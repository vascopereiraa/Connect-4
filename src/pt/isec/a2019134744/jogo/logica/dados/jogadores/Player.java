package pt.isec.a2019134744.jogo.logica.dados.jogadores;

public abstract class Player {
    private final String nome;

    public Player(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
