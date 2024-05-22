package main.resources;

public class Config {
    public String uri = "";
    public Integer rate = 0;
    
    private static Config instance;
    private Config(){}

    public static synchronized Config GetInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }
}