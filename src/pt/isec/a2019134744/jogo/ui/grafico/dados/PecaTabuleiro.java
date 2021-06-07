package pt.isec.a2019134744.jogo.ui.grafico.dados;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class PecaTabuleiro extends Circle {

    private Color cor;
    private final int coluna;
    PropertyChangeSupport propertyChangeSupport;

    public PecaTabuleiro(double radiusCircle, int coluna) {
        this.coluna = coluna;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        createView(radiusCircle);
        registerListeners();
    }

    public void addObserver(String property, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(property, listener);
    }

    private void createView(double radiusCircle) {
        this.setRadius(radiusCircle);
        cor = Color.LIGHTGRAY;
        this.setFill(cor);
    }

    private void registerListeners() {
        this.setOnMouseEntered(e -> {
            propertyChangeSupport.firePropertyChange(ENTRA_RATO, null, coluna);
        });

        this.setOnMouseExited(e -> {
            propertyChangeSupport.firePropertyChange(SAI_RATO, null, coluna);
        });

        this.setOnMouseClicked(e -> {
            propertyChangeSupport.firePropertyChange(JOGA_PECA, null, coluna);
        });
    }

    public void alteraCor() {
        cor = (Color) this.getFill();
        if(cor == Color.LIGHTGRAY)
            this.setFill(Color.LIGHTPINK);
    }

    public void resetCor() {
        this.setFill(cor);
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public void alteraTamanho(int newRadius) {
        this.setRadius(newRadius);
    }

}
