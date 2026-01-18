package pl.wsb.fitnesstracker.training.internal;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingRepository;
import pl.wsb.fitnesstracker.user.internal.UserRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeeklyTrainingReportService {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;

    public void generateWeeklyConsoleReport() {

        LocalDate monday = LocalDate.now()
                .minusWeeks(1)
                .with(DayOfWeek.MONDAY);

        LocalDate sunday = monday.plusDays(6);

        Date startDate = Date.from(
                monday.atStartOfDay(ZoneId.systemDefault()).toInstant()
        );
        Date endDate = Date.from(
                sunday.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()
        );

        System.out.println("=== RAPORT TRENINGOWY (" + monday + " - " + sunday + ") ===");

        userRepository.findAll().forEach(user -> {
            try {
                List<Training> trainings =
                        trainingRepository.findByUserAndStartTimeBetween(
                                user, startDate, endDate
                        );

                System.out.println("Użytkownik: "
                        + user.getFirstName() + " " + user.getLastName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Liczba treningów: " + trainings.size());
                System.out.println("-----------------------------------");

            } catch (Exception e) {
                System.err.println(
                        "Błąd przetwarzania użytkownika: " + user.getEmail()
                );
            }
        });
    }
}
