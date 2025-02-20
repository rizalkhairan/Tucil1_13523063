package tucil1_13523063;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Input {
    static String inputPath = "input/";

    public static Problem read_file() {
        Scanner input, filename;
        File file;

        System.out.print("Enter the file name with extension : ");
        filename = new Scanner(System.in);
        try {
            file = new File(inputPath + filename.nextLine());
            input = new Scanner(file);
        } catch (Exception e) {
            System.out.println("File failed to load");
            return null;
        }

        String[] first_line_split = input.nextLine().split(" ");
        String second_line = input.nextLine();

        List<String> board_input = new ArrayList<String>();
        char[][] customBoard = null;
        if (second_line.equals("CUSTOM")) {
            for (int i=0;i<Integer.parseInt(first_line_split[0]);i++) {
                board_input.add(input.nextLine());
            }

            customBoard = new char[board_input.size()][board_input.get(0).length()];
            for (int i=0;i<board_input.size();i++) {
                for (int j=0;j<board_input.get(i).length();j++) {
                    customBoard[i][j] = board_input.get(i).charAt(j);
                }
            }
        }
        board_input.clear();

        List<Block> blocks = new ArrayList<Block>();
        List<String> shape = new ArrayList<String>();
        char current = '@';
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (current == '@') {
                for (int i=0;i<line.length();i++) {
                    if (line.charAt(i) != ' ') {
                        current = line.charAt(i);
                        break;
                    }
                }
            }
            // Check if line has new character
            for (int i=0;i<line.length();i++) {
                if (line.charAt(i) != current && line.charAt(i) != ' ') {
                    Block block = new Block(current, shape);
                    blocks.add(block);
                    shape.clear();
                    current = line.charAt(i);
                    break;
                }
            }
            shape.add(line);
        }
        Block block = new Block(current, shape);
        blocks.add(block);
        shape.clear();
        
        input.close();
        
        Problem problem = new Problem();

        int validity = problem.set_dimension(Integer.parseInt(first_line_split[0]), Integer.parseInt(first_line_split[1]));
        if (validity != 0) {
            System.out.println("Invalid dimension");
            return null;
        }

        validity = problem.set_number_of_blocks(Integer.parseInt(first_line_split[2]));
        if (validity != 0) {
            System.out.println("Invalid number of blocks");
            return null;
        }

        validity = problem.set_mode(second_line);
        if (validity != 0) {
            System.out.println("Invalid mode");
            return null;
        }

        validity = problem.set_board(customBoard);
        if (validity != 0) {
            System.out.println("Invalid board");
            return null;
        }

        validity = problem.set_blocks(blocks);
        if (validity != 0) {
            if (validity == 1) {
                System.out.println("WARNING: The blocks provided do not have the total space that can completely occupy and fit the whole board. No solution is possible");
                System.out.println("Attempt to solve the problem anyway...");
            } else {
                System.out.println("Invalid blocks");
                return null;
            }
        }
        blocks.clear();

        return problem;
    }
}
