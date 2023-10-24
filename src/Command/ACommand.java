package Command;

import User.*;


import java.util.HashMap;
import java.util.Map;

public abstract class ACommand {
    private HashMap<String, String> params;
    protected static String type;

    protected ACommand(HashMap<String, String> params) {
        this.params = params;
    }

    public User makeUser() throws IllegalArgumentException {
        if(!type.equals("login")) {
            throw new IllegalArgumentException("No \"login\" field");
        }
        Token token = TokenGenerator.generate();
        return new User(params.get("username"), params.get("key"), params.get("iv"), token);
    }

    protected ACommand() {
        params = new HashMap<>();
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public String getParamValue(String param) {
        if(params.containsKey(param)) {
            return params.get(param);
        }
        throw new IllegalArgumentException();
    }

    public void removeParam(String key) {
        params.remove(key);
    }

    protected String quotify(String text) {
        return "\"" + text + "\"";
    }

    public String toJSON() {
        StringBuilder sb = new StringBuilder("{");
        sb.append(quotify("type")).append(":").append(quotify(type)).append(",");
        for(String key: params.keySet()) {
            sb.append(quotify(key)).append(":").append(quotify(params.get(key))).append(",");
        }
        sb.replace(sb.lastIndexOf(","), sb.lastIndexOf(",")+1, "}");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("---------\n");
        sb.append("type : " + type + "\n");
        for(Map.Entry<String, String> temp: params.entrySet()) {
            sb.append(temp.getKey() + " : " + temp.getValue() + "\n");
        }
        sb.append("---------");
        return sb.toString();
    }
}
