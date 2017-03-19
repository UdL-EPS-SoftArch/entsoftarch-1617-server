package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Field;
import cat.udl.eps.entsoftarch.domain.Schema;
import cat.udl.eps.entsoftarch.repository.FieldRepository;
import cat.udl.eps.entsoftarch.repository.SchemaRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RestMediaTypes;
import org.springframework.http.MediaType;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @And("^There is a field with title \"([^\"]*)\" in schema \"([^\"]*)\"$")
    public void thereIsAFieldWithTitleInSchema(String fieldTitle, String schemaTitle) throws Throwable {
        Schema schema = SchemaRepository.findByTitle(schemaTitle).get(0);
        Field field = new Field();
        field.setTitle(fieldTitle);
        field.setPartOf(schema);
        fieldRepository.save(field);
    }

    @When("^I add a field with title \"([^\"]*)\" and description \"([^\"]*)\" to schema \"([^\"]*)\"$")
    public void iAddAFieldWithTitleAndDescriptionToSchema(String title, String description, String schemaTitle)
            throws Throwable {
        Schema schema = SchemaRepository.findByTitle(schemaTitle).get(0);
        Field field = new Field();
        field.setTitle(title);
        field.setDescription(description);
        field.setPartOf(schema);

        String message = stepDefs.mapper.writeValueAsString(field);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/fields")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                    .andDo(print());
    }

    @When("^I move the field with title \"([^\"]*)\" to schema \"([^\"]*)\"$")
    public void iAssociateTheFieldWithTitleToSchema(String fieldTitle, String schemaTitle) throws Throwable {
        Schema schema = SchemaRepository.findByTitle(schemaTitle).get(0);
        Field field = fieldRepository.findByTitle(fieldTitle).get(0);

        String message = "/schemas/" + schema.getId();

        stepDefs.result = stepDefs.mockMvc.perform(
                put("/fields/{id}/partOf", field.getId())
                        .contentType(RestMediaTypes.TEXT_URI_LIST)
                        .content(message)
                        .with(authenticate()))
                .andDo(print());
    }

    @And("^The schema with title \"([^\"]*)\" has a field with title \"([^\"]*)\"$")
    public void theSchemaWithTitleHasFieldWithTitle(String schemaTitle, String fieldTitle) throws Throwable {
        Schema schema = SchemaRepository.findByTitle(schemaTitle).get(0);

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/schemas/{id}/contains", schema.getId())
                        .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(jsonPath("$._embedded.fields[*].title", hasItem(fieldTitle)));
    }

    @And("^The schema with title \"([^\"]*)\" has (\\d+) (?:field|fields)$")
    public void theSchemaWithTitleHasFields(String schemaTitle, int count) throws Throwable {
        Schema schema = SchemaRepository.findByTitle(schemaTitle).get(0);

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/schemas/{id}/contains", schema.getId())
                        .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(jsonPath("$._embedded.fields", hasSize(count)));
    }

    @Then("^The new field has title \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void theNewFieldHasTitleAndDescription(String title, String description) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.title", is(title)));
        stepDefs.result.andExpect(jsonPath("$.description", is(description)));
    }
}
