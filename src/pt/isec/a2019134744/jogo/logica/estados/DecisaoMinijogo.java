package pt.isec.a2019134744.jogo.logica.estados;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;

public class DecisaoMinijogo extends EstadoAdapter {

    public DecisaoMinijogo(JogoConnect4 jogoConnect4) {
        super(jogoConnect4);
    }

    @Override
    public IEstado desisteMinijogo() {
        super.getJogo().switchMinijogoExecucao();
        return new AguardaJogada(super.getJogo());
    }

    @Override
    public IEstado jogaMinijogo() {
        return new JogaMinijogo(super.getJogo());
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.DecisaoMinijogo;
    }
}
