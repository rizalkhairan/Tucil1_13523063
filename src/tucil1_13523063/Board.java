package tucil1_13523063;

public class Board {
    public static final char inputBlank = 'X'; // File input as is
    public static final char blank = '_';
    public static final char filledDefault = '#';
    public static final char invalid = '.';

    boolean validBoard;
    char[][] rectangularBoard;
    int width, height;
    int emptySpace;
    Coordinate[] possibleCoordinates;

    public Board(String mode, char[][] customBoard, int width, int height) {
        if (mode.equals("DEFAULT")) {
            this.rectangularBoard = new char[height][width];
            for (int i=0;i<height;i++) {
                for (int j=0;j<width;j++) {
                    this.rectangularBoard[i][j] = blank;
                }
            }

            this.emptySpace = width * height;

            this.possibleCoordinates = new Coordinate[width * height];
            for (int i=0;i<height;i++) {
                for (int j=0;j<width;j++) {
                    this.possibleCoordinates[i*width+j] = new Coordinate(j, i);
                }
            }

            this.validBoard = true;
        } else if (mode.equals("CUSTOM")) {
            this.rectangularBoard = customBoard;

            this.emptySpace = 0;
            for (int i=0;i<height;i++) {
                for (int j=0;j<width;j++) {
                    if (this.rectangularBoard[i][j] == Board.inputBlank) {
                        this.emptySpace++;
                    }
                }
            }

            this.possibleCoordinates = new Coordinate[this.emptySpace];
            for (int i=0;i<height;i++) {
                for (int j=0;j<width;j++) {
                    if (this.rectangularBoard[i][j] == Board.inputBlank) {
                        this.possibleCoordinates[i*width+j] = new Coordinate(j, i);
                    }
                }
            }

            this.validBoard = true;
        } else {
            this.validBoard = false;
        }

    }

    /* Getter */
    public int get_empty_space() {
        return this.emptySpace;
    }
    public Coordinate[] get_possible_coordinates() {
        return this.possibleCoordinates;
    }

    /* Board modification */
    public boolean placeable_block(Block block, Coordinate coord) {
        for (int i=0;i<block.shape.length;i++) {
            int x = coord.x + block.shape[i].x;
            int y = coord.y + block.shape[i].y;
            if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
                return false;
            }
            if (this.rectangularBoard[y][x] != Board.blank) {
                // In custom board, an invalid position is either occupied or out of bound
                return false;
            }
        }
        return true;
    }

    public void place_block(Block block, Coordinate coord) {
        char c = block.name;
        for (int i=0;i<block.shape.length;i++) {
            int x = coord.x + block.shape[i].x;
            int y = coord.y + block.shape[i].y;
            this.rectangularBoard[y][x] = c;
            this.emptySpace--;
        }
    }

    public void remove_block(Block block, Coordinate coord) {
        for (int i=0;i<block.shape.length;i++) {
            int x = coord.x + block.shape[i].x;
            int y = coord.y + block.shape[i].y;
            this.rectangularBoard[y][x] = Board.blank;
            this.emptySpace++;
        }
    }

    
    public void display() {
        for (int i=0;i<this.height;i++) {
            for (int j=0;j<this.width;j++) {
                System.out.print(this.rectangularBoard[i][j]);
            }
            System.out.println();
        }
    }

    public String[] get_board() {
        String[] board = new String[this.height];
        for (int i=0;i<this.height;i++) {
            board[i] = "";
            for (int j=0;j<this.width;j++) {
                board[i] += this.rectangularBoard[i][j];
            }
        }
        return board;
    }
}
