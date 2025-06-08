// unboundedknapsack/UnboundedKnapsack.java
package unboundedknapsack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ItemUK {
    String name;  
    int weight;   
    int value;    

    public ItemUK(String name, int weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ItemUK{name='" + name + "', weight=" + weight + "kg, value=R$" + value + "}";
    }
}

public class UnboundedKnapsack {

    public static void run() {
        System.out.println("\n--- Problema da Mochila Ilimitado (Unbounded Knapsack) ---");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a capacidade máxima da mochila (em kg, valor inteiro): ");
        int capacity = scanner.nextInt(); 
        scanner.nextLine(); 

        System.out.print("Quantos objetos você deseja adicionar? ");
        int numItems = scanner.nextInt(); 
        scanner.nextLine();

        List<ItemUK> items = new ArrayList<>();
        for (int i = 0; i < numItems; i++) {
            System.out.println("--- Item " + (i + 1) + " ---");
            System.out.print("Nome: ");
            String name = scanner.nextLine();
            System.out.print("Peso (kg, valor inteiro): ");
            int weight = scanner.nextInt();
            System.out.print("Valor (R$, valor inteiro): ");
            int value = scanner.nextInt();
            scanner.nextLine(); 
            items.add(new ItemUK(name, weight, value)); 
        }
        solveUnboundedKnapsack(capacity, items);
        scanner.close();
    }

    public static void solveUnboundedKnapsack(int W, List<ItemUK> items) {
        // Complexidade de Tempo: O(N * W), onde N é o número de itens e W é a capacidade.
        // Complexidade de Espaço: O(W) para o array dp.

        int n = items.size(); 
        int[] dp = new int[W + 1]; 
        System.out.println("\n--- Preenchimento da Tabela de Programação Dinâmica ---");
        for (int w = 1; w <= W; w++) { // Itera sobre cada capacidade (de 1 até W)
            dp[w] = 0; 
            for (int i = 0; i < n; i++) { 
                ItemUK currentItem = items.get(i); 
                if (currentItem.weight <= w) { // O(1)
                    int valIfIncluded = currentItem.value + dp[w - currentItem.weight];
                    if (valIfIncluded > dp[w]) {
                        dp[w] = valIfIncluded; // O(1)
                        System.out.println("dp[" + w + "] atualizado com item '" + currentItem.name + "'. Novo valor: R$" + dp[w]);
                    } else {
                        System.out.println("dp[" + w + "] (R$" + dp[w] + ") não melhorado por item '" + currentItem.name + "' (tentativa: R$" + valIfIncluded + ")");
                    }
                } else {
                    System.out.println("Item '" + currentItem.name + "' (Peso: " + currentItem.weight + ") muito pesado para capacidade " + w + ". dp[" + w + "] permanece R$" + dp[w]);
                }
            }
        }

        System.out.println("\n--- Tabela DP Final ---");
        for (int w = 0; w <= W; w++) {
            System.out.println("dp[" + w + "] = R$" + dp[w]);
        }
        List<String> selectedItemsNames = new ArrayList<>();
        int currentW = W; 

        System.out.println("\n--- Rastreamento para Itens Selecionados ---");
        while (currentW > 0) { // O(W) no pior caso
            boolean itemFound = false;
            for (int i = 0; i < n; i++) {
                ItemUK item = items.get(i);
                if (currentW >= item.weight && dp[currentW] == item.value + dp[currentW - item.weight]) {
                    selectedItemsNames.add(item.name); 
                    currentW -= item.weight; 
                    System.out.println("Item '" + item.name + "' adicionado. Capacidade restante: " + currentW + "kg.");
                    itemFound = true;
                    break; 
                }
            }
            if (!itemFound && currentW > 0) {
                System.out.println("Não foi possível rastrear mais itens para capacidade " + currentW + "kg. Possível erro ou capacidade restante.");
                break;
            }
        }

        System.out.println("\n--- Resultado Final ---");
        System.out.println("Itens na mochila:");
        if (selectedItemsNames.isEmpty()) {
            System.out.println("Nenhum item selecionado.");
        } else {
            for (String itemName : selectedItemsNames) {
                System.out.println("- " + itemName);
            }
        }
        System.out.println("Valor total máximo na mochila: R$" + dp[W]);
    }
}