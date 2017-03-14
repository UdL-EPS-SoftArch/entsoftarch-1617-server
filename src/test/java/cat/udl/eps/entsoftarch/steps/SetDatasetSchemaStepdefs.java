package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.domain.Schema;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import cat.udl.eps.entsoftarch.repository.SchemaRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RestMediaTypes;
import org.springframework.http.MediaType;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by gerard on 06/03/17.
 */
public class SetDatasetSchemaStepdefs {
    private static final Logger logger = LoggerFactory.getLogger(RegisterSchemaStepdefs.class);

    @Autowired private StepDefs stepDefs;
    @Autowired private SchemaRepository schemaRepository;
    @Autowired private DatasetRepository datasetRepository;

    @When("^I set a schema with title \"([^\"]*)\" to dataset with title \"([^\"]*)\"$")
    public void setSchemaToDataset(String schema_title, String dataset_title) throws Throwable {
        Schema schema = schemaRepository.findByTitle(schema_title).get(0);
        Dataset dataset = datasetRepository.findByTitle(dataset_title).get(0);


        String message = "/schemas/" + schema.getId();

        stepDefs.result = stepDefs.mockMvc.perform(
                put("/datasets/{id}/schema", dataset.getId())
                        .contentType(RestMediaTypes.TEXT_URI_LIST)
                        .content(message)
                        .with(authenticate()))
                    .andDo(print());

    }

    @Then("^The dataset with title \"([^\"]*)\" has a schema with title \"([^\"]*)\"$")
    public void datasetHasSchema(String dataset_title, String schema_title) throws Throwable {
        Dataset dataset = datasetRepository.findByTitle(dataset_title).get(0);
        Schema schema = dataset.getSchema();
        assertEquals(schema.getTitle(), schema_title);
    }

    @And("^The datasets defined by schema with title \"([^\"]*)\" include one titled \"([^\"]*)\"$")
    public void theDatasetsDefinedBySchemaWithTitleIncludeOneTitled(String schemaTitle, String datasetTitle) throws Throwable {
        Schema schema = schemaRepository.findByTitle(schemaTitle).get(0);

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/schemas/{id}/datasets", schema.getId())
                        .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._embedded.datasets[*].title", hasItem(datasetTitle)));
    }

    @And("^The schema with title \"([^\"]*)\" don't include a dataset with title \"([^\"]*)\"$")
    public void theSchemaDontIncludeDataset(String schemaTitle, String datasetTitle) throws Throwable{
        Schema schema = schemaRepository.findByTitle(schemaTitle).get(0);

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/schemas/{id}/datasets", schema.getId())
                        .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._embedded.datasets[*].title", not(hasItem(datasetTitle))));
    }

    @And("^The schema with title \"([^\"]*)\" has (\\d+) datasets? registered$")
    public void thereAreSchemasRegistered(String schemaTitle, int count) throws Throwable {
        Schema schema = schemaRepository.findByTitle(schemaTitle).get(0);

        stepDefs.result = stepDefs.mockMvc.perform(get("/schemas/{id}/datasets", schema.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$._embedded.datasets[*]", hasSize(count)));
    }
}
