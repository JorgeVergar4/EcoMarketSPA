package com.ecomarketspa.EcoMarketSPA.Controller;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import com.ecomarketspa.EcoMarketSPA.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductController {
    @Autowired
    private ProductService productService;


    @PostMapping
    public ResponseEntity<ProductModel> saveProduct(@RequestBody ProductModel product) {
        ProductModel savedProduct = productService.saveProducto(product);
        return ResponseEntity.ok(savedProduct);
    }


    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable int id) {
        ProductModel product = productService.getIdProducto(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductModel> updateProduct(@PathVariable int id, @RequestBody ProductModel product) {
        product.setId_producto(id);
        ProductModel updatedProduct = productService.updateProducto(product);
        return ResponseEntity.ok(updatedProduct);
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        String response = productService.deleteProducto(id);
        return ResponseEntity.ok(response);
    }
}
