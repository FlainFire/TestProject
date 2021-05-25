import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.List;

import static org.apache.commons.io.IOUtils.close;

public class Test {
    @BeforeClass(alwaysRun = true)
    public void setUp() {
        Configuration.browser = "chrome";
    }

    public static void main(String[] args) {
        Selenide.open("https://www.gov.pl/web/poland-businessharbour-ru/itspecialist");
        List<SelenideElement> contacts = Selenide.
                $$x("//div[@class='editor-content'][2]//div[2]//details//p[last()]//a[@href][last()]");
        for (SelenideElement cntcts : contacts) {
            String strLinkText = cntcts.getOwnText();
            System.out.println("Контакты: " + strLinkText);
        }
    }


    @AfterMethod
    public void tearDown() throws Exception {
        close();
    }

}