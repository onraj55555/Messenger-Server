package JSON;

import Annotations.AnnotationError;
import Annotations.JsonElement;
import Annotations.JsonSerializable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonParser {
    private boolean checkAnnotation(Object object) throws AnnotationError {
        if(Objects.isNull(object)) {
            return false;
        }

        if(object.getClass().isAnnotationPresent(JsonSerializable.class)) {
            return true;
        }

        throw new AnnotationError(JsonSerializable.class.getName());
    }

    public String toJson(Object object) {
        try {
            if(!checkAnnotation(object)) {
                throw new NullPointerException();
            }
        } catch (AnnotationError ae) {
            throw ae;
        }

        Class<?> annotatedClass = object.getClass();

        HashMap<String, String> jsonMap = new HashMap<>();

        for(Field field : annotatedClass.getDeclaredFields()) {
            field.setAccessible(true);
            if(field.isAnnotationPresent(JsonElement.class)) {
                String key = field.getName();
                Object value = null;
                try {
                    value = field.get(object);
                } catch (IllegalAccessException ignored) {}

                jsonMap.put(key, (String) value);
            }
        }

        StringBuilder sb = new StringBuilder("{");
        sb.append(jsonMap
                .entrySet()
                .stream()
                .map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"")
                .collect(Collectors.joining(",")));
        return sb.append("}").toString();
    }
}
