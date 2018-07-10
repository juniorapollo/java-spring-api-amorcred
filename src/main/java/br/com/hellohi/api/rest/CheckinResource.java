package br.com.hellohi.api.rest;

import br.com.hellohi.api.controller.CheckinController;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONObject;

import br.com.hellohi.api.models.AgendaManutencao;
import br.com.hellohi.api.models.AgendaProspeccao;
import br.com.hellohi.api.models.Checkin;
import br.com.hellohi.api.models.FotoCheckin;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.repository.CheckinRepository;
import br.com.hellohi.api.repository.FotoCheckinRepository;
import br.com.hellohi.api.repository.RepresentanteRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping(value = "${baseUrl}/hellohi/api/checkin")
public class CheckinResource {

    @Autowired
    CheckinRepository cr;
    Checkin checkin;
    CheckinController cc;

    FotoCheckin fotoCheckin;

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

    @Autowired
    FotoCheckinRepository fotoCheckinRepository;

    //Retorna a Lista de Todos os checkins realizados
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping()
    public Iterable<Checkin> listaCheckin() {

        // UserSS user = UserService.authenticated();
        List<Checkin> listaCheckin = cr.findAll();
         carregaHora(listaCheckin);
        return listaCheckin;
    }

    @GetMapping(value = "/pagina")
    public Page<Checkin> findPageCheckin(
            @RequestParam(value = "page", defaultValue = "0") Integer page, // se nao passar o valor da pagina , automaticamente vai para a pagina 0
            @RequestParam(value = "linhasPorPagina", defaultValue = "10") Integer linhasPorPagina,
            @RequestParam(value = "orderBy", defaultValue = "idCheckin") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {

        try {
            Page<Checkin> listaCheckin = findPage(page, linhasPorPagina, orderBy, direction);
            System.out.println(listaCheckin);
            // carregaHora(listaCheckin);
            return listaCheckin;
        } catch (Exception e) {
            System.out.println("Checkin Resource: 94" + e);
        }
        return null;
    }

    //Retorna checkin por Id
    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "/id/{idCheckin}", method = RequestMethod.GET)
    public Checkin pegarCheckinPorId(@PathVariable("idCheckin") Long idCheckin) {

        //fotoCheckin = new FotoCheckin();
        checkin = cr.findByIdCheckin(idCheckin);
        //fotoCheckin.setFoto(checkin.getFoto());
        
        //fotoCheckin.setCheckin(checkin);
        //fotoCheckinRepository.save(fotoCheckin);
        //checkin.setFoto(null);
       // cr.save(checkin);
        return checkin;
    }

    //Retorna todos os checkins por Representante
    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/checkin/{idRepresentante}", method = RequestMethod.GET)
    public Iterable<Checkin> checkinsPorRepresentante(@PathVariable("idRepresentante") Long idRepresentante) {
        representante = rr.representantePorId(idRepresentante);

        return cr.findByRepresentanteOrderByDataHoraCheckinDesc(representante);
    }

    //Retorna a Lista de Todos os checkins por Agenda de Prospeccao
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/checkin/agenda/prospeccao/{idAgendaProspeccao}", method = RequestMethod.GET)
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

    //Salvar Checkin referente a agenda da Manutencao
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/checkin/agenda/manutencao/{idAgendaManutencao}", method = RequestMethod.POST)
    public Checkin salvarCheckinManutencao(@RequestBody @Valid Checkin checkin, @PathVariable("idAgendaManutencao") Long idAgendaManutencao) {

        checkin.setAgendaManutencao(amr.pegarAgendaManutencaoPorId(idAgendaManutencao));
        checkin.setIdCheckin(null);

        return cr.save(checkin);

    }

