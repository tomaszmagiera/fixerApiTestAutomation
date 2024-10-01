package domain;

import lombok.Value;
import java.util.Map;

@Value
public class GetFixerDateSuccessResponse {
    String base;
    String date;
    boolean historical;
    Map<String, Double> rates;
    boolean success;
    long timestamp;
    ErrorMessage error;

    @Value
    public static class ErrorMessage {
        int code;
        String type;
        String info;
    }
}