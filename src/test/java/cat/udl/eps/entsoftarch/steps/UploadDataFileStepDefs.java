package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.DataFile;
import cat.udl.eps.entsoftarch.domain.DataOwner;
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
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by ElTrioMaquinero on 4/24/17.
 */

public class UploadDataFileStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DataFileRepository dataFileRepository;

    @Autowired
    private DataOwnerRepository dataOwnerRepository;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;

    private DataFile dataFile;

    private ResultActions result;


    @When("^I upload a file with filename \"([^\"]*)\" and owner \"([^\"]*)\" and title \"([^\"]*)\" and separator \"([^\"]*)\"$")
    public void iUploadAFileWithFilenameAndOwnerAndTitle(String filename, String username, String title, String separator) throws Throwable {
        DataOwner owner = dataOwnerRepository.findOne(username);
        dataFile = new DataFile();
        dataFile.setFilename(filename);
        Resource file = wac.getResource("classpath:" + filename);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileCopyUtils.copy(file.getInputStream(), output);
        dataFile.setTitle(title);
        dataFile.setOwner(owner);
        dataFile.setContent(output.toString());
        dataFile.setSeparator(separator);

        String message = mapper.writeValueAsString(dataFile);

        result = stepDefs.mockMvc.perform(
                post("/dataFiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

    @Then("^The dataset contains a file with filename \"([^\"]*)\"$")
    public void theDatasetContainsAFileWithFilename(String filename) throws Throwable {
        result.andExpect(jsonPath("$.filename").value(filename));
    }

    @Then("^The datafile content contains \"([^\"]*)\" content$")
    public void theDatafileContentContains(String filename) throws Throwable {
        dataFile = new DataFile();
        dataFile.setFilename(filename);
        Resource file = wac.getResource("classpath:" + filename);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileCopyUtils.copy(file.getInputStream(), output);
        result.andExpect(jsonPath("$.content").value(output.toString()));
    }

    @Then("^The datafile separator is \"([^\"]*)\"$")
    public void theDatafileSeparatorIs(String separator) throws Throwable {
        result.andExpect(jsonPath("$.separator").value(separator));
    }

    @And("^The datafile contains (\\d+) records$")
    public void theDatafileContainsRecords(int numRecords) throws Throwable {

        result.andExpect(jsonPath("$.records.*", hasSize(numRecords)));

    }

    @And("^The line (\\d+) contains \"([^\"]*)\"$")
    public void theLineContains(int line, String content) throws Throwable {

        result.andExpect(jsonPath("$.records["+line+"].data").value(content));

    }
}

