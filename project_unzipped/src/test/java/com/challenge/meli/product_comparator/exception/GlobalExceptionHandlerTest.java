package com.challenge.meli.product_comparator.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class GlobalExceptionHandlerTest {

    private MockMvc mvc;

    @RestController
    static class ThrowingController {
        @GetMapping("/throw-404") public void notFound() { throw new NotFoundException("Product not found: X"); }
        @GetMapping("/throw-500") public void boom() { throw new RuntimeException("Kaboom"); }
    }

    @BeforeEach
    void setUp() {
        mvc = standaloneSetup(new ThrowingController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    void notFound_isHandledAs404Json() throws Exception {
        mvc.perform(get("/throw-404"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message", containsString("Product not found")))
                .andExpect(jsonPath("$.path").value("/throw-404"));
    }

    @Test
    void generic_isHandledAs500Json() throws Exception {
        mvc.perform(get("/throw-500"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Kaboom"))
                .andExpect(jsonPath("$.path").value("/throw-500"));
    }

}