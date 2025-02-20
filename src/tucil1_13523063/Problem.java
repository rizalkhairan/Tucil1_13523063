package tucil1_13523063;

import java.util.List;

public class Problem {
    public static final String[] modes = {"DEFAULT", "CUSTOM"};

    int width, height, blocksAvailable;
    String mode;
    
    Board board;
    
    Block[] blocks;
    boolean[] usedBlock;
    
    boolean isSolved;
    int caseTested;

    /* Constructor */
    // All setters has to be called manually and succesfully

    public int set_dimension(int n, int m) {
        if (n < 0 || m < 0) {
            return -1;
        }
        this.height = n;
        this.width = m;
        return 0;
    }

    public int set_number_of_blocks(int p) {
        if (p < 0) {
            return -1;
        }
        this.blocksAvailable = p;
        return 0;
    }

    public int set_mode(String s) {
        for (int i=0;i<modes.length;i++) {
            if (s.equals(Problem.modes[i])) {
                this.mode = s;
                return 0;
            }
        }
        return -1;
    }
    
    public int set_board(char[][] customBoard) {
        this.isSolved = false;
        this.board = new Board(this.mode, customBoard, this.width, this.height);
        if (!this.board.validBoard) {
            return -1;
        }
        return 0;
    }

    public int set_blocks(List<Block> blocks) {
        // Check if all blocks are inputted
        if (blocks.size() != this.blocksAvailable) {
            return -1;
        }

        // Check if all blocks has different characters here
        for (int i = 0; i < blocks.size(); i++) {
            for (int j = i + 1; j < blocks.size(); j++) {
                if (blocks.get(i).name == blocks.get(j).name) {
                    return -1;
                }
            }
        }
        
        
        // Valid blocks
        this.blocks = new Block[blocks.size()];
        for (int i=0;i<blocks.size();i++) {
            this.blocks[i] = blocks.get(i);
        }
        this.usedBlock = new boolean[this.blocksAvailable];
        for (int i=0;i<this.blocksAvailable;i++) {
            this.usedBlock[i] = false;
        }
        
        // Count the space of all blocks
        int total_space = 0;
        for (int i=0;i<this.blocks.length;i++) {
            total_space += this.blocks[i].shape.length;
        }
        if (total_space != this.board.get_empty_space()) {
            return 1;  // Guaranteed to not have solution
        }

        return 0;

    }

    /*  Board modification for solving */
    public boolean placeable_block(int block_index, Coordinate coordinate) {
        // If block already used
        if (this.usedBlock[block_index]) {
            return false;
        }

        // Check if block is in bound and not overlapping with other blocks
        Block block = this.blocks[block_index];
        return this.board.placeable_block(block, coordinate);
    }

    public void place_block(int block_index, Coordinate coordinate) {
        Block block = this.blocks[block_index];
        this.usedBlock[block_index] = true;
        this.board.place_block(block, coordinate);
    }

    // Make sure the block is not transformed between place and remove!!!
    public void remove_block(int block_index, Coordinate coordinate) {
        Block block = this.blocks[block_index];
        this.usedBlock[block_index] = false;
        this.board.remove_block(block, coordinate);
    }

    /* Solution state */
    public boolean solved() {
        if (this.isSolved) {
            return true;    // Already passed all solve condition
        }

        /* Solve conditions */
        // Completely filled
        if (this.board.get_empty_space() != 0) {
            return false;
        }

        // All blocks used
        for (int i=0;i<this.blocksAvailable;i++) {
            if (!this.usedBlock[i]) {
                return false;
            }
        }

        this.isSolved = true;
        return true; // Yay!
    }

    // Not pretty print. Better display should be in Output.java
    public void display_board() {
        this.board.display();
    }
}
