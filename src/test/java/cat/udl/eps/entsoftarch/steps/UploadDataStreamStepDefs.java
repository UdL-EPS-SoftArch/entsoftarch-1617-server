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
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayOutputStream;

import static cat.udl.eps.entsoftarch.steps.AuthenticationStepDefs.authenticate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by SergiGrau on 31/5/17.
 */
public class UploadDataStreamStepDefs {


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

    @When("^I upload a stream with streamname \"([^\"]*)\" and owner \"([^\"]*)\" and title \"([^\"]*)\" and separator \"([^\"]*)\"$")
    public void iUploadAStreamWithStreamnameAndOwnerAndTitleAndSeparator(String streamname, String username, String title, String separator) throws Throwable {
        DataOwner owner = dataOwnerRepository.findOne(username);
        dataStream = new DataStream();
        dataStream.setStreamname(streamname);
        Resource file = wac.getResource("classpath:" + streamname);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileCopyUtils.copy(file.getInputStream(), output);
        dataStream.setTitle(title);
        dataStream.setOwner(owner);
        dataStream.setContent(output.toString());
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

    @And("^The datastream content contains \"([^\"]*)\" content$")
    public void theDatastreamContentContainsContent(String streamname) throws Throwable {
        dataStream = new DataStream();
        dataStream.setStreamname(streamname);
        Resource stream = wac.getResource("classpath:" + streamname);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileCopyUtils.copy(stream.getInputStream(), output);
        result.andExpect(jsonPath("$.content").value(output.toString()));
    }
}
