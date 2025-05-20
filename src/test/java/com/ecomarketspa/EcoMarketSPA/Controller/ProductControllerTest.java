package com.ecomarketspa.EcoMarketSPA.Controller;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import com.ecomarketspa.EcoMarketSPA.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void testMostrarProductos() throws Exception {
        ProductModel product = new ProductModel(
                1L, "EcoBotella", "Botella reutilizable", 15.0, 100, true
        );
        Mockito.when(productService.findAll()).thenReturn(List.of(product));

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(view().name("list_product"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    void testShowAddProductForm() throws Exception {
        mockMvc.perform(get("/productos/agregar"))
                .andExpect(status().isOk())
                .andExpect(view().name("add_product"))
                .andExpect(model().attributeExists("productRequest"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(get("/productos/eliminar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/productos"));
    }
}

