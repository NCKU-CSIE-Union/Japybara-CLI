package main;
import main.resources.Executor;
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        Executor executor = new Executor(2);
        executor.Init();
        executor.Execute();
        executor.Stop();
    }
}
