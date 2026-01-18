package pl.wsb.fitnesstracker.mail.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.TrainingRepository;
import pl.wsb.fitnesstracker.user.internal.UserRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class WeeklyTrainingReportMailService {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final EmailService emailService;

    public void generateWeeklyReportAndSendEmails() {

        LocalDate monday = LocalDate.now().minusWeeks(1)
                .with(DayOfWeek.MONDAY);
        LocalDate sunday = monday.plusDays(6);

        Date startDate = Date.from(
                monday.atStartOfDay(ZoneId.systemDefault()).toInstant()
        );
        Date endDate = Date.from(
                sunday.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()
        );

        userRepository.findAll().forEach(user -> {
            try {
                var trainings =
                        trainingRepository.findByUserAndStartTimeBetween(
                                user, startDate, endDate
                        );

                System.out.println("Użytkownik: "
                        + user.getFirstName() + " " + user.getLastName());
                System.out.println("Liczba treningów: " + trainings.size());
                emailService.sendWeeklySummary(user, trainings.size());

                Thread.sleep(1000);

            } catch (MailException e) {
                System.err.println("Nie udało się wysłać maila do: " + user.getEmail());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

            } catch (Exception e) {
                System.err.println("Błąd przetwarzania użytkownika: " + user.getEmail());
            }
        });
    }
}
