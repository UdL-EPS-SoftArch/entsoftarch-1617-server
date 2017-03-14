package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Field;
import cat.udl.eps.entsoftarch.domain.Schema;
import cat.udl.eps.entsoftarch.repository.FieldRepository;
import cat.udl.eps.entsoftarch.repository.SchemaRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RestMediaTypes;
import org.springframework.http.MediaType;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Xavi on 7/3/17.
 */
public class AddSchemaFieldStepDefs {


    @Autowired private StepDefs stepDefs;
    @Autowired private FieldRepository fieldRepository;
    @Autowired private SchemaRepository SchemaRepository;



    @And("^There (?:are|is) (\\d+) (?:field|fields) added$")
    public void ThereAreFieldsAdded(int num) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(get("/fields")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$._embedded.fields", hasSize(num)));
    }

    @When("^I add a field with title \"([^\"]*)\" and description \"([^\"]*)\" to schema (\\d+)$")
    public void iAddAFieldWithTitleAndDescriptionToSchema(String title, String description, Long schemaID) throws Throwable {

        Schema schema = SchemaRepository.findOne(schemaID);

        Field field = new Field();
        field.setTitle(title);
        field.setDescription(description);
      //  field.setSchema(schema);
        fieldRepository.save(field);

        String message = stepDefs.mapper.writeValueAsString(field);
        stepDefs.result = stepDefs.mockMvc.perform(
            post("/fields")
                    .contentType(RestMediaTypes.TEXT_URI_LIST)
                    .content(message)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(authenticate()))
            .andDo(print());
    }

    @And("^There is a field with title \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void thereIsAFieldWithTitleAndDescription(String title, String description) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.title", is(title)));
        stepDefs.result.andExpect(jsonPath("$.description", is(description)));
    }

    @Then("^The new field has title \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void theNewFieldHasTitleAndDescription(String title, String description) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.title", is(title)));
        stepDefs.result.andExpect(jsonPath("$.description", is(description)));
    }



}
