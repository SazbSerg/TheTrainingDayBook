package com.strenght.program.controllers;

import com.strenght.program.entities.Movement;
import com.strenght.program.entities.User;
import com.strenght.program.repositories.MovementRepo;
import com.strenght.program.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MovementController {

    private final UserRepo userRepo;
    private final MovementRepo movementRepo;

    @GetMapping("/movement")
    public ModelAndView showMainPage(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("movement");
        modelAndView.addObject("userAttr", userRepo.findUserByEmail(principal.getName()));
        return modelAndView;
    }


    @GetMapping("/add-movement")
    public ModelAndView showAddMovementPage(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("add-movement");
        modelAndView.addObject("userAttr", userRepo.findUserByEmail(principal.getName()));
        return modelAndView;
    }


    @PostMapping("/add-movement")
    public String saveMovement(@RequestParam String movementName,
                                     @RequestParam Integer oneRepMaximum,
                                     @RequestParam Float incrementCoefficient2,
                                     @RequestParam Float incrementCoefficient3,
                                     @RequestParam Float incrementCoefficient4,
                                     Principal principal){
        User user = userRepo.findUserByEmail(principal.getName());
        Movement movement = new Movement();
        movement.setMovementName(movementName);
        movement.setOneRepMaximum(oneRepMaximum);
        movement.setIncrementCoefficient2(incrementCoefficient2);
        movement.setIncrementCoefficient3(incrementCoefficient3);
        movement.setIncrementCoefficient4(incrementCoefficient4);
        movementRepo.save(movement);
        user.getMovements().add(movement);
        userRepo.save(user);
        return "redirect:/movement";
    }


    @GetMapping("/movement-edit/{id}")
    public ModelAndView showEditMovementPage(Principal principal, @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("movement-edit");
        modelAndView.addObject("userAttr", userRepo.findUserByEmail(principal.getName()));
        modelAndView.addObject("movementAttr", movementRepo.findById(id).orElseThrow());
        return modelAndView;
    }


    @PostMapping("/movement-edit/{id}")
    public String updateMovement(@PathVariable Long id,
                                 @RequestParam String movementName,
                                 @RequestParam Integer oneRepMaximum,
                                 @RequestParam Float incrementCoefficient2,
                                 @RequestParam Float incrementCoefficient3,
                                 @RequestParam Float incrementCoefficient4,
                                 Principal principal){
        Movement movement = movementRepo.findById(id).orElseThrow();
        movement.setMovementName(movementName);
        movement.setOneRepMaximum(oneRepMaximum);
        movement.setIncrementCoefficient2(incrementCoefficient2);
        movement.setIncrementCoefficient3(incrementCoefficient3);
        movement.setIncrementCoefficient4(incrementCoefficient4);
        movementRepo.save(movement);
        return "redirect:/movement";
    }



    @RequestMapping(value="/movement-remove/{id}", method= RequestMethod.POST, produces = "application/json", consumes="application/json")
    @ResponseBody
    public void removeMovement(@PathVariable Long id, Principal principal){
        Movement movement = movementRepo.findById(id).orElseThrow();
        User user = userRepo.findUserByEmail(principal.getName());
        user.getMovements().remove(movement);
        movementRepo.delete(movement);
    }

    @GetMapping("/movement-cycle/{id}")
    public ModelAndView showMovementCyclePage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("movement-cycle");
        modelAndView.addObject("movementAttr", movementRepo.findById(id).orElseThrow());
        return modelAndView;
    }

    @PostMapping("/movement-cycle-save-first-wave-result/{id}")
    public String saveFirstWaveResultAndReturnMovementCyclePage(@PathVariable Long id, @RequestParam Integer firstWaveRepMaximum) {
        Movement movement = movementRepo.findById(id).orElseThrow();
        movement.setFirstWaveRepMaximum(firstWaveRepMaximum);
        movementRepo.save(movement);
        return "redirect:/movement-cycle/" + id;
    }

    @PostMapping("/movement-cycle-save-second-wave-result/{id}")
    public String saveSecondWaveResultAndReturnMovementCyclePage(@PathVariable Long id, @RequestParam Integer secondWaveRepMaximum) {
        Movement movement = movementRepo.findById(id).orElseThrow();
        movement.setSecondWaveRepMaximum(secondWaveRepMaximum);
        movementRepo.save(movement);
        return "redirect:/movement-cycle/" + id;
    }

    @PostMapping("/movement-cycle-save-third-wave-result/{id}")
    public String saveThirdWaveResultAndReturnMovementCyclePage(@PathVariable Long id, @RequestParam Integer thirdWaveRepMaximum) {
        Movement movement = movementRepo.findById(id).orElseThrow();
        movement.setThirdWaveRepMaximum(thirdWaveRepMaximum);
        movementRepo.save(movement);
        return "redirect:/movement-cycle/" + id;
    }

    @PostMapping("/movement-cycle-save-forth-wave-result/{id}")
    public String saveForthWaveResultAndReturnMovementCyclePage(@PathVariable Long id, @RequestParam Integer forthWaveRepMaximum) {
        Movement movement = movementRepo.findById(id).orElseThrow();
        movement.setForthWaveRepMaximum(forthWaveRepMaximum);
        movementRepo.save(movement);
        return "redirect:/movement-cycle/" + id;
    }

}
