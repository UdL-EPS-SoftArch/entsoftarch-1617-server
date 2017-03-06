package cat.udl.eps.entsoftarch.steps;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

/**
 * Created by santi on 6/03/17.
 */
public class CommentAboutDataSetStepDefs {

    private static String currentPassword;
    private static String currentUsername;

    @Before
    public void setup() {
        // Clear authentication credentials at the start of every test.
        CommentAboutDataSetStepDefs.currentPassword = "";
        CommentAboutDataSetStepDefs.currentUsername = "";
    }
    static RequestPostProcessor authenticate() {
        return currentUsername!=null ? httpBasic(currentUsername, currentPassword) : anonymous();
    }
    @Given("^I login as \"([^\"]*)\" with password \"([^\"]*)\"$")
    public void iLoginAsWithPassword(String username, String password) throws Throwable {
        CommentAboutDataSetStepDefs.currentUsername = username;
        CommentAboutDataSetStepDefs.currentPassword = password;
    }

    @When("^I comment a dataset$")
    public void iCommentADataset() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^This dataset has a new comment$")
    public void thisDatasetHasANewComment() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The new comment has been published with username \"([^\"]*)\" or username \"([^\"]*)\"$")
    public void theNewCommentHasBeenPublishedWithUsernameOrUsername(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The new comment has date and time approximately now$")
    public void theNewCommentHasDateAndTimeApproximatelyNow() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
