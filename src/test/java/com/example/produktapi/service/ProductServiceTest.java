package com.example.produktapi.service;

import com.example.produktapi.exception.BadRequestException;
import com.example.produktapi.exception.EntityNotFoundException;
import com.example.produktapi.model.Product;
import com.example.produktapi.repository.ProductRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.*;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // A library with functionality for Mock, setting up the environment for mocking
class ProductServiceTest {

    //The tests are NOT run against the database, BECAUSE OF MOCKING

    @Mock //@Mock makes a copy of the repository, so we can just test what we want, handles what is to be mocked
    private ProductRepository repository;


    // @InjectMocks makes an instance of ProductService (constructor),
    // and makes everything hang together with Mock
    @InjectMocks
    private ProductService underTest;

    @Captor//Store capture arguments values for further assertions
    ArgumentCaptor<Product> productsCaptor;

    @BeforeAll
    public static void beforeTests(){
        System.out.println("\nRunning tests...\n¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
    }


    @DisplayName("Get all products")
    @Test
    public void whenGetAllProducts_theExactlyOneInteractionWithRepositoryMethodFindAll() {

        //when, method invocation
        underTest.getAllProducts();

        //then, what we expect
        //checks if the method is called (we can reach it) in this case one time
        Assertions.assertAll(
                ()-> BDDMockito.verify(repository,times(1)).findAll(),
                ()-> verifyNoMoreInteractions(repository)
        );
    }

    @DisplayName("Get all categories")
    @Test
    public void whenGetAllCategories_thenExactlyOneInteractionWithRepositoryMethodsGetByCategory() {

        //when, method invocation
        underTest.getAllCategories();

        //then, what we expect
        //checks that we are able to get the method only one time and no more
        Assertions.assertAll(
                ()-> verify(repository,times(1)).findAllCategories(),
                ()-> verifyNoMoreInteractions(repository)
        );

    }

    @DisplayName("Get products by category")
    @Test
    public void givenAnExistingCategory_whenGetProductsByCategory_thenReceivesANonEmptyList() {

        //given, setup for test
        String category = "Electronic";
        Product product = new Product("Usb", 150.0,category, "Good for computers","urlForUsbImage");
        given(repository.findByCategory(category)).willReturn(List.of(product));


        //when, method invocation
        List <Product> categoryResult = underTest.getProductsByCategory(category);

        //then, what we expect
        Assertions.assertAll(
                ()-> verify(repository,times(1)).findByCategory(category),
                ()-> verifyNoMoreInteractions(repository),
                ()-> assertFalse(categoryResult.isEmpty())
        );

    }

    //Normal flow
    @DisplayName("Add Product")
    @Test
    public void givenProductTitle_whenAddingAProduct_thenSaveMethodShouldBeCalled(){

        //given, setup for test
        String title = "Shirt";
        Product product = new Product(title,4000.0,"Clothes","Casual","urlForShirtImage");

        //when, this decides witch product is saved in productCaptor
        underTest.addProduct(product);


        //then, what we expect
        //checks that this product is the one we saved
        //Check the product we sent in is the same as the one
        //was passed in when our mocked repository was called.
        Assertions.assertAll(
                ()-> verify(repository).save(productsCaptor.capture()),
                ()-> assertEquals(product,productsCaptor.getValue())
        );
    }

    //Wrong flow
    @DisplayName("Add product with duplicated title")
    @Test
    public void givenProductTitle_whenAddingAProductWithDuplicatedTitle_thenThrowError(){

        //given, setup for test
        String title = "Shirt";
        Product product = new Product(title,34.0,"Clothes","Casual","urlForShirtImage");
        given(repository.findByTitle(title)).willReturn(Optional.of(product));


        //then, what we expect
        //the title already exists, if trying to add another product with same title, throw exception
       BadRequestException exception = assertThrows(BadRequestException.class,
                //when, method invocation
                ()-> underTest.addProduct(product));

        //then, what we expect
        Assertions.assertAll(
                ()-> verify(repository,times(1)).findByTitle(title),
                ()-> verify(repository,never()).save(any()), // If exception occurs don't save anything!
                ()-> assertEquals("En produkt med titeln: Shirt finns redan",exception.getMessage())
        );


    }

    //Normal flow
    @DisplayName("Get existing product id")
    @Test
    public void givenProductId_whenExistingProductId_thenThatAlreadyExistsInDatabaseCheckOneInteractionIsMade() {

        //given, setup for test
        Integer id = 2;
        Product product = new Product("Dress",34.0,"Clothes","Casual","urlForDressImage");
        given(repository.findById(id)).willReturn(Optional.of(product));


        //when, method invocation
         Product productGet = underTest.getProductById(id);

         productGet.setId(id);

        //then, what we expect
        //checks that findById exists and is called one time
        verify(repository,times(1)).findById(id);
        assertTrue(repository.findById(id).isPresent());

    }

    //Wrong flow, any instead of id
    @DisplayName("Get non existing product")
    @Test
    public void givenProductId_whenGetNonExistingProductId_thenThrowException(){

        //given, setup for test
        Product product = new Product("Dress",34.0,"Clothes","Casual","urlForDressImage");
        given(repository.findById(any())).willReturn(Optional.empty());

        //then, what we expect
        //Trying to get a product id that not exists and then gives exception
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                //when, method invocation
                ()-> underTest.getProductById(any()));

        //then, what we expect
        Assertions.assertAll(
                ()-> verify(repository,times(1)).findById(any()),
                ()-> assertEquals("Produkt med id null hittades inte",exception.getMessage())
        );
    }


