import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "j8", mixinStandardHelpOptions = true, version = "1.0")
public class Tester implements Runnable {
    @Option(names = {"--uri"},          required = true)    private String uri;
    @Option(names = {"--rate"},    defaultValue = "100")    private int rate;
    @Option(names = {"--help"},        usageHelp = true)    private boolean help;
    @Option(names = {"--duration"}, defaultValue = "1m")    private String duration;
    @Option(names = {"--vu"},      defaultValue = "500")    private int vu;
    @Option(names = "--verbose"                        )    private boolean verbose;
    @Parameters(index = "0", arity = "0..1" )               private String configPath;
    public static void main(String[] args){
        int exitCode = picocli.CommandLine(new Tester()).execute(args);
        System.exit(exitCode);
    } 
    public void run() {
        System.out.println("URI: " + uri);
        System.out.println("Rate: " + rate);
        System.out.println("Duration: " + duration);
        System.out.println("Virtual Users: " + vu);
        System.out.println("Verbose: " + verbose);
        if (configPath != null) {
            System.out.println("Config path: " + configPath);
        }
    }
}
