package testing;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getProductTest() throws Exception {

        mockMvc.perform(get("/product/{name}/{price}", "Beer", 10))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Beer"))
                .andExpect(jsonPath("$.price").value(10));
    }
}
