package com.ncku_csie_union.resources;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class CommandLineTest {
    @Test
    public void NoURIProvided() {
        String[] args = {"run"};
        CommandLine commandLine = new CommandLine();
        Exception e = assertThrows(RuntimeException.class,() -> commandLine.Parse(args));
        assertEquals("Exception: --uri must be provided when not using a config file.", e.getMessage());
    }
    @Test
    public void NegativeRate() {
        String[] args = {"run", "https://www.google.com/", "--rate", "-10"};
        CommandLine commandLine = new CommandLine();
        Exception e = assertThrows(RuntimeException.class,() -> commandLine.Parse(args));
        assertEquals("Exception: --rate can't be negative or zero.", e.getMessage());
    }
    @Test
    public void ZeroRate() {
        String[] args = {"run", "https://www.google.com/", "--rate", "0"};
        CommandLine commandLine = new CommandLine();
        Exception e = assertThrows(RuntimeException.class,() -> commandLine.Parse(args));
        assertEquals("Exception: --rate can't be negative or zero.", e.getMessage());
    }

}