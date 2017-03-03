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
        logger.info("Before creating: {}", schema.toString());

        DataOwner principal = (DataOwner) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        schema.setOwner(principal);
    }

    @HandleBeforeSave
    @PreAuthorize("#schema.owner.username == principal.username")
    public void handleSchemaPreSave(Schema schema){
        logger.info("Before updating: {}", schema.toString());
    }

    @HandleBeforeDelete
    @PreAuthorize("#schema.owner.username == principal.username")
    public void handleSchemaPreDelete(Schema schema){
        logger.info("Before deleting: {}", schema.toString());
    }

    @HandleBeforeLinkSave
    public void handleSchemaPreLinkSave(Schema schema, Object o) {
        logger.info("Before linking: {} to {}", schema.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleSchemaPostCreate(Schema schema){
        logger.info("After creating: {}", schema.toString());
    }

    @HandleAfterSave
    public void handleSchemaPostSave(Schema schema){
        logger.info("After updating: {}", schema.toString());
    }

    @HandleAfterDelete
    public void handleSchemaPostDelete(Schema schema){
        logger.info("After deleting: {}", schema.toString());
    }

    @HandleAfterLinkSave
    public void handleSchemaPostLinkSave(Schema schema, Object o) {
        logger.info("After linking: {} to {}", schema.toString(), o.toString());
    }
}
