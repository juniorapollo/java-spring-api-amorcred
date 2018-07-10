/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.models.Checkin;
import br.com.hellohi.api.models.FotoCheckin;
import br.com.hellohi.api.repository.CheckinRepository;
import br.com.hellohi.api.repository.FotoCheckinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author junior
 */
@RestController
@RequestMapping(value = "${baseUrl}/hellohi/api/checkin/fotoCheckin")
public class FotoCheckinResource {

    @Autowired
    FotoCheckinRepository fr;
    FotoCheckin fotoCheckin;

    @Autowired
    CheckinRepository cr;
    Checkin checkin;

    /**
     *
     * @param idCheckin
     * @return
     */
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping(path = "/{idCheckin}")
    public FotoCheckin pegarCheckinPorId(@PathVariable("idCheckin") Long idCheckin) {

        checkin = cr.findOne(idCheckin);
        fotoCheckin = fr.findByCheckin(checkin);

        return fotoCheckin;
    }
}
