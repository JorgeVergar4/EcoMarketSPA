package com.ecomarketspa.EcoMarketSPA.Service;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import com.ecomarketspa.EcoMarketSPA.Repository.ProductRepository;
import com.ecomarketspa.EcoMarketSPA.Service.Impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testSaveProduct() {
        ProductModel product = new ProductModel();
        product.setNombre("Pan");

        when(productRepository.save(any())).thenReturn(product);

        ProductModel saved = productService.saveProducto(product);
        assertEquals("Pan", saved.getNombre());
    }
}