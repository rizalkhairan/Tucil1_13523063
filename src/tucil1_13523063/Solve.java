package tucil1_13523063;

public class Solve {
    public static Problem solve(Problem problem) {
        if (problem.s.equals("DEFAULT")) {
            problem.set_board(null);    // Empty board

            Solve.recurse_default(problem, 0);
        } else {
            System.out.println("Invalid board");
            return null;
        }
        
        return problem;
    }

    public static void recurse_default(Problem problem, int blockIndex) {
        if (problem.solved()) {
            return;
        }

        Block currentBlock = problem.blocks[blockIndex];
        // Place block on each possible position
        for (int i=0;i<problem.n;i++) {
            for (int j=0;j<problem.m;j++) {
                Coordinate coord = new Coordinate(j, i);

                // recurse_default_branch place the block, recurse, remove, then rotate the block
                // recurse_default_branch does not do anything if the problem is solved                
                
                // Not flipped. All rotations
                Solve.recurse_default_branch(problem, blockIndex, coord);
                Solve.recurse_default_branch(problem, blockIndex, coord);
                Solve.recurse_default_branch(problem, blockIndex, coord);
                Solve.recurse_default_branch(problem, blockIndex, coord);

                // Horizontally flipped. All rotations
                if (!problem.isSolved) {
                    currentBlock.reflect_horizontal();
                    Solve.recurse_default_branch(problem, blockIndex, coord);
                    Solve.recurse_default_branch(problem, blockIndex, coord);
                    Solve.recurse_default_branch(problem, blockIndex, coord);
                    Solve.recurse_default_branch(problem, blockIndex, coord);
                }

            }
        }
        
    }

    public static void recurse_default_branch(Problem problem, int blockIndex, Coordinate coord) {
        if (problem.isSolved || problem.solved()) {
            return;
        }

        if (problem.placeable_block(blockIndex, coord)) {
            problem.place_block(blockIndex, coord);

            recurse_default(problem, blockIndex+1);

            if (!problem.isSolved) {
                problem.remove_block(blockIndex, coord);
                problem.blocks[blockIndex].rotate_90_cw(blockIndex);
            }
        }
    }
}