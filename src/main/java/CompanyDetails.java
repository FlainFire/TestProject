import java.util.List;

public class CompanyDetails {
    private String companyName;
    private List<String> companyEmails;


    public CompanyDetails(String companyName, List<String> companyEmails) {
        this.companyName = companyName;
        this.companyEmails = companyEmails;
    }

    @Override
    public String toString() {
        return "CompanyDetails{" +
                "contactName='" + companyName + '\'' +
                ", contactEmails=" + companyEmails +
                '}';
    }

    public List<String> getCompanyEmails() {
        return companyEmails;
    }


}
