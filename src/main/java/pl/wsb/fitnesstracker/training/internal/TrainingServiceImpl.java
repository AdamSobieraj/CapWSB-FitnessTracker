package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation responsible for handling business logic
 * related to training operations.
 *
 * <p>
 * This class coordinates interactions between repositories,
 * domain entities and DTO mapping.
 * </p>
 */
@Service
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;
    private final TrainingMapper trainingMapper;

    /**
     * Creates a new instance of {@link TrainingServiceImpl}.
     *
     * @param trainingRepository repository for training persistence
     * @param userRepository repository for user persistence
     * @param trainingMapper mapper used to convert entities to DTOs
     */
    public TrainingServiceImpl(
            TrainingRepository trainingRepository,
            UserRepository userRepository,
            TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
        this.trainingMapper = trainingMapper;
    }

    /**
     * Retrieves a single training by its identifier.
     *
     * @param trainingId training identifier
     * @return optional training entity
     */
    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    /**
     * Retrieves all trainings for a specific user as entities.
     *
     * @param userId user identifier
     * @return list of training entities
     */
    @Override
    public List<Training> getTrainingsForUser(final Long userId) {
        return trainingRepository.findByUserId(userId);
    }

    /**
     * Retrieves all trainings as DTOs.
     *
     * @return list of training DTOs
     */
    @Override
    public List<TrainingDto> findAll() {
        return trainingRepository.findAll()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Retrieves all trainings for a specific user as DTOs.
     *
     * @param userId user identifier
     * @return list of training DTOs
     */
    @Override
    public List<TrainingDto> findByUserId(final Long userId) {
        return trainingRepository.findByUserId(userId)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Creates a new training.
     *
     * @param dto data required to create a training
     * @return created training DTO
     * @throws IllegalArgumentException if user does not exist
     */
    @Override
    public TrainingDto createTraining(final CreateTrainingDto dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found: " + dto.getUserId())
                );

        Training training = new Training(
                user,
                dto.getStartTime(),
                dto.getEndTime(),
                ActivityType.valueOf(dto.getActivityType()),
                dto.getDistance(),
                dto.getAverageSpeed()
        );

        Training savedTraining = trainingRepository.save(training);
        return trainingMapper.toDto(savedTraining);
    }

    /**
     * Deletes a training by its identifier.
     *
     * @param trainingId training identifier
     * @throws IllegalArgumentException if training does not exist
     */
    @Override
    public void deleteTraining(final Long trainingId) {

        if (!trainingRepository.existsById(trainingId)) {
            throw new IllegalArgumentException(
                    "Training not found: " + trainingId
            );
        }

        trainingRepository.deleteById(trainingId);
    }
}
