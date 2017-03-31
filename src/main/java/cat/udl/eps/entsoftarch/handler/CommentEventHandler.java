package cat.udl.eps.entsoftarch.handler;

import cat.udl.eps.entsoftarch.domain.Comment;
import cat.udl.eps.entsoftarch.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;


@Component
@Transactional
@RepositoryEventHandler(Comment.class)
public class CommentEventHandler {
    private final Logger logger = LoggerFactory.getLogger(CommentEventHandler.class);

    @HandleBeforeCreate
    @PreAuthorize("hasRole('USER')")
    public void handleCommentPreCreate(Comment comment) {
        logger.info("Before creating: {}", comment);

        comment.setDateTime(ZonedDateTime.now());
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setUser(principal);

    }

    @HandleBeforeSave
    @PreAuthorize("#comment.user.username == principal.username")
    public void handleCommentPreSave(Comment comment) {
        logger.info("Before updating: {}", comment);
    }

    @HandleBeforeDelete
    @PreAuthorize("#comment.user.username == principal.username")
    public void handleCommentPreDelete(Comment comment) {
        logger.info("Before deleting: {}", comment);
    }

    @HandleBeforeLinkSave
    @PreAuthorize("#comment.user.username == principal.username")
    public void handleCommentPreLinkSave(Comment comment, Object o) {
        logger.info("Before linking: {} to {}", comment, o);
    }

    @HandleAfterCreate
    public void handleCommentPostCreate(Comment comment) {
        logger.info("After creating: {}", comment);
    }

    @HandleAfterSave
    public void handleCommentPostSave(Comment comment) {
        logger.info("After updating: {}", comment);
    }

    @HandleAfterDelete
    public void handleCommentPostDelete(Comment comment) {
        logger.info("After deleting: {}", comment);
    }

    @HandleAfterLinkSave
    public void handleCommentPostLinkSave(Comment comment, Object o) {
        logger.info("After linking: {} to {}", comment, o);
    }
}
