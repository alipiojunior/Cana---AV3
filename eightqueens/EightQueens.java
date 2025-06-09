// eightqueens/EightQueens.java
package eightqueens;

public class EightQueens {

    private static final int BOARD_SIZE = 8;
    private static int[] board;

    public static void run() {
        System.out.println("\n--- Problema das 8 Damas (Eight Queens) ---");
        board = new int[BOARD_SIZE]; 
        solveEightQueens(0); 
    }

    private static boolean solveEightQueens(int row) {
        // Complexidade de Tempo: O(N!) no pior caso, mas com podas é mais eficiente.
        // Complexidade de Espaço: O(N) para a pilha de recursão e o array 'board'.

        if (row == BOARD_SIZE) {
            System.out.println("Solução encontrada:");
            printBoard(); 
            return true; 
        }

        for (int col = 0; col < BOARD_SIZE; col++) { 
            if (isSafe(row, col)) { 
                board[row] = col; 
                if (solveEightQueens(row + 1)) { 
                    return true; 
                }
                System.out.println("Backtracking: Removendo rainha da linha " + row + ", coluna " + col); // Mostrar backtrack
            }
        }
        return false; // Nenhuma posição segura encontrada nesta linha, backtrack
    }

    private static boolean isSafe(int row, int col) {
        // Complexidade de Tempo: O(row), pois itera pelas rainhas já colocadas.
        // Complexidade de Espaço: O(1)

        for (int i = 0; i < row; i++) {
            if (board[i] == col) {
                return false; 
            }
            if (Math.abs(row - i) == Math.abs(col - board[i])) {
                return false; 
            }
        }
        return true; 
    }

    private static void printBoard() {
        // Complexidade de Tempo: O(N^2) para imprimir o tabuleiro.
        // Complexidade de Espaço: O(1)
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i] == j) {
                    System.out.print("Q "); 
                } else {
                    System.out.print(". "); 
                }
            }
            System.out.println(); 
        }
        System.out.println();
    }
}