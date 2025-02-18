package tucil1_13523063;

public class Main {
    public static void main(String[] args) {
        Problem problem = Input.read_file();
        
        problem = Solve.solve(problem);

        problem.display_board();
        return;
    }
}