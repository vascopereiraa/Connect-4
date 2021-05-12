package pt.isec.a2019134744.jogo.logica.estados;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;

import java.io.Serializable;

public class AguardaJogadores extends EstadoAdapter implements Serializable {

    public AguardaJogadores(JogoConnect4 jogoConnect4) {
        super(jogoConnect4);
    }

    @Override
    public IEstado comeca(String... jogadores) {
        // verifica se os nomes sao iguais -> se forem manda para trás
        if(jogadores.length == 2 && jogadores[0].equalsIgnoreCase(jogadores[1]))
            return this;
        super.getJogo().resetJogo();
        if(!super.getJogo().comecaJogo(jogadores))
            return this;
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
