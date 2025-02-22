package tucil1_13523063;

public class Coordinate {
    // Origin at (0, 0, 0) and all coordinates are measured relative to the origin
    // Point that is directly above (x, y, z) is (x, y, z+2)
    // Point that is directly below (x, y, z) is (x, y, z-2)

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

    // 2D transformations

    public void reflect_horizontal() {
        this.x = -this.x;
    }

    public void rotate_90_cw() {
        int temp = this.x;
        this.x = this.y;
        this.y = -temp;
    }

    // 3D transformations

    // Raise flat block such that the normal is parallel to vector i-j
    public void raise() {
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
    }

    // Skew around the axis of i-j
    public void skew_90_ccw_ij() {
        // Divided into multiple cases based on the position of the coordinate
        // The transformation is simply defined on the first case
        // All other cases try to transform the coordinate to conform to the first case
        if (this.x == this.y && this.x == this.level) {
            // Point that is on the line that incide i+j+k
            int temp = this.x;
            this.x = -this.level;
            this.y = -this.level;
            this.level = temp;
        } else if (this.x == this.y) {
            // On the plane whose normal is parallel to i-j
            // Bring to the line that incide i+j+k
            int levelDiff = this.level - this.x;
            this.level = this.x;

            // Skew
            int temp = this.x;
            this.x = -this.level;
            this.y = -this.level;
            this.level = temp;

            // Reverse first transformation
            this.x -= levelDiff;
            this.y -= levelDiff;
        } else if ((this.x - this.y)%2 == 0 || (this.y - this.x)%2 == 0) {
            // Other part of the space with the above condition
            // Bring to the plane whose normal is parallel to i-j
            int ijDiff = (this.y - this.x) / 2;
            this.x += ijDiff;
            this.y -= ijDiff;
            
            // Bring to the line that incide i+j+k
            int levelDiff = this.level - this.x;
            this.level = this.x;
            
            // Skew
            int temp = this.x;
            this.x = -this.level;
            this.y = -this.level;
            this.level = temp;
            
            // Reverse second transformation
            this.x -= levelDiff;
            this.y -= levelDiff;
            
            // Reverse first transformation
            this.x -= ijDiff;
            this.y += ijDiff;
        } else {
            // Most general case
            // Make even
            int evenDiff = 1;
            this.x -= evenDiff;

            // Bring to the plane whose normal is parallel to i-j
            int ijDiff = (this.y - this.x) / 2;
            this.x += ijDiff;
            this.y -= ijDiff;

            // Bring to the line that incide i+j+k
            int levelDiff = this.level - this.x;
            this.level = this.x;

            // Skew
            int temp = this.x;
            this.x = -this.level;
            this.y = -this.level;
            this.level = temp;

            // Reverse third transformation
            this.x -= levelDiff;
            this.y -= levelDiff;

            // Reverse second transformation
            this.x -= ijDiff;
            this.y += ijDiff;

            // Reverse first transformation. I don't understand how this came to be but it kind of make sense
            this.y -= evenDiff;
            this.level += evenDiff;
        }
    }
}
