package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by pau on 28/2/17.
 */
public class UpdateDatasetStepDefs {
    private static final Logger logger = LoggerFactory.getLogger(RegisterDatasetStepdefs.class);

    @Autowired
    private StepDefs stepDefs;
    @Autowired
    private DatasetRepository datasetRepository;

    @When("^I update my dataset with desciption \"([^\"]*)\" to description \"([^\"]*)\"$")
    public void iUpdateMyDatasetWithDesciptionToDescription(String oldDescription, String newDescription) throws Throwable {
        Dataset dataset = datasetRepository.findByDescription(oldDescription).get(0);
        dataset.setDescription(newDescription);
        //dataset.setDateTime(null);
        String message = stepDefs.mapper.writeValueAsString(dataset);

        stepDefs.result = stepDefs.mockMvc.perform(
                put("/datasets/{id}", dataset.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }
}

