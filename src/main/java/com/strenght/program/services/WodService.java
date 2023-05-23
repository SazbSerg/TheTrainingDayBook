package com.strenght.program.services;

import com.strenght.program.entities.Exercise;
import com.strenght.program.entities.VideoLink;
import com.strenght.program.models.ExerciseDto;
import com.strenght.program.repositories.ExerciseRepo;
import com.strenght.program.repositories.VideoLinkRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WodService {

    private final VideoLinkRepo videoLinkRepo;
    private final ExerciseRepo exerciseRepo;

    public List<Exercise> convertFromExercisesDtoToExercises(List<ExerciseDto> exerciseDtoList) {
        List<Exercise> exerciseList = new ArrayList<>();
        for (ExerciseDto exerciseDto : exerciseDtoList) {
            Exercise exercise = new Exercise();
            exercise.setDescription(exerciseDto.getDescription());
            exercise.setVideoLinks(convertFromExerciseDtoVideoLinksToVideoLinks(exerciseDto.getVideoLinks()));
            exerciseRepo.save(exercise);
            exerciseList.add(exercise);
        }
        return exerciseList;
    }

    public List<VideoLink> convertFromExerciseDtoVideoLinksToVideoLinks(List<String> videoLinksString) {
        List<VideoLink> videoLinkList = new ArrayList<>();
        for (String currentVideoLinkString : videoLinksString){
            VideoLink videoLink = new VideoLink();
            videoLink.setLink(currentVideoLinkString);
            videoLinkRepo.save(videoLink);
            videoLinkList.add(videoLink);
        }
        return videoLinkList;
    }

}
