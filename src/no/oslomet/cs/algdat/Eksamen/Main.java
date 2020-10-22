package no.oslomet.cs.algdat.Eksamen;

import java.util.Comparator;

public class Main {

    public static void main(String[] args) {

        EksamenSBinTre<Integer> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        System.out.println(tre.antall()); // Utskrift: 0

        //// Oppgave 1 ////
        Integer[] a = {4,7,2,9,5,10,8,1,3,6};
        for (int verdi : a) {
            tre.leggInn(verdi);
        }
        System.out.println(tre.antall());  // Utskrift: 10

        //// Oppgave 2 ////
        Integer[] b = {4,7,2,9,4,10,8,7,4,6};
        EksamenSBinTre<Integer> tre2 = new EksamenSBinTre<>(Comparator.naturalOrder());
        for (int verdi : b) {
            tre2.leggInn(verdi);
        }
        System.out.println(tre2.antall(5));  // Utskrift: 0
        System.out.println(tre2.antall(4));  // Utskrift: 3
        System.out.println(tre2.antall(7));  // Utskrift: 2
        System.out.println(tre2.antall(10));  // Utskrift: 1


    }
}
