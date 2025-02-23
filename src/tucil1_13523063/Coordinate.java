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

    /* 2D transformations */

    // Reflect along i (plane yz)
    public void reflect_i() {
        this.x = -this.x;
    }

    // Reflect along i-j
    public void reflect_ij() {
        // Define here

        return;
    }

    // Rotate about axis k
    public void rotate_90_cw_k() {
        int temp = this.x;
        this.x = this.y;
        this.y = -temp;
    }

    /* 3D transformations */

    // Raise flat block such that the normal is parallel to vector i-j
    public void raise() {
        // Define here

        return;
    }

    // Flatten block whose normal is parallel with vector i-j. Inverse of raise
    public void flatten() {
        // Define here

        return;
    }

    // Reflect along i+j+k
    public void reflect_ijk() {
        // Define here

        return;
    }

    // Rotate about axis i-j
    public void rotate_90_ccw_ij() {
        // Define here

        return;
    }
}
