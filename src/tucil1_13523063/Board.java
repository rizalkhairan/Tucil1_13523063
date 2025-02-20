package tucil1_13523063;

public class Board {
    public static final char inputBlank = 'X'; // File input as is
    public static final char blank = '_';
    public static final char filledDefault = '#';
    public static final char invalid = '.';

    boolean validBoard;
    char[][] rectangularBoard;
    char[][][] pyramidBoard;
    int width, height, altitude;
    int emptySpace;
    Coordinate[] possibleCoordinates;

    public Board(String mode, char[][] customBoard, int width, int height) {
        if (mode.equals("DEFAULT")) {
            this.width = width;
            this.height = height;
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
                    this.possibleCoordinates[i*width+j] = new Coordinate(j, i, 0);
                }
            }

            this.validBoard = true;
        } else if (mode.equals("CUSTOM")) {
            this.width = width;
            this.height = height;
            this.rectangularBoard = new char[height][width];
            this.emptySpace = 0;
            for (int i=0;i<height;i++) {
                for (int j=0;j<width;j++) {
                    if (customBoard[i][j] == Board.inputBlank) {
                        this.rectangularBoard[i][j] = Board.blank;
                        this.emptySpace++;
                    } else if (customBoard[i][j] == Board.invalid) {
                        this.rectangularBoard[i][j] = Board.invalid;
                    }
                }
            }

            this.possibleCoordinates = new Coordinate[this.emptySpace];
            int count = 0;
            for (int i=0;i<height;i++) {
                for (int j=0;j<width;j++) {
                    if (this.rectangularBoard[i][j] == Board.blank) {
                        this.possibleCoordinates[count] = new Coordinate(j, i, 0);
                        count++;
                    }
                }
            }

            this.validBoard = true;
        } else if (mode.equals("PYRAMID")) {
            this.width = width;
            this.height = height;
            this.altitude = height;
            this.pyramidBoard = new char[this.altitude][width][height];
            this.emptySpace = 0;
            for (int i=0;i<this.altitude;i++) {
                for (int j=0;j<height;j++) {
                    for (int k=0;k<width;k++) {
                        if (j < this.altitude-i && k < this.altitude-i) {
                            this.pyramidBoard[i][j][k] = Board.blank;
                            this.emptySpace++;
                        } else {
                            this.pyramidBoard[i][j][k] = Board.invalid;
                        }
                    }
                }
            }

            this.possibleCoordinates = new Coordinate[this.emptySpace];
            int count = 0;
            for (int i=0;i<this.altitude;i++) {
                for (int j=0;j<height;j++) {
                    for (int k=0;k<width;k++) {
                        if (this.pyramidBoard[i][j][k] == Board.blank) {
                            this.possibleCoordinates[count] = new Coordinate(j, k, i);
                            count++;
                        }
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

    /* Coordinate calc */
    public Coordinate add_coordinate(Coordinate origin, Coordinate add) {
        Coordinate res = new Coordinate(origin.x, origin.y, origin.level);
        if (add.level == 0) {
            res.x += add.x;
            res.y += add.y;
        } else {
            res.level += add.level;
            if (add.x * add.y < 0) {
                res.x += add.x;
            }
        }

        return res;
    }


    /* Board modification */
    public boolean placeable_block(Block block, Coordinate coord) {
        if (this.altitude != 0) {
            for (int i=0;i<block.shape.length;i++) {
                Coordinate res = this.add_coordinate(coord, block.shape[i]);
                int x = res.x;
                int y = res.y;
                int z = res.level;

                if (z < 0 || z >= this.altitude || x < 0 || x >= this.width-z || y < 0 || y >= this.height-z) {
                    return false;
                }
                if (this.pyramidBoard[z][y][x] != Board.blank) {
                    return false;
                }
            }

            return true;
        } else {
            for (int i=0;i<block.shape.length;i++) {
                Coordinate res = this.add_coordinate(coord, block.shape[i]);
                int x = res.x;
                int y = res.y;

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
    }

    public void place_block(Block block, Coordinate coord) {
        char c = block.name;
        if (altitude != 0) {
            for (int i=0;i<block.shape.length;i++) {
                Coordinate res = this.add_coordinate(coord, block.shape[i]);
                int x = res.x;
                int y = res.y;
                int z = res.level;

                this.pyramidBoard[z][y][x] = c;
                this.emptySpace--;
            }
        } else {
            for (int i=0;i<block.shape.length;i++) {
                Coordinate res = this.add_coordinate(coord, block.shape[i]);
                int x = res.x;
                int y = res.y;

                this.rectangularBoard[y][x] = c;
                this.emptySpace--;
            }
        }
    }

    public void remove_block(Block block, Coordinate coord) {
        if (this.altitude != 0) {
            for (int i=0;i<block.shape.length;i++) {
                Coordinate res = this.add_coordinate(coord, block.shape[i]);
                int x = res.x;
                int y = res.y;
                int z = res.level;

                this.pyramidBoard[z][y][x] = Board.blank;
                this.emptySpace++;
            }
        } else {
            for (int i=0;i<block.shape.length;i++) {
                Coordinate res = this.add_coordinate(coord, block.shape[i]);
                int x = res.x;
                int y = res.y;

                this.rectangularBoard[y][x] = Board.blank;
                this.emptySpace++;
            }
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
        if (this.altitude != 0) {
            int lines = this.altitude * (this.altitude+1) / 2;
            String[] board = new String[lines];
            int count = 0;
            for (int i=this.altitude-1;i>=0;i--) {
                for (int j=0;j<this.height-i;j++) {
                    board[count] = "";
                    for (int k=0;k<this.width-i;k++) {
                        board[count] += this.pyramidBoard[i][j][k];
                    }
                    count++;
                }
            }
            return board;
        } else {
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
}
