/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Consulta o cep e busca na API para carregar nos inputs o endereco
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

//Funcao Para Mostrar Data e hora Atual
function mostrarDataHora() {
    var currentTime = new Date();
    var horas = currentTime.getHours();
    var minutos = currentTime.getMinutes();
    var segundos = currentTime.getSeconds();
    var dia = currentTime.getDate();
    var mes = currentTime.getMonth();
    var ano = currentTime.getFullYear();
    var Dia = currentTime.getDay();
    var Mes = currentTime.getUTCMonth();
    if (minutos < 10) {
        minutos = "0" + minutos;
    }
    if (segundos < 10) {
        segundos = "0" + segundos;
    }
    if (dia < 10) {
        dia = "0" + dia;
    }
    if (mes < 10) {
        mes = "0" + mes;
    }

    var arrayDia = new Array();//Array recebe os Dias 
    arrayDia[0] = "Domingo";
    arrayDia[1] = "Segunda-Feira";
    arrayDia[2] = "Terça-Feira";
    arrayDia[3] = "Quarta-Feira";
    arrayDia[4] = "Quinta-Feira";
    arrayDia[5] = "Sexta-Feira";
    arrayDia[6] = "Sábado";
 
    var arrayMes = new Array();//Array recebe os Meses 
    arrayMes[0] = "Janeiro";
    arrayMes[1] = "Fevereiro";
    arrayMes[2] = "Março";
    arrayMes[3] = "Abril";
    arrayMes[4] = "Maio";
    arrayMes[5] = "Junho";
    arrayMes[6] = "Julho";
    arrayMes[7] = "Agosto";
    arrayMes[8] = "Setembro";
    arrayMes[9] = "Outubro";
    arrayMes[10] = "Novembro";
    arrayMes[11] = "Dezembro";
    
    var diaAtual = arrayDia[Dia] + ", " + dia + " de " + arrayMes[Mes] + " de " + ano;
    var horaAtual = horas + ":" + minutos + ":" + segundos;
    
    var horaDisplayCelular  = horas + ":" + minutos;
     var dataDisplayCelular = arrayDia[Dia]+ ", " + arrayMes[Mes]  + ", " + dia ; 
    $("#horaDisplay").text(horaDisplayCelular);
    $("#dataDisplay").text(dataDisplayCelular);
    
    dataDisplay
    
    
    $("#datahora").html("<div class='pull-right'><b>" + diaAtual + " &nbsp " + horaAtual + "</b></div>"); // MOstrar Data Hora no NavBar
    $("#timer").html("" + diaAtual + " &nbsp " + horaAtual + "");
}
$(document).ready(function () {
    // O metodo nativo setInterval executa uma determinada funcao em um determinado tempo 
    setInterval(mostrarDataHora, 1000);
});


function Imprimir() {
    mostrarDataHora();
    $("#marcaDagua").attr("id", "jr-marcaDagua");
    $("#datatable").attr("class", "table table-condensed table-hover");
    $("#contentPage").attr("class", "content");
    window.print();
    $("#contentPage").attr("class", "content-page");
    $("#datatable").attr("class", "table table-condensed table-hover dataTable");
    $("#jr-marcaDagua").attr("id", "marcaDagua");
}


//Gerar Senha Automaticamente
function Password() {
    var pass = "";
    var chars = 5; //Número de caracteres da senha
    for (var i = 0; i <= chars; i++) {
        pass += Math.floor((Math.random() * 9) + 1);
    }
    $("#inputSenha").val(pass);
}


//ONSIGNAL NOTIFICACOES
$(function () {
    $('#btnEnviarNotificacao').click(enviarFormNotificacao);

});

