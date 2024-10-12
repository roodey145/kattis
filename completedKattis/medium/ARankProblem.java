package completedKattis.medium;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ARankProblem {
    private static Map<String, Integer> teams; // To save the index of the rank
    private static String[] ranks; // To keep track of the ranks
    // private static Map<Integer, String> ranks;
    private static Scanner _scanner = new Scanner(System.in);

    public static void main(String[] args) {
        teams = new HashMap<>();
        // ranks = new TreeMap<>();

        int nrOfTeams = _scanner.nextInt();
        int matches = _scanner.nextInt();
        _scanner.nextLine();

        ranks = new String[nrOfTeams];

        String teamName;

        for (int i = 1; i <= nrOfTeams; i++) {
            teamName = "T" + i;
            teams.put(teamName, i - 1);
            ranks[i - 1] = teamName;
            // ranks.put(i, "T" + i);
        }

        String[] teams;
        for (int match = 0; match < matches; match++) {
            teams = _scanner.nextLine().split(" ");
            changeRank(teams[0], teams[1]);
        }

        // Print the teams new ranking
        for (int i = 0; i < ranks.length; i++) {
            System.out.print(ranks[i]);

            if (i != ranks.length - 1) {
                System.out.print(" ");
            }
        }
    }

    private static void changeRank(String winnerTeam, String loserTeam) {
        // Get the index of the loser team
        int loserTeamIndex = teams.get(loserTeam);
        int winnerTeamIndex = teams.get(winnerTeam);

        String tempTeamName;

        for (int i = loserTeamIndex; i < winnerTeamIndex; i++) {
            tempTeamName = ranks[i];
            ranks[i] = ranks[i + 1];
            ranks[i + 1] = tempTeamName;

            // Change the map ranks
            teams.put(ranks[i], i);
            teams.put(tempTeamName, i + 1);
        }
    }
}
