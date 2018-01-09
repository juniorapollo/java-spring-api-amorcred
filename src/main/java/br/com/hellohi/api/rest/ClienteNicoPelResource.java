///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package br.com.hellohi.api.rest;
//
//import br.com.hellohi.api.models.ClienteNicoPel;
//import br.com.hellohi.api.repository.ClienteNicoPelRepository;
//import javax.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//
//
///**
// *
// * @author junior
// */
//@RestController
//public class ClienteNicoPelResource {
// 
//    @Autowired
//    ClienteNicoPelRepository cra;   
//    ClienteNicoPel cliente;
//
//   
//    
//    @RequestMapping(path = "/nicopel/clientes", method = RequestMethod.GET)
//    public Iterable<ClienteNicoPel> listaCliente() {
//        Iterable<ClienteNicoPel> listaCliente = cra.findAll();
//        return listaCliente;
//    }
//
//    @RequestMapping(path = "/nicopel/clientes/{idCliente}", method = RequestMethod.GET)
//    public ClienteNicoPel clienteFindById(@PathVariable("idCliente") Long idCliente) {
//        cliente = cra.findByIdClienteNicoPel(idCliente);
//        return cliente;
//    }
//
////    //Deletar Cliente
////    @RequestMapping(path = "/nicopel/clientes/{idCliente}", method = RequestMethod.DELETE)
////    public String deletaPorId(@PathVariable("idCliente") Long idCliente) {
////        cr.delete(clienteFindById(idCliente));
////        return "Pagina HTML para mostrar que foi deletado";
////    }
//    
////    SalvaCliente
//    @RequestMapping(path = "/nicopel/clientes", method = RequestMethod.POST)
//    public ClienteNicoPel salvaCliente(@RequestBody @Valid ClienteNicoPel cliente) {
//     return cra.save(cliente);
//    }
//    
//}