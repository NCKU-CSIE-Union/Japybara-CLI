package com.ncku_csie_union.resources;
import com.ncku_csie_union.resources.interfaces.ICommandLine;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "j8", mixinStandardHelpOptions = true, version = "1.0")
public class CommandLine implements Runnable, ICommandLine {
    // Handle Run Mode
    @Command(name = "run", description = "Run the test")
    static class RunCommand implements Runnable {
        @Option(names = {"--uri"}, required = false)
        private String uri;
        @Option(names = {"--rate"}, defaultValue = "100")
        private int rate;
        @Option(names = {"--duration"}, defaultValue = "1m")
        private String duration;
        @Option(names = {"--vu"}, defaultValue = "500")
        private int vu;
        @Option(names = "--verbose")
        private boolean verbose;
        @Option(names = {"--config"}, defaultValue = "", description = "Path to configuration YAML file.")
        private String YAMLconfigPath;

        @Override
        public void run() {
            Config config = Config.GetInstance();
            if(YAMLconfigPath.isEmpty()){
                if (uri == null || uri.isEmpty()) {
                    System.err.println("Error: --uri must be provided when not using a config file.");
                } else {
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
            } else { // If no YAML file is provided, use the command line arguments
                System.out.println("Config Path: " + YAMLconfigPath);
                // TODO : Load YAML file
            }
        }
    }
    // Handle Help Mode
    @Command(name = "help")
    static class HelpCommand implements Runnable {
        @Override
        public void run() {
            Config config = Config.GetInstance();
            config.help = true;
            System.out.println(config.help);
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