package com.ncku_csie_union.resources;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.junit.jupiter.api.Assertions.*;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
public class URIRequestTest {
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;
    private Task task;
    @Before
    public void setUp() {
        System.setErr(new PrintStream(errContent));
        task = new Task();
    }

    @After
    public void restoreStreams() {
        System.setErr(originalErr);
    }
    @Test
    public void InvalidURITest(){
        task.Init_uri("PeterEatShit.com.ja so n");
        assertTrue(errContent.toString().contains("Error: Invalid URI."), "Didn't find the  invalid uri error message.");
    }
}