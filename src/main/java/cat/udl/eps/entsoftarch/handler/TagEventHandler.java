package cat.udl.eps.entsoftarch.handler;

import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.domain.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by UNI on 02/05/2017.
 */
@Component
@Transactional
@RepositoryEventHandler(Tag.class)
public class TagEventHandler {
    private final Logger logger = LoggerFactory.getLogger(DatasetEventHandler.class);

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ADMIN')")
    public void handleTagPreDelete(Tag tag){
        logger.info("Before deleting: {}", tag.toString());
        for (Dataset d: tag.getTags()) {
            d.removeTag(tag);
        }
    }
}
