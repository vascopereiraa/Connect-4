package pt.isec.a2019134744.jogo.logica.memento;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

public class CareTaker implements Serializable {

    // Versao da Serializacao
    @Serial
    private static final long serialVersionUID = 10;

    private final IMementoOriginator originator;
    private final Deque<Memento> stackUndo = new ArrayDeque<>();

    public CareTaker(IMementoOriginator originator) {
        this.originator = originator;
    }

    public void gravaMemento() {
        stackUndo.push(originator.getMemento());
    }

    public void undo(int nJogadas) {
        if (stackUndo.size() < nJogadas)
            return;

        Deque<Memento> anteriores = new ArrayDeque<>();
        for (int i = 0; i < nJogadas + 1; ++i)
            anteriores.push(stackUndo.pop());
        Memento anterior = anteriores.pop();
        if (!originator.setMemento(anterior, nJogadas)) {
            anteriores.push(anterior);
            for (int i = 0; i < anteriores.size(); ++i)
                stackUndo.push(anteriores.pop());
        }
    }

    public void reset() {
        stackUndo.clear();
    }

    public Memento getLastMemento() {
        if(!stackUndo.isEmpty())
            return stackUndo.removeLast();
        return null;
    }

    public boolean isLastMementoEmpty() {
        return stackUndo.isEmpty();
    }

    public int getJogadasGravadas() {
        return stackUndo.size();
    }

}
