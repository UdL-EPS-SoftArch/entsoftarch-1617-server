package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.repository.DataOwnerRepository;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import cat.udl.eps.entsoftarch.repository.UserRepository;
import com.jayway.jsonpath.JsonPath;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by http://rhizomik.net/~roberto/
 */
public class RegisterDatasetStepdefs {
    private static final Logger logger = LoggerFactory.getLogger(RegisterDatasetStepdefs.class);

    @Autowired private StepDefs stepDefs;
    @Autowired private DatasetRepository datasetRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private DataOwnerRepository dataOwnerRepository;

    @And("^There (?:are|is) (\\d+) datasets? registered$")
    public void thereAreDatasetsRegistered(int count) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(get("/datasets")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.datasets", hasSize(count)));
    }

    @Given("^There is a dataset with title \"([^\"]*)\" and owner \"([^\"]*)\"$")
    public void thereIsADatasetWithTitleAndOwner(String title, String username) throws Throwable {
        DataOwner owner = dataOwnerRepository.findOne(username);
        Dataset dataset = new Dataset();
        dataset.setTitle(title);
        dataset.setOwner(owner);
        dataset.setDateTime(ZonedDateTime.now());
        datasetRepository.save(dataset);
    }

    @Given("^There is a dataset with description \"([^\"]*)\" and owner \"([^\"]*)\"$")
    public void thereIsADatasetWithDescriptionAndOwner(String description, String username) throws Throwable {
        DataOwner owner = dataOwnerRepository.findOne(username);
        Dataset dataset = new Dataset();
        dataset.setDescription(description);
        dataset.setTitle("title");
        dataset.setOwner(owner);
        dataset.setDateTime(ZonedDateTime.now());
        datasetRepository.save(dataset);
    }

    @When("^I register a dataset with title \"([^\"]*)\"$")
    public void iRegisterADatasetWithTitle(String title) throws Throwable {
        Dataset dataset = new Dataset();
        dataset.setTitle(title);
        String message = stepDefs.mapper.writeValueAsString(dataset);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/datasets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

    @And("^The new dataset has title \"([^\"]*)\"$")
    public void existsADatasetWithDescription(String title) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.title", is(title)));
    }

    @And("^The new dataset has date and time approximately now$")
    public void theNewDatasetHasDateAndTimeApproximatelyNow() throws Throwable {
        String response = stepDefs.result.andReturn().getResponse().getContentAsString();
        ZonedDateTime dateTime = ZonedDateTime.parse(JsonPath.read(response, "$.dateTime"));
        Assert.assertThat(dateTime, greaterThan(ZonedDateTime.now().minus(5, ChronoUnit.SECONDS)));
        Assert.assertThat(dateTime, lessThan(ZonedDateTime.now()));
    }

    @And("^User \"([^\"]*)\" owns (\\d+) dataset$")
    public void userOwnsDataset(String username, int count) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(get("/dataOwners/{username}/ownsDatasets", username)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.datasets", hasSize(count)));
    }
}
