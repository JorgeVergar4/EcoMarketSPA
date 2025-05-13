package com.ecomarketspa.EcoMarketSPA.Controller;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import com.ecomarketspa.EcoMarketSPA.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductModel> listarProductos() { return productService.getAllProducts();}

    @PostMapping
    public ResponseEntity<String> crearProducto(@RequestBody ProductModel producto) {
        return ResponseEntity.ok("Producto creado");
    }

    @PostMapping()
    public ProductModel addProducto(@RequestBody ProductModel product) {return productService.saveProducto(product);}

    @GetMapping("{id}")
    public ProductModel actualizarProducto(@PathVariable int id, @RequestBody ProductModel product) {
        return productService.updateProducto(product);
    }

    @DeleteMapping("{id}")
    public String eliminarProducto(@PathVariable int id) {return productService.deleteProducto(id);}
}
