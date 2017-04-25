package cat.udl.eps.entsoftarch.steps;

import cat.udl.eps.entsoftarch.domain.DataFile;
import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.repository.DataFileRepository;
import cat.udl.eps.entsoftarch.repository.DataOwnerRepository;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.net.URLConnection;

/**
 * Created by ElTrioMaquinero on 4/24/17.
 */

public class UploadDataFileStepDefs {


    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DataFileRepository dataFileRepository;

    @Autowired
    private DataOwnerRepository dataOwnerRepository;


    @When("^I upload a file with filename \"([^\"]*)\" and owner \"([^\"]*)\" and title \"([^\"]*)\"$")
    public void iUploadAFileWithFilenameAndOwnerAndTitle(String filename, String username, String title) throws Throwable {
        DataOwner owner = dataOwnerRepository.findOne(username);
        DataFile dataFile = new DataFile();
        dataFile.setFilename(filename);
        Resource file = wac.getResource("classpath:" + filename);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileCopyUtils.copy(file.getInputStream(), output);
        String contentType = URLConnection.guessContentTypeFromName(filename);
        dataFile.setTitle(title);
        dataFile.setOwner(owner);
        dataFile.setContent("data:" + contentType + ";base64," + DatatypeConverter.printBase64Binary(output.toByteArray()));
        dataFileRepository.save(dataFile);
    }
}

