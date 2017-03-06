package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.domain.Schema;
import cat.udl.eps.entsoftarch.repository.DataOwnerRepository;
import cat.udl.eps.entsoftarch.repository.SchemaRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by gerard on 28/02/17.
 */
public class RegisterSchemaStepdefs {

    private static final Logger logger = LoggerFactory.getLogger(RegisterSchemaStepdefs.class);

    @Autowired
    private StepDefs stepDefs;
    @Autowired private SchemaRepository schemaRepository;
    @Autowired private DataOwnerRepository dataOwnerRepository;

    @And("^There (?:are|is) (\\d+) schemas? registered$")
    public void thereAreSchemasRegistered(int count) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(get("/schemas")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$._embedded.schemas", hasSize(count)));
    }

    @And("^There is a schema with title \"([^\"]*)\" " +
            "and description \"([^\"]*)\" and owner \"([^\"]*)\"$")
    public void thereIsASchemaWithTitleAndDescription(String title,
                                                      String description,
                                                      String username)
            throws Throwable {
        DataOwner owner = dataOwnerRepository.findOne(username);
        Schema schema = new Schema();
        schema.setTitle(title);
        schema.setDescription(description);
        schema.setOwner(owner);
        schemaRepository.save(schema);
    }

    @When("^I register a schema with title \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void iRegisterASchemaWithTitleAndDescription(String title, String description)
            throws Throwable {
        Schema schema = new Schema();
        schema.setTitle(title);
        schema.setDescription(description);
        String message = stepDefs.mapper.writeValueAsString(schema);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/schemas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

    @And("^The new schema has title \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void existsASchemaWithTitleAndDescription(String title, String description)
            throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.title", is(title)));
        stepDefs.result.andExpect(jsonPath("$.description", is(description)));
    }

    @And("^User \"([^\"]*)\" owns (\\d+) schema$")
    public void userOwnsSchema(String username, int count) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(get("/dataOwners/{username}/owns_schemas", username)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.schemas", hasSize(count)));
    }

}
