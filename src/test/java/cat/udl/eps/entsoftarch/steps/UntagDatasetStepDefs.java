package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.domain.Tag;
import cat.udl.eps.entsoftarch.repository.DataOwnerRepository;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import cat.udl.eps.entsoftarch.repository.TagRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RestMediaTypes;
import org.springframework.http.MediaType;

import java.util.List;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by pau on 13/3/17.
 */
public class UntagDatasetStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    DatasetRepository datasetRepository;

    @Autowired
    DataOwnerRepository dataOwnerRepository;

    @Autowired
    TagRepository tagRepository;

    @When("^I remove the tag \"([^\"]*)\" from the dataset titled \"([^\"]*)\"$")
    public void iRemoveTheTagFromTheDatasetTitled(String tagName, String datasetTitle) throws Throwable {
        Dataset dataset = datasetRepository.findByTitle(datasetTitle).get(0);
        Tag tag = tagRepository.findByName(tagName).get(0);
        List<Tag> tagList = dataset.getTaggedWith();
        tagList.remove(tag);
        String message = stepDefs.mapper.writeValueAsString(tagList);

        stepDefs.result = stepDefs.mockMvc.perform(
                put("/datasets/{id}/taggedWith", dataset.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }
}
