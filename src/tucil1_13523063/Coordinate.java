package tucil1_13523063;

public class Coordinate {
    // Origin at (0, 0, 0) and all coordinates are measured relative to the origin
    // Point that is directly above (x, y, z) is (x, y, z+2)
    // Point that is directly below (x, y, z) is (x, y, z-2)

    public int x, y, level;

    public Coordinate(int x, int y, int level) {
        this.x = x;
        this.y = y;
        this.level = level;
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
            if (coord.x == 0 && coord.y == 0) {
                res.x -= coord.level / 2;
                res.y -= coord.level / 2;
            }
        } else {
            if (coord.x > 0) {
                res.x += coord.x;
            }
            if (coord.y > 0) {
                res.y += coord.y;
            }
            if (coord.x == 0 && coord.y == 0) {
                res.x += coord.level / 2;
                res.y += coord.level / 2;
            }
        }

        return res;
    }

    public void print_coord() {
        System.out.println(this.x + ", " + this.y + ", " + this.level);
    }

    /* Transformation */
    // These transformations are affine/does not transform the origin

    /* 2D transformations */

    // Reflect along i (plane yz)
    public void reflect_i() {
        this.x = -this.x;
    }

    // Rotate about axis k
    public void rotate_90_cw_k() {
        int temp = this.x;
        this.x = this.y;
        this.y = -temp;
    }

    /* 3D transformations */

    // Raise flat block such that the normal is parallel to vector i-j
    // Call flatten() to restore original position
    public void raise() {
        this.rotate_90_cw_k();
        this.rotate_90_cw_k();
        this.rotate_90_cw_k();
        int tempX = this.x;
        int tempY = this.y;
        this.level = tempX + tempY;   // Taxicab distance
        this.x = tempX - tempY;
        this.y = tempX - tempY;
    }

    // Flatten block whose normal is parallel with vector i-j. Inverse of raise
    public void flatten() {
        int tempX = this.x;
        this.x = (this.level + tempX) / 2;
        this.y = (this.level - tempX) / 2;
        this.level = 0;
        this.rotate_90_cw_k();
    }

    // Reflect along z-axis
    public void reflect_k() {
        this.level = -this.level;
    }

}
