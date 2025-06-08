// subsetsumbruteforce/SubsetSumBruteForce.java
package subsetsumbruteforce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubsetSumBruteForce {

    public static void run() {
        System.out.println("\n--- Problema da Soma dos Subconjuntos (Força Bruta) ---");
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

        findSubsetsWithZeroSum(arr);
        scanner.close();
    }

    public static void findSubsetsWithZeroSum(int[] arr) {
        // Complexidade de Tempo: O(2^N * N), onde N é o número de elementos.
        // Complexidade de Espaço: O(N) para armazenar o subconjunto atual.

        int n = arr.length; 
        boolean foundSolution = false; 

        System.out.println("\n--- Gerando e Verificando Subconjuntos ---");
        for (int i = 1; i < (1 << n); i++) { 
            List<Integer> currentSubset = new ArrayList<>();
            int currentSum = 0; 

            System.out.print("Subconjunto baseado no binário " + Integer.toBinaryString(i) + ": [");
            
            for (int j = 0; j < n; j++) { // O(N) para construir o subconjunto e calcular a soma
                if ((i & (1 << j)) > 0) { // O(1) - Verifica o bit
                    currentSubset.add(arr[j]); // Adiciona o elemento ao subconjunto
                    currentSum += arr[j]; // Adiciona o elemento à soma
                    System.out.print(arr[j] + (j < n -1 ? ", " : ""));
                }
            }
            System.out.print("] -> Soma: " + currentSum);
            if (currentSum == 0) { // O(1)
                System.out.println(" --- SOLUÇÃO ENCONTRADA!");
                foundSolution = true;
            } else {
                System.out.println(" (Soma não é zero)");
            }
        }

        System.out.println("\n--- Resumo ---");
        if (!foundSolution) {
            System.out.println("Nenhum subconjunto encontrado cuja soma seja zero.");
        } else {
            System.out.println("Processo concluído. Todas as soluções encontradas foram exibidas acima.");
        }
    }
}