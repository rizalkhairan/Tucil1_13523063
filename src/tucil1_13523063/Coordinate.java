package tucil1_13523063;

public class Coordinate {
    // Origin at (0, 0)
    public int x, y, level;

    public Coordinate(int x, int y, int level) {
        this.x = x;
        this.y = y;
        if (level < 0) {
            this.level = 0;
        } else {
            this.level = level;
        }
    }

    public Coordinate add(Coordinate coord) {
        Coordinate res = new Coordinate(this.x, this.y, this.level);
        res.level += coord.level;
        if (coord.level == 0) {
            res.x += coord.x;
            res.y += coord.y;
        } else if (coord.level > 0){
            if (coord.x < 0) {
                res.x += coord.x;
            }
            if (coord.y < 0) {
                res.y += coord.y;
            }
        } else {
            if (coord.x > 0) {
                res.x += coord.x;
            }
            if (coord.y > 0) {
                res.y += coord.y;
            }
        }

        return res;
    }

    public void print_coord() {
        System.out.println(this.x + ", " + this.y + ", " + this.level);
    }

    /* Transformation */
    // These transformations are affine/does not transform the origin

    public void reflect_horizontal() {
        this.x = -this.x;
    }

    public void rotate_90_cw() {
        int temp = this.x;
        this.x = this.y;
        this.y = -temp;
    }

    // Raise flat block such that the normal is aligned with vector i-j
    public void raise() {
        int tempX = this.x;
        int tempY = this.y;
        this.level = tempX + tempY;   // Taxicab distance
        this.x = tempX - tempY;
        this.y = tempX - tempY;
    }

    // Flatten block aligned with vector i-j. Inverse of raise
    public void flatten() {
        // Rotate 4 times before flattening
        int tempX = this.x;
        this.x = (this.level + tempX) / 2;
        this.y = (this.level - tempX) / 2;
        this.level = 0;
    }

    // Skew around the axis of i-j
    public void skew_90_ccw_ij() {
        if (this.x == this.y && this.x == this.level) {
            // Point that is on the line that incide i+j+k
            int temp = this.x;
            this.x = -this.level;
            this.y = -this.level;
            this.level = temp;
        } else if (this.x == this.y) {
            // Point on the plane whose normal is parallel to i-j
            int diff = this.level - this.x;
            Coordinate temp = new Coordinate(this.x, this.y, this.x);
            temp.skew_90_ccw_ij();
            this.x = -diff;
            this.y = -diff;
            this.level = diff;
            this.add(temp);
        } else {
            // Point on other parts of the space
            Coordinate temp;
            if (this.y < this.x) {
                temp = new Coordinate(this.x, this.x, this.level);
                temp.rotate_90_cw();
                this.y = this.x - this.y;
                this.x = 0;
            } else {
                temp = new Coordinate(this.y, this.y, this.level);
                temp.rotate_90_cw();
                this.x = this.y - this.x;
                this.y = 0;
            }
            this.level = 0;
            this.add(temp);
        }
    }
}
