package net.hydrogen2oxygen.hyperselenium.services;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.hydrogen2oxygen.hyperselenium.domain.WebSite;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class DataService {

    public WebSite saveOrUpdateWebSite(WebSite webSite) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File("target/test.json"), webSite);

        return webSite;
    }

    // TODO load all websites, load one specific
}