    //Salvar Checkin referente a agenda da Prospeccao
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/checkin/agenda/prospeccao/{idAgendaProspeccao}", method = RequestMethod.POST)
    public Checkin salvarCheckinProspeccao(@RequestBody @Valid Checkin checkin, @PathVariable("idAgendaProspeccao") Long idAgendaProspeccao) {

        checkin.setAgendaProspeccao(apr.pegarAgendaProspeccaoPorId(idAgendaProspeccao));
        checkin.setDataHoraCheckin(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        checkin.setIdCheckin(null);

        return cr.save(checkin);
    }

    /**
     * Salva Checkin realizado pelo Representante no app
     *
     * @param checkin
     * @param nomeEmpresa
     * @param result
     * @param request
     * @throws JSONException
     * @throws IOException
     */
    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void salvarCheckin(@RequestBody @Valid Checkin checkin, BindingResult result, HttpServletRequest request) throws JSONException, IOException {

        String login = request.getHeader("login"); // recuperar o Email passado pelo Header da requisicao
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatter_2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = (hoje).format(formatter_2);

        if (result.hasErrors()) {
            System.out.println("DEU ERRO");
        }
        try {
            this.representante = representanteRepository.findByLogin(login); // Recupera o Representante por email(Login)
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Salvando Checkin ..." + dataFormatada);

        checkin.setDataHoraCheckin(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        checkin.setDataFormatada(dataFormatada);
        checkin.setRepresentante(this.representante);
        checkin.setEmpresa(this.representante.getEmpresa());

        fotoCheckin = new FotoCheckin();
        fotoCheckin.setFoto(checkin.getFoto());
        checkin.setFoto(null);

        try {
            checkin.setEnderecoCompleto(converterLatitudeLongitudeEmEndereco(checkin.getLatitude(), checkin.getLongitude())); //converte lat lng em endereco completo
            cr.save(checkin);
            fotoCheckin.setCheckin(checkin);
            fotoCheckinRepository.save(fotoCheckin);
        } catch (Exception e) {
            checkin.setEnderecoCompleto("Endereço Inválido! Abrir Mapa.");
            cr.save(checkin);
            fotoCheckin.setCheckin(checkin);
            fotoCheckinRepository.save(fotoCheckin);
        }

    }

    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/checkin/{idCheckin}", method = RequestMethod.PUT)
    public Checkin editarCheckin(@RequestParam Map<String, String> body, @RequestBody @Valid Checkin checkin) {
        checkin.setDataHoraCheckin(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return cr.save(checkin);
    }

    /**
     * Método transforma um arquivo em string
     *
     * @param rd
     * @return
     * @throws IOException
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * Método que recebe uma latitude e longitude e retorna o endereço completo
     * referente a latitude e longitude passada , (usa-se a Api do Google)
     *
     * @param lat
     * @param lng
     * @return
     * @throws JSONException
     * @throws IOException
     */
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

    /**
     * Método recebe como parâmetro o endereco e retorna a Latitude e Longitude
     *
     * @param logradouro
     * @param numeroEnd
     * @param bairro
     * @param cidade
     * @param estado
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public double[] converterEnderecoLatitudeLongitude(String logradouro, int numeroEnd, String bairro, String cidade, String estado) throws JSONException, IOException {
        double lat = 0;
        double lng = 0;
        String numero = Integer.toString(numeroEnd);
        String endereco = logradouro + "+" + numero + "+" + cidade + "+" + estado;
        System.out.println("Endereço completo Checkin Resources: " + endereco.replace(" ", ""));

        try (InputStream is = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + endereco.replace(" ", "") + "&key=AIzaSyAojEMgcdjBL6qC-WCAiKJbW1ekoPaME_o").openStream()) {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(bufferedReader);//pegando o resultado da URL e convertendo para String
            JSONObject resultJson = new JSONObject(jsonText); // Convertando a String para OBJETO JSON

            JSONArray arrayResults = resultJson.getJSONArray("results");//Pega array Results do Json

            System.out.println(arrayResults);
            try {
                JSONObject jsonLocation = arrayResults.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                System.out.println(jsonLocation);
                lat = jsonLocation.getDouble("lat");
                lng = jsonLocation.getDouble("lng");
                System.out.println("Latitude e Longitude : CheckinResource: " + lat + "," + lng);
            } catch (JSONException e) {
                System.err.println("Erro Converter Latitude e Longitude:270, Json Array Vazio: " + e);
                double[] coordenadas = new double[]{lat, lng};
                return coordenadas;
            }

        } catch (IOException e) {
            System.out.println("Exception" + e);
        }

        double[] coordenadas = new double[]{lat, lng};

        return coordenadas;
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "${baseUrl}/checkin", method = RequestMethod.GET)
    public Iterable<Checkin> listaCheckinPorRepresentanteEntreDatas(Representante r , String dataInicio, String dataFim) {
        r = rr.representantePorId(1l);
        LocalDateTime hoje =  LocalDateTime.now();
        System.out.println("HOJE  = " + hoje);
        LocalDateTime dataInicial= LocalDateTime.of(hoje.getYear() , hoje.getMonth().APRIL, 1 , 0, 0);
        LocalDateTime dataFinal= LocalDateTime.of(hoje.getYear(),hoje.getMonthValue() ,hoje.getMonth().length(true) , 0, 0);
        System.out.println("Data Inicial " + dataInicial);
        System.out.println("DataFinal " + dataFinal);
        
       Iterable<Checkin> listaCheckin = cr.findByRepresentanteAndDataHoraCheckinBetween(r,dataInicial, dataFinal);
        
        return listaCheckin;
    }

    private void carregaHora(Iterable<Checkin> listaCheckin) {
        ArrayList<Checkin> a;
        a = (ArrayList<Checkin>) listaCheckin;
        for (Checkin c : a) {
            System.out.println(c.getIdCheckin());

            c.setDataFormatada(c.getDataHoraCheckin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            cr.save(c);

        }

    }

    public Page<Checkin> findPage(Integer page, Integer linhasPorPagina, String orderBy, String direction) {
        PageRequest pageRequest = new PageRequest(page, linhasPorPagina, Sort.Direction.valueOf(direction), orderBy);
        return cr.findAll(pageRequest);
    }
    
    
    

}
