package cat.udl.eps.entsoftarch.handler;

import cat.udl.eps.entsoftarch.domain.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Component
@Transactional
@RepositoryEventHandler(Field.class)
public class FieldEventHandler {
    private final Logger logger = LoggerFactory.getLogger(FieldEventHandler.class);

    @HandleBeforeCreate
    @PreAuthorize("#field.partOf.owner.username == principal.username")
    public void handleFieldPreCreate(Field field) { logger.info("Before creating: {}", field); }

    @HandleBeforeSave
    @PreAuthorize("#field.partOf.owner.username == principal.username")
    public void handleFieldPreSave(Field field){ logger.info("Before updating: {}", field); }

    @HandleBeforeDelete
    @PreAuthorize("#field.partOf.owner.username == principal.username")
    public void handleFieldPreDelete(Field field){ logger.info("Before deleting: {}", field); }

    @HandleBeforeLinkSave
    @PreAuthorize("#field.partOf.owner.username == principal.username")
    public void handleFieldPreLinkSave(Field field, Object o) { logger.info("Before linking: {} to {}", field, o); }

    @HandleAfterCreate
    public void handleFieldPostCreate(Field field){
        logger.info("After creating: {}", field);
    }

    @HandleAfterSave
    public void handleFieldPostSave(Field field){
        logger.info("After updating: {}", field);
    }

    @HandleAfterDelete
    public void handleFieldPostDelete(Field field){
        logger.info("After deleting: {}", field);
    }

    @HandleAfterLinkSave
    public void handleFieldPostLinkSave(Field field, Object o) { logger.info("After linking: {} to {}", field, o); }
}
