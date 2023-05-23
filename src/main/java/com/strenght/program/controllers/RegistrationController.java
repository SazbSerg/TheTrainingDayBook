package com.strenght.program.controllers;

import com.strenght.program.entities.User;
import com.strenght.program.models.Role;
import com.strenght.program.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    @Value("${host}")
    private String HOST;

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @GetMapping("/registration")
    public String registration()
    {
        return "registration";
    }


    @PostMapping("/registration")
    public String addUser(@RequestParam String email, @RequestParam String password, @RequestParam String password2)
    {
        if(!(userRepository.findUserByEmail(email) == null)){
            return "redirect:registration?error";
        } else if (!(password.equals(password2))) {
            return "redirect:registration?error2";
        } else {



            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles(Collections.singleton(Role.USER));
            //user.setAuthenticationProvider(AuthenticationProvider.LOCAL);
            userRepository.save(user);

            long id = user.getId();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Вам пришло данное сообщение, потому-что вы, видимо, сказочный долбоёб. Подтвердите регистрацию и без лишних вопросов.");
            message.setText("Click the link " + HOST +"/registration-confirm/"+ id + "");
            javaMailSender.send(message);
            return "redirect:/login?confirm";
        }
    }

    @GetMapping("/registration-confirm/{id}")
    public String showConfirmRegPage(@PathVariable long id){
        return "registration-confirm";
    }


    @PostMapping("/registration-confirm/{id}")
    public String confirmRegistration(@PathVariable long id){
        User user = userRepository.findById(id).orElseThrow();
        user.setEnabled(true);
        userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("RecordProof success registration!");
        message.setText("Welcome to RecordProof application! Your registration completed successfully!");
        javaMailSender.send(message);

        return "redirect:/login?success";
    }
}
