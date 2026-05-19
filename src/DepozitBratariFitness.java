import java.util.Scanner;

public class DepozitBratariFitness {

    // Campurile structurii 

    static String[]  brand            = new String[100];
    static String[]  tipProdus        = new String[100];
    static String[]  compatibilitate  = new String[100];
    static int[]     durataBaterie    = new int[100];      // zile (camp intreg)
    static double[]  rezistentaApa   = new double[100];   // ATM
    static double[]  pret             = new double[100];   // RON
    static int n = 0;


    // 1. Citire vector

    static void citire(Scanner sc) {
        System.out.print("Introduceti numarul de bratari fitness: ");
        n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Bratara fitness " + (i + 1) + " ---");
            System.out.print("Brand: ");
            brand[i] = sc.nextLine();
            System.out.print("Tip produs (Smart Band / Smartwatch / GPS Band): ");
            tipProdus[i] = sc.nextLine();
            System.out.print("Compatibilitate (iOS / Android / Universal): ");
            compatibilitate[i] = sc.nextLine();
            System.out.print("Durata baterie (zile): ");
            durataBaterie[i] = sc.nextInt();
            System.out.print("Rezistenta la apa (ATM): ");
            rezistentaApa[i] = sc.nextDouble();
            System.out.print("Pret (RON): ");
            pret[i] = sc.nextDouble();
            sc.nextLine();
        }
    }


    // 2. Afisare vector

    static void afisare() {
        if (n == 0) { System.out.println("Depozitul este gol!"); return; }
        System.out.println("\n LISTA BRATARI FITNESS ");
        for (int i = 0; i < n; i++) {
            System.out.println("-----");
            System.out.println("Nr. crt.       : " + (i + 1));
            System.out.println("Brand          : " + brand[i]);
            System.out.println("Tip produs     : " + tipProdus[i]);
            System.out.println("Compatibilitate: " + compatibilitate[i]);
            System.out.println("Durata baterie : " + durataBaterie[i] + " zile");
            System.out.println("Rezistenta apa : " + rezistentaApa[i] + " ATM");
            System.out.printf ("Pret           : %.2f RON%n", pret[i]);
        }
        System.out.println("---");
    }


    // 3. Cautare dupa Brand si Pret

    static void cautare(Scanner sc) {
        System.out.print("Brand cautat: ");
        String b = sc.nextLine();
        System.out.print("Pret maxim (RON): ");
        double p = sc.nextDouble();
        sc.nextLine();

        boolean gasit = false;
        System.out.println("\n=== REZULTATE CAUTARE (Brand=\"" + b + "\", Pret<=" + p + " RON) ===");
        for (int i = 0; i < n; i++) {
            if (brand[i].equalsIgnoreCase(b) && pret[i] <= p) {
                afisareProdus(i);
                gasit = true;
            }
        }
        if (!gasit) System.out.println("Nu s-a gasit niciun produs cu aceste criterii.");
    }

    // 4. Afisare dupa Compatibilitate

    static void afisareDupaCompatibilitate(Scanner sc) {
        System.out.print("Compatibilitate cautata (iOS / Android / Universal): ");
        String comp = sc.nextLine();

        boolean gasit = false;
        System.out.println("\n BRATARI CU COMPATIBILITATE \"" + comp + "\" ");
        for (int i = 0; i < n; i++) {
            if (compatibilitate[i].equalsIgnoreCase(comp)) {
                afisareProdus(i);
                gasit = true;
            }
        }
        if (!gasit) System.out.println("Nu s-au gasit bratari cu aceasta compatibilitate.");
    }


    // 5. Stergere dupa Brand si Pret

    static void stergere(Scanner sc) {
        System.out.print("Brand de sters: ");
        String b = sc.nextLine();
        System.out.print("Pret maxim pentru stergere (RON): ");
        double p = sc.nextDouble();
        sc.nextLine();

        int newN = 0;
        String[]  nBrand  = new String[100];
        String[]  nTip    = new String[100];
        String[]  nComp   = new String[100];
        int[]     nDur    = new int[100];
        double[]  nRez    = new double[100];
        double[]  nPret   = new double[100];

        int sters = 0;
        for (int i = 0; i < n; i++) {
            if (brand[i].equalsIgnoreCase(b) && pret[i] <= p) {
                sters++;
            } else {
                nBrand[newN] = brand[i];  nTip[newN] = tipProdus[i];
                nComp[newN]  = compatibilitate[i]; nDur[newN] = durataBaterie[i];
                nRez[newN]   = rezistentaApa[i];   nPret[newN] = pret[i];
                newN++;
            }
        }
        brand = nBrand; tipProdus = nTip; compatibilitate = nComp;
        durataBaterie = nDur; rezistentaApa = nRez; pret = nPret;
        n = newN;

        System.out.println("Au fost sterse " + sters + " produs(e). Vector actualizat:");
        afisare();
    }


    // 6. Sortare dupa Durata Baterie (crescator, bubble sort)

    static void sortare() {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (durataBaterie[j] > durataBaterie[j + 1]) {
                    swap(j, j + 1);
                }
            }
        }
        System.out.println("\nVector sortat crescator dupa Durata Baterie:");
        afisare();
    }


    // 7. Matrice cu Durata Baterie pe diagonala principala

    static void crearMatrice() {
        if (n == 0) { System.out.println("Depozitul este gol!"); return; }
        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                m[i][j] = (i == j) ? durataBaterie[i] : 0;

        System.out.println("\n=== MATRICE (diagonala = Durata Baterie, rest = 0) ===");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.printf("%6d", m[i][j]);
            System.out.println();
        }
    }

    // Utilitare

    static void afisareProdus(int i) {
        System.out.println("  [" + (i + 1) + "] " + brand[i] + " | " + tipProdus[i]
                + " | " + compatibilitate[i] + " | " + durataBaterie[i]
                + " zile | " + rezistentaApa[i] + " ATM | " + pret[i] + " RON");
    }

    static void swap(int a, int b) {
        int    ti = durataBaterie[a]; durataBaterie[a] = durataBaterie[b]; durataBaterie[b] = ti;
        double td = rezistentaApa[a]; rezistentaApa[a] = rezistentaApa[b]; rezistentaApa[b] = td;
        td = pret[a];          pret[a]          = pret[b];          pret[b]          = td;
        String ts = brand[a];         brand[a]         = brand[b];         brand[b]         = ts;
        ts = tipProdus[a];     tipProdus[a]     = tipProdus[b];     tipProdus[b]     = ts;
        ts = compatibilitate[a]; compatibilitate[a] = compatibilitate[b]; compatibilitate[b] = ts;
    }


    // MAIN

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println(" DEPOZIT BRATARI FITNESS - Gestiune  ");


        citire(sc);

        int opt;
        do {
            System.out.println("\n MENIU");
            System.out.println("1. Afisare vector ");
            System.out.println("2. Cautare (Brand + Pret)");
            System.out.println("3. Afisare dupa Compatibilitate ");
            System.out.println("4. Stergere (Brand + Pret)");
            System.out.println("5. Sortare dupa Durata Baterie ");
            System.out.println("6. Creare matrice diagonala");
            System.out.println("0. Iesire ");
            System.out.println("---");
            System.out.print("Optiunea dvs.: ");
            opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1 -> afisare();
                case 2 -> cautare(sc);
                case 3 -> afisareDupaCompatibilitate(sc);
                case 4 -> stergere(sc);
                case 5 -> sortare();
                case 6 -> crearMatrice();
                case 0 -> System.out.println("La revedere!");
                default -> System.out.println("Optiune invalida! Alegeti intre 0 si 6.");
            }
        } while (opt != 0);

        sc.close();
    }
}
