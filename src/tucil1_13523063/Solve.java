package tucil1_13523063;

public class Solve {
    public static Problem solve(Problem problem) {
        if (problem.mode.equals("DEFAULT") || problem.mode.equals("CUSTOM")) {
            Solve.recurse_2D(problem, 0);
        } else {
            System.out.println("Invalid board");
            return null;
        }
        
        return problem;
    }

    public static void recurse_2D(Problem problem, int blockIndex) {
        if (blockIndex >= problem.blocksAvailable || problem.solved()) {
            return;
        }

        Block currentBlock = problem.blocks[blockIndex];
        Coordinate[] possibleCoordinates = problem.get_possible_coordinates();
        // Place block on each possible position
        for (int i=0;i<possibleCoordinates.length;i++) {
            Coordinate coord = possibleCoordinates[i];
            
            // recurse_2D_branch place the block, recurse, remove, then rotate the block
            // recurse_2D_branch does not do anything if the problem is solved                
            
            // Not flipped. All rotations
            Solve.recurse_2D_branch(problem, blockIndex, coord);
            Solve.recurse_2D_branch(problem, blockIndex, coord);
            Solve.recurse_2D_branch(problem, blockIndex, coord);
            Solve.recurse_2D_branch(problem, blockIndex, coord);
    
            // Horizontally flipped. All rotations
            if (!problem.solved()) {
                currentBlock.reflect_horizontal();
                Solve.recurse_2D_branch(problem, blockIndex, coord);
                Solve.recurse_2D_branch(problem, blockIndex, coord);
                Solve.recurse_2D_branch(problem, blockIndex, coord);
                Solve.recurse_2D_branch(problem, blockIndex, coord);
            }
        }
    }

    public static void recurse_2D_branch(Problem problem, int blockIndex, Coordinate coord) {
        problem.caseTested++;
        if (!problem.solved()) {
            if (problem.placeable_block(blockIndex, coord)) {
                problem.place_block(blockIndex, coord);
    
                recurse_2D(problem, blockIndex+1);
    
                if (problem.solved()) {
                    return;
                }
                
                problem.remove_block(blockIndex, coord);
            }

            problem.blocks[blockIndex].rotate_90_cw(1);
        }
    }
}