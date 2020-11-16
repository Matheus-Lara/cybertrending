package com.br.cybertrending.clientApi.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.br.cybertrending.clientApi.Dto.NoticiaIpDto;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Data
@Entity
@Table(name = "noticiaip")
@NoArgsConstructor
@AllArgsConstructor

public class NoticiaIp {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name="ID_NOTICIA")
    private Integer idNoticia;

    @Column(name="USER_IP")
    private String userIp;

    @Column(name="VOTE_TYPE")
    private String voteType;

    @Transient
    public NoticiaIpDto noticiaIpDto(){
        return new NoticiaIpDto(
                id,
                idNoticia,
                userIp,
                voteType
        );
    }
}
