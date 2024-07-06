package com.example.jart_cafe.repositories;

import com.example.jart_cafe.model.SizePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizePriceRepository extends JpaRepository<SizePrice,Long> {
//    void deleteByArtworkId(Long artworkId);
//    List<SizePrice> findAllByArtworkId(Long artworkId);


}
