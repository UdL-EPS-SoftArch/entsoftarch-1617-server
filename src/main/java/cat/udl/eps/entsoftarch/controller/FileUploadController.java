package cat.udl.eps.entsoftarch.controller;

import cat.udl.eps.entsoftarch.domain.DataFile;
import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.domain.Record;
import cat.udl.eps.entsoftarch.repository.DataFileRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ElTrioMaquinero on 07/06/2017.
 * <p>
 * At moment only can upload files using multipart/form-data POST request
 * After upload to server, the controller parse the xlsx file uploaded and convert to plain text file
 * and then save to local server.
 */


@Controller
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private DataFileRepository dataFileRepository;

    @PostMapping("/uploadFile")
    @PreAuthorize("hasRole('OWNER')")
    public
    @ResponseBody
    String handleFileUpload(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("file") MultipartFile file) {

        DataOwner principal = (DataOwner) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String fileName = file.getOriginalFilename();

        if (!file.isEmpty()) {
            try {

                // UPLOAD FILE
                /*byte[] bytes = file.getBytes();
                File serverFile = new File(fileName);
                file.transferTo(serverFile);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();*/

                //comment the following line, and uncomment the previous piece of code to save a file instance in the server
                InputStream serverFile =  new BufferedInputStream(file.getInputStream());

                logger.error("Server File Location="+ fileName);

                // Parse the xlsx to plain text.txt
                Workbook wb = new XSSFWorkbook(serverFile);
                DataFormatter formatter = new DataFormatter();

                List<Record> records = new ArrayList<>();

                for (Sheet sheet : wb) {
                    for (Row row : sheet) {
                        Record r;
                        String text = null;
                        boolean firstCell = true;
                        for (Cell cell : row) {
                            if (firstCell) {
                                text = formatter.formatCellValue(cell);
                                firstCell = false;
                            } else {
                                text += "," + formatter.formatCellValue(cell);
                            }
                        }
                        r = new Record();
                        r.setData(text);
                        r.setSeparator(",");
                        r.setDateTime(ZonedDateTime.now());
                        records.add(r);
                    }
                }

                DataFile df = new DataFile();

                df.setOwner(principal);
                df.setDateTime(ZonedDateTime.now());
                df.setRecords(records);
                df.setFilename(fileName);
                df.setTitle(title);
                df.setDescription(description);
                df.setSeparator(","); //excel doesn't use separator, so Trio Makinero team decided to set comma as the default.

                dataFileRepository.save(df);

                // And send the response
                return "You successfully uploaded file=" + fileName;
            } catch (Exception e) {
                return "You failed to upload " + fileName + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + fileName
                    + " because the file was empty.";
        }
    }


}