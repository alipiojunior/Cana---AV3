// knapsack01greedy/Knapsack01Greedy.java
package knapsack01greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Item {
    String name;    
    double weight;  
    double value;   
    double density; 

    // Construtor do item
    public Item(String name, double weight, double value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.density = value / weight; 
    }

    @Override
    public String toString() {
        return "Item{name='" + name + "', weight=" + weight + "kg, value=R$" + value + ", density=" + String.format("%.2f", density) + "}";
    }
}

public class Knapsack01Greedy {

    public static void run() {
        System.out.println("\n--- Problema da Mochila 0/1 (Algoritmo Guloso) ---");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a capacidade máxima da mochila (em kg): ");
        double capacity = scanner.nextDouble();
        scanner.nextLine(); 

        System.out.print("Quantos objetos você deseja adicionar? ");
        int numItems = scanner.nextInt(); 
        scanner.nextLine(); 

        List<Item> items = new ArrayList<>(); 
        for (int i = 0; i < numItems; i++) {
            System.out.println("--- Item " + (i + 1) + " ---");
            System.out.print("Nome: ");
            String name = scanner.nextLine();
            System.out.print("Peso (kg): ");
            double weight = scanner.nextDouble();
            System.out.print("Valor (R$): ");
            double value = scanner.nextDouble();
            scanner.nextLine(); 
            items.add(new Item(name, weight, value));
        }

        solveKnapsackGreedy(capacity, items);
        scanner.close();
    }

    public static void solveKnapsackGreedy(double capacity, List<Item> items) {
        // Complexidade de Tempo: O(N log N) devido à ordenação. O(N) para iteração. Total: O(N log N).
        // Complexidade de Espaço: O(N) para armazenar os itens e a lista de itens selecionados.

        // Passo 1: Ordenar os itens pela densidade de valor em ordem decrescente.
        // Isso é crucial para a estratégia gulosa.
        Collections.sort(items, (item1, item2) -> Double.compare(item2.density, item1.density)); // Lambda para ordenação decrescente

        System.out.println("\nItens disponíveis (ordenados por densidade de valor):");
        for (Item item : items) {
            System.out.println(item);
        }

        double currentWeight = 0; 
        double currentValue = 0;  
        List<Item> selectedItems = new ArrayList<>(); 

        System.out.println("\n--- Processo de Seleção ---");
        // Passo 2: Iterar sobre os itens ordenados e adicioná-los à mochila se couberem.
        for (Item item : items) { // Itera sobre cada item (N iterações)
            System.out.println("Analisando item: " + item.name + " (Peso: " + item.weight + "kg, Valor: R$" + item.value + ")");
            if (currentWeight + item.weight <= capacity) { // O(1)
                selectedItems.add(item); // Adiciona o item à mochila
                currentWeight += item.weight; // Atualiza o peso total
                currentValue += item.value;   // Atualiza o valor total
                System.out.println("Escolha feita: Adicionado '" + item.name + "'. Peso atual: " + String.format("%.2f", currentWeight) + "kg, Valor atual: R$" + String.format("%.2f", currentValue));
            } else {
                System.out.println("Escolha ignorada: '" + item.name + "' excede a capacidade restante. Peso restante: " + String.format("%.2f", (capacity - currentWeight)) + "kg.");
            }
        }

        System.out.println("\n--- Resultado Final ---");
        System.out.println("Itens na mochila:");
        if (selectedItems.isEmpty()) {
            System.out.println("Nenhum item selecionado.");
        } else {
            for (Item item : selectedItems) {
                System.out.println("- " + item.name + " (Peso: " + String.format("%.2f", item.weight) + "kg, Valor: R$" + String.format("%.2f", item.value) + ")");
            }
        }
        System.out.println("Peso total na mochila: " + String.format("%.2f", currentWeight) + " kg");
        System.out.println("Valor total na mochila: R$" + String.format("%.2f", currentValue));
    }
}