package utils.assertions;

import domain.GetFixerDateErrorHtmlResponse;
import domain.GetFixerDateErrorResponse;
import domain.GetFixerDateSuccessResponse;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.SoftAssertions;

public class GetFixerDateAssertions {
    SoftAssertions sa = new SoftAssertions();

    private boolean verifyIfSuccessIn200(GetFixerDateSuccessResponse successResponse) {
        return successResponse.getError() == null;
    }
    private Integer getErrorCodeFromBodyOf200(GetFixerDateSuccessResponse successResponse) {
            return successResponse.getError().getCode();
    }

    private void assert106(GetFixerDateSuccessResponse successResponse) {
        sa.assertThat(successResponse.isSuccess()).isFalse();
        sa.assertThat(successResponse.getError().getType()).isEqualTo("no_rates_available");
        sa.assertThat(successResponse.getError().getInfo())
                .isEqualTo("Your query did not return any results. Please try again.");
        sa.assertAll();
    }

    private void assert201(GetFixerDateSuccessResponse successResponse) {
        sa.assertThat(successResponse.isSuccess()).isFalse();
        sa.assertThat(successResponse.getError().getType()).isEqualTo("invalid_base_currency");
        sa.assertThat(successResponse.getError().getInfo()).isNull();
        sa.assertAll();
    }

    private void assert202(GetFixerDateSuccessResponse successResponse) {
        sa.assertThat(successResponse.isSuccess()).isFalse();
        sa.assertThat(successResponse.getError().getType()).isEqualTo("invalid_currency_codes");
        sa.assertThat(successResponse.getError().getInfo())
                .isEqualTo("You have provided one or more invalid Currency Codes. [Required format: currencies=EUR,USD,GBP,...]");
        sa.assertAll();
    }

    private void assert302(GetFixerDateSuccessResponse successResponse) {
        sa.assertThat(successResponse.isSuccess()).isFalse();
        sa.assertThat(successResponse.getError().getType()).isEqualTo("invalid_date");
        sa.assertThat(successResponse.getError().getInfo())
                .isEqualTo("You have entered an invalid date. [Required format: date=YYYY-MM-DD]");
        sa.assertAll();
    }

    private void assert200Success(GetFixerDateSuccessResponse successResponse, String symbols) {
        if (StringUtils.isEmpty(symbols)) {
            sa.assertThat(successResponse.isSuccess()).isTrue();
            sa.assertThat(successResponse.getTimestamp()).isEqualTo(915235199);
            sa.assertThat(successResponse.isHistorical()).isTrue();
            sa.assertThat(successResponse.getBase()).isEqualTo("EUR");
            sa.assertThat(successResponse.getDate()).isEqualTo("1999-01-01");
            sa.assertThat(successResponse.getRates().keySet().size()).isEqualTo(32);
            sa.assertAll();
        } else {
            sa.assertThat(successResponse.isSuccess()).isTrue();
            sa.assertThat(successResponse.getTimestamp()).isEqualTo(915235199);
            sa.assertThat(successResponse.isHistorical()).isTrue();
            sa.assertThat(successResponse.getBase()).isEqualTo("EUR");
            sa.assertThat(successResponse.getDate()).isEqualTo("1999-01-01");
            sa.assertThat(successResponse.getRates().keySet().size()).isEqualTo(2);
            sa.assertThat(successResponse.getRates().get("USD")).isEqualTo(1.171626);
            sa.assertThat(successResponse.getRates().get("GBP")).isEqualTo(0.706421);
            sa.assertAll();
        }
    }

    public void assert200(GetFixerDateSuccessResponse successResponse, String symbols) {
        if (verifyIfSuccessIn200(successResponse)) {
            assert200Success(successResponse, symbols);
        } else {
            switch (getErrorCodeFromBodyOf200(successResponse)) {
                case 106:
                    assert106(successResponse);
                    break;
                case 201:
                    assert201(successResponse);
                    break;
                case 202:
                    assert202(successResponse);
                    break;
                case 302:
                    assert302(successResponse);
                    break;
            }
        }
    }

    public void assert400(GetFixerDateErrorHtmlResponse htmlResponsePojo) {
        sa.assertThat(htmlResponsePojo.getTitle()).isEqualTo("Error 400 (Bad Request)!!1");
        sa.assertThat(htmlResponsePojo.getErrorMessage()).isEqualTo("400. That’s an error. Your client has issued a malformed or illegal request. That’s all we know.");
        sa.assertAll();
    }

    public void assert401(GetFixerDateErrorResponse errorResponsePojo, String apiKey) {
        if (StringUtils.isEmpty(apiKey)) {
            sa.assertThat(errorResponsePojo.getMessage()).isEqualTo("No API key found in request");
            sa.assertAll();
        } else {
            sa.assertThat(errorResponsePojo.getMessage()).isEqualTo("Invalid authentication credentials");
            sa.assertAll();
        }
    }

    public void assert403(GetFixerDateErrorResponse errorResponsePojo) {
        sa.assertThat(errorResponsePojo.getMessage()).isEqualTo("You cannot consume this service");
        sa.assertAll();
    }

    public void assert404(GetFixerDateErrorResponse errorResponsePojo) {
        sa.assertThat(errorResponsePojo.getMessage()).isEqualTo("no Route matched with those values");
        sa.assertAll();
    }

    public void assert429(GetFixerDateErrorResponse errorResponsePojo) {
        sa.assertThat(errorResponsePojo.getMessage()).isEqualTo("You have exceeded your daily/monthly API rate limit. Please review and upgrade your subscription plan at https://promptapi.com/subscriptions to continue.");
        sa.assertAll();
    }
}
