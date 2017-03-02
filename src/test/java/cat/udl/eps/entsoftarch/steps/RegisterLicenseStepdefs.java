package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.License;
import cat.udl.eps.entsoftarch.domain.OpenLicense;
import cat.udl.eps.entsoftarch.repository.DataOwnerRepository;
import cat.udl.eps.entsoftarch.repository.LicenseRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by victorserrate on 2/3/17.
 */
public class RegisterLicenseStepdefs {

    private static final Logger logger = LoggerFactory.getLogger(RegisterDatasetStepdefs.class);

    @Autowired
    private StepDefs stepDefs;
    @Autowired
    private DataOwnerRepository dataOwnerRepository;
    @Autowired
    private LicenseRepository licenseRepository;

    @And("^There (?:are|is) (\\d+) licenses? registered$")
    public void thereAreLicensesRegistered(int count) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(get("/licenses")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$._embedded.licenses", hasSize(count)));
    }



    @When("^I register a license with text \"([^\"]*)\"$")
    public void iRegisterALicenseWithText(String text) throws Throwable{
        License license = new OpenLicense();
        license.setText(text);
        String message = stepDefs.mapper.writeValueAsString(license);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/licenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

    @Then("^The new license has text \"([^\"]*)\"$")
    public void theNewLicenseHasText(String text) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.text", is(text)));
    }
}


