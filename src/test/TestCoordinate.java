// java src/test/TestCoordinate.java

package test;

import tucil1_13523063.Coordinate;

public class TestCoordinate {
    public static void main(String[] args) {
        System.out.println("Test Coordinate");
        System.out.println("Test add commutativity: " + (test_add_commutativity(true) ? "OK" : "ERROR"));
        System.out.println("Test add associativity: " + (test_add_associativity(true) ? "OK" : "ERROR"));        
    
        System.out.println("Test 3D raise and flatten: " + (test_3D_raise_and_flatten(true) ? "OK" : "ERROR"));
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

    public static boolean test_3D_raise_and_flatten(boolean silent) {
        // Test reversibility
        Coordinate original, test;

        // 
        for (int x=-20;x<=20;x++) {
            for (int y=-20;y<=20;y++) {
                original = new Coordinate(x, y, 0);     
                test = new Coordinate(x, y, 0);
                test.raise();
                test.flatten();

                if (!silent) {
                    System.out.print("Original | Test: ");
                    original.print_coord();
                    test.print_coord();
                }

                if (original.x == test.x && original.y == test.y && original.level == test.level) {
                    if (!silent) {
                        System.out.println("OK");
                    }
                } else {
                    if (!silent) {
                        System.out.println("ERROR");
                    }
                    return false;
                }
            }
        }

        return true;
    }
}
