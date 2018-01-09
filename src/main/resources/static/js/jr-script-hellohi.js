/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */






$('#cep').on('blur', function () {

    if ($.trim($("#cep").val()) != "") {

        $("#mensagem").html('(Aguarde, consultando CEP ...)');
        $.getScript("http://cep.republicavirtual.com.br/web_cep.php?formato=javascript&cep=" + $("#cep").val(), function () {

            if (resultadoCEP["resultado"]) {
                $("#rua").val(unescape(resultadoCEP["tipo_logradouro"]) + " " + unescape(resultadoCEP["logradouro"]));
                $("#bairro").val(unescape(resultadoCEP["bairro"]));
                $("#cidade").val(unescape(resultadoCEP["cidade"]));
                $("#uf").val(unescape(resultadoCEP["uf"]));
            }

            $("#mensagem").html('');
        });
    }
    ;
});





function openPage(tipo) {
    var url;
    switch (tipo) {
        case "usuario":
            url = "cadastro/usuarios";
            break;

        case "representante":
            url = "/representante";
            break;

        case "checkin":
            url = "/checkin";
            break;

        case "cliente":
            url = "/cliente";
            break;

        case "empresaConveniada":
            url = "/estagio/cadastro/empresaConveniada";
            break;

        case 'cadastroAluno':
            url = "/estagio/cadastro/aluno";
            break;

        case "profOrientador":
            url = "/estagio/formProfOrientador";
            break;

        case "listarAlunos":
            url = "/estagio/aluno";
            break;

        case "relatorioProfOrientador":
            url = "/estagio/relatorio/profOrientador";
            break;

        case "setor":
            url = "/estagio/cadastrar/setor";
            break;

        case "representante":
            url = "/estagio/cadastro/representanteDepartamento";
            break;

        case "inventarioConcedentes":
            url = "/estagio/cadastro/inventarioConcedente";
            break;

        case "relatorioEmpresaConveniada":
            url = "/estagio/relatorio/empresaConveniada";
            break;

        case "listaConvenio":
            url = "/estagio/listaConvenio";
            break;

        case "convenioEstagio":
            url = "/estagio/cadastro/convenioEstagio";
            break;

        case "orgaoIntegrador":
            url = "/estagio/orgaoIntegrador";
            break;
        default:
            url = "/merda";
            break;
    }
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        xhttp.open(url, true);
        xhttp.send();
    };

}

meses = new Array("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
semana = new Array("Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado");
function DiaExtenso() {
    hoje = new Date();
    dia = hoje.getDate();
    dias = hoje.getDay();
    mes = hoje.getMonth();
    ano = hoje.getYear();
    if (navigator.appName === "Netscape")
        ano = ano + 1900;
    diaext = semana[dias] + ", " + dia + " de " + meses[mes] + " de " + ano;
    return diaext;
}


//function MudarEstado(foto, mapa) {
//    var displayFoto = document.getElementById(foto).style.display;
//
//
//    if (displayFoto === "block") {
//        document.getElementById(foto).style.display = 'none';
//        document.getElementById(mapa).style.display = 'none';
//       
//    } else
//        document.getElementById(foto).style.display = 'block';
//        document.getElementById(mapa).style.display = 'block';
//        initMap();
//        
//}


