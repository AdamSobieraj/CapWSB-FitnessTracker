package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDistDto;
import pl.wsb.fitnesstracker.user.api.UserDto;

@Component
public class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    UserDistDto toDistDto(User user) {
        return new UserDistDto(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

}
