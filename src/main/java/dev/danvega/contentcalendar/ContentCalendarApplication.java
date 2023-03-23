package dev.danvega.contentcalendar;

import dev.danvega.contentcalendar.config.ContentCalendarProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(ContentCalendarProperties.class)
@SpringBootApplication
public class ContentCalendarApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentCalendarApplication.class, args);
    }
}
