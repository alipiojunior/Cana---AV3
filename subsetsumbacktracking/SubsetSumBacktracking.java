// subsetsumbacktracking/SubsetSumBacktracking.java
package subsetsumbacktracking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubsetSumBacktracking {
    private static List<List<Integer>> solutions;
    private static boolean foundSolution;

    public static void run() {
        System.out.println("\n--- Problema da Soma dos Subconjuntos (Backtracking) ---");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o número de elementos no array: ");
        int n = scanner.nextInt(); 
        scanner.nextLine(); 

        int[] arr = new int[n]; 
        System.out.println("Digite os elementos do array (inteiros, positivos ou negativos):");
        for (int i = 0; i < n; i++) {
            System.out.print("Elemento " + (i + 1) + ": ");
            arr[i] = scanner.nextInt(); 
        }
        scanner.nextLine(); 

        solutions = new ArrayList<>(); 
        foundSolution = false; 
        List<Integer> currentSubset = new ArrayList<>(); 

        System.out.println("\n--- Processo de Backtracking ---");
        findSubsetsWithZeroSumBacktracking(arr, 0, 0, currentSubset);

        System.out.println("\n--- Resumo ---");
        if (!foundSolution) {
            System.out.println("Nenhum subconjunto encontrado cuja soma seja zero.");
        } else {
            System.out.println("Todas as soluções encontradas:");
            int count = 1;
            for (List<Integer> solution : solutions) {
                System.out.println("Solução " + count++ + ": " + solution);
            }
        }
        scanner.close();
    }

    private static void findSubsetsWithZeroSumBacktracking(int[] arr, int index, int currentSum, List<Integer> currentSubset) {
        // Complexidade de Tempo: O(2^N) no pior caso.
        // Complexidade de Espaço: O(N) para a pilha de recursão e o 'currentSubset'.

        // Caso base 1: Se a soma atual for zero e o subconjunto não for vazio
        if (currentSum == 0 && !currentSubset.isEmpty()) { // O(1)
            // Uma solução foi encontrada. Adiciona uma cópia do subconjunto atual à lista de soluções.
            solutions.add(new ArrayList<>(currentSubset)); // O(N) para copiar a lista
            foundSolution = true; // Marca que uma solução foi encontrada
            System.out.println("Solução encontrada: " + currentSubset + " (Soma: " + currentSum + ")");
            // Não retorna aqui imediatamente se quisermos encontrar TODAS as soluções.
            // No entanto, para otimizar, se a primeira solução for suficiente, podemos retornar true.
            // Para este problema, a questão pede para "encontrar subconjuntos", então vamos continuar.
        }

        // Caso base 2: Se o índice exceder o tamanho do array, não há mais elementos para considerar.
        if (index == arr.length) { // O(1)
            return; // Retorna (fim de um caminho de recursão)
        }

        // Mostrar o estado atual antes de ramificar
        System.out.println("No índice " + index + ", elemento: " + arr[index] + ". Soma atual: " + currentSum + ". Subconjunto: " + currentSubset);

        // Opção 1: Incluir o elemento atual no subconjunto
        currentSubset.add(arr[index]); // O(1)
        System.out.println("Incluindo " + arr[index] + ". Novo subconjunto: " + currentSubset);
        findSubsetsWithZeroSumBacktracking(arr, index + 1, currentSum + arr[index], currentSubset); // Chamada recursiva
        // Backtrack: Remove o elemento para explorar outros caminhos
        currentSubset.remove(currentSubset.size() - 1); // O(1)
        System.out.println("Backtracking: Removendo " + arr[index] + ". Subconjunto: " + currentSubset);

        // Opção 2: Não incluir o elemento atual no subconjunto
        System.out.println("Não incluindo " + arr[index] + ". Subconjunto: " + currentSubset);
        findSubsetsWithZeroSumBacktracking(arr, index + 1, currentSum, currentSubset); // Chamada recursiva
    }
}