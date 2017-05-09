package cat.udl.eps.entsoftarch.handler;

import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.domain.License;
import cat.udl.eps.entsoftarch.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Victor on 06/03/2017.
 */

@Component
@Transactional
@RepositoryEventHandler(License.class)
public class LicenseEventHandler {
    private final Logger logger=LoggerFactory.getLogger(LicenseEventHandler.class);

    @HandleBeforeCreate
    @PreAuthorize("hasRole('OWNER')")
    public void handleLicensePreCreate(License license) {
        logger.info("Before creating: {}", license);

        DataOwner principal=(DataOwner) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        license.setOwner(principal);
    }

    @HandleBeforeSave
    @PreAuthorize("#license.owner.username == principal.username")
    public void handleLicensePreSave(License license) {
        logger.info("Before updating: {}", license);
    }

    @HandleBeforeDelete
    @PreAuthorize("#license.owner.username == principal.username")
    public void handleLicensePreDelete(License license) {
        logger.info("Before deleting: {}", license);
    }

    @HandleBeforeLinkSave
    public void handleLicensePreLinkSave(License license, Object o) {
        logger.info("Before linking: {} to {}", license, o);

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (o instanceof User) {
            User linkedUser = (User) o;
            if (linkedUser.getUsername().equals(principal.getUsername()))
                return; // Transferring ownership
            else
                throw new AccessDeniedException("Just license owner can transfer ownership");
        }
        if (!license.getOwner().getUsername().equals(principal.getUsername()))
            throw new AccessDeniedException("Access is denied");
    }

    @HandleAfterCreate
    public void handleLicensePostCreate(License license) { logger.info("After creating: {}", license); }

    @HandleAfterSave
    public void handleLicensePostSave(License license) { logger.info("After updating: {}", license); }

    @HandleAfterDelete
    public void handleLicensePostDelete(License license) { logger.info("After deleting: {}", license); }

    @HandleAfterLinkSave
    public void handleLicensePostLinkSave(License license, Object o) { logger.info("After linking: {} to {}", license, o); }
}
