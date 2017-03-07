package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Comment;
import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.domain.User;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.time.Instant;
import java.time.ZonedDateTime;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by santi on 6/03/17.
 */
public class CommentAboutDataSetStepDefs {

    private static String currentPassword;
    private static String currentUsername;
    private static ZonedDateTime zonedDateTime;
    private StepDefs stepDefs;
    private Dataset dataset;

    @Before
    public void setup() {
        // Clear authentication credentials at the start of every test.
        CommentAboutDataSetStepDefs.currentPassword = "";
        CommentAboutDataSetStepDefs.currentUsername = "";
        this.dataset = new Dataset();
        zonedDateTime = ZonedDateTime.now();
    }

    static RequestPostProcessor authenticate() {
        return currentUsername!=null ? httpBasic(currentUsername, currentPassword) : anonymous();
    }

    @Given("^I login as \"([^\"]*)\" with password \"([^\"]*)\"$")
    public void iLoginAsWithPassword(String username, String password) throws Throwable {
        CommentAboutDataSetStepDefs.currentUsername = username;
        CommentAboutDataSetStepDefs.currentPassword = password;
    }

    @When("^I comment a dataset with text \"([^\"]*)\" and user \"([^\"]*)\"$")
    public void iCommentADataset(String text, User user) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Comment comment = new Comment();
        comment.setTxt(text);
        comment.setUser(user);
        comment.setDateTime(zonedDateTime);
        String message = stepDefs.mapper.writeValueAsString(comment);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());

    }

    @Then("^This dataset has a new comment$")
    public void thisDatasetHasANewComment() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        /*Comment comment = new Comment();
        comment.setTxt(text);
        comment.setUser(user);
        comment.setDateTime(zonedDateTime);*/
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
