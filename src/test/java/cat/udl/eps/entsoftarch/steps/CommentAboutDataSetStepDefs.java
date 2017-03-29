package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Comment;
import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.domain.User;
import cat.udl.eps.entsoftarch.repository.CommentRepository;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import cat.udl.eps.entsoftarch.repository.UserRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class CommentAboutDataSetStepDefs {

    private static ZonedDateTime zonedDateTime;
    @Autowired
    private UserRepository dataOwnerRepository;
    @Autowired
    private DatasetRepository datasetRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private StepDefs stepDefs;
    private Dataset dataset;



    @When("^I comment a dataset \"([^\"]*)\" with text \"([^\"]*)\" and user \"([^\"]*)\"$")
    public void iCommentADataset(String dataset, String text, String user) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Dataset data = datasetRepository.findByTitle(dataset).get(0);
        User userC = dataOwnerRepository.findOne(user);
        Comment comment = new Comment();
        comment.setUser(userC);
        comment.setAbout(data);
        comment.setText(text);
        comment.setDateTime(zonedDateTime);
        String message = stepDefs.mapper.writeValueAsString(comment);


        stepDefs.result = stepDefs.mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        commentRepository.save(comment);


    }

    @Then("^The new comment with text \"([^\"]*)\" has been published with username \"([^\"]*)\"$")
    public void theNewCommentWithTextHasBeenPublishedWithUsername(String text, String user) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.text", is(text)));
        stepDefs.result.andExpect(jsonPath("$.user", is(user)));

    }

    @And("^The new comment has date and time approximately now$")
    public void theNewCommentHasDateAndTimeApproximatelyNow() throws Throwable {
        Comment comment = commentRepository.findOne(0L);
        Assert.assertThat(comment.getDateTime(), lessThan(ZonedDateTime.now()));
    }


    @When("^I comment a dataset \"([^\"]*)\" with text \"([^\"]*)\"$")
    public void iCommentADatasetWithText(String arg0, String arg1) throws Throwable {
        Dataset data = datasetRepository.findByTitle(arg0).get(0);
        Comment comment = new Comment();
        comment.setAbout(data);
        comment.setText(arg1);
        comment.setDateTime(zonedDateTime);

        String message = stepDefs.mapper.writeValueAsString(comment);

        stepDefs.result = stepDefs.mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());


        commentRepository.save(comment);
    }


}
