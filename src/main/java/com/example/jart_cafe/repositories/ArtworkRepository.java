package com.example.jart_cafe.repositories;

import com.example.jart_cafe.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork,Long> {

}
