package com.lucaslouca;

import com.lucaslouca.commands.LLCommandFactory;
import com.lucaslouca.commands.LLCommandProccesor;
import com.lucaslouca.commands.LLRailRoadServiceCommandFactory;
import com.lucaslouca.service.LLRailRoadService;
import com.lucaslouca.service.LLRailRoadServiceImpl;
import com.lucaslouca.util.LLPropertyFactory;
import com.lucaslouca.util.LLTownMap;
import com.lucaslouca.util.LLTownMapImpl;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println(LLPropertyFactory.getProperties().get("usage"));
            System.exit(1);
        }

        String graphFilePath    = args[0];
        String commandsFilePath = args[1];

        LLTownMap map = new LLTownMapImpl();
        try {
            map.init(graphFilePath);

            LLRailRoadService service = new LLRailRoadServiceImpl(map);

            // Create an LLCommandFactory
            LLCommandFactory commandFactory = new LLRailRoadServiceCommandFactory(service);

            // Create an LLCommandProccesor that uses commandFactory
            LLCommandProccesor processor = new LLCommandProccesor(commandsFilePath, commandFactory);

            System.out.println(processor.runAll());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
