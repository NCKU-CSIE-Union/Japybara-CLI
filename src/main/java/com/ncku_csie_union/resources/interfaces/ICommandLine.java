package com.ncku_csie_union.resources.interfaces;
import com.ncku_csie_union.resources.Config;

public interface ICommandLine {
    public Config Parse(String[] args);
}