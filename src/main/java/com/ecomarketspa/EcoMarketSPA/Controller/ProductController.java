package com.ecomarketspa.EcoMarketSPA.Controller;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import com.ecomarketspa.EcoMarketSPA.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("productos")
@Tag(name = "Productos", description = "Operaciones sobre nuestro catálogo de productos")
@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Pág principal de productos (lista de producto)
    @Operation(summary = "Listar productos", description = "Devuelve la lista de todos los productos")
    @GetMapping("")
    public String mostrarProductos(Model model) {
        List<ProductModel> productos = productService.findAll();
        model.addAttribute("products", productos);
        return "list_product";
    }

    // agregar producto
    @Operation(summary = "Formulario para agregar producto", description = "Muestra el formulario para registrar un nuevo producto")
    @GetMapping("/agregar")
    public String showAddProductForm(Model model) {
        model.addAttribute("productRequest", new ProductModel());
        return "add_product";
    }

    // Procesar registro de producto
    @Operation(summary = "Registrar producto", description = "Registra un nuevo producto en el sistema")
    @PostMapping("/agregar")
    public String saveProduct(@ModelAttribute("productRequest") ProductModel productModel) {
        System.out.println("Producto recibido: " + productModel);
        ProductModel saved = productService.saveProducto(productModel);
        return saved == null ? "error_page" : "redirect:/productos";
    }

    // Mostrar formulario de edición
    @Operation(summary = "Formulario para editar producto", description = "Muestra un formulario para modificar un producto existente")
    @GetMapping("/editar/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        ProductModel product = productService.getIdProducto(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "edit_product";
        }
        return "error_page";
    }

    // Procesar edición
    @Operation(summary = "Actualizar producto", description = "Guarda los cambios realizados a un producto")
    @PostMapping("/editar")
    public String updateProduct(@ModelAttribute ProductModel product) {
        productService.updateProducto(product);
        return "redirect:/productos";
    }

    // Eliminar producto
    @Operation(summary = "Eliminar producto", description = "Elimina un producto por su ID")
    @GetMapping("/eliminar/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProducto(id);
        return "redirect:/productos";
    }
}
