package com.strenght.program.controllers;

import com.strenght.program.entities.User;
import com.strenght.program.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepo userRepo;

    @Value("/home/sazbserg/IdeaProjects/Jugger/program/src/main/resources/static/avatars")
    private String avatarsPath;

    private final PasswordEncoder passwordEncoder;


    @GetMapping("/profile")
    public ModelAndView showProfilePage(Principal principal){
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("userAttr", userRepo.findUserByEmail(principal.getName()));
        return modelAndView;
    }


    @PostMapping("/profile")
    public ModelAndView updateProfile(Principal principal, @RequestParam MultipartFile avatar, @RequestParam String nickName, @RequestParam String email, @RequestParam String password,
                                      @RequestParam boolean closed) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/profile");

        User user = userRepo.findUserByEmail(principal.getName());

        if ((avatar != null && !avatar.getOriginalFilename().isEmpty())) {

            File uploadDir = new File(avatarsPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
        }

        String resultName = null;

        if (avatar != null && !avatar.getOriginalFilename().isEmpty()) {
            resultName = "-" + new Date() + "-" + avatar.getOriginalFilename();
            try {
                avatar.transferTo(new File(avatarsPath + "/" + resultName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            user.setAvatar(resultName);
        } else {
            user.setAvatar(user.getAvatar());
        }
        user.setNickName(nickName);
        user.setEmail(email);
        if (password != null) {
            user.setPassword(passwordEncoder.encode(password));
        }
        user.setClosed(closed);
        userRepo.save(user);
        return modelAndView;
    }
}
