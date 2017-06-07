package cat.udl.eps.entsoftarch.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Created by ElTrioMaquinero on 07/06/2017.
 */


@Controller
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @PostMapping("/uploadFile")
    public @ResponseBody
    String handleFileUpload(@RequestParam("name") String name,
                                   @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
//                String rootPath = System.getProperty("catalina.home");
//                File dir = new File("/");
//                        //rootPath + File.separator + "tmpFiles");
//                if (!dir.exists())
//                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.error("Server File Location="
                        + serverFile.getAbsolutePath());

                Workbook wb = new XSSFWorkbook(serverFile.getAbsolutePath());
                DataFormatter formatter = new DataFormatter();
                PrintStream out = new PrintStream(new FileOutputStream("test.txt"),
                        true, "UTF-8");
                for (Sheet sheet : wb) {
                    for (Row row : sheet) {
                        boolean firstCell = true;
                        for (Cell cell : row) {
                            if ( ! firstCell ) out.print(',');
                            String text = formatter.formatCellValue(cell);
                            out.print(text);
                            firstCell = false;
                        }
                        out.println();
                    }
                }

                out.close();

                return "You successfully uploaded file=" + name;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name
                    + " because the file was empty.";
        }
    }


}