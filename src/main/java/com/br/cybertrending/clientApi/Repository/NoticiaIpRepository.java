package com.br.cybertrending.clientApi.Repository;

import com.br.cybertrending.clientApi.Entity.NoticiaIp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticiaIpRepository extends JpaRepository<NoticiaIp, Integer> {
    Optional<NoticiaIp> findOptionalByIdNoticiaAndUserIp(int idNoticia, String userIp);
    NoticiaIp findByUserIpAndIdNoticia(String userIp, int idNoticia);
}
