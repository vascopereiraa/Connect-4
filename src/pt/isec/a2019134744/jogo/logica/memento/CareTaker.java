package pt.isec.a2019134744.jogo.logica.memento;

import java.util.ArrayDeque;
import java.util.Deque;

public class CareTaker {
    private final IMementoOriginator originator;
    private Deque<Memento> stackUndo = new ArrayDeque<>();

    public CareTaker(IMementoOriginator originator) {
        this.originator = originator;
    }

    public void gravaMemento() {
        stackUndo.push(originator.getMemento());
    }


    // todo: Momento em que é verificado se é humano e se tem creditos suficientes para andar para tras
    public void undo() {
        if(stackUndo.isEmpty())
            return;

        Memento anterior = stackUndo.pop();
        originator.setMemento(anterior);
    }

    // todo: reset -> quando o jogo da reset o memento (design) tambem da reset
}
