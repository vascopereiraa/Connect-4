package pt.isec.a2019134744.jogo.logica.dados;

import java.io.Serializable;
import java.util.Arrays;

public class Tabuleiro implements Serializable {

    // TAMANHO DO TABULEIRO
    private final int NR_LINHAS = 6;
    private final int NR_COLUNAS = 7;

    private final Peca[][] tab;

    public Tabuleiro() {
        this.tab = new Peca[NR_LINHAS][NR_COLUNAS];
        resetTabuleiro();
    }

    public void resetTabuleiro() {
        for(Peca[] linha: tab)
            Arrays.fill(linha, Peca.none);
    }

    String imprimeTab() {
        StringBuilder sb = new StringBuilder("\n");
        for(Peca[] linha : tab) {
            for (Peca item : linha) {
                sb.append("|");
                sb.append(item == Peca.none ? "   " : item.getCor(" O "));
            }
            sb.append("|\n");
        }
        sb.append("$ 1 $ 2 $ 3 $ 4 $ 5 $ 6 $ 7 $\n");
        return sb.toString();
    }

    boolean introduzPeca(int nColuna, Peca peca) {
        if(isColunaInvalida(nColuna))
            return false;
        for(int i = tab.length - 1; i >= 0; --i)
            if(tab[i][nColuna - 1] == Peca.none) {
                tab[i][nColuna - 1] = peca;
                return true;
            }
        return false;
    }

    boolean verificaVencedor(Peca peca) {
        return verificaVencedorVertical(peca) ||
                verificaVencedorHorizontal(peca) ||
                verificaVencedorDiagonalCrescente(peca) ||
                verificaVencedorDiagonalDecrescente(peca);
    }

    private boolean verificaVencedorDiagonalDecrescente(Peca peca) {
        for(int linha = 0; linha < tab.length - 3; linha++){
            for(int coluna = 0; coluna < tab[0].length - 3; coluna++){
                if (tab[linha][coluna] == peca   &&
                        tab[linha + 1][coluna + 1] == peca &&
                        tab[linha + 2][coluna + 2] == peca &&
                        tab[linha + 3][coluna + 3] == peca){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verificaVencedorDiagonalCrescente(Peca peca) {
        for(int linha = 3; linha < tab.length; linha++){
            for(int coluna = 0; coluna < tab[0].length - 3; coluna++){
                if (tab[linha][coluna] == peca   &&
                        tab[linha - 1][coluna + 1] == peca &&
                        tab[linha - 2][coluna + 2] == peca &&
                        tab[linha - 3][coluna + 3] == peca){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verificaVencedorHorizontal(Peca peca) {
        for (Peca[] pecas : tab) {
            for (int coluna = 0; coluna < tab[0].length - 3; coluna++) {
                if (pecas[coluna] == peca &&
                        pecas[coluna + 1] == peca &&
                        pecas[coluna + 2] == peca &&
                        pecas[coluna + 3] == peca) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verificaVencedorVertical(Peca peca) {
        for(int linha = 0; linha < tab.length - 3; linha++){
            for(int coluna = 0; coluna < tab[0].length; coluna++){
                if (tab[linha][coluna] == peca   &&
                        tab[linha + 1][coluna] == peca &&
                        tab[linha + 2][coluna] == peca &&
                        tab[linha + 3][coluna] == peca){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeColuna(int nColuna) {
        if(isColunaInvalida(nColuna))
            return false;
        for(int i = 0; i < NR_LINHAS; ++i)
            tab[i][nColuna - 1] = Peca.none;
        return true;
    }

    private boolean isColunaInvalida(int nColuna) {
        return nColuna - 1 < 0 || nColuna - 1 >= NR_COLUNAS;
    }

}
