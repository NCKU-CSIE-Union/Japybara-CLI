package com.ncku_csie_union.resources;
import com.ncku_csie_union.resources.interfaces.IBase;
import com.ncku_csie_union.resources.core.Logger;

public class Base extends IBase {
    public Config config = Config.GetInstance();
    public Logger logger = Logger.GetInstance();
}
