package pt.isec.a2019134744.jogo.ui.grafico.dados;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import pt.isec.a2019134744.jogo.ui.grafico.Colors;
import pt.isec.a2019134744.jogo.ui.grafico.resources.FontLoader;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class PecaTabuleiro extends StackPane {

    private Circle circle;
    private Text text;

    private Color cor;
    private final int coluna;
    PropertyChangeSupport propertyChangeSupport;

    public PecaTabuleiro(double radiusCircle, int coluna, String num) {
        this.coluna = coluna;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        createView(radiusCircle, num);
        registerListeners();
    }

    public void addObserver(String property, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(property, listener);
    }

    private void createView(double radiusCircle, String num) {
        circle = new Circle();
        text = new Text(num);
        text.setFont(FontLoader.loadFont(APP_FONT, 18));
        text.setVisible(false);
        circle.setRadius(radiusCircle);
        cor = Color.LIGHTGRAY;
        circle.setFill(cor);
        this.getChildren().addAll(circle, text);

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
        cor = (Color) circle.getFill();
        if(cor == Colors.getDefaultColor())
            circle.setFill(Colors.getColorHover());
    }

    public void resetCor() {
        circle.setFill(cor);
    }

    public void mostraColuna() {
        if(circle.getFill() == Color.BLACK)
            text.setFill(Color.WHITE);
        else
            text.setFill(Color.BLACK);
        text.setVisible(true);
    }

    public void escondeColuna() {
        text.setVisible(false);
    }

    public void setCor(Color cor) {
        this.cor = cor;
        circle.setFill(cor);
        if(cor == Color.WHITE) {
            circle.setStroke(Color.BLACK);
            circle.setRadius(circle.getRadius() == VistaTabuleiro.radiusCircle ? circle.getRadius() - 2.5 : circle.getRadius());
            circle.setStrokeWidth(2.5);
        } else {
            circle.setRadius(circle.getRadius() != 25 ? circle.getRadius() + 2.5 : circle.getRadius());
            circle.setStrokeWidth(0.0);
        }
    }

}
