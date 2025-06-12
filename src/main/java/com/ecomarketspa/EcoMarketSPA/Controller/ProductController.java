package com.ecomarketspa.EcoMarketSPA.Controller;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import com.ecomarketspa.EcoMarketSPA.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/productos")
@Tag(name = "Productos", description = "Operación sobre el catálogo de productos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Listar productos", description = "Devuelve la lista de todos los productos")
    @GetMapping("")
    public String mostrarProductos(Model model) {
        List<ProductModel> productos = productService.findAll();
        model.addAttribute("products", productos);
        return "list_product";
    }

    @Operation(summary = "Formulario para agregar producto")
    @GetMapping("/agregar")
    public String showAddProductForm(Model model) {
        model.addAttribute("productRequest", new ProductModel());
        return "add_product";
    }

    @Operation(summary = "Registrar producto")
    @PostMapping("/agregar")
    public String saveProduct(@ModelAttribute("productRequest") ProductModel productModel) {
        System.out.println("Producto recibido: " + productModel);
        ProductModel saved = productService.saveProducto(productModel);
        return saved == null ? "error_page" : "redirect:/productos";
    }

    @Operation(summary = "Formulario para editar producto")
    @GetMapping("/editar/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        ProductModel product = productService.getIdProducto(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "edit_product";
        }
        return "error_page";
    }

    @Operation(summary = "Actualizar producto")
    @PostMapping("/editar")
    public String updateProduct(@ModelAttribute ProductModel product) {
        productService.updateProducto(product);
        return "redirect:/productos";
    }

    @Operation(summary = "Eliminar producto")
    @GetMapping("/eliminar/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProducto(id);
        return "redirect:/productos";
    }
}

