import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JsonTest {
    Logger logger = LogManager.getLogger(JsonTest.class);
    public static class JsonTestingClass {
        private int i;
        private String s;

        public JsonTestingClass() {
            i = 0;
            s = "";
        }
        public JsonTestingClass(int i, String s) {
            this.i = i;
            this.s = s;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null) {
                return false;
            }
            JsonTestingClass t = (JsonTestingClass) obj;
            return this.i == t.i && this.s.equals(t.s);
        }
    }

    @Test
    public void toAndFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonTestingClass test = new JsonTestingClass(5, "Hello world!");
        try {
            String jsonString = objectMapper.writeValueAsString(test);
            logger.debug(jsonString);
            JsonTestingClass test2 = objectMapper.readValue(jsonString, JsonTestingClass.class);
            assertEquals(test, test2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
