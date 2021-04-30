package pt.isec.a2019134744.jogo.logica.estados;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;

public class AguardaJogadores extends EstadoAdapter {

    public AguardaJogadores(JogoConnect4 jogoConnect4) {
        super(jogoConnect4);
    }

    @Override
    public IEstado comeca(String... jogadores) {
        super.getJogo().comecaJogo(jogadores);
        return new AguardaJogada(super.getJogo());
    }

    @Override
    public IEstado recomeca() {
        super.getJogo().resetJogo();
        return new Inicio(super.getJogo());
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.AguardaJogadores;
    }
}
