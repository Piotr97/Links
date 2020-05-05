package com.authorization.controller;

import com.authorization.model.Profiles;
import com.authorization.model.crud_model.FavoriteLink;
import com.authorization.model.crud_model.dto.LinkDto;
import com.authorization.service.ProfileService;
import com.authorization.service.crud_service.LinksDtoService;
import com.authorization.service.crud_service.LinksService;
import com.mail.model.MyMail;
import com.mail.service.EmailSender;
import com.parser.CreatorXLS;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Controller
public class ProfilesController {
    private ProfileService profileService;
    private LinksService linksService;
    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;
    private LinksDtoService linksDtoService;


    public ProfilesController(ProfileService profileService, LinksService linksService, EmailSender emailSender, TemplateEngine templateEngine, LinksDtoService linksDtoService) {
        this.profileService = profileService;
        this.linksService = linksService;
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.linksDtoService = linksDtoService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        model.addAttribute("message", "You are logged in as: " + context.getAuthentication().getName());
        model.addAttribute("choose", "Click me if you logged in " + context.getAuthentication().getName());
        model.addAttribute("profile", "Logout" + context.getAuthentication().getName());
        return "index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String securedAdminPage(Model model) {
        String welcome = " Welcome Administrator in my FavLink application";
        model.addAttribute("welcome", welcome);
        model.addAttribute("links", linksService.allLinks());
        model.addAttribute("profiles", profileService.allProfile());
        return "admin_secured";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String securedUserPage(Model model) {
        String welcome = " Welcome User in my FavLink application";
        model.addAttribute("welcome", welcome);
        model.addAttribute("links", linksService.allLinks());
        return "user_secured";
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/choose")
    public String changePage(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        /*model.addAttribute("choose", "Your panel: " + context.getAuthentication().getName());*/
        return "choose";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerForm(@ModelAttribute Profiles user) {
        profileService.registerUser(user);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/register/admin")
    public String registerAdminPage() {
        return "register-admin";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/admin")
    public String registerAdminForm(@ModelAttribute Profiles user) {
        profileService.registerUser(user);
        return "redirect:/";
    }
    /* ------------------- CRUD FavoriteLinks Admin --------------------*/

    @PostMapping("/links/add/admin")
    public String addLink(@ModelAttribute FavoriteLink favoriteLink) {
        FavoriteLink favoriteLink1 = new FavoriteLink();
        favoriteLink1.setLink(favoriteLink.getLink());
        favoriteLink1.setDescribe(favoriteLink.getDescribe());
        favoriteLink1.setUsername(favoriteLink.getUsername());
        String result = linksService.addLink(favoriteLink).getLink();
        return "redirect:/admin";
    }

    @GetMapping("/links/delete/admin")
    public String deleteLink(@RequestParam long id) {
        linksService.deleteLink(id);
        return "redirect:/admin";
    }

    @GetMapping("/links/update/admin")
    public String updateLink(Model model, @RequestParam long id) {
        FavoriteLink favoriteLink1 = linksService.getLinkById(id);
        if (favoriteLink1 == null) {
            return "Can't find link!";
        }
        model.addAttribute("link", favoriteLink1);
        return "update-admin-link";
    }

    @PostMapping("/links/update/confirm/admin")
    public String updateLink(FavoriteLink favoriteLink) {
        FavoriteLink favoriteLink1 = new FavoriteLink();
        favoriteLink1.setUsername(favoriteLink.getUsername());
        favoriteLink1.setLink(favoriteLink.getLink());
        favoriteLink1.setDescribe(favoriteLink.getDescribe());
        String update = linksService.updateLink(favoriteLink.getId(), favoriteLink).getLink();
        return "redirect:/admin";
    }
    /* ------------------- CRUD FavoriteLinks User --------------------*/

    @PostMapping("/links/add/user")
    public String addLinkUser(@ModelAttribute FavoriteLink favoriteLink) {
        FavoriteLink favoriteLink1 = new FavoriteLink();
        favoriteLink1.setLink(favoriteLink.getLink());
        favoriteLink1.setDescribe(favoriteLink.getDescribe());
        favoriteLink1.setUsername(favoriteLink.getUsername());
        String result = linksService.addLink(favoriteLink).getLink();
        return "redirect:/user";
    }

    @GetMapping("/links/delete/user")
    public String deleteLinkUser(@RequestParam long id) {
        linksService.deleteLink(id);
        return "redirect:/user";
    }

    @GetMapping("/links/update/user")
    public String updateLinkUser(Model model, @RequestParam long id) {
        FavoriteLink favoriteLink1 = linksService.getLinkById(id);
        if (favoriteLink1 == null) {
            return "Can't find link!";
        }
        model.addAttribute("link", favoriteLink1);
        return "update-user";
    }

    @PostMapping("/links/update/confirm/user")
    public String updateLinkUser(FavoriteLink favoriteLink) {
        FavoriteLink favoriteLink2 = new FavoriteLink();
        favoriteLink2.setUsername(favoriteLink.getUsername());
        favoriteLink2.setLink(favoriteLink.getLink());
        favoriteLink2.setDescribe(favoriteLink.getDescribe());
        String update = linksService.updateLink(favoriteLink.getId(), favoriteLink).getLink();
        return "redirect:/user";
    }
    /* ------------------- CRUD Profiles --------------------*/

    @PostMapping("/links/add/profiles")
    public String addProfile(@ModelAttribute Profiles profiles) {
        Profiles profiles1 = new Profiles();
        profiles1.setName(profiles.getName());
        profiles1.setLastName(profiles.getLastName());
        profiles1.setEmail(profiles.getEmail());
        profiles1.setLogin(profiles.getLogin());
        profiles1.setPassword(profiles.getPassword());
        String result = profileService.addProfile(profiles).getLogin();
        return "redirect:/admin" + result;
    }

    @GetMapping("/links/delete/profiles")
    public String deleteProfiles(@RequestParam long id) {
        profileService.deleteProfile(id);
        return "redirect:/admin";
    }

    @GetMapping("/links/update/profiles")
    public String updateProfiles(Model model, @RequestParam long id) {
        Profiles profile = profileService.getProfileById(id);
        if (profile == null) {
            return "Can't find link!";
        }
        model.addAttribute("profile", profile);
        return "update-admin-link";
    }

    @PostMapping("/links/update/confirm/profiles")
    public String updateProfiles(Profiles profiles) {
        Profiles profiles1 = new Profiles();
        profiles1.setName(profiles.getName());
        profiles1.setLastName(profiles.getLastName());
        profiles1.setEmail(profiles.getEmail());
        profiles1.setLogin(profiles.getLogin());
        profiles1.setPassword(profiles.getPassword());
        String update = profileService.updateProfile(profiles.getId(), profiles).getLogin();
        return "redirect:/admin";
    }


    /*--------------- Mail -----------------*/


    @PostMapping("/send/support")
    public String sendEmail(@ModelAttribute MyMail myMail) {
        Context context = new Context();
        context.setVariable("body", myMail.getBody());
        String templateEmail = templateEngine.process("template-email", context);
        myMail.setBody(templateEmail);
        emailSender.sendEmail(myMail);
        return "index";
    }

    @GetMapping("/sender/support")
    public String senderPage() {
        return "sender";
    }

    /*--------------- Parser -----------------*/
    @GetMapping("/parser/create")
    public String getCarsToXlsFile() {

        List<LinkDto> series = linksDtoService.getLinksDto();
        CreatorXLS<LinkDto> creatorXLS = new CreatorXLS<>(LinkDto.class);
        try {
            creatorXLS.createFile(series, "src/main/resources/", "FavLink");

        } catch (NoSuchMethodException | IOException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return "choose";
    }
}
