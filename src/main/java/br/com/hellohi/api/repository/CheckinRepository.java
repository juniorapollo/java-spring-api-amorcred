/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.repository;

import br.com.hellohi.api.models.Checkin;
import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Representante;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author junior
 */
public interface CheckinRepository extends JpaRepository<Checkin, Long> {

    public Checkin findByIdCheckin(Long idCheckin);
    
    public Checkin findBydataHoraCheckin(LocalDateTime dataHoraCheckin);

    public  Iterable<Checkin> findByRepresentante(Representante representante);

    public Iterable<Checkin> findByEmpresa(Empresa empresa);

    
    public List<Checkin> findByRepresentanteOrderByDataHoraCheckinDesc(Representante representante);
    
    public List<Checkin> findByRepresentanteAndDataFormatadaBetween (Representante representante,String dataInicial, String dataFinal);
    
    public List<Checkin> findByRepresentanteAndDataHoraCheckinBetween (Representante representante, LocalDateTime dataInicial, LocalDateTime dataFinal);
    
     public List<Checkin> findByDataFormatadaBetween(String dataInicial, String dataFinal);

     
     
}
