package ma.mundiapolis.springmvc.repository;

import ma.mundiapolis.springmvc.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveAndFindProduct() {
        Product saved = productRepository.save(
                Product.builder().name("Clavier").price(300).quantity(5).build());

        assertThat(saved.getId()).isNotNull();
        assertThat(productRepository.findById(saved.getId())).isPresent();
    }
    @Test
    void shouldSearchByNameIgnoringCase() {
        productRepository.save(Product.builder().name("Clavier gamer").price(300).quantity(5).build());
        productRepository.save(Product.builder().name("Souris").price(100).quantity(8).build());

        var result = productRepository.findByNameContainsIgnoreCase("CLAV", PageRequest.of(0, 5));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Clavier gamer");
    }

    @Test
    void shouldDeleteProduct() {
        Product saved = productRepository.save(
                Product.builder().name("Souris").price(100).quantity(8).build());

        productRepository.deleteById(saved.getId());

        assertThat(productRepository.findById(saved.getId())).isEmpty();
    }
}