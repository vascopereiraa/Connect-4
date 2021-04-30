package pt.isec.a2019134744.jogo.logica.estados;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;

public abstract class EstadoAdapter implements IEstado {

    private JogoConnect4 jogoConnect4;

    protected EstadoAdapter(JogoConnect4 jogoConnect4) {
        this.jogoConnect4 = jogoConnect4;
    }

    @Override
    public IEstado comeca() {
        return this;
    }

    @Override
    public IEstado comeca(String... jogadores) {
        return this;
    }

    @Override
    public IEstado termina() {
        return this;
    }

    @Override
    public IEstado joga(int nColuna) {
        return this;
    }

    @Override
    public IEstado jogaEspecial(int nColuna) {
        return this;
    }

    @Override
    public IEstado desisteMinijogo() {
        return this;
    }

    @Override
    public IEstado jogaMinijogo() {
        return this;
    }

    @Override
    public IEstado terminaMinijogo() {
        return this;
    }

    @Override
    public IEstado recomeca() {
        return this;
    }

    public JogoConnect4 getJogo() {
        return jogoConnect4;
    }
}
