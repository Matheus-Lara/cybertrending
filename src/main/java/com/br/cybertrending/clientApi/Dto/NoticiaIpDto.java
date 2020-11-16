package com.br.cybertrending.clientApi.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class NoticiaIpDto {
    private long id;
    private long idNoticia;
    private String userIp;
    private String voteType;
}
