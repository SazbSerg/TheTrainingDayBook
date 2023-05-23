package com.strenght.program.controllers;

import com.strenght.program.entities.WoD;
import com.strenght.program.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepo userRepo;

    @GetMapping("/users")
    public ModelAndView showUsersList(){
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("usersAttr", userRepo.findAll());
        return modelAndView;
    }

    @GetMapping("/user/{userId}/daybook")
    public ModelAndView showUserDaybook(@PathVariable Long userId){
        ModelAndView modelAndView = new ModelAndView("daybook");
        List<WoD> woDList = userRepo.findById(userId).get().getWoDList();
        Collections.reverse(woDList);
        modelAndView.addObject("wodListAttr", woDList);
        modelAndView.addObject("userIdAttr", userId);
        return modelAndView;
    }
}
