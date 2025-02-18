package tucil1_13523063;

public class Coordinate {
    // Origin at (0, 0)
    int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
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
}
