package com.ecomarketspa.EcoMarketSPA.Service;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import com.ecomarketspa.EcoMarketSPA.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductModel> getAllProducts() {return productRepository.obtenerProductos();}

    public ProductModel saveProducto(ProductModel product) {return productRepository.guardar(product);}

    public ProductModel getIdProducto(int id) {return productRepository.buscarPorId(id);}

    public ProductModel updateProducto(ProductModel product) {return productRepository.actualizar(product);}

    public String deleteProducto(int id) {
        productRepository.eliminar(id);
        return "Producto eliminado";
    }
}
