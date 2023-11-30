package org.messenger.Command;

import java.util.*;

public class CommandParser {
    public static ACommand parse(String JSON) {
        if(!JSON.startsWith("{") || !JSON.endsWith("}")) {
            throw new CommandParseException("Missing { or }");
        }

        ArrayList<String> splitCommand = new ArrayList<>(List.of(JSON.substring(1, JSON.lastIndexOf("}") - 1).split(","))); // "param1":"value1","param2":"value2",... -> ["param1":"value1", "param2":"value2", ...]
        HashMap<String, String> parsedCommand = new HashMap<>();
        for(String command: splitCommand) {
            String[] pair = command.split(":");
            pair[0] = quoteExtractor(pair[0].strip()).strip();
            pair[1] = quoteExtractor(pair[1].strip()).strip();
            parsedCommand.put(pair[0], pair[1]);
        }

        if(!parsedCommand.containsKey("type")) {
            throw new CommandParseException("Type not specified");
        }

        ACommand command = null;
        if(CommandTypes.LOGIN.equals(parsedCommand.get("type"))) {
            command = new LoginCommand();
        }
        // ...

        if(command == null) {
            throw new CommandParseException("Type does not exist");
        }

        for(String key: parsedCommand.keySet()) {
            if(!key.equals("type")) {
                command.addParam(key, parsedCommand.get(key));
            }
        }

        return command;
    }

    private static String quoteExtractor(String item) {
        return item.replace("\"", "");
    }
}
