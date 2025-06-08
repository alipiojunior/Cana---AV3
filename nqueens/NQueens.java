// nqueens/NQueens.java
package nqueens;

import java.util.Scanner;

public class NQueens {

    private static int N; 
    private static int[] board; 

    public static void run() {
        System.out.println("\n--- Problema das N Damas (N Queens) ---");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o valor de N (tamanho do tabuleiro): ");
        N = scanner.nextInt(); 
        board = new int[N]; 

        if (N < 1) {
            System.out.println("N deve ser um número inteiro positivo.");           
        }
        if (N == 2 || N == 3) {
             System.out.println("Para N = 2 ou N = 3, não há solução possível.");
        }

        if (!solveNQueens(0)) {
            System.out.println("Nenhuma solução encontrada para N = " + N);
        }
        scanner.close();
    }

    private static boolean solveNQueens(int row) {
        // Complexidade de Tempo: O(N!) no pior caso, mas com podas.
        // Complexidade de Espaço: O(N) para a pilha de recursão e o array 'board'.

        if (row == N) {
            System.out.println("Solução encontrada para N = " + N + ":");
            printBoard(); 
            return true; 
        }

        // Tenta colocar a rainha na linha atual (row) em cada coluna possível (col)
        for (int col = 0; col < N; col++) { 
            if (isSafe(row, col)) { // O(row) para verificar segurança
                board[row] = col; // Coloca a rainha na posição (row, col)
                if (solveNQueens(row + 1)) { // Chamada recursiva para a próxima linha
                    return true; // Se encontrou solução, propaga o true
                }
                // Se a chamada recursiva acima retornou false, precisamos fazer um bcktrack
                System.out.println("Backtracking: Removendo rainha da linha " + row + ", coluna " + col); // Mostrar backtrack
            }
        }
        return false; // Nenhuma posição segura encontrada nesta linha, backtrack
    }

    private static boolean isSafe(int row, int col) {
        // Complexidade de Tempo: O(row), pois itera pelas rainhas já colocadas.
        // Complexidade de Espaço: O(1)

        for (int i = 0; i < row; i++) {
            // Verifica se há outra rainha na mesma coluna
            if (board[i] == col) {
                return false; // Não é seguro, mesma coluna
            }
            if (Math.abs(row - i) == Math.abs(col - board[i])) {
                return false; // Não é seguro, mesma diagonal
            }
        }
        return true; // É seguro colocar a rainha aqui
    }

    private static void printBoard() {
        // Complexidade de Tempo: O(N^2) para imprimir o tabuleiro.
        // Complexidade de Espaço: O(1)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i] == j) {
                    System.out.print("Q "); // Rainha nesta posição
                } else {
                    System.out.print(". "); // Posição vazia
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}