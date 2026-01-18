package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeeklyTrainingReportScheduler {

    private final WeeklyTrainingReportService reportService;

//    // co poniedziałek o 08:00
//    @Scheduled(cron = "0 0 8 ? * MON")

    // co 1 minuty
    @Scheduled(cron = "0 */1 * * * *")
    public void runWeeklyReport() {
        reportService.generateWeeklyConsoleReport();
    }
}