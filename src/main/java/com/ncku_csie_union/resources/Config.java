package com.ncku_csie_union.resources;

public class Config {
    public String uri = "";
    public String duration = "1m";
    public Integer vu = 500;
    public boolean verbose = false;
    public boolean help = false;
    public Integer rate = 100;

    private static Config instance;

    private Config() {
    }

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