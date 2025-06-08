// travellingsalesman/TravellingSalesman.java
package travellingsalesman;

import java.util.ArrayList;
import java.util.List;

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

    public static void run() {
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

        Cidade caixeiro = listaCoordenadas.get(0);
        List<Cidade> outrasCidades = new ArrayList<>(listaCoordenadas.subList(1, listaCoordenadas.size()));

        List<Cidade> currentRoute = new ArrayList<>();
        currentRoute.add(caixeiro);

        findBestRoute(caixeiro, outrasCidades, currentRoute, 0.0);

        if (melhorRotaOtimizada != null) {
            System.out.println("A melhor rota é:");
            for (Cidade cidade : melhorRotaOtimizada) {
                System.out.print(cidade.nome);
                if (melhorRotaOtimizada.indexOf(cidade) < melhorRotaOtimizada.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println(" -> " + caixeiro.nome);
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

            if (currentDistanceSquared < menorDistanciaOtimizada) {
                menorDistanciaOtimizada = currentDistanceSquared;
                melhorRotaOtimizada = new ArrayList<>(currentTour);
            }
            return;
        }

        for (int i = 0; i < remainingCities.size(); i++) {
            Cidade nextCity = remainingCities.get(i);
            double distanceToNext = calcularEuclidesQuadrado(currentTour.get(currentTour.size() - 1), nextCity);

            List<Cidade> newRemainingCities = new ArrayList<>(remainingCities);
            newRemainingCities.remove(i);

            List<Cidade> newCurrentTour = new ArrayList<>(currentTour);
            newCurrentTour.add(nextCity);

            findBestRoute(startCity, newRemainingCities, newCurrentTour, currentDistanceSquared + distanceToNext);
        }
    }
}
