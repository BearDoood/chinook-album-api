package com.gcu.chinook.album;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Maps to the album table. Package visibility only, so nothing outside the
 * album feature can touch it.
 */
@Entity
@Table(name = "album")
class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Integer albumId;

    @Column(name = "title", nullable = false, length = 160)
    private String title;

    @Column(name = "artist_id", nullable = false)
    private Integer artistId;

    protected Album() {
        // needed by JPA
    }

    Album(String title, Integer artistId) {
        this.title = title;
        this.artistId = artistId;
    }

    Integer getAlbumId() {
        return albumId;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    Integer getArtistId() {
        return artistId;
    }

    void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }
}
