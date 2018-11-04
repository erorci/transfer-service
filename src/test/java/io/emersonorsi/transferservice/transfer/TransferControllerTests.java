package io.emersonorsi.transferservice.transfer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(TransferController.class)
@AutoConfigureRestDocs("target/generated-snippets")
public class TransferControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransferService transferService;

    @Test
    public void postTransfer() throws Exception {
        String requestPayload = "{\"accountTo\":\"B5bddf2cf92712755a361b0b7\",\"amount\":1000.0}";

        Transaction t = Transaction
                .buildWith("B5bddf2cf92712755a361b0b0", "B5bddf2cf92712755a361b0b7", 1000.0);

        given(transferService.transferAmount(any(String.class), any(CreateTransferRequest.class))).willReturn(t);

        mvc.perform(post("/api/account/{id}/transfer","B5bddf2cf92712755a361b0b0")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestPayload))
                .andExpect(status().isCreated())
                .andDo(document("transfer-post-account-transfer",
                        preprocessRequest(removeHeaders("Host")),
                        preprocessResponse(prettyPrint(), removeHeaders("Content-Length")),
                        pathParameters(
                                parameterWithName("id")
                                        .description("The `Account` ID (required)")),
                        responseHeaders(headerWithName("Location")
                                .description("The URI Location of the created Transaction")),
                        requestFields(
                                fieldWithPath("accountTo")
                                        .description("Account where the money will be transfered"),
                                fieldWithPath("amount")
                                        .description("Value of the amount of money to transfer"))));
    }
}
