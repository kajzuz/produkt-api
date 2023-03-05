package com.example.produktapi.repository;

import com.example.produktapi.exception.BadRequestException;
import com.example.produktapi.model.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //Testing our JPA repository, for unit testing our repository
class ProductRepositoryTest {

    //The tests are run against the database
    //Repository -> Connection to database


    // @Autowired helps us access repositories, like a small constructor,
    // we save code by this instead of writing the whole instantiation
    // Make an instance of Product repository
    @Autowired
    private ProductRepository underTest;


    @BeforeAll
    public static void beforeTests(){

        System.out.println("\nRunning tests...\n¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
    }


    @DisplayName("Find all from repository")
    @Test
    public void testingOurRepository_byFindAll() {

        List<Product> products = underTest.findAll();
        assertFalse(products.isEmpty());
    }

    @DisplayName("Get existing category returned")
    @Test
    public void whenSearchingForAnExistingCategory_thenReturnThatCategory() {

        //given
        String category = "Electronic";
        Product product = new Product("Usb", 150.0,category, "Good for computers","urlForUsbImage");

        //when
        underTest.save(product);
        List<Product> categoryExists = underTest.findByCategory(category);

        //then
        Assertions.assertAll(
                ()->assertFalse(categoryExists.isEmpty()),
                ()->assertTrue(categoryExists.contains(product)),
                ()->assertEquals("Electronic",product.getCategory())
        );
    }

    @DisplayName("List is empty if category not existing")
    @Test
    public void whenSearchingForAnNonExistingCategory_thenReturnEmptyList() {

        //when
        List <Product> categoryExists = underTest.findByCategory("Electronic");
        underTest.deleteAll();

        //then
        assertTrue(categoryExists.isEmpty());

    }

    @DisplayName("Get existing title returned")
    @Test
    public void whenSearchingForAnExistingTitle_thenReturnThatProduct() {

        //given
        String title = "Computer";
        Product product = new Product(title, 25000.0, "Electronic", "Good for job and school", "urlForComputerImage");

        //when
        underTest.save(product);
        Optional<Product> optionalProduct = underTest.findByTitle(title);


        //then
        Assertions.assertAll(
            ()-> assertTrue(optionalProduct.isPresent()),
            ()-> assertFalse(optionalProduct.isEmpty()),
            ()-> assertEquals(title, optionalProduct.get().getTitle()),
            ()-> assertNotNull(optionalProduct)
        );

    }

    @DisplayName("Optional empty if title not existing")
    @Test
    public void whenSearchingForNonExistingTitle_thenReturnEmptyOptional(){

        //given
        String title = "Computer";

        //when
        Optional<Product> optionalProduct = underTest.findByTitle(title);

        //assertAll helps us use multiple assertions and lambda
        //Grouped assertions of title
        Assertions.assertAll(
                ()-> assertTrue(optionalProduct.isEmpty()),
                ()-> assertFalse(optionalProduct.isPresent()),
                ()-> assertThrows(NoSuchElementException.class, ()-> optionalProduct.get())
        );

    }

    @DisplayName("Get correct category size returned for all categories")
    @Test
    public void whenSearchingForAllCategories_thenReturnTheCorrectCategorySize() {

        //when
        List<String> categoryAll = underTest.findAllCategories();

        //then
        Assertions.assertAll(
                ()-> assertFalse(categoryAll.isEmpty()),
                ()-> assertEquals(4, categoryAll.size())
        );

    }

    @DisplayName("Check that all categories has no duplicates")
    @Test
    public void whenSearchingForAllCategories_thenMakeSureThereAreNoDuplicates() {

        //Return a stream of distinct element to the list categoryAll

        //when
        List<String> categoryAll = underTest.findAllCategories()
                .stream()
                .distinct().toList();


        //then
        Assertions.assertAll(
                ()->assertEquals(4, categoryAll.size()),
                ()->assertFalse(categoryAll.size() > 4)
        );

    }

}