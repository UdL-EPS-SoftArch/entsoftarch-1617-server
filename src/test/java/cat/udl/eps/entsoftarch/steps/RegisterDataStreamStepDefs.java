package cat.udl.eps.entsoftarch.steps;


import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.domain.DataStream;

import cat.udl.eps.entsoftarch.repository.DataOwnerRepository;
import cat.udl.eps.entsoftarch.repository.DataStreamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by SergiGrau on 1/6/17.
 */
public class RegisterDataStreamStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DataStreamRepository dataStreamRepository;

    @Autowired
    private DataOwnerRepository dataOwnerRepository;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;

    private DataStream dataStream;

    private ResultActions result;

    @When("^I register a stream with streamname \"([^\"]*)\" and title \"([^\"]*)\" and owner \"([^\"]*)\" and separator \"([^\"]*)\"$")
    public void iRegisterAStreamWithStreamnameAndTitleAndOwnerAndSeparator(String streamname, String title, String username, String separator) throws Throwable {
        DataOwner owner = dataOwnerRepository.findOne(username);
        dataStream = new DataStream();
        dataStream.setStreamname(streamname);
        dataStream.setTitle(title);
        dataStream.setOwner(owner);
        dataStream.setSeparator(separator);

        String message = mapper.writeValueAsString(dataStream);

        result = stepDefs.mockMvc.perform(
                post("/dataStreams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

    @Then("^The dataset contains a stream with streamname \"([^\"]*)\"$")
    public void theDatasetContainsAStreamWithStreamname(String streamname) throws Throwable {
        result.andExpect(jsonPath("$.streamname").value(streamname));
    }

    @And("^The datastream separator is \"([^\"]*)\"$")
    public void theDatastreamSeparatorIs(String separator) throws Throwable {
        result.andExpect(jsonPath("$.separator").value(separator));
    }
}
