package pl.wsb.fitnesstracker.mail.internal;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendWeeklySummary(User user, int trainingsCount) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Tygodniowe podsumowanie treningów");
        message.setText(
                "Cześć " + user.getFirstName() + ",\n\n" +
                        "W zeszłym tygodniu zarejestrowałeś/aś "
                        + trainingsCount + " treningów.\n\n" +
                        "Pozdrawiamy,\nFitness Tracker"
        );

        mailSender.send(message);
    }
}