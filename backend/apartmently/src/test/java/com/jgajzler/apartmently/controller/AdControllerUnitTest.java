package com.jgajzler.apartmently.controller;

import com.jgajzler.apartmently.UnsecuredWebMvcTest;
import com.jgajzler.apartmently.dto.AdDetailsDto;
import com.jgajzler.apartmently.dto.AdDto;
import com.jgajzler.apartmently.entity.Ad;
import com.jgajzler.apartmently.entity.enums.AdType;
import com.jgajzler.apartmently.service.AdService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@UnsecuredWebMvcTest(AdController.class)
public class AdControllerUnitTest {
    public static final String URI = "/api/ad";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdService adService;

    @Test
    void getAll_returnPages() throws Exception {
        //given
        Page<AdDto> page = new PageImpl<>(Collections.singletonList(getExampleAddDto()));
        when(adService.getAll(ArgumentMatchers.any())).thenReturn(page);


        //when
        //then
        mvc.perform(get(URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(page.getContent().get(0).getId()))
                .andExpect(jsonPath("$.content[0].adName").value(page.getContent().get(0).getAdName()))
                .andExpect(jsonPath("$.content[0].plotSurface").value(page.getContent().get(0).getPlotSurface()))
                .andExpect(jsonPath("$.content[0].price").value(page.getContent().get(0).getPrice()))
                .andExpect(jsonPath("$.content[0].active").value(page.getContent().get(0).isActive()));


        verify(adService).getAll(ArgumentMatchers.any());
    }

    @Test
    void getAdDetails_returnAdDetailsDto() throws Exception {
        when(adService.getAdDetailsById(getExampleAdDetailsDto().getId())).thenReturn(getExampleAdDetailsDto());

        mvc.perform(get(String.format("%s/details/%s",URI,getExampleAdDetailsDto().getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(getExampleAdDetailsDto().getId()))
                .andExpect(jsonPath("$.floor").value(getExampleAdDetailsDto().getFloor()));

    }

//    @Test
//    void create_returnResponseEntity() throws Exception{
//        long createdId = 2;
//        when(adService.add(getExampleAd())).thenReturn(new ResponseEntity<Map<String,Long>>(ResponseEntity.created()))
//
//    }

    private AdDto getExampleAddDto() {
        return new AdDto(
                1L,
                "a",
                55,
                123,
                2,
                1,
                new Date(),
                new Date(),
                true,
                "city",
                "country",
                Collections.emptySet(),
                AdType.RENT
        );
    }

    private AdDetailsDto getExampleAdDetailsDto(){
        return new AdDetailsDto(
                1L,
                5,
                1L,
                "username",
                "111222333",
                "email",
                "image",
                "desc",
                55.8,
                66.9
        );
    }

    private Ad getExampleAd(){
        return new Ad();
    }
}
