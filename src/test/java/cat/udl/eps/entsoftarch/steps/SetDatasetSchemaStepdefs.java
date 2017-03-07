package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.domain.Schema;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import cat.udl.eps.entsoftarch.repository.SchemaRepository;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;

/**
 * Created by gerard on 06/03/17.
 */
public class SetDatasetSchemaStepdefs {
    private static final Logger logger = LoggerFactory.getLogger(RegisterSchemaStepdefs.class);

    @Autowired private StepDefs stepDefs;
    @Autowired private SchemaRepository schemaRepository;
    @Autowired private DatasetRepository datasetRepository;

    @When("^I set the schema with title \"([^\"]*)\" to dataset with title \"([^\"]*)\"$")
    public void setSchemaToDataset(String schema_title, String dataset_title) throws Throwable {
        Schema schema = schemaRepository.findByTitle(schema_title).get(0);
        Dataset dataset = datasetRepository.findByTitle(dataset_title).get(0);
        dataset.setSchema(schema);
    }

    @Then("^The dataset with title \"([^\"]*)\" has a schema with title \"([^\"]*)\"$")
    public void datasetHasSchema(String dataset_title, String schema_title) throws Throwable {
        Dataset dataset = datasetRepository.findByTitle(dataset_title).get(0);
        Schema schema = dataset.getSchema();
        assertEquals(schema.getTitle(), schema_title);
    }
}
