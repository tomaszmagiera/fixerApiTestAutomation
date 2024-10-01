package utils;

import domain.GetFixerDateErrorHtmlResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HtmlResponseMapper {

    public static GetFixerDateErrorHtmlResponse mapHtmlToErrorResponse(String html) {
        GetFixerDateErrorHtmlResponse errorResponse = new GetFixerDateErrorHtmlResponse();
        Document doc = Jsoup.parse(html);

        Element titleElement = doc.selectFirst("title");
        if (titleElement != null) {
            errorResponse.setTitle(titleElement.text());
        }

        StringBuilder messageBuilder = new StringBuilder();

        Element statusCodeElement = doc.selectFirst("b");
        if (statusCodeElement != null) {
            messageBuilder.append(statusCodeElement.text());
        }

        Element firstInsElement = doc.selectFirst("ins");
        if (firstInsElement != null) {
            messageBuilder.append(" ").append(firstInsElement.text());
        }

        Element secondParagraphElement = doc.select("p").get(1);
        if (secondParagraphElement != null) {
            messageBuilder.append(" ").append(secondParagraphElement.text());
        }

        errorResponse.setErrorMessage(messageBuilder.toString().trim());
        return errorResponse;
    }
}