//Formulario para enviar as Notificacoes e validar 
function enviarFormNotificacao() {
    var app_id = "c88e9cdf-f914-4d60-a768-e84d82c4f89a"; //Id do App no OneSignal
    var included_segments = "All"; // Enviar mensagem para todos de uma vez . nao usando pois esta usando individual
    var tituloMensagem = $("textarea#tituloMensagem").val(); // recupera o titulo da mensagem
    var contents = $("textarea#corpoMensagem").val();//recupera texto da mensagem 
    
    var players_ids = new Array(); // array de usuarios cadastrados para receber as  notificacoes
    var total_lista_players; // Varivel para retornar o total de usuario (por empresa) que tem cadastro no OneSignal 
    var total_selecionados;//Retorna o total de selecionados para receber a mensagem de notificacao


//Cria o Array para enviar as Notificações
    $("#my_multi_select2   option:selected").each(function (i) {
        players_ids[i] = $(this).val();
    });

// Recupera o Total da Lista de Usuarios 
    var select = document.getElementById('my_multi_select2');
    total_lista_players = select.options.length;

// Recupera o Total da Selecionados
    total_selecionados = players_ids.length;
    $("#totalSelecionado").html(total_selecionados);

    if (players_ids.length === 0) {
        $.Notification.autoHideNotify('black', 'button right', '', 'Selecione ao menos 1 Cliente ou Representante.', '');
        return;
    }

    if (contents === "") {
        $.Notification.autoHideNotify('black', 'button right', '', 'Por favor digite uma mensagem.', '');
        return;

    } else if (contents.length < 20) {
        $.Notification.autoHideNotify('black', 'button right', '', 'A mensagem deve ser maior que 20 caracteres');
        return;
    }

    $.ajax({
        type: 'POST',
        url: 'https://onesignal.com/api/v1/notifications',
        data: {
            "app_id": app_id,
             "include_player_ids": players_ids,
            "headings": {"en": tituloMensagem}, // Título
            "data": {"foo": "bar"},
//            "send_after":"2018-02-08 18:39:00 GMT-2", // Envia uma mensagem automatica escolhendo a data e a hora 
            "android_accent_color": "00BFFF", //Define a cor de fundo do círculo de notificação à esquerda do texto de notificação. Aplica-se apenas a aplicações que visam a API Android nível 21+ em dispositivos Android 5.0+.
            "contents": {"en": contents} // Mensagem 
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Basic Y2RlMDcxNWEtNDA5ZC00OTc0LWEyOGItMDUwNGZlNTlmN2Qx');
        },
        success: function (textStatus) {
            if (textStatus) {
                $.Notification.notify('success', 'button right', 'Parabéns...', 'Sua mensagem foi enviada com sucesso para ' + players_ids.length + ' dispositivos.');
            }
        },
        error: function () {
            $.Notification.notify('error', 'button right', 'Erro...', 'Sua mensagem não foi enviada!');
        }
    });
}

//SetInterval de Verificar as Notificacoes
$(document).ready(function () {
    TotalNotificacoes();
    setInterval(TotalNotificacoes, 50000);
});
function TotalNotificacoes() {
    var totalNotificacoes = 0;
    var totalClientes;
    $.ajax({
        type: 'GET',
        url: '/sistema/hellohi/api/pedidos/notificacoes',
        success: function (total) {
            if (total === 0) {
                $("#totalNotificacoes").html(totalNotificacoes);
                return;
            } else {
                totalNotificacoes = total;
                $.ajax({
                    type: 'GET',
                    url: "/sistema/hellohi/api/notificacao/view",
                    success: function (clientes) {
                        $("#totalNotificacoes").html(totalNotificacoes);
                        $("#clientesNotificacao").html(clientes);
                    },
                    error: function () {
                        console.log(error);
                    }
                });
            }
        },
        error: function () {
            console.log("Erro Verificar Notificações " + error);
        }

    });


//Pagina para enviar Notificacao
    $(document).ready(function () {
        var players_ids = new Array();
        $("textarea#tituloMensagem").keyup(function () {
            var titulo = $("textarea#tituloMensagem").val();
            $("#title").html('<b>' + titulo + '</b>');
        });
        $("textarea#corpoMensagem").keyup(function () {
            var mensagem = $("textarea#corpoMensagem").val();
            $("#contents").html(mensagem);
        });

        $(".ms-list").click(function () {
            $("#my_multi_select2   option:selected").each(function (i) {
                players_ids[i] = $(this).val();
            });
            $("#totalSelecionado").html(players_ids.length);
        });
    });

}


