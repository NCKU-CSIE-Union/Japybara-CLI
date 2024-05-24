package main.resources;
import main.resources.interfaces.ICommandLine;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "j8", mixinStandardHelpOptions = true, version = "1.0")
public class CommandLine implements Runnable, ICommandLine{
    @Option(names = {"--uri"},          required = true)    private String uri;
    @Option(names = {"--rate"},    defaultValue = "100")    private int rate;
    @Option(names = {"--help"},        usageHelp = true)    private boolean help;
    @Option(names = {"--duration"}, defaultValue = "1m")    private String duration;
    @Option(names = {"--vu"},      defaultValue = "500")    private int vu;
    @Option(names = "--verbose"                        )    private boolean verbose;
    @Parameters(index = "0", arity = "0..1" )               private String configPath;
    public void Run(){
        
    }
    public Config Parse(String[] args){
        Config config = Config.GetInstance();
        return config;
    }
}