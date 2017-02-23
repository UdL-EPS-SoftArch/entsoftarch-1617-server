package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by http://rhizomik.net/~roberto/
 */
public class DeleteDatasetStepdefs {

    @Autowired StepDefs stepDefs;
    @Autowired DatasetRepository datasetRepository;

    @When("^I delete the dataset with description \"([^\"]*)\"$")
    public void iDeleteTheDatasetWithDescription(String description) throws Throwable {
        Dataset dataset = datasetRepository.findByDescription(description).get(0);
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/datasets/{id}", dataset.getId())
                        .with(authenticate()))
                .andDo(print());
    }
}
