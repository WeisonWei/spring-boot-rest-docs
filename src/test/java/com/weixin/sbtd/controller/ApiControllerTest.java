package com.weixin.sbtd.controller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class ApiControllerTest {

    //如果没有写，默认的输出目录也是target/generated-snippets
    @Rule
    //public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void getDocs() throws Exception {
        this.mockMvc.perform(get("/docs")
                .param("name", "wei")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic dXNlcjpzZWNyZXQ="))
                .andDo(print()).andExpect(status().isOk())
                .andDo(document("docs",
                        //docs为文档的Id,在target/generated-snippets文件夹下会生成hello文件夹存放snippets片段
                        requestHeaders(
                                headerWithName("Authorization").description("Basic auth credentials")),
                        requestParameters(
                                parameterWithName("name").description("name")
                        )));
    }
}
