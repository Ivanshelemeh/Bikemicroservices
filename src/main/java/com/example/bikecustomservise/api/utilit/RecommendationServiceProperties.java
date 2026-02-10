package com.example.bikecustomservise.api.utilit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.time.Duration;

@ConfigurationProperties(prefix = "services.recommendation")
@Validated
public record RecommendationServiceProperties(
        @NotBlank String baseUrl,
        @NonNull Duration timeOut

) {
    public RecommendationServiceProperties {
        if (timeOut == null)
            timeOut = Duration.ofSeconds(5);
    }
}
