package com.example.jart_cafe.api;

import com.example.jart_cafe.dto.ArtworkDTO;
import com.example.jart_cafe.dto.ArtworkDetailsDTO;
import com.example.jart_cafe.model.Artwork;
import com.example.jart_cafe.services.impl.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("api/artworks")
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;

    @PostMapping
    public Artwork createArtwork(@RequestBody ArtworkDTO artworkDTO) {
        System.out.println(artworkDTO);
        return artworkService.saveArtwork(artworkDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Artwork> deleteArtwork(@PathVariable Long id) {
        Optional<Artwork> optionalArtwork = artworkService.findById(id);
        if (optionalArtwork.isPresent()) {
            artworkService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<ArtworkDetailsDTO> getAllArtworks() {
        return artworkService.findAll();
    }

    @GetMapping("/{id}")
    public ArtworkDetailsDTO getOneArtworks(@PathVariable Long id){
        return artworkService.findOne(id);
    }


}
