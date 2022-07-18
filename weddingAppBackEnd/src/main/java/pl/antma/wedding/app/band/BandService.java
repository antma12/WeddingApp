package pl.antma.wedding.app.band;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class BandService {

    @Autowired
    BandRepository bandRepository;

    public Stream<Band> findAll() {
        return bandRepository.findAll(PageRequest.of(0, 20)).get();
    }

    public Band addBand(Band band) {
        return bandRepository.save(band);
    }

    public Optional<Band> findBandById(Long id) {
        return bandRepository.findById(id);
    }

    public Optional<Band> updateBand(Band band, Long id) {
        Optional<Band> updatedBand = bandRepository.findById(id);
        updatedBand.ifPresent(
                updBand -> {
                    updBand.setName(band.getName());
                    updBand.setInfo(band.getInfo());
                    updBand.setPrice(band.getPrice());
                    updBand.setOpinion(band.getOpinion());
                    bandRepository.save(updBand);
                }
        );
        return updatedBand;
    }

    public Optional<Band> deleteBand(Long id) {
        Optional<Band> deletedBand = bandRepository.findById(id);
        deletedBand.ifPresent(bandRepository::delete);
        return deletedBand;
    }
}
