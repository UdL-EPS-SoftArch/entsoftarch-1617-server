package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.ClosedLicense;
import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.domain.OpenLicense;
import cat.udl.eps.entsoftarch.repository.ClosedLicenseRepository;
import cat.udl.eps.entsoftarch.repository.DataOwnerRepository;
import cat.udl.eps.entsoftarch.repository.OpenLicenseRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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
 * Created by Victor on 02/03/2017.
 */
public class RegisterLicenseStepDefs {

    @Autowired
    private StepDefs stepDefs;
    @Autowired
    private OpenLicenseRepository openLicenseRepository;
    @Autowired
    private ClosedLicenseRepository closedLicenseRepository;
    @Autowired
    private DataOwnerRepository dataOwnerRepository;

    //Open License
    @When("^I register a open license with text \"([^\"]*)\"$")
    public void iRegisterAOpenLicenseWithText(String text) throws Throwable {
        OpenLicense license=new OpenLicense();
        license.setText(text);

        String message=stepDefs.mapper.writeValueAsString(license);

        stepDefs.result=stepDefs.mockMvc.perform(post("/openLicenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(authenticate()))
                .andDo(print());
    }

    @And("^There (?:are|is) (\\d+) open licenses? registered$")
    public void thereAreOpenLicensesRegistered(int count) throws Throwable {
        stepDefs.result=stepDefs.mockMvc.perform(get("/openLicenses")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.openLicenses", hasSize(count)));
    }

    @And("^There is a open license with text \"([^\"]*)\" and owner \"([^\"]*)\"$")
    public void thereIsAOpenLicenseWithTextAndOwner(String text, String username) throws Throwable {
        DataOwner owner=dataOwnerRepository.findOne(username);
        OpenLicense license=new OpenLicense();
        license.setText(text);
        license.setOwner(owner);

        openLicenseRepository.save(license);
    }

    @Then("^The new open license has text \"([^\"]*)\"$")
    public void theNewOpenLicenseHasText(String text) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.text", is(text)));
    }


    @And("^User \"([^\"]*)\" owns (\\d+) open license$")
    public void userOwnsOpenLicense(String username, int count) throws Throwable {
        stepDefs.result=stepDefs.mockMvc.perform(get("/dataOwners/{username}/owns_openLicenses", username)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.openLicenses", hasSize(count)));
    }

    //Closed License
    @When("^I register a closed license with text \"([^\"]*)\" and price (\\d+)$")
    public void iRegisterAClosedLicenseWithTextAndPrice(String text, double price) throws Throwable {
        ClosedLicense license=new ClosedLicense();
        license.setText(text);
        license.setPrice(price);

        String message=stepDefs.mapper.writeValueAsString(license);

        stepDefs.result=stepDefs.mockMvc.perform(post("/closedLicenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(authenticate()))
                .andDo(print());
    }

    @And("^There (?:are|is) (\\d+) closed licenses? registered$")
    public void thereIsClosedLicenseRegistered(int count) throws Throwable {
        stepDefs.result=stepDefs.mockMvc.perform(get("/closedLicenses")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.closedLicenses", hasSize(count)));
    }

    @Then("^The new closed license has text \"([^\"]*)\" and price (\\d+)$")
    public void theNewClosedLicenseHasTextAndPrice(String text, double price) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.text", is(text)));
        stepDefs.result.andExpect(jsonPath("$.price", is(price)));
    }

    @And("^There is a closed license with text \"([^\"]*)\" and price (\\d+) and owner \"([^\"]*)\"$")
    public void thereIsAClosedLicenseWithTextAndPriceAndOwner(String text, double price, String username) throws Throwable {
        DataOwner owner=dataOwnerRepository.findOne(username);
        ClosedLicense license=new ClosedLicense();
        license.setText(text);
        license.setPrice(price);
        license.setOwner(owner);

        closedLicenseRepository.save(license);
    }

    @And("^User \"([^\"]*)\" owns (\\d+) closed license$")
    public void userOwnsClosedLicense(String username, int count) throws Throwable {
        stepDefs.result=stepDefs.mockMvc.perform(get("/dataOwners/{username}/owns_closedLicenses", username)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.closedLicenses", hasSize(count)));
    }
}
