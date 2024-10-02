package dariocecchinato.capstone_sicily_fresh.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ServerConfig {

    @Bean
    public Cloudinary imageUploader(@Value("${cloudinary.name}") String name,
                                    @Value("${cloudinary.key}") String key,
                                    @Value("${cloudinary.secret}") String secret) {
        Map<String, String> configuration = new HashMap<>();
        configuration.put("cloud_name", name);
        configuration.put("api_key", key);
        configuration.put("api_secret", secret);
        return new Cloudinary(configuration);
    }
}
