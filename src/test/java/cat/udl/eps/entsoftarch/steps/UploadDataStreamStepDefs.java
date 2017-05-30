package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.DataFile;
import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.domain.DataStream;
import cat.udl.eps.entsoftarch.repository.DataFileRepository;
import cat.udl.eps.entsoftarch.repository.DataOwnerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.PendingException;
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
import static com.sun.tools.doclets.formats.html.markup.HtmlStyle.title;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by SergiGrau on 30/5/17.
 */
public class UploadDataStreamStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private WebApplicationContext wac;


    @Autowired
    private DataOwnerRepository dataOwnerRepository;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;

    private DataStream dataStream;

    private ResultActions result;


    @Then("^The dataset contains a stream with name \"([^\"]*)\"$")
    public void theDatasetContainsAStreamWithName(String filename) throws Throwable {
        result.andExpect(jsonPath("$.filename").value(filename));

    }

    @When("^I upload a stream with name \"([^\"]*)\" and owner \"([^\"]*)\" and title \"([^\"]*)\" and separator \"([^\"]*)\"$")
    public void iUploadAStreamWithNameAndOwnerAndTitleAndSeparator(String name, String username, String title, String separator) throws Throwable {
        DataOwner owner = dataOwnerRepository.findOne(username);
        dataStream = new DataStream();
        dataStream.setName(name);
        Resource file = wac.getResource("classpath:" + name);
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

    @And("^The datastream content contains \"([^\"]*)\" content$")
    public void theDatastreamContentContainsContent(String name) throws Throwable {
        dataStream = new DataStream();
        dataStream.setName(name);
        Resource stream = wac.getResource("classpath:" + name);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileCopyUtils.copy(stream.getInputStream(), output);
        result.andExpect(jsonPath("$.content").value(output.toString()));
    }

    @And("^The datastream separator is \"([^\"]*)\"$")
    public void theDatastreamSeparatorIs(String separator) throws Throwable {
        result.andExpect(jsonPath("$.separator").value(separator));
    }

    @And("^The datastream contains (\\d+) records$")
    public void theDatastreamContainsRecords(int numRecords) throws Throwable {
        result.andExpect(jsonPath("$.records.*", hasSize(numRecords)));
    }
}
