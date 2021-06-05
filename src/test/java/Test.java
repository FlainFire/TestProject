import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.*;

import static com.codeborne.selenide.Selenide.close;

@SuppressWarnings("deprecation")
public class Test {
    public class InfoCollect {

        private String contactName;
        private List<String> contactEmails;


        public InfoCollect(String contactName, List<String> contactEmails) {
            this.contactName = contactName;
            this.contactEmails = contactEmails;
        }

        public InfoCollect() {
            this.contactName = "";
            this.contactEmails = new LinkedList<>();
        }

    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        Configuration.browser = "chrome";
    }

    List<SelenideElement> contactEmailsList = Selenide.
            $$x("//div[2]//details");  /**This one is more general and matches
                                                       the number of contactNamesList in DOM (158)*/
    List<SelenideElement> contactNamesList = Selenide.
            $$x("//div[2]//details//summary/child::text()");
    //List<String> stringNamesList = new LinkedList<>();

    //div[@class='editor-content'][2]//div[2]//details//p[last()]//a[@href][last()]
    /** The most precise contactEmailsList xpath.*/


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


    private InfoCollect infoCollectInitializer(List<SelenideElement> contactNamesList, List<SelenideElement> contactEmailsList) {
        InfoCollect result = new InfoCollect();


        return result;
    }

    public List<InfoCollect> InfoCollectList(List<SelenideElement> contactNamesList, List<SelenideElement> contactEmailsList) {
        List<InfoCollect> result = new LinkedList<>();
        for (int i = 0; i <= contactNamesList.size(); i++) {
            result.add(infoCollectInitializer(contactNamesList, contactEmailsList));
        }
        return result;
    }


    @AfterMethod
    public void tearDown() throws Exception {
        close();
    }

}

