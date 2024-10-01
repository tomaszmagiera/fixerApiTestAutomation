package client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HttpClient {
    public Response get(RequestSpecification request) {
        return request
                .when()
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }
}
