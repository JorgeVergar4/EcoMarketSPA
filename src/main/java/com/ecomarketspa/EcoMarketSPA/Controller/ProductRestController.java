package com.ecomarketspa.EcoMarketSPA.Controller;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import com.ecomarketspa.EcoMarketSPA.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    // Obtener todos los productos
    @GetMapping
    public List<ProductModel> getAllProducts() {
        return productService.findAll();
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable Long id) {
        ProductModel product = productService.getIdProducto(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    // Crear producto
    @PostMapping
    public ResponseEntity<ProductModel> createProduct(@RequestBody ProductModel productModel) {
        ProductModel saved = productService.saveProducto(productModel);
        return saved != null ? ResponseEntity.ok(saved) : ResponseEntity.badRequest().build();
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductModel> updateProduct(@PathVariable Long id, @RequestBody ProductModel productModel) {
        productModel.setId(id); // Asegúrate de establecer el ID
        ProductModel updated = productService.updateProducto(productModel);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        ProductModel producto = productService.getIdProducto(id);
        if (producto == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Producto con ID " + id + " no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        productService.deleteProducto(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Producto eliminado con éxito");
        return ResponseEntity.ok(response);
    }

}
