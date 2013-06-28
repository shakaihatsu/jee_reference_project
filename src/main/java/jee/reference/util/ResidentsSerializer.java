package jee.reference.util;

import java.io.IOException;
import java.util.Set;

import jee.reference.model.Person;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class ResidentsSerializer extends JsonSerializer<Set<Person>> {

    @Override
    public void serialize(Set<Person> residents, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        StringBuilder residentsIdsStringBuilder = new StringBuilder();

        residentsIdsStringBuilder.append("[");
        boolean first = true;
        for (Person person : residents) {
            if (first) {
                first = false;
            } else {
                residentsIdsStringBuilder.append(", ");
            }
            residentsIdsStringBuilder.append(person.getId().toString());
        }
        residentsIdsStringBuilder.append("]");

        jsonGenerator.writeString(residentsIdsStringBuilder.toString());
    }
}
