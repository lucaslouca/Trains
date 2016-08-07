package com.lucaslouca.commands;

import com.lucaslouca.service.LLRailRoadService;
import com.lucaslouca.service.LLRailRoadServiceImpl;
import com.lucaslouca.util.LLTownMap;
import com.lucaslouca.util.LLTownMapImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@code LLCommandProccesor}.
 */
public class LLCommandProccesorTest {
    private LLCommandProccesor processor;

    @Before
    public void initObjects() {
        LLTownMap map = new LLTownMapImpl();
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            // Create a LLRailRoadService
            File graphFile = new File(classLoader.getResource("graph.txt").getFile());
            map.init(graphFile.getAbsolutePath());
            LLRailRoadService service = new LLRailRoadServiceImpl(map);

            // Create an LLCommandFactory
            LLCommandFactory commandFactory = new LLRailRoadServiceCommandFactory(service);

            // Create an LLCommandProccesor that uses commandFactory
            File commandsFile = new File(classLoader.getResource("commands.txt").getFile());
            processor = new LLCommandProccesor(commandsFile.getAbsolutePath(), commandFactory);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRunAll() {
        String ans = processor.runAll();
        assertEquals("9;5;13;22;2;3;9;9;7;7", ans);
    }

    @Test
    public void testRunCommand1() {
        String ans = processor.run("distance;A;D");
        assertEquals("5", ans);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRunUnknownCommand1() {
        String ans = processor.run("xxxx;A;D");
    }
}
