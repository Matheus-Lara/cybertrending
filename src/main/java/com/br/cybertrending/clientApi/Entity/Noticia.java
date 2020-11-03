package com.br.cybertrending.clientApi.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.br.cybertrending.clientApi.Dto.NoticiaDto;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Data
@Entity
@Table(name = "noticia")
@NoArgsConstructor
@AllArgsConstructor

public class Noticia implements Serializable {

    private static final long serialVersionUID = -1559864615786877060L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name = "DATA_INCLUSAO")
    private java.sql.Timestamp dataInclusao;

    @Column(name = "DATA_FINAL")
    private java.sql.Timestamp dataFinal;

    @Column(name = "TAG_TWITTER")
    private String tagTwitter;

    @Column(name = "URL_TAG_TWITTER")
    private String urlTagTwitter;

    @Column(name = "URL_NOTICIA")
    private String urlNoticia;

    @Column(name = "TITULO_NOTICIA")
    private String tituloNoticia;

    @Column(name = "CONTEUDO_NOTICIA", columnDefinition="TEXT")
    private String conteudoNoticia;

    @Column(name = "UP_QTD")
    private long upQtd;

    @Column(name = "DOWN_QTD")
    private long downQtd;

    @Transient
    public NoticiaDto noticiaDto(){
        return new NoticiaDto(
            id,
            dataInclusao,
            dataFinal,
            tagTwitter,
            urlTagTwitter,
            urlNoticia,
            tituloNoticia,
            conteudoNoticia,
            upQtd,
            downQtd
        );
    }
}
