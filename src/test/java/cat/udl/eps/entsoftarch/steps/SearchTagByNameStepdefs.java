package cat.udl.eps.entsoftarch.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by UNI on 07/03/2017.
 */
public class SearchTagByNameStepdefs {

    @Autowired
    private StepDefs stepDefs;

    @When("^I search tag with name \"([^\"]*)\"$")
    public void iSearchTagWithName(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tags/search/findByName?name={name}",name))
                .andDo(print());
    }

    @Then("^Show (\\d+) tags$")
    public void showTag(int count) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$._embedded.tags", hasSize(count)));
    }

    @When("^I search tag with name containing \"([^\"]*)\"$")
    public void iSearchTagWithNameContaining(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tags/search/findByNameContaining?name={name}",name))
                .andDo(print());
    }
}
