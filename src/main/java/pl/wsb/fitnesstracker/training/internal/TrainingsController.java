package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.CreateTrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingDto;

import java.util.List;

/**
 * REST controller responsible for handling HTTP requests
 * related to training resources.
 *
 * <p>
 * The controller exposes endpoints for:
 * <ul>
 *     <li>retrieving all trainings</li>
 *     <li>retrieving trainings for a specific user</li>
 *     <li>creating a new training</li>
 *     <li>deleting an existing training</li>
 * </ul>
 * </p>
 *
 * <p>
 * This controller delegates business logic to {@link TrainingServiceImpl}
 * and does not contain any domain or mapping logic.
 * </p>
 */
@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingsController {

    private final TrainingServiceImpl trainingService;

    /**
     * Retrieves all trainings.
     *
     * @return list of all trainings as DTOs
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAll();
    }

    /**
     * Retrieves all trainings for a specific user.
     *
     * @param userId unique identifier of the user
     * @return list of trainings assigned to the given user
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<TrainingDto>> getTrainingsForUser(
            @PathVariable final Long userId) {

        return ResponseEntity.ok(trainingService.findByUserId(userId));
    }

    /**
     * Creates a new training.
     *
     * @param dto data required to create a training
     * @return created training DTO
     */
    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(
            @RequestBody final CreateTrainingDto dto) {

        TrainingDto createdTraining = trainingService.createTraining(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTraining);
    }

    /**
     * Deletes a training by its identifier.
     *
     * @param trainingId unique identifier of the training
     * @return HTTP 204 No Content if deletion was successful
     */
    @DeleteMapping("/{trainingId}")
    public ResponseEntity<Void> deleteTraining(
            @PathVariable final Long trainingId) {

        trainingService.deleteTraining(trainingId);
        return ResponseEntity.noContent().build();
    }
}
