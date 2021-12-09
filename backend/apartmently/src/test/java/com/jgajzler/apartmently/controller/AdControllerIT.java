package com.jgajzler.apartmently.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgajzler.apartmently.dto.AdDetailsDto;
import com.jgajzler.apartmently.entity.enums.AdType;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
public class AdControllerIT {
    public static final String URI = "/api/ad";

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @WithMockUser()
    void getAdDetails_returnAdDetailsDto() throws Exception {

        AdDetailsDto adDetailsDto = getAdDetail();
        mvc
                .perform(get(URI + "/details/" + adDetailsDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(adDetailsDto)));
    }

    @Test
    @WithMockUser
    void getAdTypes_returnAdType() throws Exception {
        mvc
                .perform(get(URI + "/ad-types"))
                .andExpect(content().json(mapper.writeValueAsString(AdType.values())));
    }

    AdDetailsDto getAdDetail() {
        return new AdDetailsDto(
                14L,
                0,
                1L,
                "Agnieszka",
                "165789234",
                "test@mail.com",
                "https://firebasestorage.googleapis.com/v0/b/apartmently-cbddd.appspot.com/o/user%2F1%2F22db43f9-731a-4da8-b5fe-b6ba83e05890?alt=media&token=252d094e-54b7-4185-b7eb-66a6e3902420",
                "Cozy House ner forest for sale.",
                20.00200498113162,
                50.17103015
        );
    }

}
//        JSONAssert
//                .assertEquals(mapper.writeValueAsString(adDetailsDto),
//                result.getResponse().getContentAsString(),
//                true);
