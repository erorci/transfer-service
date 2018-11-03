package io.emersonorsi.transferservice.mapping;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
@WebMvcTest(AccountController.class)
@AutoConfigureRestDocs("target/generated-snippets")
public class AccountControllerTests {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void postAccount() throws Exception {
        String requestPayload = "{\"accountOwner\":\"Bessie Mcdonnell\",\"initialBalance\":1000.0}";
        Account account = Account.createFor("Bessie Mcdonnell")
                .setAccountBalance(AccountBalance.of(1000.0));

        given(account.saveTo(accountRepository::save)).willReturn(account);

        mvc.perform(post("/api/account")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestPayload))
                .andExpect(status().isOk())
                .andDo(document("mapping-post-account",
                        requestFields(
                                fieldWithPath("accountOwner")
                                        .description("The Name of the account's Owner"),
                                fieldWithPath("initialBalance")
                                        .description("The initial account balance"))));
    }

    @Test
    public void getAllAccounts() throws Exception {
        List<Account> accounts = new ArrayList<>();
        accounts.add(Account.createFor("Bessie Mcdonnell")
                .setAccountBalance(AccountBalance.of(1000.0)));

        given(accountRepository.findAll()).willReturn(accounts);

        mvc.perform(get("/api/account")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].accountId").doesNotExist())
                .andExpect(jsonPath("$[0].accountOwner", is("Bessie Mcdonnell")))
                .andExpect(jsonPath("$[0].currentCurrency", is("Euro")))
                .andExpect(jsonPath("$[0].accountBalance.value", is("1000.0")))
                .andDo(document("mapping-get-accounts",
                        preprocessRequest(removeHeaders("Host")),
                        preprocessResponse(prettyPrint(), removeHeaders("Content-Length")),
                        responseFields(
                                fieldWithPath("[].accountId")
                                        .description("`Account` ID"),
                                fieldWithPath("[].accountOwner")
                                        .description("`Account` owner name"),
                                fieldWithPath("[].currentCurrency")
                                        .description("The currency set for this account"),
                                fieldWithPath("[].accountBalance.value")
                                        .description("The current account balance"))));
    }
}
