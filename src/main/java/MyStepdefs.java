import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class MyStepdefs {

    private String path;
    private List<String> chatters;

    @Given("^the following path: \"([^\"]*)\"$")
    public void theFollowingPath(String arg0) throws Throwable {
        this.path = arg0;
        throw new PendingException();
    }


    @When("^the chatter names are detected$")
    public void theChatterNamesAreDetected() {
        List<String> temp = new ArrayList<String>();
        temp.add("asd");
        temp.add("asda");
        temp.add("asdad");
       //this.chatters= Input.detectChatterNames(this.path);
       this.chatters= temp;
    }


    @Then("^there are (\\d+) people listed$")
    public void thereArePeopleListed(int arg0) {
        Assert.assertEquals(chatters.size(),arg0);
    }
}
