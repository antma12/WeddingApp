package pl.antma.wedding.app.viedographer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class VideographerService {

    @Autowired
    VideographerRepository videographerRepository;

    public Stream<Videographer> findAll() {
        return videographerRepository.findAll(PageRequest.of(0, 20)).get();
    }

    public Videographer addVideographer(Videographer videographer) {
        return videographerRepository.save(videographer);
    }

    public Optional<Videographer> findVideographerById(Long id) {
        return videographerRepository.findById(id);
    }

    public Optional<Videographer> updateVideographer(Videographer videographer, Long id) {
        Optional<Videographer> updatedVideographer = videographerRepository.findById(id);
        updatedVideographer.ifPresent(
                updVideographer -> {
                    updVideographer.setName(videographer.getName());
                    updVideographer.setWebsite(videographer.getWebsite());
                    updVideographer.setCameraman(videographer.isCameraman());
                    updVideographer.setPhotographer(videographer.isPhotographer());
                    updVideographer.setChosen(videographer.isChosen());
                    videographerRepository.save(updVideographer);
                });
        return updatedVideographer;
    }

    public Optional<Videographer> deleteVideographer(Long id) {
        Optional<Videographer> deletedVideographer = videographerRepository.findById(id);
        deletedVideographer.ifPresent(videographerRepository::delete);
        return deletedVideographer;
    }
}
