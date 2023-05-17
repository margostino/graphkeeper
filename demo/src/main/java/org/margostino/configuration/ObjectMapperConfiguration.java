package org.margostino.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;
import org.margostino.serializer.InstantDeserializer;

import java.time.Instant;

@ApplicationScoped
public class ObjectMapperConfiguration {

    @Singleton
    @Produces
    public ObjectMapper objectMapper() {
        JavaTimeModule timeModule = new JavaTimeModule();
        SimpleModule simpleModule = new SimpleModule();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        simpleModule.addDeserializer(Instant.class, new InstantDeserializer());
        objectMapper.registerModule(simpleModule);
        objectMapper.registerModule(timeModule);
        return objectMapper;
    }
}