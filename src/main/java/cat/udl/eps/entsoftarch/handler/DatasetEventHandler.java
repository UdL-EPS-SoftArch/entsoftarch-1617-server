package cat.udl.eps.entsoftarch.handler;

import cat.udl.eps.entsoftarch.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
    public void handleDataFilePostCreate(DataFile dataFile) {

        logger.info("After create datafile: {}", dataFile);
        String[] rows = dataFile.getContent().split("[\\r\\n]+");

        List<Record> records = new ArrayList<>();

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
        String[] rows = dataStream.getContent().split("[\\r\\n]+");

        List<Record> records = new ArrayList<>();

        for (String row: rows){
            Record r = new Record();
            r.setSeparator(dataStream.getSeparator());
            r.setData(row);
            r.setDateTime(ZonedDateTime.now());
            records.add(r);
        }
        dataStream.setRecords(records);
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
