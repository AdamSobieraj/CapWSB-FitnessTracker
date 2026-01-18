package pl.wsb.fitnesstracker.training.api;

import lombok.Data;
import pl.wsb.fitnesstracker.user.api.UserDto;

import java.util.Date;

@Data
public class TrainingDto {

    private Long id;
    private UserDto user;
    private Date startTime;
    private Date endTime;
    private double distance;
    private double averageSpeed;

}