package com.strenght.program.controllers;

import com.strenght.program.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final UserRepo userRepo;

    @GetMapping("main")
    public ModelAndView showMainPage(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("main");
        modelAndView.addObject("userAttr", userRepo.findUserByEmail(principal.getName()));
        return modelAndView;
    }

    @PostMapping("/main-save")
    public ModelAndView saveMovement(@RequestParam String movementName,
                                     @RequestParam Integer oneRepMaximum,
                                     @RequestParam Float incrementCoefficient){
        System.out.println(movementName);
        System.out.println(oneRepMaximum);
        System.out.println(incrementCoefficient);
        ModelAndView modelAndView = new ModelAndView("main");
        return modelAndView;
    }
}
