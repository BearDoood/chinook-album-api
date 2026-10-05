package com.gcu.chinook.album;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AlbumService {

    private static final Logger log = LoggerFactory.getLogger(AlbumService.class);

    private final AlbumRepository repository;

    AlbumService(AlbumRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<AlbumDto> findAll() {
        log.debug("Finding all albums");
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public AlbumDto findById(Integer id) {
        log.debug("Finding album {}", id);
        return toDto(getOrThrow(id));
    }

    public AlbumDto create(AlbumDto dto) {
        Album saved = repository.save(new Album(dto.title(), dto.artistId()));
        log.info("Created album {}", saved.getAlbumId());
        return toDto(saved);
    }

    public AlbumDto update(Integer id, AlbumDto dto) {
        Album album = getOrThrow(id);
        album.setTitle(dto.title());
        album.setArtistId(dto.artistId());
        log.info("Updated album {}", id);
        return toDto(repository.save(album));
    }

    public void delete(Integer id) {
        Album album = getOrThrow(id);
        repository.delete(album);
        log.info("Deleted album {}", id);
    }

    private Album getOrThrow(Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            log.warn("Album {} not found", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Album " + id + " not found");
        });
    }

    private AlbumDto toDto(Album album) {
        return new AlbumDto(album.getAlbumId(), album.getTitle(), album.getArtistId());
    }
}
