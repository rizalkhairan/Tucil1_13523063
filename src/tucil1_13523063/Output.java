package tucil1_13523063;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Output {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String ORANGE = "\u001B[38;5;208m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String VIOLET = "\u001B[35m";
    public static final String GRAY = "\u001B[37m";

    private static final String[] RAINBOW_COLORS = {RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE, CYAN, VIOLET, GRAY};

    public static void print_char(char c, int color) {
        if (0 <= color && color < RAINBOW_COLORS.length) {
            System.out.print(RAINBOW_COLORS[color] + c + ANSI_RESET);
        } else {
            System.out.print(c);
        }
    }

    public static void display_board(Problem problem) {
        Map<Character, Integer> blockColorMap = new HashMap<>();
        for (int i = 0; i < problem.blocks.length; i++) {
            blockColorMap.put(problem.blocks[i].name, i % RAINBOW_COLORS.length);
        }
        
        for (int y = 0; y < problem.board.length; y++) {
            for (int x = 0; x < problem.board[y].length; x++) {
                char c = problem.board[y][x];
                print_char(c, blockColorMap.getOrDefault(c, -1));
            }
            System.out.println();
        }
    }

    public static void display_solution(Problem problem, long timeElapsed) {
        if (problem.isSolved) {
            System.out.println("Solusi ditemukan!");
            Output.display_board(problem);
            System.out.println("\nWaktu pencarian: " + timeElapsed + " ms");
            System.out.println("\nJumlah kasus yang ditinjau: " + problem.case_tested);
        } else {
            System.out.println("No solution found.");
        }
    }
}
