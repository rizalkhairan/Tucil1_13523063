package tucil1_13523063;

public class Main {
    public static void main(String[] args) {
        Problem problem = Input.read_file();
        
        long startTime = System.currentTimeMillis();

        problem = Solve.solve(problem);

        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;

        Output.display_solution(problem, timeElapsed);

        return;
    }
}