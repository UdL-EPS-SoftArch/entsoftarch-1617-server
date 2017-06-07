package cat.udl.eps.entsoftarch.handler;

import cat.udl.eps.entsoftarch.domain.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by http://rhizomik.net/~roberto/
 */
@Component
@Transactional
@RepositoryEventHandler(Dataset.class)
public class DatasetEventHandler {
    private final Logger logger = LoggerFactory.getLogger(DatasetEventHandler.class);

    @HandleBeforeCreate
    @PreAuthorize("hasRole('OWNER')")
    public void handleDatasetPreCreate(Dataset dataset) {
        logger.info("Before creating: {}", dataset);

        dataset.setDateTime(ZonedDateTime.now());
        dataset.setLastModified(dataset.getDateTime());
        DataOwner principal = (DataOwner) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dataset.setOwner(principal);
    }

    @HandleBeforeSave
    @PreAuthorize("#dataset.owner.username == principal.username")
    public void handleDatasetPreSave(Dataset dataset) {
        logger.info("Before updating: {}", dataset);

        dataset.setLastModified(ZonedDateTime.now());
    }

    @HandleBeforeDelete
    @PreAuthorize("#dataset.owner.username == principal.username")
    public void handleDatasetPreDelete(Dataset dataset) {
        logger.info("Before deleting: {}", dataset);
    }

    @HandleBeforeLinkSave
    public void handleDatasetPreLinkSave(Dataset dataset, Object o) {
        logger.info("Before linking: {} to {}", dataset, o);

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (o instanceof User) {
            User linkedUser = (User) o;
            if (linkedUser.getUsername().equals(principal.getUsername()))
                return; // Transferring ownership
            else
                throw new AccessDeniedException("Just dataset owner can transfer ownership");
        }
        if (!dataset.getOwner().getUsername().equals(principal.getUsername()))
            throw new AccessDeniedException("Access is denied");
    }

    @HandleAfterCreate
    public void handleDataFilePostCreate(DataFile dataFile) throws IOException, InvalidFormatException {

        logger.info("After create datafile: {}", dataFile);
        String[] rows = dataFile.getContent().split("[\\r\\n]+");

        List<Record> records = new ArrayList<>();



        Workbook wb = new XSSFWorkbook(new File("c:/Libro1.xlsx"));
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

        for (String row: rows){
            Record r = new Record();
            r.setSeparator(dataFile.getSeparator());
            r.setData(row);
            r.setDateTime(ZonedDateTime.now());
            records.add(r);
        }
        dataFile.setRecords(records);
    }


    @HandleAfterCreate
    public void handleDataStreamPostCreate(DataStream dataStream) {

        logger.info("After create datastream: {}", dataStream);

    }



    @HandleAfterCreate
    public void handleDatasetPostCreate(Dataset dataset) {
        logger.info("After creating dataset: {}", dataset);
    }

    @HandleAfterSave
    public void handleDatasetPostSave(Dataset dataset) {
        logger.info("After updating: {}", dataset);
    }

    @HandleAfterDelete
    public void handleDatasetPostDelete(Dataset dataset) {
        logger.info("After deleting: {}", dataset);
    }

    @HandleAfterLinkSave
    public void handleDatasetPostLinkSave(Dataset dataset, Object o) {
        logger.info("After linking: {} to {}", dataset, o);
    }
}
