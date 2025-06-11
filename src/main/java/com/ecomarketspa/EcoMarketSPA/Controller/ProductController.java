package com.ecomarketspa.EcoMarketSPA.Controller;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import com.ecomarketspa.EcoMarketSPA.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("productos/")
@Tag(name = "Productos", description = "Operacion sobre el nuestro catalogo de productos")
@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Página principal de productos (lista de productos)
    @Operation(summary = "Listar productos", description = "Devuelve la lista de todos los productos")
    @GetMapping("/productos")
    public String mostrarProductos(Model model) {
        List<ProductModel> productos = productService.findAll();
        model.addAttribute("products", productos);
        return "list_product";
    }

    // Mostrar formulario para agregar producto
    @Operation(summary = "Formulario para agregar producto", description = "Muestra el formulario para registrar un nuevo producto")
    @GetMapping("/productos/agregar")
    public String showAddProductForm(Model model) {
        model.addAttribute("productRequest", new ProductModel());
        return "add_product";
    }

    // Procesar registro de producto
    @Operation(summary = "Registrar producto", description = "Registra un nuevo producto en el sistema")
    @PostMapping("/productos/agregar")
    public String saveProduct(@ModelAttribute("productRequest") ProductModel productModel) {
        System.out.println("Producto recibido: " + productModel);
        ProductModel saved = productService.saveProducto(productModel);
        return saved == null ? "error_page" : "redirect:/productos";
    }

    // Mostrar formulario de edición
    @Operation(summary = "Formulario para editar producto", description = "Muestra un formulario para modificar un producto existente")
    @GetMapping("/productos/editar/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        ProductModel product = productService.getIdProducto(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "edit_product"; // Asegúrate de tener esta vista
        }
        return "error_page";
    }

    // Procesar edición
    @Operation(summary = "Actualizar producto", description = "Guarda los cambios realizados a un producto")
    @PostMapping("/productos/editar")
    public String updateProduct(@ModelAttribute ProductModel product) {
        productService.updateProducto(product);
        return "redirect:/productos";
    }

    // Eliminar producto
    @Operation(summary = "Eliminar producto", description = "Elimina un producto por su ID")
    @GetMapping("/productos/eliminar/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProducto(id);
        return "redirect:/productos";
    }
}
