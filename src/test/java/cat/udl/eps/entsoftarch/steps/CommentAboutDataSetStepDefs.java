package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Comment;
import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.domain.User;
import cat.udl.eps.entsoftarch.repository.CommentRepository;
import cat.udl.eps.entsoftarch.repository.DataOwnerRepository;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class CommentAboutDataSetStepDefs {

    private static String currentPassword;
    private static String currentUsername;
    private static ZonedDateTime zonedDateTime;
    @Autowired
    private DataOwnerRepository dataOwnerRepository;
    @Autowired
    private DatasetRepository datasetRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private StepDefs stepDefs;
    private Dataset dataset;

    @Before
    public void setup() {
        // Clear authentication credentials at the start of every test.
        CommentAboutDataSetStepDefs.currentPassword = "";
        CommentAboutDataSetStepDefs.currentUsername = "";
        zonedDateTime = ZonedDateTime.now();
        this.dataset = new Dataset();
        dataset.setTitle("dataset");
        dataset.setOwner(dataOwnerRepository.findOne(currentUsername));
        dataset.setDateTime(zonedDateTime);
        datasetRepository.save(dataset);
    }


    @When("^I comment a dataset \"([^\"]*)\" with text \"([^\"]*)\" and user \"([^\"]*)\"$")
    public void iCommentADataset(String dataset, String text, String user) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Dataset data = datasetRepository.findByTitle(dataset).get(0);
        User userC = dataOwnerRepository.findOne(user);
        userC.setUsername("owner");
        Comment comment = new Comment();
        comment.setUser(userC);
        comment.setAbout(data);
        comment.setTxt(text);
        comment.setDateTime(zonedDateTime);
        String message = stepDefs.mapper.writeValueAsString(comment);

        stepDefs.result = stepDefs.mockMvc.perform( post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                        .andDo(print());

        commentRepository.save(comment);
    }

    @Then("^This comment comment about dataset \"([^\"]*)\"$")
    public void thisCommentCommentAboutDataset(String arg0) throws Throwable {
        Dataset dataset = datasetRepository.findByTitle(arg0).get(0);
        Comment comment = commentRepository.findByAbout(dataset).get(0);

        Assert.assertEquals(dataset, comment.getAbout());
    }

    @And("^The new comment has been published with username \"([^\"]*)\" or username \"([^\"]*)\"$")
    public void theNewCommentHasBeenPublishedWithUsernameOrUsername(String arg0, String arg1) throws Throwable {
        User owner = dataOwnerRepository.findOne(arg0);
        User user = dataOwnerRepository.findOne(arg1);
        Comment commentO = commentRepository.findByUser(owner).get(0);
        Comment commentU = commentRepository.findByUser(user).get(0);

        Assert.assertEquals(owner, commentO.getUser());
        Assert.assertEquals(user, commentU.getUser());

    }

    @And("^The new comment has date and time approximately now$")
    public void theNewCommentHasDateAndTimeApproximatelyNow() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I comment a dataset$")
    public void iCommentADataset() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
