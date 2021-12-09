package com.jgajzler.apartmently.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgajzler.apartmently.dto.AdDetailsDto;
import com.jgajzler.apartmently.dto.AdDto;
import com.jgajzler.apartmently.entity.Ad;
import com.jgajzler.apartmently.entity.Address;
import com.jgajzler.apartmently.entity.User;
import com.jgajzler.apartmently.entity.enums.AdType;
import com.jgajzler.apartmently.service.AdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(AdController.class)
public class AdControllerUnitTest {

    @Mock
    private AdService adService;

    @InjectMocks
    private AdController adController;

    @Test
    public void getAll_returnPages() {
        //given
        Page<AdDto> page = new PageImpl<>(Collections.singletonList(getExampleAddDto()));
        ResponseEntity<Page<AdDto>> expectedResponseEntity = ResponseEntity.ok().body(page);
        when(adService.getAll(ArgumentMatchers.any())).thenReturn(page);
        //when
        ResponseEntity<Page<AdDto>> actualResponseEntity = adController.getAll(ArgumentMatchers.any());
        //then
        assertThat(actualResponseEntity.getBody()).isSameAs(expectedResponseEntity.getBody());
    }

    @Test
    public void getAdDetails_returnAdDetailsDto() {
        //given
        AdDetailsDto adDetailsDto = getExampleAdDetailsDto();
        ResponseEntity<AdDetailsDto> expectedResponseEntity = ResponseEntity.ok().body(adDetailsDto);
        when(adService.getAdDetailsById(adDetailsDto.getId())).thenReturn(adDetailsDto);
        //when
        ResponseEntity<AdDetailsDto> actualResponseEntity = adController.getAdDetailsById(adDetailsDto.getId());
        //then
        assertThat(actualResponseEntity.getBody()).isSameAs(expectedResponseEntity.getBody());
    }

//    @Test
//    void getAdDetails_returnAdDetailsDto() throws Exception {
//        when(adService.getAdDetailsById(getExampleAdDetailsDto().getId())).thenReturn(getExampleAdDetailsDto());
//
//        mvc.perform(get(String.format("%s/details/%s", URI, getExampleAdDetailsDto().getId())))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(getExampleAdDetailsDto().getId()))
//                .andExpect(jsonPath("$.floor").value(getExampleAdDetailsDto().getFloor()));
//
//    }
//
//    @Test
//    void getAdTypes_returnAdType() throws Exception{
//        mvc.perform(get(URI+"/ad-types"))
//                .andExpect(content().json("[SALE, RENT]"));
//
//    }
//
//    @Test
//    void create_returnCreated() throws Exception {
//        Long createdId = 1L;
//        Ad ad = getExampleAd();
////        when(adService.add(getExampleAd())).thenReturn(new ResponseEntity<Map<String,Long>>(ResponseEntity.created()));
//        when(adService.add(ad)).thenReturn(createdId);
//        ad.setId(createdId);
//
//        mvc.perform(post(URI)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(ad)))
//                .andExpect(status().isCreated());
//
////        MvcResult result =  mvc.perform(post(URI)
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(asJsonString(ad))).andReturn();
////
////        MockHttpServletResponse response = result.getResponse();
//
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

    private AdDetailsDto getExampleAdDetailsDto() {
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

    private Ad getExampleAd() {
        Ad ad = new Ad();
        ad.setAdName("name");
        ad.setDescription("desc");
        ad.setPlotSurface(1);
        ad.setPrice(1);
        ad.setNumberOfBedrooms(1);
        ad.setNumberOfBathrooms(1);
        ad.setFloorNumber(1);
        ad.setDateCreated(new Date());
        ad.setLastUpdated(new Date());
        ad.setAddress(new Address());
        ad.setUser(new User());
        ad.setAdType(AdType.RENT);

        return ad;
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
