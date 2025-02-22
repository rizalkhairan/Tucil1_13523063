// java src/test/TestCoordinate.java

package test;

import tucil1_13523063.Coordinate;

public class TestCoordinate {
    public static void main(String[] args) {
        System.out.println("Test Coordinate");
        System.out.println("Test add commutativity: " + (test_add_commutativity() ? "OK" : "ERROR"));
    }

    public static boolean test_add_commutativity() {
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

                                System.out.print("Result 1 | Result 2: " + result1.x + ", " + result1.y + ", " + result1.level + " | " + result2.x + ", " + result2.y + ", " + result2.level + " | ");
                                if (result1.x == result2.x && result1.y == result2.y && result1.level == result2.level) {
                                    System.out.println("OK");
                                } else {
                                    System.out.println("ERROR");
                                    a.print_coord();
                                    b.print_coord();
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
}
