function ehNumero(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function prazoProrrogacaoValido(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 49 || charCode > 54)) {
        return false;
    }
    return true;
}

function exibirConfirmacao(mensagem, idBotaoClicar) {
    bootbox.confirm(mensagem, function(result) {
        if (result === true) {
            document.getElementById(idBotaoClicar).click();
        }
    });    
}

function setarFocoEm(item){
     document.getElementById(item).focus();              
}
