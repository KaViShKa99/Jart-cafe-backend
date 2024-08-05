package com.example.jart_cafe.api;

import com.example.jart_cafe.dto.ArtworkDTO;
import com.example.jart_cafe.dto.ArtworkDetailsDTO;
import com.example.jart_cafe.model.Artwork;
import com.example.jart_cafe.services.impl.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:5173/")
//@CrossOrigin(origins = "https://jartcafe.com", allowCredentials = "true")
@RestController
@RequestMapping("api/artworks")
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;

    @PostMapping
    public Artwork createArtwork(@RequestBody ArtworkDTO artworkDTO) {
        return artworkService.saveArtwork(artworkDTO);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteArtwork(@PathVariable Long id) {
        boolean isUpdated = artworkService.deleteById(id);
        if (isUpdated) {
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<ArtworkDetailsDTO> getAllArtworks() {
        return artworkService.findAll();
    }

    @GetMapping("get/{id}")
    public ArtworkDetailsDTO getOneArtworks(@PathVariable Long id){
        return artworkService.findOne(id);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updatedEntity(@PathVariable Long id,@RequestBody ArtworkDTO artworkDTO){
        boolean isUpdated = artworkService.updateArtwork(id, artworkDTO);
        if (isUpdated) {
            return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not updated", HttpStatus.BAD_REQUEST);
        }
    }


}
