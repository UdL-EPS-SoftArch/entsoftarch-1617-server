package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.repository.DataOwnerRepository;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import com.jayway.jsonpath.JsonPath;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by pau on 28/2/17.
 */
public class UpdateDatasetStepDefs {
    private static final Logger logger = LoggerFactory.getLogger(UpdateDatasetStepDefs.class);

    @Autowired
    private StepDefs stepDefs;
    @Autowired
    private DatasetRepository datasetRepository;
    @Autowired
    private DataOwnerRepository dataOwnerRepository;


    @When("^I update my dataset with title \"([^\"]*)\" to title \"([^\"]*)\"$")
    public void iUpdateMyDatasetWithTitleToTitle(String oldTitle, String newTitle) throws Throwable {
        Dataset dataset = datasetRepository.findByTitle(oldTitle).get(0);
        dataset.setTitle(newTitle);
        String message = stepDefs.mapper.writeValueAsString(dataset);

        stepDefs.result = stepDefs.mockMvc.perform(
                put("/datasets/{id}", dataset.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

    @And("^The new dataset has lastModified field approximately now$")
    public void theNewDatasetHasLastModifiedFieldApproximatelyNow() throws Throwable {
        String response = stepDefs.result.andReturn().getResponse().getContentAsString();
        ZonedDateTime dateTime = ZonedDateTime.parse(JsonPath.read(response, "$.lastModified"));
        Assert.assertThat(dateTime, greaterThan(ZonedDateTime.now().minus(5, ChronoUnit.SECONDS)));
        Assert.assertThat(dateTime, lessThan(ZonedDateTime.now()));
    }

    @And("^There is a dataset with description \"([^\"]*)\" and owner \"([^\"]*)\"$")
    public void thereIsADatasetWithDescriptionAndOwner(String description, String username) throws Throwable {
        DataOwner owner = dataOwnerRepository.findOne(username);
        Dataset dataset = new Dataset();
        dataset.setTitle("Any title");
        dataset.setDescription(description);
        dataset.setOwner(owner);
        dataset.setDateTime(ZonedDateTime.now());
        datasetRepository.save(dataset);
    }

    @When("^I update my dataset with description \"([^\"]*)\" to description \"([^\"]*)\"$")
    public void iUpdateMyDatasetWithDescriptionToDescription(String oldDesc, String newDesc) throws Throwable {
        Dataset dataset = datasetRepository.findByDescription(oldDesc).get(0);
        dataset.setDescription(newDesc);
        String message = stepDefs.mapper.writeValueAsString(dataset);

        stepDefs.result = stepDefs.mockMvc.perform(
                put("/datasets/{id}", dataset.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

    @Then("^The new dataset has description \"([^\"]*)\"$")
    public void theNewDatasetHasDescription(String description) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.description", is(description)));
    }

    @And("^The dataset has title \"([^\"]*)\"$")
    public void theDatasetHasTitle(String title) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.title", is(title)));
    }
}

