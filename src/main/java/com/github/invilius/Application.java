package com.github.invilius;

import com.github.invilius.model.CompanyDetails;
import com.github.invilius.service.EmailSender;
import com.github.invilius.service.InformationCollector;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class Application {

    public static final String USERNAME = "your username in gmail (typically it's your email)";
    public static final String PASSWORD = "your password that you crated in google account";

    public static final String SUBJECT = "TEST SUBJECT";
    public static final String MY_EMAIL = "your email";
    public static final String FILENAME = "CvToUpload";

    public static void main(String[] args) {
        try (InformationCollector ic = new InformationCollector()) {
            System.out.println("Start application");

            //For Test purposes I will create my own company to test
            //List<CompanyDetails> pbhCompanyDetails = ic.parsePbhCompaniesDetails();
            List<CompanyDetails> pbhCompanyDetails = ic.getTestData();

            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");

            EmailSender emailSender = new EmailSender(prop, USERNAME, PASSWORD, SUBJECT, MY_EMAIL);

            for (CompanyDetails company : pbhCompanyDetails) {
                System.out.println("Sending email to " + company.getCompanyName());

                InternetAddress[] sendTo = InternetAddress.parse(String.join(",", company.getCompanyEmails()));

                //You can use HTML here
                StringBuilder message = new StringBuilder("Hello, ");
                message.append(company.getCompanyName())
                        .append("<br><br>")
                        .append("I would like to work with you, please check my CV that attached below")
                        .append("<br><br>")
                        .append("Best Regards");

                emailSender.sendEmail(sendTo, message.toString(), getFileFromResource());

                System.out.println("Email to company " + company.getCompanyName() + " sent successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("All emails sent successfully, close application");
    }

    private static File getFileFromResource() throws URISyntaxException {
        ClassLoader classLoader = Application.class.getClassLoader();
        URL resource = classLoader.getResource(FILENAME);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + FILENAME);
        } else {
            return new File(resource.toURI());
        }
    }
}
