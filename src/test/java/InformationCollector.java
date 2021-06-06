import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@SuppressWarnings("deprecation")
public class InformationCollector {

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        Configuration.browser = "chrome";
    }

    public static void main(String[] args) {
        InformationCollector ic = new InformationCollector();
        CompanyDetails cd = new CompanyDetails(null, null);
        ic.parseDetailsByUrlTest();
    }

    @org.testng.annotations.Test
    public void parseDetailsByUrlTest() {
        Selenide.open("https://www.gov.pl/web/poland-businessharbour-ru/itspecialist");
        Selenide.$$x("//div[2]//details").stream()
                .map(elemDetails -> parseDetails(elemDetails.$(By.tagName("summary")), elemDetails.$$(By.tagName("a"))))
                .filter(info -> !info.getCompanyEmails().isEmpty())
                .forEach(f -> System.out.println(f.toString()));
    }

    private CompanyDetails parseDetails(SelenideElement summary, ElementsCollection aHrefList) {
        String companyName = summary.text();
        List<String> companyEmails = aHrefList.stream()
                .map(elem -> elem.attr("href"))
                .map(this::getEmailFromHref)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new CompanyDetails(companyName, companyEmails);
    }

    private String getEmailFromHref(String href) {
        Pattern emailPattern = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}", Pattern.CASE_INSENSITIVE);
        Matcher emailMatcher = emailPattern.matcher(href);

        if (emailMatcher.find()) {
            return emailMatcher.group();
        }

        return null;
    }


    @AfterMethod
    public void tearDown() {
        try {
            Selenide.closeWebDriver();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

}

