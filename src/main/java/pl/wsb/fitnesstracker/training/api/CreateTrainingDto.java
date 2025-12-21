package pl.wsb.fitnesstracker.training.api;

import lombok.Data;

import java.util.Date;

@Data
public class CreateTrainingDto {

    private Long userId;
    private Date startTime;
    private Date endTime;
    private String activityType;
    private double distance;
    private double averageSpeed;

}