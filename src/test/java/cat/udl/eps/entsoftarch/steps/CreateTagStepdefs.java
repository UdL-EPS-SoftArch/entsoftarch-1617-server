package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.repository.TagRepository;
import cucumber.api.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @And("^There are (\\d+) tags registered$")
    public void thereAreIsTagsRegistered(int count) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(get("/tags")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.tags", hasSize(count)));
    }
}
