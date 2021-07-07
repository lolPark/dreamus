package com.dreamus.lolpark.purchase.controller;

import com.dreamus.lolpark.purchase.config.MvcConfig;
import com.dreamus.lolpark.purchase.domain.Product;
import com.dreamus.lolpark.purchase.dto.ProductCreateDto;
import com.dreamus.lolpark.purchase.dto.ProductUpdateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("dev")
@SpringBootTest(args = "--app.datasource.channel.path=~/test")
@AutoConfigureMockMvc
@ContextConfiguration(classes = {MvcConfig.class})
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnPagedProduct() throws Exception {
        this.mockMvc.perform(get("/product"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnProduct() throws Exception {
        this.mockMvc.perform(get("/product/5000701"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("id").value(5000701));
    }

    @Test
    public void shouldCreatedProduct() throws Exception {

        ProductCreateDto productCreateDto = new ProductCreateDto();
        productCreateDto.setName("무제한 스트리밍");
        productCreateDto.setPrice(8700);
        productCreateDto.setIsDel(false);

        String content = objectMapper.writeValueAsString(productCreateDto);

        ResultActions resultActions = this.mockMvc.perform(post("/product")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated());

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();

        Product product = objectMapper.readValue(contentAsString, Product.class);

        assertEquals(productCreateDto.getPrice(), product.getPrice());
        assertTrue(productCreateDto.getName().equals(product.getName()));
    }

    @Test
    public void shouldUpdateProduct() throws Exception {

        ProductUpdateDto productUpdateDto = new ProductUpdateDto();
        productUpdateDto.setIsDel(false);
        productUpdateDto.setPrice(6000);

        String content = objectMapper.writeValueAsString(productUpdateDto);

        ResultActions resultActions = this.mockMvc.perform(put("/product/5000701")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();

        Product product = objectMapper.readValue(contentAsString, Product.class);

        assertEquals(productUpdateDto.getPrice(), product.getPrice());
    }
}
