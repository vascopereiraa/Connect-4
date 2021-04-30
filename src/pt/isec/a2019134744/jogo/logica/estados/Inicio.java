package pt.isec.a2019134744.jogo.logica.estados;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;

public class Inicio extends EstadoAdapter {

    public Inicio(JogoConnect4 jogoConnect4) {
        super(jogoConnect4);
    }

    @Override
    public IEstado comeca() {
        return new AguardaJogadores(super.getJogo());
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.Inicio;
    }
}
