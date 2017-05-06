package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.DataFile;
import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.repository.DataFileRepository;
import cat.udl.eps.entsoftarch.repository.DataOwnerRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private MockMvc mockMvc;

    private DataFile dataFile;

    private ResultActions result;


    @When("^I upload a file with filename \"([^\"]*)\" and owner \"([^\"]*)\" and title \"([^\"]*)\"$")
    public void iUploadAFileWithFilenameAndOwnerAndTitle(String filename, String username, String title) throws Throwable {
        DataOwner owner = dataOwnerRepository.findOne(username);
        dataFile = new DataFile();
        dataFile.setFilename(filename);
        Resource file = wac.getResource("classpath:" + filename);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileCopyUtils.copy(file.getInputStream(), output);
        dataFile.setTitle(title);
        dataFile.setOwner(owner);
        dataFile.setContent(output.toString());
        dataFileRepository.save(dataFile);
    }


    @Then("^the dataset contains a file$")
    public void theDatasetContainsAFile() throws Throwable {
        result = stepDefs.mockMvc.perform(get(dataFile.getUri())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(dataFile.getContent()))
                .andDo(print());
    }
}

