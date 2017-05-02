package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.Tag;
import cat.udl.eps.entsoftarch.repository.TagRepository;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by UNI on 02/05/2017.
 */
public class DeleteTagStepdefs {


    @Autowired
    StepDefs stepDefs;
    @Autowired
    TagRepository tagRepository;

    @When("^I delete the tag with name \"([^\"]*)\"$")
    public void iDeleteTheTagWithName(String name) throws Throwable {
        Tag tag = tagRepository.findByName(name).get(0);
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/tags/{id}", tag.getId())
                        .with(authenticate()))
                .andDo(print());
    }

}
