package jee.reference.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class DateDeserializer extends JsonDeserializer<Date> {
    private final ThreadLocal<SimpleDateFormat> threadLocalDateFormat = new ThreadLocal<SimpleDateFormat>();

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            SimpleDateFormat dateFormat = this.threadLocalDateFormat.get();
            if (dateFormat == null) {
                dateFormat = new SimpleDateFormat(DateSerializer.FORMAT_STRING);
                threadLocalDateFormat.set(dateFormat);
            }
            return dateFormat.parse(parser.getText());
        } catch (ParseException e) {
            throw new IOException("Date parsing failed!", e);
        }
    }
}