    //Normal flow
    @DisplayName("Update Product")
    @Test
    public void givenProductId_whenUpdateProduct_thenSaveProductAsUpdated() {

        //given, setup for test
        Integer id = 8;
        Product productOld = new Product("Necklace", 1800.0,"Jewelry", "Bling bling","urlForNecklaceImage");
        Product productNew = new Product("Rings", 1600.0,"Jewelry", "Bling bling","urlForRingsImage");
        given(repository.findById(id)).willReturn(Optional.of(productOld));

        //repository saves the old product and when called then it should return the old product
        when(repository.save(productOld)).thenReturn(productOld);

        //when, method invocation
        productNew.setTitle("Necklace");
        Product updatedResult = underTest.updateProduct(productNew,id);


        //then //then, what we expect
        Assertions.assertAll(
                ()-> verify(repository,times(1)).findById(id),
                ()-> assertEquals(productNew.getTitle(), updatedResult.getTitle())
        );

    }

    //Wrong flow
    @DisplayName("Update a product that is not found")
    @Test
    public void givenProductId_whenUpdatedProductNotFound_thenThrowException() {

        //given, setup for test
        Product product = new Product("Necklace", 1800.0,"Jewelry", "Bling bling","urlForNecklaceImage");
        given(repository.findById(any())).willReturn(Optional.empty());

        //then, what we expect
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                //when, method invocation
                ()-> underTest.updateProduct(product,any()));

        //then, what we expect
        Assertions.assertAll(
                ()-> verify(repository,times(1)).findById(any()),
                ()-> assertEquals("Produkt med id null hittades inte",exception.getMessage())
        );


    }

    //Normal flow
    @DisplayName("Delete product")
    @Test
    public void givenProductId_whenDeletingProductId_thenItShouldBeDeleted() {

        //given, setup for test
        Integer id = 3;
        Product product = new Product("Necklace", 1800.0,"Jewelry", "Bling bling","urlForNecklaceImage");
        given(repository.findById(id)).willReturn(Optional.of(product));


        //when, method invocation
        underTest.deleteProduct(id);


        //then, what we expect
        Assertions.assertAll(
                ()-> verify(repository,times(1)).deleteById(id),
                ()-> assertEquals(product.getId(),null)
        );


    }

    //Wrong flow
    @DisplayName("Delete product that does not exist")
    @Test
    public void givenProductId_whenDeletingProduct_thenIfNotExistingThrowException() {

        //given, setup for test
        Integer id = 3;
        Product product = new Product("Ring", 1200.0,"Jewelry", "Bling bling","urlForRingImage");
        given(repository.findById(id)).willReturn(Optional.empty());


        //repository.delete(product);

        //then, what we expect
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                //when, method invocation
                ()-> underTest.deleteProduct(id));

        //then, what we expect
        Assertions.assertAll(
                ()-> verify(repository,times(1)).findById(id),
                ()-> assertEquals("Produkt med id 3 hittades inte",exception.getMessage())
        );
    }

    @AfterAll
    public static void afterTests(){
        System.out.println("\nAll tests was successful!\n¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
    }

}