package cat.udl.eps.entsoftarch.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by UNI on 07/03/2017.
 */
public class SearchTagByNameStepdefs {

    @Autowired
    private StepDefs stepDefs;

    @When("^I search tag with name \"([^\"]*)\"$")
    public void iSearchTagWithName(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/tags/search/findByNameContaining?id={id}",name))
                .andDo(print());
    }
}
