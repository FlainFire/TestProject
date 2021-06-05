import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.close;

@SuppressWarnings("deprecation")
public class Test {

    //TODO: better not to use an inner classes if it's not needed, move it outside
    //TODO: also you forget to override at least toString()
    public class CompanyDetails {

        private String companyName;
        private List<String> companyEmails;

        public CompanyDetails() {
            this.companyName = "";
            //TODO: why LinkedList?
            this.companyEmails = new LinkedList<>();
        }

        public CompanyDetails(String companyName, List<String> companyEmails) {
            this.companyName = companyName;
            this.companyEmails = companyEmails;
        }

        @Override
        public String toString() {
            return "InfoCollect{" +
                    "contactName='" + companyName + '\'' +
                    ", contactEmails=" + companyEmails +
                    '}';
        }
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        Configuration.browser = "chrome";
    }

    //TODO: why this elements outside the method?
    //TODO: always try to find more meaningful names for methods and variables
    /**This one is more general and matches the number of contactNamesList in DOM (158)*/
    List<SelenideElement> contactEmailsList = Selenide.$$x("//div[2]//details");

    List<SelenideElement> contactNamesList = Selenide.
            $$x("//div[2]//details//summary/child::text()");
    //List<String> stringNamesList = new LinkedList<>();

    //div[@class='editor-content'][2]//div[2]//details//p[last()]//a[@href][last()]
    /** The most precise contactEmailsList xpath.*/


    //TODO: if it's test - use @Test
    //TODO: not sure why you use Test class here and that's all... I can't find any reasonable logic in this class
    public static void main(String[] args) {
        Test test = new Test();
        Selenide.open("https://www.gov.pl/web/poland-businessharbour-ru/itspecialist");

    }

    /** I thought about this kind of logic to store data of each object in its List field
     *
     for (SelenideElement el : test.detailsList) {
     if (el.$x(".//a[@href][last()]").sibling(0).exists()) {
        some code to add it to the List<String> contactEmails of each object
     }
     */


    //TODO: this method do nothing...
    private CompanyDetails infoCollectInitializer(List<SelenideElement> contactNamesList, List<SelenideElement> contactEmailsList) {
        CompanyDetails result = new CompanyDetails();


        return result;
    }

    //TODO: please rename the method to match the Code Convection rules
    //TODO: Same question - why LinkedList?
    //TODO: no need to use fori loop, you can use foreach or streams
    public List<CompanyDetails> InfoCollectList(List<SelenideElement> contactNamesList, List<SelenideElement> contactEmailsList) {
        List<CompanyDetails> result = new LinkedList<>();
        for (int i = 0; i <= contactNamesList.size(); i++) {
            result.add(infoCollectInitializer(contactNamesList, contactEmailsList));
        }
        return result;
    }

    @org.testng.annotations.Test
    public void parseDetailsByUrlTest() {
        Selenide.open("https://www.gov.pl/web/poland-businessharbour-ru/itspecialist");

        Selenide.$$x("//div[2]//details").stream()
                .map(elemDetails -> parseDetails(elemDetails.$(By.tagName("summary")), elemDetails.$$(By.tagName("a"))))
                .filter(info -> !info.companyEmails.isEmpty())
                .forEach(System.out::println);
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


    //TODO: looks like close() method is deprecated - try not to use deprecated methods - find another solutions
    @AfterMethod
    public void tearDown() throws Exception {
        close();
    }

}

