package main.resources;
import main.resources.interfaces.IBase;
import main.resources.core.Logger;

public class Base extends IBase {
    public Config config = Config.GetInstance();
    public Logger logger = Logger.GetInstance();
}
