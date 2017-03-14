package cat.udl.eps.entsoftarch.handler;

import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.domain.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;

/**
 * Created by gerard on 02/03/17.
 */
@Component
@Transactional
@RepositoryEventHandler(Schema.class)
public class SchemaEventHandler {
    private final Logger logger = LoggerFactory.getLogger(SchemaEventHandler.class);

    @HandleBeforeCreate
    @PreAuthorize("hasRole('OWNER')")
    public void handleSchemaPreCreate(Schema schema) {
        logger.info("Before creating: {}", schema);

        DataOwner principal = (DataOwner) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        schema.setOwner(principal);
    }

    @HandleBeforeSave
    @PreAuthorize("#schema.owner.username == principal.username")
    public void handleSchemaPreSave(Schema schema){
        schema.setLastModified(ZonedDateTime.now());
        logger.info("Before updating: {}", schema);
    }

    @HandleBeforeDelete
    @PreAuthorize("#schema.owner.username == principal.username")
    public void handleSchemaPreDelete(Schema schema){ logger.info("Before deleting: {}", schema); }

    @HandleBeforeLinkSave
    public void handleSchemaPreLinkSave(Schema schema, Object o) { logger.info("Before linking: {} to {}", schema, o); }

    @HandleAfterCreate
    public void handleSchemaPostCreate(Schema schema){
        logger.info("After creating: {}", schema);
    }

    @HandleAfterSave
    public void handleSchemaPostSave(Schema schema){
        logger.info("After updating: {}", schema);
    }

    @HandleAfterDelete
    public void handleSchemaPostDelete(Schema schema){ logger.info("After deleting: {}", schema); }

    @HandleAfterLinkSave
    public void handleSchemaPostLinkSave(Schema schema, Object o) { logger.info("After linking: {} to {}", schema, o); }
}
