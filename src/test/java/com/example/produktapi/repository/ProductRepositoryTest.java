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

    //Instance to be able to pass information

    // @Autowired helps us access repositories, like a small constructor,
    // we save code by this instead of writing the whole instantiation
    // Make an instance if Product repository
    @Autowired
    private ProductRepository underTest;


    @BeforeAll
    public static void beforeTests(){
        System.out.println("\nRunning tests...\n¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
    }


    @DisplayName("Find all from repository")
    @Test //Find all from repository
    public void testingOurRepository_byFindAll() {

        List<Product> products = underTest.findAll();
        assertFalse(products.isEmpty());
    }

    @DisplayName("Get existing category returned")
    @Test //Find by category 1
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
                ()->assertEquals(product.getCategory(),"Electronic")
        );
    }

    @DisplayName("List empty if category not existing")
    @Test //Find by category 2
    public void whenSearchingForAnNonExistingCategory_thenReturnEmptyList() {

        //when
        List <Product> categoryExists = underTest.findByCategory("Electronic");
        underTest.deleteAll();

        //then
        assertTrue(categoryExists.isEmpty());

    }

    @DisplayName("Get existing title returned ")
    @Test // Find by title 1
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
            ()-> assertEquals(optionalProduct.get().getTitle(), title),
            ()-> assertNotNull(optionalProduct)
        );

    }

    @DisplayName("Optional empty if title not existing")
    @Test //Find by title 2
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
                ()-> assertThrows(NoSuchElementException.class, ()-> optionalProduct.get())//
        );

    }

    @DisplayName("Get correct category size returned")
    @Test //Find by all categories 1
    public void whenSearchingForAllCategories_thenReturnTheCorrectCategorySize() {

        //when
        List<String> categoryAll = underTest.findAllCategories();

        //then
        Assertions.assertAll(
                ()-> assertFalse(categoryAll.isEmpty()),
                ()-> assertEquals(categoryAll.size(),4)
        );

    }

    @DisplayName("All categories check for no duplicates")
    @Test //Find all categories 2
    public void whenSearchingForAllCategories_thenMakeSureThereAreNoDuplicates() {

        //Return a stream of distinct element to the list categoryAll

        //when
        List<String> categoryAll = underTest.findAllCategories()
                .stream()
                .distinct()
                .collect(Collectors.toList());


        //then
        Assertions.assertAll(
                ()->assertEquals(categoryAll.size(),4),
                ()->assertFalse(categoryAll.size() > 4),
                ()->assertTrue(categoryAll.size() == 4)
        );

    }

    @AfterAll
    public static void afterTests(){
        System.out.println("\nAll tests was successful!\n¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
    }

}