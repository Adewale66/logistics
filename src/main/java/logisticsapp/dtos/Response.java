package logisticsapp.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Response(
        String message,
        Object data,
        @JsonProperty("status_code")
        int statusCode) {
}