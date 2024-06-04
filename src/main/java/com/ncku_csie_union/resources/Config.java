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

    public long GetDuration() {
        // return in milliseconds
        long duration = 0;
        if (this.duration.endsWith("s")) {
            duration = Long.parseLong(this.duration.substring(0, this.duration.length() - 1));
        } else if (this.duration.endsWith("m")) {
            duration = Long.parseLong(this.duration.substring(0, this.duration.length() - 1)) * 60;
        } else if (this.duration.endsWith("h")) {
            duration = Long.parseLong(this.duration.substring(0, this.duration.length() - 1)) * 60 * 60;
        } else if (this.duration.endsWith("d")) {
            duration = Long.parseLong(this.duration.substring(0, this.duration.length() - 1)) * 60 * 60 * 24;
        }
        return duration * 1000;
    }
}