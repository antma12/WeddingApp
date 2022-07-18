package pl.antma.wedding.app.ballroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class BallroomService {

    @Autowired
    BallroomRepository ballroomRepository;

    Stream<Ballroom> findAll(int page, int size) {
        return ballroomRepository.findAll(PageRequest.of(page, size)).get();
    }

    public Ballroom addBallroom(Ballroom ballroom) {
        return ballroomRepository.save(ballroom);
    }

    public Optional<Ballroom> findBallroomById(Long id) {
        return ballroomRepository.findById(id);
    }

    public Optional<Ballroom> updateBallroom(Ballroom ballroom, Long id) {
        Optional<Ballroom> updatedBallroom = ballroomRepository.findById(id);
        updatedBallroom.ifPresent(
                updBallroom -> {
                    updBallroom.setName(ballroom.getName());
                    updBallroom.setCity(ballroom.getCity());
                    updBallroom.setDistance(ballroom.getDistance());
                    updBallroom.setAvailableDates(ballroom.getAvailableDates());
                    updBallroom.setPrice(ballroom.getPrice());
                    updBallroom.setIsWithNightStay(ballroom.getIsWithNightStay());
                    updBallroom.setPricePerNight(ballroom.getPricePerNight());
                    updBallroom.setOpinion(ballroom.getOpinion());
                    ballroomRepository.save(updBallroom);
                });
        return updatedBallroom;
    }

    public Optional<Ballroom> deleteBallroom(Long id) {
        Optional<Ballroom> deletedBallroom = ballroomRepository.findById(id);
        deletedBallroom.ifPresent(ballroomRepository::delete);
        return deletedBallroom;
    }
}
