package tucil1_13523063;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Input {
    static String inputPath = "input/";

    public static Problem read_file() {
        Scanner input;
        File file;

        try {
            System.out.print("Enter the file name with extension : ");
            input = new Scanner(System.in);

            file = new File(inputPath + input.nextLine());
            input = new Scanner(file);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String[] first_line_split = input.nextLine().split(" ");
        String second_line = input.nextLine();

        List<Block> blocks = new ArrayList<Block>();
        List<String> shape = new ArrayList<String>();
        char current = '_';
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.charAt(0) != current && current != '_') {
                Block block = new Block(current, shape);
                blocks.add(block);
                shape.clear();
            }

            current = line.charAt(0);
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

        validity = problem.set_board(null);
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
