package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Comment;
import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.domain.User;
import cat.udl.eps.entsoftarch.repository.CommentRepository;
import cat.udl.eps.entsoftarch.repository.DataOwnerRepository;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class CommentAboutDataSetStepDefs {

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

//    @Before
//    public void setup() {
//        // Clear authentication credentials at the start of every test.
//        zonedDateTime = ZonedDateTime.now();
//        this.dataset = new Dataset();
//        dataset.setTitle("dataset");
//        dataset.setOwner();
//        dataset.setDateTime(zonedDateTime);
//        datasetRepository.save(dataset);
//    }


    @When("^I comment a dataset \"([^\"]*)\" with text \"([^\"]*)\" and user \"([^\"]*)\"$")
    public void iCommentADataset(String dataset, String text, String user) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Dataset data = datasetRepository.findByTitle(dataset).get(0);
        User userC = dataOwnerRepository.findOne(user);
        Comment comment = new Comment();
        comment.setUser(userC);
        comment.setAbout(data);
        comment.setTxt(text);
        comment.setDateTime(zonedDateTime);
        String message = stepDefs.mapper.writeValueAsString(comment);

        if (comment.getUser() != null) {
            stepDefs.result = stepDefs.mockMvc.perform(post("/comments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(message)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                    .andDo(print());

            commentRepository.save(comment);
        }

    }


    @And("^The new comment has been published with username \"([^\"]*)\"$")
    public void theNewCommentHasBeenPublishedWithUsername(String arg0) throws Throwable {
        User user = dataOwnerRepository.findOne(arg0);
        Comment comment = commentRepository.findByUser(user).get(0);
        Assert.assertSame(user, comment.getUser());
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
        comment.setTxt(arg1);
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
