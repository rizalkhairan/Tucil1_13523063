package tucil1_13523063;

public class Solve {
    public static Problem solve(Problem problem) {
        if (problem.mode.equals("DEFAULT") || problem.mode.equals("CUSTOM")) {
            Solve.recurse_2D(problem, 0);
        } else if (problem.mode.equals("PYRAMID")) {
            Solve.recurse_3D(problem, 0);
        } else {
            System.out.println("Invalid mode");
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
                currentBlock.reflect_i();
                Solve.recurse_2D_branch(problem, blockIndex, coord);
                Solve.recurse_2D_branch(problem, blockIndex, coord);
                Solve.recurse_2D_branch(problem, blockIndex, coord);
                Solve.recurse_2D_branch(problem, blockIndex, coord);

                if (!problem.solved()) {
                    currentBlock.reflect_i();
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

            problem.blocks[blockIndex].rotate_90_cw_k(1);
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
            // Not in cartesian coordinate system currently. See Coordinate.java
            // recurse_3D_branch place the block, recurse, remove, then transform the block
            // Block is raised, rotated about the z-axis, and flattened in recurse_3D
            // There may be redundant cases, but i just don't want to think any more
            
            /* Planar */
            // Not flipped. All rotations
            Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
            Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
            Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
            Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
    
            // Horizontally flipped. All rotations
            if (!problem.solved()) {
                currentBlock.reflect_i();
                Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
                Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
                Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
                Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
            }
            
            /* 3D */
            if (!problem.solved()) {
                currentBlock.reflect_i();
                currentBlock.raise();

                // Not flipped. All rotations about the vertical
                Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
                Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
                Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
                Solve.recurse_3D_branch(problem, blockIndex, coord, 0);

                // Vertically flipped. All rotations about the vertical
                if (!problem.solved()) {
                    currentBlock.reflect_k();
                    Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
                    Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
                    Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
                    Solve.recurse_3D_branch(problem, blockIndex, coord, 0);
                }

                if (!problem.solved()) {
                    currentBlock.reflect_k();
                    currentBlock.flatten();
                }
            }

        }
    }

    public static void recurse_3D_branch(Problem problem, int blockIndex, Coordinate coord, int transformMode) {
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

            if (transformMode == 0) {
                problem.blocks[blockIndex].rotate_90_cw_k(1);
            } else if (transformMode == 2) {
                problem.blocks[blockIndex].reflect_i();
            }
        }
    }
}