$("#loginForm").submit(function(event){
    event.preventDefault();
    var porta = 8080;
    var formData = SerializedUserData();
    $.ajax({
        type: "POST",
        url: $(this).attr('action') + ":" + porta + "/logarConta",
        data: formData,
        success: function(data, textStatus, jqXHR) {
            var userSession = JSON.parse(jqXHR.responseText);
            localStorage.setItem("userData", JSON.stringify(userSession));
            location.href = ("pages/userPageValidation.php");       
        },
        error: function(xhr, textStatus, errorThrown) {
            sweetAlert("Login inválido!","","error");
        }
    });
});

function SerializedUserData() {
    var userEmail = document.getElementById('userEmail').value;
    var userPassword = document.getElementById('userPassword').value;
    return "email=" + userEmail + "&senha=" + userPassword;
}