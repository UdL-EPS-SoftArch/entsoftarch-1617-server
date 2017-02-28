package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by yatan on 2/27/17.
 */
public class SearchDatasetByDescriptionStepDefs {

    @Autowired private StepDefs stepDefs;
    @Autowired private DatasetRepository datasetRepository;

    @When("^I search with a blank description$")
    public void iSearchWithABlankDescription() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I search with \"([^\"]*)\"$")
    public void iSearchWith(String description) throws Throwable {
        Dataset dataset = datasetRepository.findByDescription(description).get(0);
        throw new PendingException();
    }

    @Then("^Show (\\d+) datasets$")
    public void showDatasets(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
