package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.domain.Tag;
import cat.udl.eps.entsoftarch.domain.User;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import cat.udl.eps.entsoftarch.repository.UserRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RestMediaTypes;

import java.time.ZonedDateTime;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by ubuntu on 16/03/17.
 */
public class ShareDatasetStepDefs {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    StepDefs stepDefs;

    @And("^User \"([^\"]*)\" have (\\d+) shared dataset$")
    public void userHaveSharedDataset(String userName, int sharedDatasets) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasets/search/sharedWith?name={userName}",userName))
                .andDo(print());
    }



    @When("^I share the dataset titled \"([^\"]*)\" with user \"([^\"]*)\"$")
    public void iShareTheDatasetTitledWithUser(String datasetTitle, String userName) throws Throwable {
        Dataset dataset = datasetRepository.findByTitle(datasetTitle).get(0);
        //User user = userRepository.findByName(user).get(0);
        User user = userRepository.findOne(userName);
        String message = "/user/" + user.getUsername();

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/datasets/{id}/sharedWith", dataset.getId())
                        .contentType(RestMediaTypes.TEXT_URI_LIST)
                        .content(message)
                        .with(authenticate()))
                .andDo(print());
    }
}
