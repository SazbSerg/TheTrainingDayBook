package com.strenght.program.controllers;

import com.google.gson.Gson;
import com.strenght.program.entities.*;
import com.strenght.program.models.WodDto;
import com.strenght.program.repositories.*;
import com.strenght.program.services.WodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class DayBookController {

    private final UserRepo userRepo;
    private final WodRepo wodRepo;
    private final WodService wodService;
    private final CommentRepo commentRepo;
    private final ExerciseRepo exerciseRepo;
    private final VideoLinkRepo videoLinkRepo;
    private final static Gson gson = new Gson();

    @GetMapping("/daybook")
    public ModelAndView showDayBookPage(Principal principal){
        ModelAndView modelAndView = new ModelAndView("daybook");
        List<WoD> woDList = userRepo.findUserByEmail(principal.getName()).getWoDList();
        Collections.reverse(woDList);
        modelAndView.addObject("wodListAttr", woDList);
        modelAndView.addObject("userIdAttr", userRepo.findUserByEmail(principal.getName()).getId());
        return modelAndView;
    }

    @GetMapping("/wod-add")
    public ModelAndView showNewWodPage(Principal principal, Model model){
        ModelAndView modelAndView = new ModelAndView("wod-create");
        //modelAndView.addObject("wodListAttr", userRepo.findUserByEmail(principal.getName()).getWoDList());
        model.addAttribute("wod", new WoD());
        modelAndView.addObject("userIdAttr", userRepo.findUserByEmail(principal.getName()).getId());
        return modelAndView;
    }

    @GetMapping("/wod-edit/{idWod}")
    public ModelAndView showEditWodPage(Principal principal, Model model, @PathVariable Long idWod){
        ModelAndView modelAndView = new ModelAndView("wod-edit");
        //modelAndView.addObject("wodListAttr", userRepo.findUserByEmail(principal.getName()).getWoDList());
        model.addAttribute("wod", wodRepo.findById(idWod).get());
      //  modelAndView.addObject("userIdAttr", userRepo.findUserByEmail(principal.getName()).getId());
        return modelAndView;
    }


    @RequestMapping(value="/wod-save", method= RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public void saveAllRolesChanges(@RequestBody String jsonObject, Principal principal){
        WodDto wodDto =  gson.fromJson(jsonObject, WodDto.class);
        WoD woD = new WoD();
        woD.setLocalDate(LocalDate.parse(wodDto.getLocalDate()));
        woD.setHashTag(wodDto.getHashTag());
        woD.setMyComment(wodDto.getMyComment());
        woD.setExercises(wodService.convertFromExercisesDtoToExercises(wodDto.getExercises()));
        woD.setComments(new ArrayList<>());
        wodRepo.save(woD);
        User user = userRepo.findUserByEmail(principal.getName());
        user.getWoDList().add(woD);
        userRepo.save(user);
    }

    @GetMapping("/wod/{idUser}/{idWod}")
    public ModelAndView showWodPage(Principal principal, Model model, @PathVariable Long idWod, @PathVariable Long idUser){
        ModelAndView modelAndView = new ModelAndView("wod-show");
        //modelAndView.addObject("wodListAttr", userRepo.findUserByEmail(principal.getName()).getWoDList());
        model.addAttribute("wod", wodRepo.findById(idWod).get());
        modelAndView.addObject("userIdAttr", userRepo.findUserByEmail(principal.getName()).getId());
        return modelAndView;
    }

    @RequestMapping(value="/wod-remove/{wodId}", method= RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public void removeWod(@PathVariable Long wodId, Principal principal){


        WoD woD = wodRepo.findById(wodId).get();
        User user = userRepo.findUserByEmail(principal.getName());
        user.getWoDList().remove(woD);
        userRepo.save(user);

        List<Exercise> exercises = woD.getExercises();

        for (Exercise exercise : exercises){
            List<VideoLink> videoLinksList = exercise.getVideoLinks();
            for (VideoLink videoLink : videoLinksList){
                videoLinksList.remove(videoLink);
                videoLinkRepo.delete(videoLink);
            }
            exercises.remove(exercise);
            exerciseRepo.delete(exercise);
        }

        List<Comment> commentList = woD.getComments();

        for (Comment comment : commentList){
            commentList.remove(comment);
            commentRepo.delete(comment);
        }
        wodRepo.delete(woD);
    }


    @RequestMapping(value="/comment-add/{wodId}", method= RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public void saveNewComment(@RequestBody String jsonObject, Principal principal, @PathVariable Long wodId){
        String commentJson =  gson.fromJson(jsonObject, String.class);
        User user = userRepo.findUserByEmail(principal.getName());
        WoD woD = wodRepo.findById(wodId).get();
        Comment comment = new Comment();
        comment.setContent(commentJson);
        comment.setAuthorNickName(user.getNickName());
        String pattern = "dd.MM.yy  в  HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateTime = simpleDateFormat.format(new Date());
        comment.setLocalDateTime(dateTime);
        commentRepo.save(comment);
        woD.getComments().add(comment);
        wodRepo.save(woD);
    }

    @RequestMapping(value="/comment-remove/{wodId}/{commentId}", method= RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public void removeComment(@PathVariable Long wodId, @PathVariable Long commentId){

    //    User user = userRepo.findUserByEmail(principal.getName());
        WoD woD = wodRepo.findById(wodId).get();
        List<Comment> commentList = woD.getComments();
        Comment comment = commentRepo.findById(commentId).get();
        commentList.remove(comment);
        wodRepo.save(woD);
        commentRepo.delete(comment);
    }


    @RequestMapping(value="/comment-update/{wodId}/{commentId}", method= RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public void updateComment(@RequestBody String commentContent, @PathVariable Long wodId, @PathVariable Long commentId){
        String commentJson =  gson.fromJson(commentContent, String.class);

        //    User user = userRepo.findUserByEmail(principal.getName());
        WoD woD = wodRepo.findById(wodId).get();
        //List<Comment> commentList = woD.getComments();
        Comment comment = commentRepo.findById(commentId).get();
        comment.setContent(commentJson);
        //wodRepo.save(woD);
        commentRepo.save(comment);
    }

}
