package pl.antma.wedding.app.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class SongService {

    @Autowired
    SongRepository songRepository;

    Stream<Song> findAll() {
        return songRepository.findAll(PageRequest.of(0, 20)).get();
    }

    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    public Optional<Song> findSongById(Long id) {
        return songRepository.findById(id);
    }

    public Optional<Song> updateSong(Song song, Long id) {
        Optional<Song> updatedSong = songRepository.findById(id);
        updatedSong.ifPresent(updSong -> {
            updSong.setName(song.getName());
            updSong.setArtist(song.getArtist());
            updSong.setComment(song.getComment());
            songRepository.save(updSong);
        });
        return updatedSong;
    }

    public Optional<Song> deleteSong(Long id) {
        Optional<Song> deletedSong = songRepository.findById(id);
        deletedSong.ifPresent(songRepository::delete);
        return deletedSong;
    }


}
