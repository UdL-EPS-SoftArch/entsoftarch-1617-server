package cat.udl.eps.entsoftarch.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by loc3 on 14/03/17.
 */
public class SearchDatasetByTagStepdefs {

    @Autowired
    private StepDefs stepDefs;

    @When("^I search dataset with tag \"([^\"]*)\"$")
    public void iSearchDatasetWithTag(String tagName) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasets/search/findByTaggedWith_Name?tag={tag}",tagName))
                .andDo(print());
    }


}
