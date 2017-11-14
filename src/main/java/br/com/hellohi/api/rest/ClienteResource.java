/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.models.Cliente;
import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.repository.ClienteRepository;
import br.com.hellohi.api.repository.RepresentanteRepository;
import com.sun.java.swing.plaf.windows.resources.windows;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.util.calendar.BaseCalendar.Date;

/**
 *
 * @author junior
 */
@RestController
public class ClienteResource {

    @Autowired
    ClienteRepository cr;
    Cliente cliente;

    @Autowired
    RepresentanteResource rr;
    Representante representante;

    @Autowired
    EmpresaResource er;
    Empresa empresa;

    @RequestMapping(path = "/hellohi/api/cliente", method = RequestMethod.GET)
    public Iterable<Cliente> listaCliente() {
        Iterable<Cliente> listaCliente = cr.findAll();
        return listaCliente;
    }

    @RequestMapping(path = "/hellohi/api/cliente/{idCliente}", method = RequestMethod.GET)
    public Cliente clientePorId(@PathVariable("idCliente") Long idCliente) {
        cliente = cr.findByIdCliente(idCliente);
        return cliente;
    }

    //Deletar Cliente
    @RequestMapping(path = "/hellohi/api/cliente/{idCliente}", method = RequestMethod.DELETE)
    public String deletaPorId(@PathVariable("idCliente") Long idCliente) {
        cr.delete(clientePorId(idCliente));
        return "Pagina HTML para mostrar que foi deletado";
    }

    //Salva Cliente
    @RequestMapping(path = "/hellohi/api/representante/cliente/{idRepresentante}", method = RequestMethod.POST)
    public Cliente salvarCliente(@RequestBody @Valid Cliente cliente, @PathVariable("idRepresentante") Long idRepresentante) {
        representante = rr.representantePorId(idRepresentante);
        cliente.setRepresentante(representante);
        cliente.setIdCliente(null);
        cliente = cr.save(cliente);
        return cliente;
    }

    //Edita Cliente
    @RequestMapping(path = "/hellohi/api/empresa/representante/cliente/{idCliente}", method = RequestMethod.PUT)
    public Cliente editarCliente(@RequestBody @Valid Cliente cliente, @PathVariable("idCliente") Long idCliente) {
        this.cliente = clientePorId(idCliente);
        representante = rr.representantePorId(this.cliente.getRepresentante().getIdRepresentante());
        cliente.setRepresentante(representante);
        cliente.setIdCliente(idCliente);
        cliente = cr.save(cliente);
        return cliente;
    }

    //Listar Clientes dos respectivos Representantes Comerciais{idRepresentante}  e avisar se um determinado Representante nao tem Cliente Cadastrado
    @RequestMapping(path = "/hellohi/api/representante/cliente/{idRepresentante}", method = RequestMethod.GET)
    public ArrayList<Cliente> listarClientePorRepresentante(@PathVariable("idRepresentante") Long idRepresentante) {
        representante = rr.representantePorId(idRepresentante);
        ArrayList<Cliente> novaListaCliente = new ArrayList<>();
        try {
            for (Cliente c : listaCliente()) {
                if (c.getRepresentante() == representante) {
                    novaListaCliente.add(c);
                }
            }
            if (novaListaCliente.isEmpty()) {
                System.out.println("REPRESENTANTE SEM CADASTRO DE CLIENTE");
            }
            return novaListaCliente;
        } catch (Exception e) {
            return novaListaCliente;
        }
    }

    //Listar Clientes dos respectivas Empresa {idEmpresa}
    @RequestMapping(path = "/hellohi/api/empresa/cliente/{idEmpresa}", method = RequestMethod.GET)
    public ArrayList<Cliente> listarClientePorEmpresa(@PathVariable("idEmpresa") Long idEmpresa) {
        ArrayList<Cliente> novaListaCliente = new ArrayList<>();
        empresa = er.buscarEmpresaId(idEmpresa);
        try {
            for (Cliente c : listaCliente()) {
                if (c.getRepresentante().getEmpresa() == empresa) {
                    novaListaCliente.add(c);
                }
            }
            return novaListaCliente;
        } catch (Exception e) {
            return novaListaCliente;
        }

    }

}
