package lara.carmen.GamesShop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Sirve las imágenes directamente desde la carpeta del proyecto para que
    // las subidas se vean en tiempo real sin reiniciar ni recompilar
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploads = Paths.get("src/main/resources/static/uploads").toAbsolutePath();
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploads.toUri().toString());
    }
}
