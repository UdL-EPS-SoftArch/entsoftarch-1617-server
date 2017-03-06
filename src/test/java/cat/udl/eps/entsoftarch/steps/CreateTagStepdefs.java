package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Tag;
import cat.udl.eps.entsoftarch.repository.TagRepository;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by UNI on 06/03/2017.
 */
public class CreateTagStepdefs {
    @Autowired
    private StepDefs stepDefs;
    @Autowired private TagRepository tagRepository;

    @And("^There are (\\d+) tags created$")
    public void thereAreIsTagsRegistered(int count) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(get("/tags")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.tags", hasSize(count)));
    }

    @When("^I create a tag with name \"([^\"]*)\"$")
    public void iRegisterATagWithName(String name) throws Throwable {
        Tag tag = new Tag();
        tag.setName(name);

        String message = stepDefs.mapper.writeValueAsString(tag);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

    @Then("^The new tag has name \"([^\"]*)\"$")
    public void theNewTagHasName(String name) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.name", is(name)));
    }


}
