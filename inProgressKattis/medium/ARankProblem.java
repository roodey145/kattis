package inProgressKattis.medium;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ARankProblem {
    private static Map<String, Integer> map;
    private static Scanner _scanner = new Scanner(System.in);

    public static void main(String[] args) {
        map = new HashMap<>();

        int nrOfTeams = _scanner.nextInt();
        int matches = _scanner.nextInt();
        _scanner.nextLine();

        for (int i = 1; i <= nrOfTeams; i++) {
            map.put("T" + i, i);
        }

    }
}
