package org.messenger.JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.messenger.Annotations.AnnotationError;
import org.messenger.Annotations.JsonElement;
import org.messenger.Annotations.JsonSerializable;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonParser {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Logger logger = LogManager.getLogger(JsonParser.class);

    public static String stringify(Object object) throws JsonProcessingException {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error(e);
            throw e;
        }
    }

    public static <T> T parse(String jsonString, Class<T> type) throws IOException {
        try {
            return objectMapper.readValue(jsonString, type);
        } catch (IOException e) {
            logger.error(e);
            throw e;        }
    }
}
