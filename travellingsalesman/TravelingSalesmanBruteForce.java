// travellingsalesman/TravellingSalesman.java
package travellingsalesman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Cidade {
    String nome;
    int x;
    int y;

    public Cidade(String nome, int x, int y) {
        this.nome = nome;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return nome + ": (" + x + ", " + y + ")";
    }
}

public class TravelingSalesmanBruteForce {

    private static List<Cidade> listaCoordenadas = new ArrayList<>();
    private static double menorDistanciaOtimizada = Double.MAX_VALUE;
    private static List<Cidade> melhorRotaOtimizada = null;

    // NOVO MÉTODO RUN PARA SER CHAMADO DO MAIN
    public static void run() {
        System.out.println("\n--- Problema do Caixeiro-Viajante (Força Bruta) ---");

        // Limpa as listas e variáveis estáticas para garantir que não haja resíduos de execuções anteriores
        listaCoordenadas.clear();
        menorDistanciaOtimizada = Double.MAX_VALUE;
        melhorRotaOtimizada = null;

        listaCoordenadas.add(new Cidade("Caixeiro", 0, 1));
        listaCoordenadas.add(new Cidade("A", 1, 0));
        listaCoordenadas.add(new Cidade("B", 1, 1));
        listaCoordenadas.add(new Cidade("C", 0, 2));
        listaCoordenadas.add(new Cidade("D", 1, 2));
        listaCoordenadas.add(new Cidade("E", 2, 0));
        listaCoordenadas.add(new Cidade("F", 2, 1));
        listaCoordenadas.add(new Cidade("G", 0, 3));
        listaCoordenadas.add(new Cidade("H", 1, 3));
        listaCoordenadas.add(new Cidade("I", 2, 3));

        System.out.println("Cidades disponíveis:");
        for (Cidade city : listaCoordenadas) {
            System.out.println("- " + city.nome + " (" + city.x + ", " + city.y + ")");
        }


        Cidade caixeiro = listaCoordenadas.get(0);
        List<Cidade> outrasCidades = new ArrayList<>(listaCoordenadas.subList(1, listaCoordenadas.size()));

        List<Cidade> currentRoute = new ArrayList<>();
        currentRoute.add(caixeiro);

        System.out.println("\n--- Gerando e Avaliando Rotas ---");
        findBestRoute(caixeiro, outrasCidades, currentRoute, 0.0);

        System.out.println("\n--- Resultado Final ---");
        if (melhorRotaOtimizada != null) {
            System.out.println("A melhor rota é:");
            for (Cidade cidade : melhorRotaOtimizada) {
                System.out.print(cidade.nome); // Imprime o nome da cidade
                if (melhorRotaOtimizada.indexOf(cidade) < melhorRotaOtimizada.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println(" -> " + caixeiro.nome); // Retorno à cidade de origem
            System.out.println("Com distância total de: " + String.format("%.2f", Math.sqrt(menorDistanciaOtimizada)));
        } else {
            System.out.println("Nenhuma rota encontrada.");
        }
    }


    private static double calcularEuclidesQuadrado(Cidade c1, Cidade c2) {
        double dx = c1.x - c2.x;
        double dy = c1.y - c2.y;
        return (dx * dx) + (dy * dy);
    }

    private static void findBestRoute(Cidade startCity, List<Cidade> remainingCities, List<Cidade> currentTour, double currentDistanceSquared) {
        if (remainingCities.isEmpty()) {
            currentDistanceSquared += calcularEuclidesQuadrado(currentTour.get(currentTour.size() - 1), startCity);

            System.out.print("Rota avaliada: ");
            for (int i = 0; i < currentTour.size(); i++) {
                System.out.print(currentTour.get(i).nome);
                if (i < currentTour.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println(" -> " + startCity.nome);
            System.out.println("Distância total desta rota (quadrado): " + String.format("%.2f", currentDistanceSquared)); // Exibe o quadrado aqui

            if (currentDistanceSquared < menorDistanciaOtimizada) {
                menorDistanciaOtimizada = currentDistanceSquared;
                melhorRotaOtimizada = new ArrayList<>(currentTour);
                System.out.println(">>>> NOVA MELHOR ROTA ENCONTRADA! Distância (quadrado): " + String.format("%.2f", menorDistanciaOtimizada));
            }
            return;
        }

        for (int i = 0; i < remainingCities.size(); i++) {
            Cidade nextCity = remainingCities.get(i);

            double distanceToNext = calcularEuclidesQuadrado(currentTour.get(currentTour.size() - 1), nextCity);

            // Não mostrar o "Tentando ir de" para cada sub-tentativa recursiva, pois fica muito verboso.
            // A exibição da rota avaliada já faz isso.
            // System.out.println("Tentando ir de " + currentTour.get(currentTour.size() - 1).nome + " para " + nextCity.nome + ". Distância (quadrado): " + String.format("%.2f", distanceToNext));


            List<Cidade> newRemainingCities = new ArrayList<>(remainingCities);
            newRemainingCities.remove(i);

            List<Cidade> newCurrentTour = new ArrayList<>(currentTour);
            newCurrentTour.add(nextCity);

            findBestRoute(startCity, newRemainingCities, newCurrentTour, currentDistanceSquared + distanceToNext);
        }
    }
}