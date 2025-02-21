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
        this.validBoard = true;
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
    public char get_elem(Coordinate coord) {
        if (this.altitude != 0) {
            return this.pyramidBoard[coord.level][coord.y][coord.x];
        } else {
            return this.rectangularBoard[coord.y][coord.x];
        }
    }
    public void set_elem(Coordinate coord, char c) {
        if (this.altitude != 0) {
            this.pyramidBoard[coord.level][coord.y][coord.x] = c;
        } else {
            this.rectangularBoard[coord.y][coord.x] = c;
        }
    }

    /* Coordinate calc */
    public Coordinate add_coordinate(Coordinate origin, Coordinate add) {
        return origin.add(add);
    }


    /* Board modification */
    public boolean placeable_block(Block block, Coordinate coord) {
        for (int i=0;i<block.shape.length;i++) {
            Coordinate res = this.add_coordinate(coord, block.shape[i]);
            int x = res.x;
            int y = res.y;
            int z = res.level;
            
            if (x<0 || x>=this.width-z || y<0 || y>=this.height-z) {
                return false;
            }
            if (this.altitude!=0 && (z<0 || z>=this.altitude)) {
                return false;
            }
            if (this.get_elem(res) != Board.blank) {
                return false;
            }
        }

        return true;
    }

    public void place_block(Block block, Coordinate coord) {
        for (int i=0;i<block.shape.length;i++) {
            Coordinate res = this.add_coordinate(coord, block.shape[i]);
            this.set_elem(res, block.name);
            this.emptySpace--;
        }
    }

    public void remove_block(Block block, Coordinate coord) {
        for (int i=0;i<block.shape.length;i++) {
            Coordinate res = this.add_coordinate(coord, block.shape[i]);
            this.set_elem(res, blank);
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
