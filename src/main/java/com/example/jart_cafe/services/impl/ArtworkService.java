package com.example.jart_cafe.services.impl;

import com.example.jart_cafe.dto.ArtworkDTO;
import com.example.jart_cafe.dto.ArtworkDetailsDTO;
import com.example.jart_cafe.dto.SizePriceDTO;
import com.example.jart_cafe.model.Artwork;
import com.example.jart_cafe.model.Image;
import com.example.jart_cafe.model.SizePrice;
import com.example.jart_cafe.repositories.ArtworkRepository;
import com.example.jart_cafe.repositories.ImageRepository;
import com.example.jart_cafe.repositories.SizePriceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private SizePriceRepository sizePriceRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Transactional
    public Artwork saveArtwork(ArtworkDTO artworkDTO) {

        Artwork artwork = new Artwork();
        artwork.setType(artworkDTO.getType());
        artwork.setMaterial(artworkDTO.getMaterial());
        artwork.setPrice(artworkDTO.getPrice());
        Artwork save = artworkRepository.save(artwork);

        for (String imageDTO : artworkDTO.getImageUrl()) {

            Image image = new Image();
            image.setArtworkId(save.getArtworkId());
            image.setUrl(imageDTO);
            imageRepository.saveAndFlush(image);
        }
        for (SizePriceDTO sizePriceDTO : artworkDTO.getSize()) {

            SizePrice sizePrice = new SizePrice();
            sizePrice.setArtworkId(save.getArtworkId());
            sizePrice.setDesign(sizePriceDTO.getDesign());
            sizePrice.setPrice(sizePriceDTO.getPrice());
            sizePriceRepository.saveAndFlush(sizePrice);
        }

        return artwork;

    }

    @Transactional
    public void deleteById(Long id) {
        imageRepository.deleteByArtworkId(id);
        sizePriceRepository.deleteByArtworkId(id);
        artworkRepository.deleteById(id);
    }

    public Optional<Artwork> findById(Long id) {
        return artworkRepository.findById(id);
    }

    public List<ArtworkDetailsDTO> findAll() {

        List<Artwork> artworkList = artworkRepository.findAll();
        List<ArtworkDetailsDTO> artworkDetailsList = new ArrayList<>();

        for (Artwork artwork : artworkList) {
            ArtworkDetailsDTO artworkDetailsDTO = new ArtworkDetailsDTO();
            artworkDetailsDTO.setArtworkId(artwork.getArtworkId());
            artworkDetailsDTO.setMaterial(artwork.getMaterial());
            artworkDetailsDTO.setType(artwork.getType());
            artworkDetailsDTO.setPrice(artwork.getPrice());
            artworkDetailsDTO.setSizePrices(sizePriceRepository.findAllByArtworkId(artwork.getArtworkId()));
            artworkDetailsDTO.setImages(imageRepository.findAllByArtworkId(artwork.getArtworkId()));

            artworkDetailsList.add(artworkDetailsDTO);
        }

        return artworkDetailsList;
    }

    public ArtworkDetailsDTO findOne(Long id) {


        Optional<Artwork> artworks = artworkRepository.findById(id);
        List<ArtworkDetailsDTO> artworkDetailsList = new ArrayList<>();
        ArtworkDetailsDTO artworkDetailsDTO = new ArtworkDetailsDTO();

        artworkDetailsDTO.setArtworkId(id);
        artworkDetailsDTO.setMaterial(artworks.get().getMaterial());
        artworkDetailsDTO.setType(artworks.get().getType());
        artworkDetailsDTO.setPrice(artworks.get().getPrice());
        artworkDetailsDTO.setSizePrices(sizePriceRepository.findAllByArtworkId(id));
        artworkDetailsDTO.setImages(imageRepository.findAllByArtworkId(id));
//        artworkDetailsList.add(artworkDetailsDTO);


        return artworkDetailsDTO;
    }


}
