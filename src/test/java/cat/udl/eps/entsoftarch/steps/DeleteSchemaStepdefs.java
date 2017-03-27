package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Field;
import cat.udl.eps.entsoftarch.domain.Schema;
import cat.udl.eps.entsoftarch.repository.FieldRepository;
import cat.udl.eps.entsoftarch.repository.SchemaRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by TrioMaquinero
 */
public class DeleteSchemaStepdefs {

    @Autowired StepDefs stepDefs;
    @Autowired SchemaRepository schemaRepository;
    @Autowired FieldRepository fieldRepository;


    @When("^I delete the schema with title \"([^\"]*)\"$")
    public void iDeleteTheSchemaWithTitle(String title) throws Throwable {
        Schema schema = schemaRepository.findByTitle(title).get(0);
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/schemas/{id}", schema.getId())
                        .with(authenticate()))
                .andDo(print());
    }

    @When("^I delete the field with title \"([^\"]*)\"$")
    public void iDeleteTheFieldWithTitle(String title) throws Throwable {
        Field field = fieldRepository.findByTitle(title).get(0);
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/fields/{id}", field.getId())
                        .with(authenticate()))
                .andDo(print());
    }
}
