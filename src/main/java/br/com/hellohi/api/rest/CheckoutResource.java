/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.models.Checkin;
import br.com.hellohi.api.models.Checkout;
import br.com.hellohi.api.repository.CheckoutRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author junior
 */
@RestController
public class CheckoutResource {

    @Autowired
    CheckoutRepository checkoutR;
    Checkout checkout;

    @Autowired
    CheckinResource checkinR;
    Checkin checkin;

    // Lista de Todos os Checkouts
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @RequestMapping(path = "/hellohi/api/checkout", method = RequestMethod.GET)

    public Iterable<Checkout> listaCheckout() {

        return checkoutR.findAll();

    }

    //Listar Checkouts relacionado ao Determinado Checkin
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @RequestMapping(path = "/hellohi/api/checkout/checkin/{idCheckin}", method = RequestMethod.GET)
    public Checkout pegarCheckoutPorCheckin(@PathVariable("idCheckin") Long idCheckin) {
        checkin = checkinR.pegarCheckinPorId(idCheckin);
        try {
            for (Checkout c : listaCheckout()) {
                if (c.getCheckin() == checkin) {
                    break;
                }
                return c;
            }

        } catch (Exception e) {
            System.out.println("CATCH " + e);
        }
        return null;
    }

    //Salvar Checkout referente a agenda da Prospeccao
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @RequestMapping(path = "/hellohi/api/checkout/checkin{idCheckin}", method = RequestMethod.POST)
    public Checkout salvarCheckinProspeccao(@RequestBody @Valid Checkout checkout, @PathVariable("idCheckin") Long idCheckin) {

        checkout.setCheckin(checkinR.pegarCheckinPorId(idCheckin));
        checkout.setDataHoraCheckout(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        checkout.setIdCheckout(null);

        return checkoutR.save(checkout);
    }

}
