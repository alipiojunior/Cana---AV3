// beachhouselibrary/BeachHouseLibrary.java
package beachhouselibrary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class PDFFile {
    String name;      
    int pages;        
    double sizeMB;    
    double density;   

    public PDFFile(String name, int pages, double sizeMB) {
        this.name = name;
        this.pages = pages;
        this.sizeMB = sizeMB;
        this.density = pages / sizeMB; 
    }

    @Override
    public String toString() {
        return "PDFFile{name='" + name + "', pages=" + pages + ", size=" + String.format("%.2f", sizeMB) + "MB, density=" + String.format("%.2f", density) + "}";
    }
}

public class BeachHouseLibrary {

    public static void run() {
        System.out.println("\n--- Casa de Praia dos Algoritmos (Mochila Gulosa para PDFs) ---");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a capacidade do pendrive (em MBs): ");
        double pendriveCapacity = scanner.nextDouble(); 
        scanner.nextLine();

        System.out.print("Quantos arquivos PDF você tem? ");
        int numPdfs = scanner.nextInt(); 
        scanner.nextLine(); 

        List<PDFFile> pdfFiles = new ArrayList<>(); 
        for (int i = 0; i < numPdfs; i++) {
            System.out.println("--- PDF " + (i + 1) + " ---");
            System.out.print("Nome: ");
            String name = scanner.nextLine();
            System.out.print("Número de páginas: ");
            int pages = scanner.nextInt();
            System.out.print("Tamanho (MBs): ");
            double size = scanner.nextDouble();
            scanner.nextLine(); 
            pdfFiles.add(new PDFFile(name, pages, size)); 
        }
        maximizePagesInPendrive(pendriveCapacity, pdfFiles); 
        scanner.close();
    }


    /**
     * Resolve o problema da "mochila" para PDFs usando um algoritmo guloso.
     * Maximiza a quantidade de páginas no pendrive.
     *
     * @param capacity Capacidade máxima do pendrive em MBs.
     * @param pdfs     Lista de arquivos PDF disponíveis.
     */
    public static void maximizePagesInPendrive(double capacity, List<PDFFile> pdfs) {
        // Complexidade de Tempo: O(N log N) devido à ordenação. O(N) para iteração. Total: O(N log N).
        // Complexidade de Espaço: O(N) para armazenar os PDFs e a lista de PDFs selecionados.

        // Passo 1: Ordenar os PDFs pela densidade de páginas (páginas/tamanho) em ordem decrescente.
        Collections.sort(pdfs, (pdf1, pdf2) -> Double.compare(pdf2.density, pdf1.density)); // Lambda para ordenação decrescente

        System.out.println("\nPDFs disponíveis (ordenados por densidade de páginas):");
        for (PDFFile pdf : pdfs) {
            System.out.println(pdf);
        }

        double currentSize = 0; // Tamanho atual ocupado no pendrive
        int totalPages = 0;     // Total de páginas na mochila
        List<PDFFile> selectedPdfs = new ArrayList<>(); // Lista de PDFs selecionados

        System.out.println("\n--- Processo de Seleção ---");
        // Passo 2: Iterar sobre os PDFs ordenados e adicioná-los se couberem.
        for (PDFFile pdf : pdfs) { // Itera sobre cada PDF (N iterações)
            System.out.println("Analisando PDF: " + pdf.name + " (Tamanho: " + String.format("%.2f", pdf.sizeMB) + "MB, Páginas: " + pdf.pages + ")");
            // Verifica se o PDF pode ser adicionado sem exceder a capacidade do pendrive
            if (currentSize + pdf.sizeMB <= capacity) { // O(1)
                selectedPdfs.add(pdf); // Adiciona o PDF
                currentSize += pdf.sizeMB; // Atualiza o tamanho total
                totalPages += pdf.pages;   // Atualiza o total de páginas
                System.out.println("Escolha feita: Adicionado '" + pdf.name + "'. Tamanho atual: " + String.format("%.2f", currentSize) + "MB, Páginas totais: " + totalPages);
            } else {
                System.out.println("Escolha ignorada: '" + pdf.name + "' excede a capacidade restante. Espaço restante: " + String.format("%.2f", (capacity - currentSize)) + "MB.");
            }
        }

        System.out.println("\n--- Resultado Final ---");
        System.out.println("PDFs no pendrive:");
        if (selectedPdfs.isEmpty()) {
            System.out.println("Nenhum PDF selecionado.");
        } else {
            for (PDFFile pdf : selectedPdfs) {
                System.out.println("- " + pdf.name + " (Tamanho: " + String.format("%.2f", pdf.sizeMB) + "MB, Páginas: " + pdf.pages + ")");
            }
        }
        System.out.println("Tamanho total ocupado: " + String.format("%.2f", currentSize) + " MB");
        System.out.println("Total de páginas lidas: " + totalPages);
    }
}