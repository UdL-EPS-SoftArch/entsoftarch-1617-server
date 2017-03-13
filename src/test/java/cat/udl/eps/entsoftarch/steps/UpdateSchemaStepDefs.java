package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.domain.Schema;
import cat.udl.eps.entsoftarch.repository.DataOwnerRepository;
import cat.udl.eps.entsoftarch.repository.SchemaRepository;
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
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by pau on 28/2/17.
 */
public class UpdateSchemaStepDefs {
    private static final Logger logger = LoggerFactory.getLogger(UpdateSchemaStepDefs.class);

    @Autowired
    private StepDefs stepDefs;
    @Autowired
    private SchemaRepository schemaRepository;
    @Autowired
    private DataOwnerRepository dataOwnerRepository;


    @When("^I update my schema with title \"([^\"]*)\" to title \"([^\"]*)\"$")
    public void iUpdateMySchemaWithTitleToTitle(String oldTitle, String newTitle) throws Throwable {
        Schema schema = schemaRepository.findByTitle(oldTitle).get(0);
        schema.setTitle(newTitle);
        String message = stepDefs.mapper.writeValueAsString(schema);

        stepDefs.result = stepDefs.mockMvc.perform(
                put("/schemas/{id}", schema.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

    @And("^The new schema has lastModified field approximately now$")
    public void theNewSchemaHasLastModifiedFieldApproximatelyNow() throws Throwable {
        String response = stepDefs.result.andReturn().getResponse().getContentAsString();
        ZonedDateTime dateTime = ZonedDateTime.parse(JsonPath.read(response, "$.lastModified"));
        Assert.assertThat(dateTime, greaterThan(ZonedDateTime.now().minus(5, ChronoUnit.SECONDS)));
        Assert.assertThat(dateTime, lessThan(ZonedDateTime.now()));
    }

    @And("^There is a schema with description \"([^\"]*)\" and owner \"([^\"]*)\"$")
    public void thereIsASchemaWithDescriptionAndOwner(String description, String username) throws Throwable {
        DataOwner owner = dataOwnerRepository.findOne(username);
        Schema schema = new Schema();
        schema.setTitle("Any title");
        schema.setDescription(description);
        schema.setOwner(owner);
        //schema.setDateTime(ZonedDateTime.now());
        schemaRepository.save(schema);
    }

    @When("^I update my schema with description \"([^\"]*)\" to description \"([^\"]*)\"$")
    public void iUpdateMySchemaWithDescriptionToDescription(String oldDesc, String newDesc) throws Throwable {
        Schema schema = schemaRepository.findByDescription(oldDesc).get(0);
        schema.setDescription(newDesc);
        String message = stepDefs.mapper.writeValueAsString(schema);

        stepDefs.result = stepDefs.mockMvc.perform(
                put("/schemas/{id}", schema.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

    @Then("^The new schema has description \"([^\"]*)\"$")
    public void theNewSchemaHasDescription(String description) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.description", is(description)));
    }

    @And("^The schema has title \"([^\"]*)\"$")
    public void theSchemaHasTitle(String title) throws Throwable {
        Schema schema = schemaRepository.findByTitle(title).get(0);
        Assert.assertThat(schema, notNullValue());
    }

    @And("^There is a schema with title \"([^\"]*)\" and owner \"([^\"]*)\"$")
    public void thereIsASchemaWithTitleAndOwner(String title, String username) throws Throwable {
        DataOwner owner = dataOwnerRepository.findOne(username);
        Schema schema = new Schema();
        schema.setTitle(title);
        schema.setOwner(owner);
        schemaRepository.save(schema);
    }

    @Then("^The new schema has title \"([^\"]*)\"$")
    public void theNewSchemaHasTitle(String title) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.title", is(title)));
    }
}

