/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.repository;

import br.com.hellohi.api.models.Checkout;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author junior
 */
public interface CheckoutRepository extends CrudRepository<Checkout, Long> {

    public Checkout findByIdCheckout(long idCheckout);

}
