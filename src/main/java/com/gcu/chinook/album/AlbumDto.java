package com.gcu.chinook.album;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * What the API sends and receives for an album. The albumId is ignored on
 * create and comes from the URL on update.
 */
public record AlbumDto(
        Integer albumId,
        @NotBlank @Size(max = 160) String title,
        @NotNull Integer artistId) {
}
