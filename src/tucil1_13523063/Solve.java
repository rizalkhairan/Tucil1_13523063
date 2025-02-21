package tucil1_13523063;

public class Solve {
    public static Problem solve(Problem problem) {
        if (problem.mode.equals("DEFAULT") || problem.mode.equals("CUSTOM")) {
            Solve.recurse_2D(problem, 0);
        } else if (problem.mode.equals("PYRAMID")) {
            Solve.recurse_3D(problem, 0);
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

                if (!problem.solved()) {
                    currentBlock.reflect_horizontal();
                }
            }
        }
    }

    public static void recurse_2D_branch(Problem problem, int blockIndex, Coordinate coord) {
        if (!problem.solved()) {
            problem.caseTested++;
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

    public static void recurse_3D(Problem problem, int blockIndex) {
        if (blockIndex >= problem.blocksAvailable || problem.solved()) {
            return;
        }

        Block currentBlock = problem.blocks[blockIndex];
        Coordinate[] possibleCoordinates = problem.get_possible_coordinates();
        // Place block on each possible position
        for (int i=0;i<possibleCoordinates.length;i++) {
            Coordinate coord = possibleCoordinates[i];            

            // Same thing as recurse_2D_branch, but with 3D orientations
            // Not cartesian currently. See Coordinate.java
            // recurse_3D_branch place the block, recurse, remove, then skew about vector i-j
            // Block is raised, rotated about the z-axis, and flattened in recurse_3D
            // There may be redundant cases, but i just don't want to think any more
            
            /* Planar */
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
            
            /* 3D orientations */
            if (!problem.solved()) {
                currentBlock.raise();
                
                Solve.recurse_3D_branch(problem, blockIndex, coord, true);
                Solve.recurse_3D_branch(problem, blockIndex, coord, true);
                Solve.recurse_3D_branch(problem, blockIndex, coord, true);
                Solve.recurse_3D_branch(problem, blockIndex, coord, true);

                if (!problem.solved()) {
                    currentBlock.rotate_90_cw(1);
                    Solve.recurse_3D_branch(problem, blockIndex, coord, true);
                    Solve.recurse_3D_branch(problem, blockIndex, coord, true);
                    Solve.recurse_3D_branch(problem, blockIndex, coord, true);
                    Solve.recurse_3D_branch(problem, blockIndex, coord, true);
                }

                if (!problem.solved()) {
                    currentBlock.rotate_90_cw(3);
                    currentBlock.flatten();
                }
            }

        }
    }

    public static void recurse_3D_branch(Problem problem, int blockIndex, Coordinate coord, boolean skew) {
        if (!problem.solved()) {
            problem.caseTested++;

            if (problem.placeable_block(blockIndex, coord)) {
                problem.place_block(blockIndex, coord);
    
                recurse_3D(problem, blockIndex+1);
    
                if (problem.solved()) {
                    return;
                }
                
                problem.remove_block(blockIndex, coord);
            }

            if (skew) {
                problem.blocks[blockIndex].skew_90_ccw_ij(1);
            } else {
                problem.blocks[blockIndex].rotate_90_cw(1);
            }
        }
    }
}