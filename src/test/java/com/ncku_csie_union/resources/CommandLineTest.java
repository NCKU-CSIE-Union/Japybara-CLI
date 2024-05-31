package com.ncku_csie_union.resources;

import com.ncku_csie_union.resources.interfaces.ICommandLine;
import org.junit.Test;
import org.junit.Before;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class CommandLineTest {
    private CommandLine cmd;
    @Before
    public void setUp() {
        cmd = new CommandLine();
    }
    @Test
    public void NoURIProvided() {
        String[] args = new String[] {"run"};
        cmd.Parse(args);
        assertEquals("non-receive", CommandLine.RunCommand.get_uri());
    }
    @Test
    public void NegativeRateResetTo100() {
        String[] args = {"run", "--uri", "https://www.google.com/", "--rate", "-10"};
        cmd.Parse(args);
        assertEquals(100, CommandLine.RunCommand.get_rate());
    }
    @Test
    public void ZeroRateResetTo100() {
        String[] args = {"run", "--uri", "https://www.google.com/", "--rate", "0"};
        cmd.Parse(args);
        assertEquals(100, CommandLine.RunCommand.get_rate());
    }
    @Test
    public void NormalVUNumber888() {
        String[] args = {"run", "--uri", "https://www.google.com/", "--vu", "888"};
        cmd.Parse(args);
        assertEquals(888, CommandLine.RunCommand.get_vu());
    }
    @Test
    public void EdgeVUNumber10000() {
        String[] args = {"run", "--uri", "https://www.google.com/", "--vu", "10000"};
        cmd.Parse(args);
        assertEquals(10000, CommandLine.RunCommand.get_vu());
    }
    @Test
    public void EdgeVUNumber10001() {
        String[] args = {"run", "--uri", "https://www.google.com/", "--vu", "10001"};
        cmd.Parse(args);
        assertEquals(500, CommandLine.RunCommand.get_vu());
    }
    @Test
    public void EdgeVUNumber1() {
        String[] args = {"run", "--uri", "https://www.google.com/", "--vu", "1"};
        cmd.Parse(args);
        assertEquals(1, CommandLine.RunCommand.get_vu());
    }
    @Test
    public void EdgeVUNumber0() {
        String[] args = {"run", "--uri", "https://www.google.com/", "--vu", "0"};
        cmd.Parse(args);
        assertEquals(500, CommandLine.RunCommand.get_vu());
    }
}