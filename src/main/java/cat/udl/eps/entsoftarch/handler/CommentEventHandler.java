package cat.udl.eps.entsoftarch.handler;

import cat.udl.eps.entsoftarch.domain.Comment;
import cat.udl.eps.entsoftarch.domain.DataOwner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Chaimae
 */

@Component
@Transactional
@RepositoryEventHandler(Comment.class)
public class CommentEventHandler {
    private final Logger logger = LoggerFactory.getLogger(CommentEventHandler.class);

    @HandleBeforeCreate
    @PreAuthorize("hasRole('USER')")
    public void handleCommentPreCreate(Comment comment) {
        logger.info("Before creating: {}", comment.toString());

        DataOwner principal = (DataOwner) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setUser(principal);
    }

    @HandleBeforeSave
    @PreAuthorize("#comment.user.username == principal.username")
    public void handleCommentPreSave(Comment comment) {
        logger.info("Before updating: {}", comment.toString());
    }

    @HandleBeforeDelete
    @PreAuthorize("#comment.user.username == principal.username")
    public void handleCommentPreDelete(Comment comment) {
        logger.info("Before deleting: {}", comment.toString());
    }

    @HandleBeforeLinkSave
    public void handleCommentPreLinkSave(Comment comment, Object o) {
        logger.info("Before linking: {} to {}", comment.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleCommentPostCreate(Comment comment) {
        logger.info("After creating: {}", comment.toString());
    }

    @HandleAfterSave
    public void handleCommentPostSave(Comment comment) {
        logger.info("After updating: {}", comment.toString());
    }

    @HandleAfterDelete
    public void handleCommentPostDelete(Comment comment) {
        logger.info("After deleting: {}", comment.toString());
    }

    @HandleAfterLinkSave
    public void handleCommentPostLinkSave(Comment comment, Object o) {
        logger.info("After linking: {} to {}", comment.toString(), o.toString());
    }
}
