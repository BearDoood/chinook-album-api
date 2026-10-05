package com.gcu.chinook.album;

import org.springframework.data.jpa.repository.JpaRepository;

interface AlbumRepository extends JpaRepository<Album, Integer> {
}
