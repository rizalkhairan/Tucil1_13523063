// java src/test/TestCoordinate.java

package test;

import tucil1_13523063.Coordinate;

public class TestCoordinate {
    public static void main(String[] args) {
        System.out.println("Test Coordinate");
        System.out.println("Test add commutativity: " + (test_add_commutativity(true) ? "OK" : "ERROR"));
        System.out.println("Test add associativity: " + (test_add_associativity(true) ? "OK" : "ERROR"));
        
        System.out.println("Test coordinate 3D rotation: " + (test_coordinate_rotate(false) ? "OK" : "ERROR"));
        // Edge case
        Coordinate original = new Coordinate(1, 1, 0);
        Coordinate coord = new Coordinate(1, 1, 0);
        coord.rotate_90_ccw_ij();
        coord.print_coord();

        System.out.println("###");
        original = new Coordinate(0, 0, 2);
        coord = new Coordinate(0, 0, 2);
        coord.rotate_90_ccw_ij();
        coord.print_coord();

        System.out.println("###");
        original = new Coordinate(-2, -2, 0);
        coord = new Coordinate(-2, -2, 0);
        coord.rotate_90_ccw_ij();
        coord.print_coord();

    }

    public static boolean test_add_commutativity(boolean silent) {
        Coordinate a, b, result1, result2;

        for (int x1=-2;x1<=2;x1++) {
            for (int y1=-2;y1<=2;y1++) {
                for (int level1=-2;level1<=2;level1++) {
                    for (int x2=-2;x2<=2;x2++) {
                        for (int y2=-2;y2<=2;y2++) {
                            for (int level2=-2;level2<=2;level2++) {
                                a = new Coordinate(x1, y1, level1);
                                b = new Coordinate(x2, y2, level2);

                                result1 = a.add(b);
                                result2 = b.add(a);

                                if (!silent) {
                                    System.out.print("Result 1 | Result 2: " + result1.x + ", " + result1.y + ", " + result1.level + " | " + result2.x + ", " + result2.y + ", " + result2.level + " | ");
                                }
                                if (result1.x == result2.x && result1.y == result2.y && result1.level == result2.level) {
                                    if (!silent) {
                                        System.out.println("OK");
                                    }
                                } else {
                                    if (!silent) {
                                        System.out.println("ERROR");
                                        a.print_coord();
                                        b.print_coord();
                                    }
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public static boolean test_add_associativity(boolean silent) {
        Coordinate a, b, c, result1, result2;

        for (int x1=-2;x1<=2;x1++) {
            for (int y1=-2;y1<=2;y1++) {
                for (int level1=-2;level1<=2;level1++) {
                    for (int x2=-2;x2<=2;x2++) {
                        for (int y2=-2;y2<=2;y2++) {
                            for (int level2=-2;level2<=2;level2++) {
                                for (int x3=-2;x3<=2;x3++) {
                                    for (int y3=-2;y3<=2;y3++) {
                                        for (int level3=-2;level3<=2;level3++) {
                                            a = new Coordinate(x1, y1, level1);
                                            b = new Coordinate(x2, y2, level2);
                                            c = new Coordinate(x3, y3, level3);

                                            result1 = a.add(b).add(c);
                                            result2 = a.add(b.add(c));

                                            if (!silent) {
                                                System.out.print("Result 1 | Result 2: " + result1.x + ", " + result1.y + ", " + result1.level + " | " + result2.x + ", " + result2.y + ", " + result2.level + " | ");
                                            }
                                            if (result1.x == result2.x && result1.y == result2.y && result1.level == result2.level) {
                                                if (!silent) {
                                                    System.out.println("OK");
                                                }
                                            } else {
                                                if (!silent) {
                                                    System.out.println("ERROR");
                                                    a.print_coord();
                                                    b.print_coord();
                                                    c.print_coord();
                                                }
                                                return false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public static boolean test_coordinate_rotate(boolean silent) {
        // Invariant
        for (int i=-3;i<=3;i++) {
            Coordinate original = new Coordinate(i, -i, 0);
            Coordinate coord = new Coordinate(i, -i, 0);
            coord.rotate_90_ccw_ij();

            if (original.x != coord.x || original.y != coord.y || original.level != coord.level) {
                if (!silent) {
                    System.out.println("Error in invariant");
                    original.print_coord();
                    coord.print_coord();
                }
                return false;
            }
        }

        // On plane whose normal is parallel to i-j
        for (int level=-2;level<=2;level++) {
            for (int plane=-2;plane<=2;plane++) {
                Coordinate original = new Coordinate(plane, plane, level);
                Coordinate coord = new Coordinate(plane, plane, level);
                coord.rotate_90_ccw_ij();
                coord.rotate_90_ccw_ij();
                coord.rotate_90_ccw_ij();
                coord.rotate_90_ccw_ij();

                if (original.x != coord.x || original.y != coord.y || original.level != coord.level) {
                    if (!silent) {
                        System.out.println("Error in plane ");
                        original.print_coord();
                        coord.print_coord();
                    }
                    return false;
                }
            }
        }

        // General case
        for (int level=-5;level<=5;level++) {
            for (int x=-5;x<=5;x++) {
                for (int y=-5;y<=5;y++) {
                    Coordinate original = new Coordinate(x, y, level);
                    Coordinate coord = new Coordinate(x, y, level);
                    coord.rotate_90_ccw_ij();
                    coord.rotate_90_ccw_ij();
                    coord.rotate_90_ccw_ij();
                    coord.rotate_90_ccw_ij();

                    if (original.x != coord.x || original.y != coord.y || original.level != coord.level) {
                        if (!silent) {
                            System.out.println("Error in general");
                            original.print_coord();
                            coord.print_coord();
                        }
                        return false;
                    }
                }
            }
        }


        return true;
    }
}
