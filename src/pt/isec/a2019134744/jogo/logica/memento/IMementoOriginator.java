package pt.isec.a2019134744.jogo.logica.memento;

import java.util.Deque;

public interface IMementoOriginator {
    Memento getMemento();
    boolean setMemento(Memento m, int nJogadas);
}
