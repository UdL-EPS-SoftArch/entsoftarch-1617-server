package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.domain.Tag;
import cat.udl.eps.entsoftarch.repository.DataOwnerRepository;
import cat.udl.eps.entsoftarch.repository.DatasetRepository;
import cat.udl.eps.entsoftarch.repository.TagRepository;
import cucumber.api.PendingException;
import cucumber.api.java.cs.A;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RestMediaTypes;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by pau on 7/3/17.
 */
public class TagDatasetStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    DatasetRepository datasetRepository;

    @Autowired
    DataOwnerRepository dataOwnerRepository;

    @Autowired
    TagRepository tagRepository;

    @Then("^I tag the dataset titled \"([^\"]*)\" with tag \"([^\"]*)\"$")
    public void iTagTheDatasetTitledWithTag(String datasetTitle, String tagName) throws Throwable {
        Dataset dataset = datasetRepository.findByTitle(datasetTitle).get(0);
        Tag tag = tagRepository.findByName(tagName).get(0);
        String message = "/tags/" + tag.getId();

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/datasets/{id}/taggedWith", dataset.getId())
                        .contentType(RestMediaTypes.TEXT_URI_LIST)
                        .content(message)
                        .with(authenticate()))
                .andDo(print());
    }

    @And("^The dataset has tag \"([^\"]*)\"$")
    public void theDatasetHasTag(String tagName) throws Throwable {
        Tag tag = tagRepository.findByName(tagName).get(0);
        Assert.assertEquals(1, tag.getTags().size());
    }

    @And("^There is a dataset with title \"([^\"]*)\", tagged with \"([^\"]*)\" and owner \"([^\"]*)\"$")
    public void thereIsADatasetWithTitleTaggedWithAndOwner(String title, String tagName, String username) throws Throwable {
        Tag tag = tagRepository.findByName(tagName).get(0);
        DataOwner owner = dataOwnerRepository.findOne(username);
        Dataset dataset = new Dataset();
        dataset.setTitle(title);
        dataset.setOwner(owner);
        dataset.setTaggedWith(Collections.singletonList(tag));
        dataset.setDateTime(ZonedDateTime.now());
        datasetRepository.save(dataset);
    }
}
