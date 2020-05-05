package com.authorization.controller;

import com.authorization.model.crud_model.FavoriteLink;
import com.authorization.service.crud_service.LinksService;
import com.mail.model.MyMail;
import com.mail.service.EmailSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationRestController {
    private EmailSenderService emailSenderService;
    private LinksService linksService;


    public ApplicationRestController(EmailSenderService emailSenderService, LinksService linksService) {
        this.emailSenderService = emailSenderService;
        this.linksService = linksService;
    }

    @GetMapping("/api/links")
    public List<FavoriteLink> getLinks() {
        return linksService.allLinks();
    }

    @GetMapping("/api/link/{id}")
    public FavoriteLink getFavLink(@PathVariable long id) {
        return linksService.getLinkById(id);
    }

    @PostMapping("/api/link")
    public FavoriteLink createLink(@RequestBody FavoriteLink favoriteLink) {
        return linksService.addLink(favoriteLink);
    }

    @PutMapping("/api/link/{id}")
    public FavoriteLink updateLink(@PathVariable long id, @RequestBody FavoriteLink favoriteLink) {
        return linksService.updateLink(id, favoriteLink);
    }

    @DeleteMapping("/api/link/{id}")
    public ResponseEntity<?> deleteLink(@PathVariable long id) {
        return linksService.deleteLink(id);
    }

    @PostMapping("/api/link/send")
    public MyMail sendMail(MyMail myMail) {
        return emailSenderService.sendEmail(myMail);
    }

}
