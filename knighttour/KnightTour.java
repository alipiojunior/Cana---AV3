// knighttour/KnightTour.java
package knighttour;

import java.util.Scanner;

public class KnightTour {

    private static int N; 
    private static int[][] board; 
    private static int[] xMove = {2, 1, -1, -2, -2, -1, 1, 2}; 
    private static int[] yMove = {1, 2, 2, 1, -1, -2, -2, -1}; 

    public static void run() {
        System.out.println("\n--- Problema do Cavalo (Knight Tour) ---");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o tamanho do tabuleiro N (e.g., 8 para 8x8): ");
        N = scanner.nextInt(); 

        board = new int[N][N];
        initializeBoard(); 
        int startX = 0;
        int startY = 0;

        System.out.println("Iniciando o passeio do cavalo a partir de (" + startX + ", " + startY + ")");
        board[startX][startY] = 0; 

        if (!solveKnightTour(startX, startY, 1)) { 
            System.out.println("Nenhuma solução encontrada para o passeio do cavalo no tabuleiro " + N + "x" + N);
        } else {
            System.out.println("\nSolução encontrada para o Passeio do Cavalo:");
            printBoard();
        }
        scanner.close();
    }

    private static void initializeBoard() {
        // Complexidade de Tempo: O(N^2) para preencher a matriz.
        // Complexidade de Espaço: O(1)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = -1; 
            }
        }
    }

    private static boolean solveKnightTour(int x, int y, int moveNum) {
        // Complexidade de Tempo: Exponencial, O(8^(N^2)) no pior caso teórico, mas com podas.
        // Complexidade de Espaço: O(N^2) para a pilha de recursão e o tabuleiro.

        if (moveNum == N * N) { // O(1)
            return true; 
        }

        // Tenta todos os 8 movimentos possíveis para o cavalo a partir da posição atual (x, y)
        for (int i = 0; i < 8; i++) { // Itera pelos 8 movimentos possíveis (constante 8)
            int nextX = x + xMove[i]; // Calcula a próxima coordenada X
            int nextY = y + yMove[i]; // Calcula a próxima coordenada Y

            if (isValidMove(nextX, nextY)) { // O(1)
                board[nextX][nextY] = moveNum; 

                
                if (solveKnightTour(nextX, nextY, moveNum + 1)) {
                    return true; 
                } else {
                    // Se a chamada recursiva não levou a uma solução, backtrack
                    // Desfaz o movimento: marca a casa como não visitada (-1)
                    board[nextX][nextY] = -1; // O(1)
                }
            }
        }
        return false; // Nenhuma jogada válida a partir desta posição, backtrack
    }

    private static boolean isValidMove(int x, int y) {
        // Complexidade de Tempo: O(1)
        // Complexidade de Espaço: O(1)
        return (x >= 0 && x < N && y >= 0 && y < N && board[x][y] == -1);
    }

    private static void printBoard() {
        // Complexidade de Tempo: O(N^2) para imprimir o tabuleiro.
        // Complexidade de Espaço: O(1)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf("%3d ", board[i][j]); 
            }
            System.out.println();
        }
    }
}