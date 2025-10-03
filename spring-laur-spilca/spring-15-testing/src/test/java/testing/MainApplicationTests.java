package testing;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import testing.repositories.ProductRepository;
import testing.services.ProductService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;


@SpringBootTest
class MainApplicationTests {

    // we are not using the actual repository. Instead, we are using a mock
    @MockBean
    private ProductRepository productRepositoryMock;

    // we are testing ProductService methods
    @Autowired
    private ProductService productService;


    @BeforeAll
    public static void toRunBeforeTesting() {
        System.out.println("BeforeAll, AfterAll or BeforeClass, AfterClass must be static");
        System.out.println(":|");
    }

    @BeforeEach
    public void toRunBeforeEveryTest() {
        System.out.println(":)");
    }

    @AfterAll
    public static void toRunAfterTesting() {
        System.out.println("> :|");
    }

    @AfterEach
    public void toRunAfterEveryTest() {
        System.out.println("> :)");
    }



    // testing for an empty database retrieval (using Mockito)
    @Test
    public void noProductsReturnedTest() {
        given(productRepositoryMock.getProductNames()).willReturn(Collections.emptyList());
        List<String> res = productService.getProductNamesWithEvenNoOfChar();

        assertTrue(res.isEmpty());
    }

    // testing for correct logic (counting strings with even number of characters)
    @Test
    public void  moreProductsAreFoundTest() {
        List<String> input = Arrays.asList("AA", "BBB", "CCCC");
        given(productRepositoryMock.getProductNames()).willReturn(input);

        List<String> res = productService.getProductNamesWithEvenNoOfChar();

        assertEquals(2, res.size());
//        verify(productRepositoryMock, times(2)).addProduct(any());
    }

    @Test
    public void moreProductsAreFoundTest2() {
        List<String> input = Arrays.asList("AA", "BB", "C", "D", "EEEE", "FFF", "GGGGG");
        given(productRepositoryMock.getProductNames()).willReturn(input);

        List<String> result = productService.getProductNamesWithEvenNoOfCharRefactored();
        assertEquals(3, result.size());
    }

}
