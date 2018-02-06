/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONObject;

import br.com.hellohi.api.controller.CheckinController;
import br.com.hellohi.api.models.AgendaManutencao;
import br.com.hellohi.api.models.AgendaProspeccao;
import br.com.hellohi.api.models.Checkin;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.repository.CheckinRepository;
import br.com.hellohi.api.repository.RepresentanteRepository;
import br.com.hellohi.api.security.UserSS;
import br.com.hellohi.api.service.UserService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author junior
 */
@RestController
public class CheckinResource {
    
    @Autowired
    CheckinRepository cr;
    Checkin checkin;
    CheckinController cc;
    
    @Autowired
    AgendaProspeccaoResource apr;
    AgendaProspeccao ap;
    
    @Autowired
    AgendaManutencaoResource amr;
    AgendaManutencao am;
    
    @Autowired
    RepresentanteRepository representanteRepository;
     
    @Autowired
    RepresentanteResource rr;
    Representante representante;

    //Retorna a Lista de Todos os checkins realizados
    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "/hellohi/api/checkin", method = RequestMethod.GET)
    public Iterable<Checkin> listaCheckin() {
        UserSS user = UserService.authenticated();
        Iterable<Checkin> listaCheckin = cr.findByEmpresa(user.getEmpresa());
        return listaCheckin;
    }

    //Retorna checkin por Id
    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "/hellohi/api/checkin/{idCheckin}", method = RequestMethod.GET)
    public Checkin pegarCheckinPorId(@PathVariable("idCheckin") Long idCheckin) {
        return cr.findByIdCheckin(idCheckin);
    }

    //Retorna todos os checkins por Representante
    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "/hellohi/api/checkin/{idRepresentante}", method = RequestMethod.GET)
    public Iterable<Checkin> checkinsPorRepresentante(@PathVariable("idRepresentante") Long idRepresentante) {
        representante = rr.representantePorId(idRepresentante);
        return cr.findByRepresentante(representante);
    }

    //Retorna a Lista de Todos os checkins por Agenda de Prospeccao
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @RequestMapping(path = "/hellohi/api/checkin/agenda/prospeccao/{idAgendaProspeccao}", method = RequestMethod.GET)
    public ArrayList<Checkin> pegarCheckinPorAgendaProspeccao(@PathVariable("idAgendaProspeccao") Long idAgendaProspeccao) {
        
        ap = apr.pegarAgendaProspeccaoPorId(idAgendaProspeccao);
        
        ArrayList<Checkin> novaListaCheckin = new ArrayList<>();
        try {
            for (Checkin c : listaCheckin()) {
                if (c.getAgendaProspeccao() == ap) {
                    novaListaCheckin.add(c);
                }
            }
            //Veirifca se a lista está vazia
            if (novaListaCheckin.isEmpty()) {
                System.out.println("Agenda Prospeccao NAO TEM Checkin (ABRIR PAGINA AVISANDO)");
            }
            return novaListaCheckin;
            
        } catch (Exception e) {
            System.out.println("CATCH " + e);
            return novaListaCheckin; // Retorna Lista Vazia
        }
        
    }

    //Retorna a Lista de Todos os checkins por Agenda de Manutencao
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @RequestMapping(path = "/hellohi/api/checkin/agenda/manutencao/{idAgendaManutencao}", method = RequestMethod.GET)
    public ArrayList<Checkin> pegarCheckinPorAgendaManutencao(@PathVariable("idAgendaManutencao") Long idAgendaManutencao) {
        
        am = amr.pegarAgendaManutencaoPorId(idAgendaManutencao);
        
        ArrayList<Checkin> novaListaCheckin = new ArrayList<>();
        try {
            for (Checkin c : listaCheckin()) {
                if (c.getAgendaManutencao() == am) {
                    novaListaCheckin.add(c);
                }
            }
            //Veirifca se a lista está vazia
            if (novaListaCheckin.isEmpty()) {
                System.out.println("Agenda Manutencao NAO TEM Checkin (ABRIR PAGINA AVISANDO)");
            }
            return novaListaCheckin;
            
        } catch (Exception e) {
            System.out.println("CATCH " + e);
            return novaListaCheckin; // Retorna Lista Vazia
        }
    }

    //Salvar Checkin referente a agenda da Manutencao
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @RequestMapping(path = "/hellohi/api/checkin/agenda/manutencao/{idAgendaManutencao}", method = RequestMethod.POST)
    public Checkin salvarCheckinManutencao(@RequestBody @Valid Checkin checkin, @PathVariable("idAgendaManutencao") Long idAgendaManutencao) {
        
        checkin.setAgendaManutencao(amr.pegarAgendaManutencaoPorId(idAgendaManutencao));
        checkin.setIdCheckin(null);
        
        return cr.save(checkin);
        
    }

    //Salvar Checkin referente a agenda da Prospeccao
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @RequestMapping(path = "/hellohi/api/checkin/agenda/prospeccao/{idAgendaProspeccao}", method = RequestMethod.POST)
    public Checkin salvarCheckinProspeccao(@RequestBody @Valid Checkin checkin, @PathVariable("idAgendaProspeccao") Long idAgendaProspeccao) {
        
        checkin.setAgendaProspeccao(apr.pegarAgendaProspeccaoPorId(idAgendaProspeccao));
        checkin.setDataHoraCheckin(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        checkin.setIdCheckin(null);
        
        return cr.save(checkin);
    }

    //Salvar Checkin 
    @RequestMapping(path = "/hellohi/api/checkin", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void salvarCheckin(@RequestBody @Valid Checkin checkin, BindingResult result, HttpServletRequest request) throws JSONException, IOException {
        
        String login = request.getHeader("login"); // recuperar o Email passado pelo Header da requisicao
        if (result.hasErrors()) {
            System.out.println("DEU ERRO");
        }
        try {
            this.representante = representanteRepository.findByLogin(login); // Recupera o Representante por email(Login)
        } catch (Exception e) {
            System.out.println(e);
        }
        
        checkin.setDataHoraCheckin(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        checkin.setRepresentante(this.representante);
        checkin.setEmpresa(this.representante.getEmpresa());
        checkin.setIdCheckin(null);
        
        try {
            checkin.setEnderecoCompleto(converterLatitudeLongitudeEmEndereco(checkin.getLatitude(), checkin.getLongitude())); //converte lat lng em endereco completo
            cr.save(checkin);
        } catch (Exception e) {
            checkin.setEnderecoCompleto("Endereço Inválido! Abrir Mapa.");            
            cr.save(checkin);
        }
        
    }

    //Editar Checkin
    @RequestMapping(path = "/hellohi/api/checkin/{idCheckin}", method = RequestMethod.PUT)
    public Checkin editarCheckin(@RequestParam Map<String, String> body, @RequestBody @Valid Checkin checkin) {
        checkin.setDataHoraCheckin(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return cr.save(checkin);
    }

    //Tranforma arquivo em String 
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    
    public String converterLatitudeLongitudeEmEndereco(double lat, double lng) throws JSONException, IOException {
        String enderecoCompleto = "";
        try (InputStream is = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=AIzaSyAojEMgcdjBL6qC-WCAiKJbW1ekoPaME_o").openStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            
            String jsonText = readAll(bufferedReader);//pegando o resultado da URL e convertendo para String
            JSONObject json = new JSONObject(jsonText); // Convertando a String para OBJETO JSON

            JSONArray arrayResults = json.getJSONArray("results");//Pega array Results do Json
            Object arrayAdress = arrayResults.get(0); // Pegando o Primeiro Array da lista 
            String arrayAdressText = arrayAdress.toString(); // Convertendo em String para converter novamente para Json
            JSONObject json3 = new JSONObject(arrayAdressText); // Convertando a String para OBJETO JSON

            enderecoCompleto = json3.getString("formatted_address");
            System.out.println(enderecoCompleto);
            return enderecoCompleto;
        } catch (IOException e) {
            System.out.println("Exception" + e);
        }
        
        return enderecoCompleto;
    }
    
}
