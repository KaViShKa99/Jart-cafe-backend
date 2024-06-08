package com.example.jart_cafe.repositories;

import com.example.jart_cafe.model.Image;
import com.example.jart_cafe.model.SizePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    void deleteByArtworkId(Long artworkId);

    List<Image> findAllByArtworkId(Long artworkId);

}

