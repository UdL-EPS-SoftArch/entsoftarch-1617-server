package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.ClosedLicense;
import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.domain.License;
import cat.udl.eps.entsoftarch.domain.OpenLicense;
import cat.udl.eps.entsoftarch.repository.ClosedLicenseRepository;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import cat.udl.eps.entsoftarch.repository.OpenLicenseRepository;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RestMediaTypes;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


/**
 * Created by Sergi on 8/3/17.
 */
public class SetDatasetLicenseStepDefs {

    @Autowired private StepDefs stepDefs;
    @Autowired private OpenLicenseRepository openLicenseRepository;
    @Autowired private ClosedLicenseRepository closedLicenseRepository;
    @Autowired private DatasetRepository datasetRepository;

    /*OPEN LICENSE*/
    @When("^I set the open license with text \"([^\"]*)\" to dataset with title \"([^\"]*)\"$")
    public void setOpenLicenseToDataset(String text, String title) throws Throwable {
        OpenLicense openLicense = openLicenseRepository.findByText(text).get(0);
        Dataset dataset = datasetRepository.findByTitle(title).get(0);

        String message = "/openLicenses/" + openLicense.getId();

        stepDefs.result = stepDefs.mockMvc.perform(
                put("/datasets/{id}/license", dataset.getId())
                    .contentType(RestMediaTypes.TEXT_URI_LIST)
                    .content(message)
                    .with(authenticate()))
                .andDo(print());
    }

    @Then("^The dataset with title \"([^\"]*)\" has a open license with text \"([^\"]*)\"$")
    public void theDatasetWithTitleHasAOpenLicenseWithText(String title, String text) throws Throwable {
        Dataset dataset = datasetRepository.findByTitle(title).get(0);
        License license = dataset.getLicense();
        assertEquals(license.getText(), text);
    }

    /*CLOSED LICENSE*/
    @When("^I set the closed license with text \"([^\"]*)\" and price (\\d+) to dataset with title \"([^\"]*)\"$")
    public void iSetTheClosedLicenseWithTextAndPriceToDatasetWithTitle(String text, int count, String title) throws Throwable {
        ClosedLicense closedLicense = closedLicenseRepository.findByText(text).get(0);
        Dataset dataset = datasetRepository.findByTitle(title).get(0);

        String message = "/closedLicenses/" + closedLicense.getId();

        stepDefs.result = stepDefs.mockMvc.perform(
                put("/datasets/{id}/license", dataset.getId())
                        .contentType(RestMediaTypes.TEXT_URI_LIST)
                        .content(message)
                        .with(authenticate()))
                .andDo(print());
    }

    @Then("^The dataset with title \"([^\"]*)\" has a closed license with text \"([^\"]*)\" and price (\\d+)$")
    public void theDatasetWithTitleHasAClosedLicenseWithTextAndPrice(String title, String text, int count) throws Throwable {
        Dataset dataset = datasetRepository.findByTitle(title).get(0);
        License license = dataset.getLicense();
        assertEquals(license.getText(), text);
    }
}
