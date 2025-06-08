// knapsack01dp/Knapsack01DP.java
package knapsack01dp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ItemDP {
    String name;  
    int weight;   
    int value;    

    public ItemDP(String name, int weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ItemDP{name='" + name + "', weight=" + weight + "kg, value=R$" + value + "}";
    }
}

public class Knapsack01DP {

    public static void run() {
        System.out.println("\n--- Problema da Mochila 0/1 (Programação Dinâmica) ---");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a capacidade máxima da mochila (em kg, valor inteiro): ");
        int capacity = scanner.nextInt(); 
        scanner.nextLine(); 

        System.out.print("Quantos objetos você deseja adicionar? ");
        int numItems = scanner.nextInt(); 
        scanner.nextLine(); 

        List<ItemDP> items = new ArrayList<>(); 
        for (int i = 0; i < numItems; i++) {
            System.out.println("--- Item " + (i + 1) + " ---");
            System.out.print("Nome: ");
            String name = scanner.nextLine();
            System.out.print("Peso (kg, valor inteiro): ");
            int weight = scanner.nextInt();
            System.out.print("Valor (R$, valor inteiro): ");
            int value = scanner.nextInt();
            scanner.nextLine(); 
            items.add(new ItemDP(name, weight, value)); 
        }

        solveKnapsackDP(capacity, items); 
        scanner.close();
    }

    public static void solveKnapsackDP(int W, List<ItemDP> items) {
        // Complexidade de Tempo: O(N * W), onde N é o número de itens e W é a capacidade.
        // Complexidade de Espaço: O(N * W) para a matriz dp.

        int n = items.size(); 
        int[][] dp = new int[n + 1][W + 1]; // Matriz de programação dinâmica.

        System.out.println("\n--- Preenchimento da Tabela de Programação Dinâmica ---");

        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) { 
                if (i == 0 || w == 0) {
                    dp[i][w] = 0; // O(1)
                } else {
                    ItemDP currentItem = items.get(i - 1); 
                    if (currentItem.weight <= w) { // O(1) 
                        dp[i][w] = Math.max(dp[i - 1][w], currentItem.value + dp[i - 1][w - currentItem.weight]); // O(1)
                        System.out.println("dp[" + i + "][" + w + "] = max(Não incluir: " + dp[i-1][w] + ", Incluir: " + (currentItem.value + dp[i-1][w - currentItem.weight]) + ") = " + dp[i][w] + " (Item: " + currentItem.name + ")");
                    } else {
                        // Se o item atual for muito pesado para a capacidade atual, não o incluímos.
                        // O valor máximo é o mesmo que com os itens anteriores e a mesma capacidade.
                        dp[i][w] = dp[i - 1][w]; // O(1)
                        System.out.println("dp[" + i + "][" + w + "] = Não inclui item: " + currentItem.name + " (muito pesado). Valor: " + dp[i][w]);
                    }
                }
            }
        }

        System.out.println("\n--- Tabela DP Final ---");
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                System.out.printf("%4d", dp[i][w]);
            }
            System.out.println();
        }

        // Recuperar os itens selecionados
        List<ItemDP> selectedItems = new ArrayList<>();
        int res = dp[n][W]; // O valor máximo total
        int w = W; // Capacidade restante para rastrear

        System.out.println("\n--- Rastreamento para Itens Selecionados ---");
        // i representa o número de itens considerados (de n para 1)
        for (int i = n; i > 0 && res > 0; i--) { // O(N) no pior caso para rastrear
            if (res != dp[i - 1][w]) { // O(1)
                ItemDP item = items.get(i - 1); // Pega o item atual (lembre-se do índice)
                selectedItems.add(item); 
                res -= item.value; 
                w -= item.weight;  
                System.out.println("Escolha feita: Item '" + item.name + "' (Valor: R$" + item.value + ", Peso: " + item.weight + "kg) foi incluído.");
            } else {
                System.out.println("Escolha ignorada: Item '" + items.get(i-1).name + "' não foi incluído.");
            }
        }

        System.out.println("\n--- Resultado Final ---");
        System.out.println("Itens na mochila:");
        if (selectedItems.isEmpty()) {
            System.out.println("Nenhum item selecionado.");
        } else {
            int totalWeight = 0;
            int totalValue = 0;
            for (int i = selectedItems.size() - 1; i >= 0; i--) { // Imprime na ordem correta
                ItemDP item = selectedItems.get(i);
                System.out.println("- " + item.name + " (Peso: " + item.weight + "kg, Valor: R$" + item.value + ")");
                totalWeight += item.weight;
                totalValue += item.value;
            }
            System.out.println("Peso total na mochila: " + totalWeight + " kg");
            System.out.println("Valor total na mochila: R$" + totalValue);
        }
        System.out.println("Valor máximo possível: R$" + dp[n][W]);
    }
}