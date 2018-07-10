/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.repository;

import br.com.hellohi.api.models.Checkin;
import br.com.hellohi.api.models.FotoCheckin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author junior
 */
public interface FotoCheckinRepository extends JpaRepository<FotoCheckin, Long> {

     public FotoCheckin findByCheckin(Checkin checkin);

    public FotoCheckin findByIdFotoCheckin(Long idFotoCheckin);

   
}
