function userFirstSession() {
    classActive();
    var userData = JSON.parse(localStorage.getItem("userData"));
    document.getElementById("userAvatar").src = "https://api.adorable.io/avatars/180/" + userData.id + ".png";
    document.getElementById("userName").innerHTML = userData.nome;
    userData.tipoSanguineo.charAt(1) == "" ? $("#userBlood").text(userData.tipoSanguineo + "+") : $("#userBlood").text(userData.tipoSanguineo);
}

function userSession() {
    classActive();
    var userData = JSON.parse(localStorage.getItem("userData"));
    document.getElementById("userAvatar").src = "https://api.adorable.io/avatars/180/" + userData.id + ".png";
    document.getElementById("userName").innerHTML = userData.nome;
    document.getElementById("userBlood").innerHTML = userData.tipoSanguineo;
    document.getElementById("cpf").value = userData.nrCpf;
    document.getElementById("email").value = userData.email;
    document.getElementById("city").value = userData.cidade;
}

function clearField(inputId) {
    document.getElementById(inputId).value = "";
}

$("#updateData").submit(function(event){
    event.preventDefault();
    var porta = 8080;
    var formData = SerializedUserData();
    $.ajax({
        type: "POST",
        url: $(this).attr('action') + ":" + porta + "/atualizarCadastroDoador",
        data: formData,
        success: function(data, textStatus, jqXHR) {
            sweetAlert("Dados Alterados com Sucesso!","","success");      
        },
        error: function(xhr, textStatus, errorThrown) {
            sweetAlert("Login inválido!","","error");
        }
    });
});

function SerializedUserData() {
    var userData = JSON.parse(localStorage.getItem("userData"));
    var cdDoador = userData.id;
    var userEmail = document.getElementById('email').value;
    var userLocation = document.getElementById('city').value;
    return "cdDoador=" + cdDoador + "&emailDoador=" + userEmail + "&cidadeDoador=" + userLocation;
}

function notBlank(inputId) {
    var userData = JSON.parse(localStorage.getItem("userData"));
    var inputValue = document.getElementById(inputId).value;
    if (inputValue == "" && inputId == "email") {
        document.getElementById(inputId).value = userData.email
    }
}

function endSession() {
    localStorage.removeItem("userData");
    location.href = ("../index.php");
}

function classActive() {
    var currentPage = location.href.split('/')[location.href.split('/').length - 1];
    if (currentPage == "userVoucherValidation.php") {
        document.getElementsByTagName("li")[0].className = "active";
    }
    if (currentPage == "userPageInfo.php") {
        document.getElementsByTagName("li")[1].className = "active";
    }
}