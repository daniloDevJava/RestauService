package com.projet.foodGo.service;

import com.projet.foodGo.dto.ProduitsDto;
import com.projet.foodGo.model.Prestataire;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The interface Produits service.
 */
@Service
public interface ProduitsService {
    /**
     * Create product produits dto.
     *
     * @param produitAlimentaireDto the produit alimentaire dto
     * @param prestataire_id        the prestataire id
     * @return the produits dto
     */
    ProduitsDto createProduct(ProduitsDto produitAlimentaireDto, UUID prestataire_id);

    /**
     * Gets products.
     *
     * @param prestataire_id the prestataire id
     * @return the products
     */
    List<ProduitsDto> getProducts(UUID prestataire_id);

    /**
     * Gets products.
     *
     * @return the products
     */
    List<ProduitsDto> getProducts();

    /**
     * Gets product.
     *
     * @param id the id
     * @return the product
     */
    ProduitsDto getProduct(UUID id);

    /**
     * Gets product.
     *
     * @param mom            the mom
     * @param prestataire_id the prestataire id
     * @return the product
     */
    ProduitsDto getProduct(String mom, UUID prestataire_id);

    /**
     * Update product produits dto.
     *
     * @param id                    the id
     * @param produitAlimentaireDto the produit alimentaire dto
     * @return the produits dto
     */
    ProduitsDto updateProduct(UUID id, ProduitsDto produitAlimentaireDto);

    /**
     * Gets corbeille.
     *
     * @param prestataire_id the prestataire id
     * @return the corbeille
     */
    List<ProduitsDto> getCorbeille(UUID prestataire_id);

    /**
     * Update quantite stock produits dto.
     *
     * @param produit_id  the produit id
     * @param produitsDto the produits dto
     * @return the produits dto
     */
    ProduitsDto updateQuantiteStock(UUID produit_id,ProduitsDto produitsDto);

    /**
     * Mark as product deleted.
     *
     * @param prestataire the prestataire
     */
    void markAsProductDeleted(Prestataire prestataire);

    /**
     * Delete product boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteProduct(UUID id);
}
