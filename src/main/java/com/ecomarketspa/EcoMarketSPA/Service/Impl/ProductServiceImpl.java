package com.ecomarketspa.EcoMarketSPA.Service.Impl;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import com.ecomarketspa.EcoMarketSPA.Repository.ProductRepository;
import com.ecomarketspa.EcoMarketSPA.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductModel> findAll() {
        return productRepository.findAll();
    }


    @Override
    public ProductModel saveProducto(ProductModel product) {
        return productRepository.save(product);
    }

    @Override
    public ProductModel getIdProducto(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public ProductModel updateProducto(ProductModel product) {
        Optional<ProductModel> existing = productRepository.findById(product.getId());
        return existing.map(p -> productRepository.save(product)).orElse(null);
    }

    @Override
    public String deleteProducto(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return "Producto eliminado correctamente.";
        } else {
            return "Producto no encontrado.";
        }
    }
}
