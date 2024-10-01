package steps;

import client.HttpClient;
import domain.GetFixerDateErrorHtmlResponse;
import domain.GetFixerDateErrorResponse;
import domain.GetFixerDateSuccessResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import restAssuredSteps.GetFixerDateRequestSpecification;
import utils.assertions.GetFixerDateAssertions;
import utils.HtmlResponseMapper;

public class GetFixerDateSteps {
    private String endpoint;
    private String date;
    private String baseCurrency;
    private String symbols;
    private String apiKey;
    private String body;
    private Response response;
    private RequestSpecification requestSpecification;
    private GetFixerDateSuccessResponse successResponsePojo;
    private GetFixerDateErrorResponse errorResponsePojo;
    private GetFixerDateErrorHtmlResponse htmlResponsePojo;
    private GetFixerDateRequestSpecification getFixerDateRequestSpecification = new GetFixerDateRequestSpecification();
    private HttpClient httpClient = new HttpClient();

    @Given("The fixer date endpoint is set to {string}")
    public void theFixerDateEndpointIsSet(String endpoint) {
        this.endpoint = endpoint;
    }

    @Given("The apiKey is set to {string}")
    public void theApiKeyIsSet(String apiKey) {
        this.apiKey = apiKey;
    }

    @Given("The date {string} in past is set")
    public void theGivenDateIsSet(String date) {
        this.date = date;
    }

    @Given("I'm setting my base currency as {string}")
    public void settingBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    @Given("I want to see the following symbols {string}")
    public void showGivenSymbols(String symbols) {
        this.symbols = symbols;
    }

    @Given("The request body is {string}")
    public void addGivenBody(String body) {
        this.body = body;
    }

    @When("I prepare request specification")
    public void prepareRequestSpecification() {
        requestSpecification = getFixerDateRequestSpecification
                .prepareRequestSpecification(endpoint, date, apiKey, baseCurrency, symbols, body);
    }

    private void mapResponseToPojo(Response response) {
        if (response.getStatusCode() == 200) {
            successResponsePojo = response.as(GetFixerDateSuccessResponse.class);
        } else if (response.getStatusCode() == 400) {
            htmlResponsePojo = HtmlResponseMapper.mapHtmlToErrorResponse(response.asString());
        }
        else {
            errorResponsePojo = response.as(GetFixerDateErrorResponse.class);
        }
    }

    @When("I execute get request {string} time(s)")
    public void executeGetRequestGivenTimes(String iterations) {
        if (iterations.equals("one")) {
            response = httpClient.get(requestSpecification);
        } else if (iterations.equals("max")) {
            response = executeGetRequestToExceedLimit();
        }
        mapResponseToPojo(response);
    }

    private Response executeGetRequestToExceedLimit() {
        int remainingLimit = Integer.MAX_VALUE;
        while (remainingLimit > 0) {
            remainingLimit = getRemainingLimit();
        }
        return response = httpClient.get(requestSpecification);
    }

    private Integer getRemainingLimit() {
        response = httpClient.get(requestSpecification);
        String remaining = response.getHeader("X-RateLimit-Remaining-Month");
        if (remaining == null) {
            return 1;
        }
        return Integer.parseInt(remaining);
    }

    @Then("I expect the {int} response code")
    public void assertResponseCode(int responseCode) {
        Assert.assertEquals(response.getStatusCode(), responseCode);
    }

    @Then("I expect the correct message for response code {int}")
    public void assertResponseMessage(int responseCode) {
        GetFixerDateAssertions getFixerDateAssertions = new GetFixerDateAssertions();
        switch (responseCode) {
            case 200:
                getFixerDateAssertions.assert200(successResponsePojo, symbols);
                break;
            case 400:
                getFixerDateAssertions.assert400(htmlResponsePojo);
                break;
            case 401:
                getFixerDateAssertions.assert401(errorResponsePojo, apiKey);
                break;
            case 403:
                getFixerDateAssertions.assert403(errorResponsePojo);
                break;
            case 404:
                getFixerDateAssertions.assert404(errorResponsePojo);
                break;
            case 429:
                getFixerDateAssertions.assert429(errorResponsePojo);
                break;
        }
    }
}
