package tucil1_13523063;

import java.util.List;

public class Problem {
    static String[] modes = {"DEFAULT"};

    int n, m, p;
    String s;
    Block[] blocks;
    
    char[][] board;
    boolean[] used_block;
    int empty_space;
    boolean isSolved;

    /* Constructor */
    // All setters has to be called manually and succesfully

    public int set_dimension(int n, int m) {
        if (n < 0 || m < 0) {
            return -1;
        }
        this.n = n;
        this.m = m;
        return 0;
    }

    public int set_number_of_blocks(int p) {
        if (p < 0) {
            return -1;
        }
        this.p = p;
        return 0;
    }

    public int set_mode(String s) {
        for (int i=0;i<modes.length;i++) {
            if (s.equals(Problem.modes[i])) {
                this.s = s;
                return 0;
            }
        }
        return -1;
    }
    
    public int set_board(char[][] customBoard) {
        if (this.s.equals("DEFAULT")) {
            // Rectangular board
            this.board = new char[this.n][this.m];
            for (int i=0;i<this.n;i++) {
                for (int j=0;j<this.m;j++) {
                    this.board[i][j] = Block.blank;
                }
            }
            this.used_block = new boolean[this.p];
            this.empty_space = this.n * this.m;
            this.isSolved = false;
            return 0;
        } else {
            System.out.println("Invalid board");
            return -1;
        }
    }

    public int set_blocks(List<Block> blocks) {
        // Check if all blocks has different shapes here
        
        // Check if all blocks has diffent characters here
        
        
        // Valid blocks
        this.blocks = new Block[blocks.size()];
        for (int i=0;i<blocks.size();i++) {
            this.blocks[i] = blocks.get(i);
        }
        return 0;

    }

    /*  Board modification for solving */
    public boolean placeable_block(int block_index, Coordinate coordinate) {
        // If block already used
        if (this.used_block[block_index]) {
            return false;
        }

        // Check if block is in bound and not overlapping with other blocks
        Block block = this.blocks[block_index];
        for (int i=0;i<block.shape.length;i++) {
            int x = block.shape[i].x + coordinate.x;
            int y = block.shape[i].y + coordinate.y;
            if (x < 0 || x >= this.m || y < 0 || y >= this.n) {
                return false;
            }
            if (this.board[y][x] != Block.blank) {
                return false;
            }
        }

        return true;
    }

    public void place_block(int block_index, Coordinate coordinate) {
        Block block = this.blocks[block_index];
        this.used_block[block_index] = true;
        for (int i=0;i<block.shape.length;i++) {
            int x = block.shape[i].x + coordinate.x;
            int y = block.shape[i].y + coordinate.y;
            this.board[y][x] = block.name;
            this.empty_space--;
        }
    }

    // Make sure the block is not transformed between place and remove!!!
    public void remove_block(int block_index, Coordinate coordinate) {

        Block block = this.blocks[block_index];
        this.used_block[block_index] = false;
        for (int i=0;i<block.shape.length;i++) {
            int x = block.shape[i].x + coordinate.x;
            int y = block.shape[i].y + coordinate.y;
            this.board[y][x] = Block.blank;
            this.empty_space++;
        }
    }

    /* Solution state */
    public boolean solved() {
        // Completely filled
        if (this.empty_space != 0) {
            return false;
        }

        // All blocks used
        for (int i=0;i<this.p;i++) {
            if (!this.used_block[i]) {
                return false;
            }
        }

        this.isSolved = true;
        return true; // Yay!
    }

    // Not pretty print. Better display should be in Output.java
    public void display_board() {

        for (int i=0;i<this.n;i++) {
            for (int j=0;j<this.m;j++) {
                System.out.print(this.board[i][j]);
            }
            System.out.println();
        }
    }
}
