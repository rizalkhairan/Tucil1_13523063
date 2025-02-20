package tucil1_13523063;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

public class Output {
    static String outputPath = "output/";

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
        
        String[] board = problem.board.get_board();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length(); j++) {
                char c = board[i].charAt(j);
                if (c == Board.invalid) {
                    System.out.print(" ");
                } else {
                    print_char(c, blockColorMap.get(c));
                }
            }
            System.out.println();
        }
    }

    public static void display_solution(Problem problem, long timeElapsed) {
        if (problem.solved()) {
            System.out.println("\nSolusi ditemukan!");
            Output.display_board(problem);
            System.out.println("\nWaktu pencarian: " + timeElapsed + " ms");
            System.out.println("\nJumlah kasus yang ditinjau: " + problem.caseTested);

        } else {
            System.out.println("\nTidak ada solusi yang ditemukan");
            System.out.println("\nWaktu pencarian: " + timeElapsed + " ms");
            System.out.println("\nJumlah kasus yang ditinjau: " + problem.caseTested);
        }

        System.out.print("Apakah anda ingin menyimpan solusi? [Y/N] ");
        Scanner input = new Scanner(System.in);
        String save = input.nextLine();
    
        if (save.equals("Y") || save.equals("y")) {
            Output.save_solution(problem, timeElapsed);
        }
    }

    public static void save_solution(Problem problem, long timeElapsed) {
        System.out.print("Masukkan nama file: ");
        Scanner input = new Scanner(System.in);
        String filename = input.nextLine();

        try {
            File file = new File(Output.outputPath + filename);
            FileWriter writer = new FileWriter(file);

            if (problem.solved()) {
                String[] board = problem.board.get_board();
                for (int i = 0; i < board.length; i++) {
                    writer.write(board[i] + "\n");
                }
            } else {
                writer.write("Tidak ada solusi yang ditemukan\n");
            }

            writer.write("\nSolve time: " + timeElapsed + " ms");
            writer.write("\nCases examined: " + problem.caseTested);

            writer.close();
            System.out.println("Solusi berhasil disimpan!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Gagal menyimpan solusi");
        }
    }

    public static void display_problem(Problem problem) {
        System.out.println("\nThe state of the problem:");
        problem.display_board();
        System.out.println("Empty space: " + problem.board.get_empty_space());
        System.out.println("Blocks available: " + problem.blocksAvailable);
        System.out.println();
        for (int i=0;i<problem.blocksAvailable;i++) {
            System.out.println("Block " + problem.blocks[i].name + ":");
            problem.blocks[i].print_shape();
            System.out.println();
        }
    }
}
