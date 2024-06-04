package com.ncku_csie_union.resources;
import com.ncku_csie_union.resources.interfaces.ICommandLine;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "j8", mixinStandardHelpOptions = true, version = "1.0", description = "Web Pressure Test Tool")
public class CommandLine implements Runnable, ICommandLine {
    // Handle Run Mode
    @Command(name = "run", description = "Run the test")
    static class RunCommand implements Runnable {
        @Option(names = {"--uri"}, defaultValue = "non-receive", description = "URI to test.")
        static private String uri;
        @Option(names = {"--rate"}, defaultValue = "100", description = "Requests per second.")
        static private int rate;
        @Option(names = {"--duration"}, defaultValue = "1m", description = "Duration of the test.")
        static private String duration;
        @Option(names = {"--vu"}, defaultValue = "500", description = "Number of virtual users.")
        static private int vu;
        @Option(names = "--verbose", description = "Verbose output.")
        static private boolean verbose;
        @Option(names = {"--config"}, defaultValue = "", description = "Path to configuration YAML file.")
        static private String YAMLconfigPath;

        static public String get_uri() { return uri; }
        static public int get_rate() { return rate; }
        static public int get_vu() { return vu; }

        @Override
        public void run() {
            if(YAMLconfigPath.isEmpty()) {
                if(rate <= 0) {
                    System.out.println("Rate can't be zero or negative. Set to default value 100.");
                    rate = 100;
                }
                if(vu > 10000 || vu < 1) {
                    System.out.println("VU number should be between 10000 and 1. Set to default value 500.");
                    vu = 500;
                }
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
            if(config.verbose){
                System.out.println("URI: " + config.uri);
                System.out.println("Rate: " + config.rate);
                System.out.println("Duration: " + config.duration);
                System.out.println("Virtual Users: " + config.vu);
                System.out.println("Verbose: " + config.verbose);
            }
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
        System.out.println("Exit Code: " + exitCode);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

}