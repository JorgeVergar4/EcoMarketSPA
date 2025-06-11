package com.ecomarketspa.EcoMarketSPA.Controller;

import com.ecomarketspa.EcoMarketSPA.Dto.ProductDto;
import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import com.ecomarketspa.EcoMarketSPA.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones CRUD sobre productos")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    // Convertir entre DTO y modelo
    private ProductDto toDto(ProductModel model) {
        ProductDto dto = new ProductDto();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setDescripcion(model.getDescripcion());
        dto.setPrecio(model.getPrecio());
        dto.setStock(model.getStock());
        dto.setEstado(model.getEstado());
        return dto;
    }

    private ProductModel toModel(ProductDto dto) {
        ProductModel model = new ProductModel();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setDescripcion(dto.getDescripcion());
        model.setPrecio(dto.getPrecio());
        model.setStock(dto.getStock());
        model.setEstado(dto.getEstado());
        return model;
    }


    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Retorna una lista de productos")
    public List<ProductDto> getAllProducts() {
        return productService.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto por ID", description = "Devuelve un producto según su ID")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductModel product = productService.getIdProducto(id);
        return product != null ? ResponseEntity.ok(toDto(product)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear nuevo producto", description = "Crea un producto nuevo")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductModel saved = productService.saveProducto(toModel(productDto));
        return saved != null ? ResponseEntity.ok(toDto(saved)) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Modifica un producto existente")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto dto) {
        ProductModel model = toModel(dto);
        model.setId(id);
        ProductModel updated = productService.updateProducto(model);
        return updated != null ? ResponseEntity.ok(toDto(updated)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto por ID")
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
