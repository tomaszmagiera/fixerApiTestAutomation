package restAssuredSteps;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class GetFixerDateRequestSpecification {

    public RequestSpecification prepareRequestSpecification(String endpoint, String date, String apiKey, String base, String symbols, String body) {

        RequestSpecification request = RestAssured
                .given()
                .baseUri("https://api.apilayer.com")
                .basePath(endpoint + date)
                .header("apikey", apiKey);

        if (body != null && !body.isEmpty()) {
            request.body(body);
        }
        if (base != null && !base.isEmpty()) {
            request.queryParam("base", base);
        }
        if (symbols != null && !symbols.isEmpty()) {
            request.queryParam("symbols", symbols);
        }
        return request;
    }
}