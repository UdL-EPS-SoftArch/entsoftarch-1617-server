package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Comment;
import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import com.jayway.jsonpath.JsonPath;
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

    @Autowired
    private DatasetRepository datasetRepository;
    @Autowired
    private StepDefs stepDefs;


    @When("^I comment a dataset \"([^\"]*)\" with text \"([^\"]*)\"$")
    public void iCommentADataset(String dataset, String text) throws Throwable {
        Dataset data = datasetRepository.findByTitle(dataset).get(0);
        Comment comment = new Comment();
        comment.setText(text);
        comment.setAbout(data);
        String message = stepDefs.mapper.writeValueAsString(comment);


        stepDefs.result = stepDefs.mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @Then("^The new comment with text \"([^\"]*)\" has been published with username \"([^\"]*)\"$")
    public void theNewCommentWithTextHasBeenPublishedWithUsername(String text, String user) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.text", is(text)));

        String response = stepDefs.result.andReturn().getResponse().getContentAsString();
        String userComment = (JsonPath.read(response, "$._links.user.href"));
        String[] parts = userComment.split("/");
        userComment = parts[5];
        Assert.assertThat(userComment, is(user));


    }

    @And("^The new comment has date and time approximately now$")
    public void theNewCommentHasDateAndTimeApproximatelyNow() throws Throwable {
        String response = stepDefs.result.andReturn().getResponse().getContentAsString();
        ZonedDateTime dateTime = ZonedDateTime.parse(JsonPath.read(response, "$.dateTime"));
        Assert.assertThat(dateTime, lessThan(ZonedDateTime.now()));
    }
}
