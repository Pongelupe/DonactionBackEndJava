
$("#voucherForm").submit(function(event){
    event.preventDefault();
    if(!saoDadosValidos())
        sweetAlert("Formulário inválido!", "Preencha todos os campos!", "error");
    else {
        var formData = serializarDados();
        var porta = 8080;
        $.ajax({
            type: "POST",
            url: $(this).attr('action') + ":" + porta + "/validarVoucher",
            data: formData,
            success: function(data, textStatus, jqXHR) {
                swal({
                    html: true,
                    title: "Pronto!",
                    text: "O seu voucher foi validado, basta utilizá-lo.",
                    type: "success",
                    confirmButtonText: "Continuar",
                    closeOnConfirm: false,
                }, function() {
                    location.href = "userPageHistory.php"
                })
            },
            error: function(xhr, textStatus, errorThrown) {
            sweetAlert("Voucher Indisponível!", "O código inserido já foi utilizado ou é inválido.", "error");
            }
        });
    }
});

function saoDadosValidos() {
    var nmVoucher = $('#nmVoucher').val();
    var dtVoucher = $('#dtVoucher').val();
    var saoValidos = true;
    if (nmVoucher == "" || dtVoucher == "")
        saoValidos = false;
    return saoValidos;
}

function serializarDados() {
    var dadosDoUsuario = JSON.parse(localStorage.getItem("userData"));
    var cdDoador = dadosDoUsuario.id;
    var nmVoucher = $('#nmVoucher').val();
    var dtVoucher = formatarData($('#dtVoucher').val());
    return "cdDoador=" + cdDoador + "&nmVoucher=" + nmVoucher + "&dtVoucher=" + dtVoucher;
}

function formatarData(data) {
    var dia = data.substring(0, data.indexOf("/"));
    var mes = data.substring(3, data.lastIndexOf("/"));
    var ano = data.substring(data.lastIndexOf("/") + 1, data.length);
    return ano + '-' + mes + '-' + dia;
}


    

