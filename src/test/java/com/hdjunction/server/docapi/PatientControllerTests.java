package com.hdjunction.server.docapi;

import com.hdjunction.server.docapi.controller.PatientController;
import com.hdjunction.server.docapi.dto.PatientDto;
import com.hdjunction.server.docapi.service.PatientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@AutoConfigureRestDocs
@WebMvcTest(PatientController.class)
public class PatientControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @DisplayName("환자를 조회한다.")
    @Test
    void findPatientById() throws Exception {
        given(patientService.getPatient(any()))
                .willReturn(new PatientDto.Info(1L,
                        1L,
                        "테스트",
                        "202401111",
                        "M",
                        "",
                        ""));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patient/1"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcRestDocumentation.document("patient",
                        Preprocessors.preprocessRequest(prettyPrint()),
                        Preprocessors.preprocessResponse(prettyPrint())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
