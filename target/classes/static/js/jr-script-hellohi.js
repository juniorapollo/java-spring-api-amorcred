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

    arrayDia = new Array();
    arrayDia[0] = "Domingo";
    arrayDia[1] = "Segunda-Feira";
    arrayDia[2] = "Terça-Feira";
    arrayDia[3] = "Quarta-Feira";
    arrayDia[4] = "Quinta-Feira";
    arrayDia[5] = "Sexta-Feira";
    arrayDia[6] = "Sábado";
    var arrayMes = new Array();
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
    $("#datahora").html("<div class='pull-right'><b>" + diaAtual + " &nbsp " + horaAtual + "</b></div>");
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

function enviarFormNotificacao() {

    var app_id = "1d5c55a8-cef8-4e32-a4af-17dde2ab6181";
    var included_segments = "All";
    var contents = $("textarea#contents").val();

    var players_ids = new Array();
    var total_lista_players;
    var total_selecionados;

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

    if (contents === "") {
        $.Notification.notify('black', 'top right', '<br></br>', 'Por favor digite uma mensagem.', '');
        return;

    } else if (contents.length < 20) {
        $.Notification.notify('black', 'top right', '<br></br>', 'A mensagem deve ser maior que 20 caracteres');
        return;
    }

console.log("Usuarios "+ players_ids);
    $.ajax({
        type: 'POST',
        url: 'https://onesignal.com/api/v1/notifications',
        data: {
            "app_id": app_id,
            "ledColor": "#80ff0000",
            "include_player_ids": players_ids,
            "data": {"foo": "bar"},
            "contents": {"en": contents}
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Basic OTJmMjNhYzYtYTI5OC00OTE0LWExODMtNzU4Y2UyNzMyZGEx');
        },
        success: function (textStatus) {
            if (textStatus) {
                $.Notification.notify('success', 'top right', 'Parabéns...', 'Sua mensagem foi enviada com sucesso para ' + players_ids.length + ' dispositivos.');
            }
        },
        error: function () {           
            $.Notification.notify('error', 'top right', 'Erro...', 'Sua mensagem não foi enviada!');
        }
    });
}


//SetInterval de Verificar as Notificacoes
$(document).ready(function () {
    TotalNotificacoes();
  setInterval(TotalNotificacoes, 1000);
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
            console.log("Erro Verificar Notificações "+ error);
        }

    });
}


