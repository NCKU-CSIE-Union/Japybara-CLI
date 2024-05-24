package main.resources;

public class Config {
    public String uri = "";
    public Integer rate = 0;
    
    private static Config instance;
    private Config(){}

    public static Config GetInstance() {
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null) {
                    instance = new Config();
                }
            }
        }
        return instance;
    }
}