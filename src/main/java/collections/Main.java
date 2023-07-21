package collections;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> sequence1 = List.of(scanner.nextLine().
                split("\\s+"));
        List<String> sequence2 = List.of(scanner.nextLine().
                split("\\s+"));
        System.out.print(Collections.indexOfSubList(sequence1, sequence2)+ " ");
        System.out.print(Collections.lastIndexOfSubList(sequence1, sequence2));
    }
}
