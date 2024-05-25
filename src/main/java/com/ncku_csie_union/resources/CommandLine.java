package com.ncku_csie_union.resources;
import com.ncku_csie_union.resources.interfaces.ICommandLine;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "j8", mixinStandardHelpOptions = true, version = "1.0", description = "Web Pressure Test Tool")
public class CommandLine implements Runnable, ICommandLine {
    // Handle Run Mode
    @Command(name = "run", description = "Run the test")
    static class RunCommand implements Runnable {
        @Option(names = {"--uri"}, required = false, description = "URI to test.")
        private String uri;
        @Option(names = {"--rate"}, defaultValue = "100", description = "Requests per second.")
        private int rate;
        @Option(names = {"--duration"}, defaultValue = "1m", description = "Duration of the test.")
        private String duration;
        @Option(names = {"--vu"}, defaultValue = "500", description = "Number of virtual users.")
        private int vu;
        @Option(names = "--verbose", description = "Verbose output.")
        private boolean verbose;
        @Option(names = {"--config"}, defaultValue = "", description = "Path to configuration YAML file.")
        private String YAMLconfigPath;

        @Override
        public void run() {
            if(YAMLconfigPath.isEmpty()){
                if (uri == null || uri.isEmpty()) 
                    System.err.println("Error: --uri must be provided when not using a config file.");
                else
                    UpdateConfig();
            } else { 
                LoadYAMLConfig();
            }
        }
        private void UpdateConfig(){
            Config config = Config.GetInstance();
            config.uri = uri;
            config.rate = rate;
            config.duration = duration;
            config.vu = vu;
            config.verbose = verbose;
            System.out.println("URI: " + config.uri);
            System.out.println("Rate: " + config.rate);
            System.out.println("Duration: " + config.duration);
            System.out.println("Virtual Users: " + config.vu);
            System.out.println("Verbose: " + config.verbose);
        }
        private void LoadYAMLConfig(){
            // TODO : Load YAML file
            System.out.println("Config Path: " + YAMLconfigPath);
        }
    }
    // Handle Help Mode
    @Command(name = "help")
    static class HelpCommand implements Runnable {
        @Override
        public void run() {
            Config config = Config.GetInstance();
            config.help = true;
            // TODO : Print help message
        }
    }

    @Override
    public void run() {
        System.err.println("Error: Must provide a subcommand.");
    }

    public void Parse(String[] args){
        int exitCode = new picocli.CommandLine(new CommandLine())
            .addSubcommand("run", new RunCommand())
            .addSubcommand("help", new HelpCommand())
            .execute(args);
        System.exit(exitCode);
    }

}