package com.br.cybertrending.clientApi.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class NoticiaDto {
    private long id;
    private java.sql.Timestamp dataInclusao;
    private java.sql.Timestamp dataFinal;
    private String tagTwitter;
    private String urlTagTwitter;
    private String urlNoticia;
    private String tituloNoticia;
    private String conteudoNoticia;
    private long upQtd;
    private long downQtd;
}