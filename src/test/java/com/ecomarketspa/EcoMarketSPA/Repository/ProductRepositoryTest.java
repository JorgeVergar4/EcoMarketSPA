package com.ecomarketspa.EcoMarketSPA.Repository;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/schema.sql") // Archivo SQL para crear la estructura
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {
        ProductModel product = new ProductModel();
        product.setNombre("Manzana");
        product.setDescripcion("Fruta fresca");
        product.setPrecio(1000.0);
        product.setStock(50);
        product.setEstado(true);

        ProductModel saved = productRepository.save(product);
        assertNotNull(saved.getId());
        assertEquals("Manzana", saved.getNombre());
    }
}