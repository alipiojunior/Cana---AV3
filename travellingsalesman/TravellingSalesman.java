// travellingsalesman/TravellingSalesman.java
package travellingsalesman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class City {
    String name; 
    int x;      
    int y;       

    public City(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return name + " (" + x + ", " + y + ")";
    }
}

public class TravellingSalesman {

    private static List<City> cities;
    private static double minTourDistance = Double.MAX_VALUE; 
    private static List<City> bestTour = null; 

    public static void run() {
        System.out.println("\n--- Problema do Caixeiro-Viajante (Força Bruta) ---");
        initializeCities(); 

        System.out.println("Cidades disponíveis:");
        for (City city : cities) {
            System.out.println("- " + city);
        }

        // A fixação da cidade de partida reduz as permutações de N! para (N-1)!
        City startCity = cities.get(0);
        // Lista de cidades a serem permutadas (todas, exceto a cidade de partida)
        List<City> remainingCities = new ArrayList<>(cities.subList(1, cities.size()));

        System.out.println("\n--- Gerando e Avaliando Rotas ---");
        // Chama a função para encontrar o tour mais curto
        findShortestTour(startCity, remainingCities, new ArrayList<>(), 0.0);

        System.out.println("\n--- Resultado Final ---");
        if (bestTour != null) {
            System.out.println("Melhor rota encontrada: ");
            // Imprime a rota completa, incluindo o retorno à cidade de origem
            for (int i = 0; i < bestTour.size(); i++) {
                System.out.print(bestTour.get(i).name);
                if (i < bestTour.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println(" -> " + startCity.name); // Retorno à cidade de origem

            System.out.println("Distância total da melhor rota: " + String.format("%.2f", minTourDistance));
        } else {
            System.out.println("Não foi possível encontrar uma rota (problema com as cidades ou lógica).");
        }
    }

    private static void initializeCities() {
        cities = new ArrayList<>();
        Random rand = new Random(42); 
        for (int i = 0; i < 10; i++) {
            cities.add(new City("Cidade " + (i + 1), rand.nextInt(100), rand.nextInt(100)));
        }
    }

    private static double calculateDistance(City city1, City city2) {
        // Complexidade de Tempo: O(1)
        // Complexidade de Espaço: O(1)
        return Math.sqrt(Math.pow(city1.x - city2.x, 2) + Math.pow(city1.y - city2.y, 2));
    }

    private static void findShortestTour(City startCity, List<City> remainingCities, List<City> currentTour, double currentDistance) {
        // Complexidade de Tempo: No pior caso, O(N! * N) devido à geração de permutações e cálculo da distância.
        // Complexidade de Espaço: O(N) para a pilha de recursão e as listas temporárias.

        if (currentTour.isEmpty()) {
            currentTour.add(startCity); 
        }

        if (remainingCities.isEmpty()) { // O(1)
            currentDistance += calculateDistance(currentTour.get(currentTour.size() - 1), startCity); // O(1)

            System.out.print("Rota avaliada: ");
            for (int i = 0; i < currentTour.size(); i++) {
                System.out.print(currentTour.get(i).name);
                if (i < currentTour.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println(" -> " + startCity.name);
            System.out.println("Distância total desta rota: " + String.format("%.2f", currentDistance));

            if (currentDistance < minTourDistance) { // O(1)
                minTourDistance = currentDistance; 
                bestTour = new ArrayList<>(currentTour); // O(N) para copiar a lista
                System.out.println(">>>> NOVA MELHOR ROTA ENCONTRADA! Distância: " + String.format("%.2f", minTourDistance));
            }
            return; 
        }

        // Itera sobre as cidades restantes para formar novas permutações
        for (int i = 0; i < remainingCities.size(); i++) { // Itera N-1 vezes
            City nextCity = remainingCities.get(i); // Próxima cidade a ser visitada

            // Cria novas listas para a recursão para evitar modificações indesejadas
            List<City> newRemainingCities = new ArrayList<>(remainingCities); // O(N) para copiar a lista
            newRemainingCities.remove(i); // Remove a cidade escolhida para a próxima recursão

            List<City> newCurrentTour = new ArrayList<>(currentTour); // O(N) para copiar a lista
            newCurrentTour.add(nextCity); // Adiciona a próxima cidade ao tour atual

            // Calcula a distância do último item do currentTour até nextCity
            double distanceToNext = calculateDistance(currentTour.get(currentTour.size() - 1), nextCity); // O(1)

            System.out.println("Tentando ir de " + currentTour.get(currentTour.size() - 1).name + " para " + nextCity.name + ". Distância: " + String.format("%.2f", distanceToNext));

            // Chamada recursiva com a nova rota e distância
            findShortestTour(startCity, newRemainingCities, newCurrentTour, currentDistance + distanceToNext); // Chamada recursiva
        }
    }
}