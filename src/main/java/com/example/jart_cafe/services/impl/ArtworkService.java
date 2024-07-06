package com.example.jart_cafe.services.impl;

import com.example.jart_cafe.dto.ArtworkDTO;
import com.example.jart_cafe.dto.ArtworkDetailsDTO;
import com.example.jart_cafe.dto.MaterialDTO;
import com.example.jart_cafe.dto.SizePriceDTO;
import com.example.jart_cafe.model.Artwork;
import com.example.jart_cafe.model.Image;
import com.example.jart_cafe.model.Material;
import com.example.jart_cafe.model.SizePrice;
import com.example.jart_cafe.repositories.ArtworkRepository;
import com.example.jart_cafe.repositories.ImageRepository;
import com.example.jart_cafe.repositories.SizePriceRepository;
import jakarta.transaction.Transactional;
import org.hibernate.engine.jdbc.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        artworkDetailsAdding(artworkDTO, artwork);

        return artworkRepository.save(artwork);

    }

    @Transactional
    public Boolean updateArtwork(Long id, ArtworkDTO artworkDTO) {
        Optional<Artwork> optionalArtwork = artworkRepository.findById(id);
        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork = optionalArtwork.get();
            artworkDetailsAdding(artworkDTO, existingArtwork);

//            artworkRepository.save(existingArtwork);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Boolean deleteById(Long id) {
        Optional<Artwork> optionalArtwork = artworkRepository.findById(id);
        if (optionalArtwork.isPresent()) {
            artworkRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private void artworkDetailsAdding(ArtworkDTO artworkDTO, Artwork existingArtwork) {
        existingArtwork.setCategory(artworkDTO.getCategory());
        existingArtwork.setDescription(artworkDTO.getDescription());
        existingArtwork.setTitle(artworkDTO.getTitle());
        existingArtwork.setPrice(artworkDTO.getPrice());
        existingArtwork.setLastPrice(artworkDTO.getLastPrice());
        existingArtwork.setImages(artworkDTO.getImages());

        List<Material> materials = artworkDTO.getMaterials().stream()
                .map(this::mapMaterialDTOToEntity)
                .collect(Collectors.toList());
        existingArtwork.setMaterials(materials);
    }

    public List<ArtworkDetailsDTO> findAll() {

        List<Artwork> artworkList = artworkRepository.findAll();
        List<ArtworkDetailsDTO> artworkDetailsList = new ArrayList<>();

        for (Artwork artwork : artworkList) {
            ArtworkDetailsDTO artworkDetailsDTO = new ArtworkDetailsDTO();
            artworkDetailsDTO.setArtworkId(artwork.getId());
            artworkDetailsDTO.setCategory(artwork.getCategory());
            artworkDetailsDTO.setDescription(artwork.getDescription());
            artworkDetailsDTO.setTitle(artwork.getTitle());
            artworkDetailsDTO.setPrice(artwork.getPrice());
            artworkDetailsDTO.setLastPrice(artwork.getLastPrice());
            artworkDetailsDTO.setImages(artwork.getImages());

            List<MaterialDTO> materialDTOList = artwork.getMaterials().stream()
                    .map(this::mapMaterialToDTO)
                    .collect(Collectors.toList());

            artworkDetailsDTO.setMaterials(materialDTOList);

            artworkDetailsList.add(artworkDetailsDTO);
        }

        return artworkDetailsList;
    }

    public ArtworkDetailsDTO findOne(Long id) {


        Optional<Artwork> artworkOptional = artworkRepository.findById(id);

//        if (artworkOptional.isEmpty()) {
//            throw new ResourceNotFoundException("Artwork not found with id: " + id);
//        }

        Artwork artwork = artworkOptional.get();
        ArtworkDetailsDTO artworkDetailsDTO = new ArtworkDetailsDTO();


        artworkDetailsDTO.setArtworkId(id);
        artworkDetailsDTO.setCategory(artwork.getCategory());
        artworkDetailsDTO.setDescription(artwork.getDescription());
        artworkDetailsDTO.setTitle(artwork.getTitle());
        artworkDetailsDTO.setPrice(artwork.getPrice());
        artworkDetailsDTO.setLastPrice(artwork.getLastPrice());
        artworkDetailsDTO.setImages(artwork.getImages());
        artworkDetailsDTO.setMaterials(mapMaterialsToDTOs(artwork.getMaterials()));

        return artworkDetailsDTO;
    }

    private Material mapMaterialDTOToEntity(MaterialDTO materialDTO) {
        Material material = new Material();

        material.setMaterial(materialDTO.getMaterial());

        List<SizePrice> sizes = materialDTO.getSizes().stream()
                .map(this::mapSizeDTOToEntity)
                .collect(Collectors.toList());
        material.setSizes(sizes);

        return material;
    }

    private SizePrice mapSizeDTOToEntity(SizePriceDTO sizePriceDTO) {
        SizePrice sizePrice = new SizePrice();
        sizePrice.setSize(sizePriceDTO.getSize());
        sizePrice.setPrice(sizePriceDTO.getPrice());

        return sizePrice;
    }

    private MaterialDTO mapMaterialToDTO(Material material) {
        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setMaterial(material.getMaterial());

        List<SizePriceDTO> sizeDTOList = material.getSizes().stream()
                .map(this::mapSizeToDTO)
                .collect(Collectors.toList());
        materialDTO.setSizes(sizeDTOList);

        return materialDTO;
    }

    private SizePriceDTO mapSizeToDTO(SizePrice size) {
        SizePriceDTO sizeDTO = new SizePriceDTO();
        sizeDTO.setSize(size.getSize());
        sizeDTO.setPrice(size.getPrice());

        return sizeDTO;
    }

    private List<MaterialDTO> mapMaterialsToDTOs(List<Material> materials) {
        return materials.stream()
                .map(this::mapMaterialToDTO)
                .collect(Collectors.toList());
    }

}



