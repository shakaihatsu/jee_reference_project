package jee.reference.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class DateSerializer extends JsonSerializer<Date> {
    static final String FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss";
    private final ThreadLocal<SimpleDateFormat> threadLocalDateFormat = new ThreadLocal<SimpleDateFormat>();

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatDate(date));
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = this.threadLocalDateFormat.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(FORMAT_STRING);
            threadLocalDateFormat.set(dateFormat);
        }
        return dateFormat.format(date);
    }
}
