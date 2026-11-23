package ma.mundiapolis.springmvc.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.mundiapolis.springmvc.entities.Product;
import ma.mundiapolis.springmvc.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(defaultValue = "") String keyword,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "5") int size) {
        Page<Product> products = productRepository.findByNameContainsIgnoreCase(
                keyword, PageRequest.of(page, size, Sort.by("id")));
        model.addAttribute("productList", products.getContent());
        model.addAttribute("pages", new int[products.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "products";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id,
                         @RequestParam(defaultValue = "") String keyword,
                         @RequestParam(defaultValue = "0") int page,
                         RedirectAttributes redirect) {
        productRepository.deleteById(id);
        redirect.addAttribute("keyword", keyword);
        redirect.addAttribute("page", page);
        return "redirect:/index";
    }

    @GetMapping("/formProducts")
    public String formProducts(Model model) {
        model.addAttribute("product", new Product());
        return "formProducts";
    }

    @GetMapping("/editProduct")
    public String editProduct(@RequestParam Long id, Model model) {
        return productRepository.findById(id)
                .map(p -> {
                    model.addAttribute("product", p);
                    return "formProducts";
                })
                .orElse("redirect:/index");
    }

    @PostMapping("/save")
    public String save(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formProducts";
        }
        productRepository.save(product);   // id présent = mise à jour, sinon insertion
        return "redirect:/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized() {
        return "notAuthorized";
    }
}