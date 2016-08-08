package com.lucaslouca.commands;

import com.lucaslouca.service.LLRailRoadService;
import com.lucaslouca.service.LLRailRoadServiceImpl;
import com.lucaslouca.util.LLTownMap;
import com.lucaslouca.util.LLTownMapImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

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
            processor = new LLCommandProccesor(commandFactory);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRunAll() {
        ClassLoader classLoader = getClass().getClassLoader();
        File commandsFile = new File(classLoader.getResource("commands.txt").getFile());
        String ans = null;
        try {
            ans = processor.runAll(commandsFile.getAbsolutePath());
            assertEquals("9\n5\n13\n22\n2\n3\n9\n9\n7\n7", ans);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
