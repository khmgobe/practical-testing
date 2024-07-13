package sample.cafekiosk.spring.docs.product;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sample.cafekiosk.api.controller.product.ProductController;
import sample.cafekiosk.api.service.product.ProductService;
import sample.cafekiosk.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.docs.RestDocsSupport;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static sample.cafekiosk.spring.domain.product.SellingType.SELLING;

class ProductControllerDocsTest extends RestDocsSupport {

    private final ProductService productService = mock(ProductService.class);

    @Override
    protected Object initController() {
        return new ProductController(productService);
    }
    
    
    @Test
    void 신규_상품_등록_API() throws Exception {

        //given
        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
                .productType(HANDMADE)
                .sellingType(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        given(productService.createProduct(any(ProductCreateServiceRequest.class)))
                .willReturn(ProductResponse.builder()
                        .id(1L)
                        .productNumber("001")
                        .productType(HANDMADE)
                        .sellingType(SELLING)
                        .name("americano")
                        .price(4000)
                        .build());

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("product-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("productType").type(JsonFieldType.STRING).description("상품타입"),
                                fieldWithPath("sellingType").type(JsonFieldType.STRING).optional().description("판매타입"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("상품이름"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품가격")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("data.productNumber").type(JsonFieldType.STRING).description("상품번호"),
                                fieldWithPath("data.productType").type(JsonFieldType.STRING).description("상품상태"),
                                fieldWithPath("data.sellingType").type(JsonFieldType.STRING).description("판매상태"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("가격")

                        )
                    ));
    }
}
