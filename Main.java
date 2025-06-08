// Main.java
import java.util.Scanner;
import eightqueens.EightQueens;
import nqueens.NQueens;
import knapsack01greedy.Knapsack01Greedy;
import knapsack01dp.Knapsack01DP;
import unboundedknapsack.UnboundedKnapsack;
import knighttour.KnightTour;
import subsetsumbruteforce.SubsetSumBruteForce;
import subsetsumbacktracking.SubsetSumBacktracking;
import beachhouselibrary.BeachHouseLibrary;
import travellingsalesman.TravelingSalesmanBruteForce;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n--- Menu de Algoritmos ---");
            System.out.println("1. Problema das 8 Damas (Eight Queens)");
            System.out.println("2. Problema das N Damas (N Queens)");
            System.out.println("3. Problema da Mochila 0/1 (Guloso)");
            System.out.println("4. Problema da Mochila 0/1 (Programação Dinâmica)");
            System.out.println("5. Problema da Mochila Ilimitado");
            System.out.println("6. Problema do Cavalo (Knight Tour)");
            System.out.println("7. Problema da Soma dos Subconjuntos (Força Bruta)");
            System.out.println("8. Problema da Soma dos Subconjuntos (Backtracking)");
            System.out.println("9. Casa de Praia dos Algoritmos (Problema da Mochila com PDFs)");
            System.out.println("10. Problema do Caixeiro-Viajante (Força Bruta)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    EightQueens.run();
                    break;
                case 2:
                    NQueens.run();
                    break;
                case 3:
                    Knapsack01Greedy.run();
                    break;
                case 4:
                    Knapsack01DP.run();
                    break;
                case 5:
                    UnboundedKnapsack.run();
                    break;
                case 6:
                    KnightTour.run();
                    break;
                case 7:
                    SubsetSumBruteForce.run();
                    break;
                case 8:
                    SubsetSumBacktracking.run();
                    break;
                case 9:
                    BeachHouseLibrary.run();
                    break;
                case 10:
                    TravelingSalesmanBruteForce.run();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);

        scanner.close();
    }
}