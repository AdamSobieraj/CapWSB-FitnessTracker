package pl.wsb.fitnesstracker.training.internal;


import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;

@Component
public class TrainingMapper {

    /**
     * Maps Training entity to TrainingDto.
     *
     * @param training training entity
     * @return mapped TrainingDto
     */
    public TrainingDto toDto(final Training training) {
        if (training == null) {
            return null;
        }

        TrainingDto dto = new TrainingDto();
        dto.setId(training.getId());
        dto.setStartTime(training.getStartTime());
        dto.setEndTime(training.getEndTime());
        dto.setDistance(training.getDistance());
        dto.setAverageSpeed(training.getAverageSpeed());
        dto.setUser(toUserDto(training.getUser()));

        return dto;
    }

    /**
     * Maps User entity to UserDto (record).
     *
     * @param user user entity
     * @return mapped UserDto
     */
    private UserDto toUserDto(final User user) {
        if (user == null) {
            return null;
        }

        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(), // LocalDate
                user.getEmail()
        );
    }
}