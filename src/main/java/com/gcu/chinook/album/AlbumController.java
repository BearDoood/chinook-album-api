package com.gcu.chinook.album;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService service;

    public AlbumController(AlbumService service) {
        this.service = service;
    }

    /**
     * Returns every album.
     *
     * @return 200 with a list of albums
     */
    @GetMapping
    public List<AlbumDto> getAll() {
        return service.findAll();
    }

    /**
     * Returns one album.
     *
     * @param id the album id
     * @return 200 with the album, or 404 if it does not exist
     */
    @GetMapping("/{id}")
    public AlbumDto getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    /**
     * Creates an album. Any albumId in the body is ignored.
     *
     * @param dto the album to create
     * @return 201 with the saved album and a Location header
     */
    @PostMapping
    public ResponseEntity<AlbumDto> create(@Valid @RequestBody AlbumDto dto) {
        AlbumDto created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/albums/" + created.albumId())).body(created);
    }

    /**
     * Replaces the title and artist of an existing album.
     *
     * @param id  the album id
     * @param dto the new values
     * @return 200 with the updated album, or 404 if it does not exist
     */
    @PutMapping("/{id}")
    public AlbumDto update(@PathVariable Integer id, @Valid @RequestBody AlbumDto dto) {
        return service.update(id, dto);
    }

    /**
     * Deletes an album.
     *
     * @param id the album id
     * @return 204 with no body, or 404 if it does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
