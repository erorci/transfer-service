package io.emersonorsi.transferservice.mapping;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class AccountJson {
    public static class Serializer extends JsonObjectSerializer<Account> {

        @Override
        protected void serializeObject(Account properties,
                                       JsonGenerator jsonGenerator,
                                       SerializerProvider serializerProvider) throws IOException {
            properties.writeJson(jsonGenerator);
        }
    }
}
