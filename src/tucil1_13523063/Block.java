package tucil1_13523063;

import java.util.List;

public class Block {
    static char blank = '_';
    static char filled_default = '#';

    char name;
    Coordinate[] shape;

    public Block(char name, List<String> input) {
        this.name = name;
        int points = 0;
        for (int i=0;i<input.size();i++) {
            for (int j=0;j<input.get(i).length();j++) {
                if (input.get(i).charAt(j) == name) {
                    points++;
                }
            }
        }

        this.shape = new Coordinate[points];
        int count = 0;
        for (int i=0;i<input.size();i++) {
            for (int j=0;j<input.get(i).length();j++) {
                if (input.get(i).charAt(j) == name) {
                    this.shape[count] = new Coordinate(j, -i);
                    count++;
                }
            }
        }
    }

    public void print_shape() {
        int min_x = 0, max_x = 0, min_y = 0, max_y = 0;
        for (int i=0;i<this.shape.length;i++) {
            if (this.shape[i].x < min_x) {
                min_x = this.shape[i].x;
            }
            if (this.shape[i].x > max_x) {
                max_x = this.shape[i].x;
            }
            if (this.shape[i].y < min_y) {
                min_y = this.shape[i].y;
            }
            if (this.shape[i].y > max_y) {
                max_y = this.shape[i].y;
            }
        }

        for (int i=max_y;i>=min_y;i--) {
            for (int j=min_x;j<=max_x;j++) {
                boolean found = false;
                for (int k=0;k<this.shape.length;k++) {
                    if (this.shape[k].x == j && this.shape[k].y == i) {
                        System.out.print(this.name);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.print(blank);
                }
            }
            System.out.println();
        }
    }

    /* Transformation */
    // All transformations preserve the origin

    // Coord.x = -Coord.x
    public void reflect_horizontal() {
        for (int i=0;i<this.shape.length;i++) {
            this.shape[i].reflect_horizontal();
        }
    }

    // Rotation about (0, 0) which is guaranteed to incide with the block
    public void rotate_90_cw(int n) {
        int times = n % 4;
        for (int i=0;i<this.shape.length;i++) {
            for (int j=0;j<times;j++) {
                this.shape[i].rotate_90_cw();
            }
        }
    }
}
