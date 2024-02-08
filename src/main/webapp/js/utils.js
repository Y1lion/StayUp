function togglePasswordVisibility(inputId, buttonId) {
    const toggleButton = document.getElementById(buttonId);
    const passwordInput = document.getElementById(inputId);

    toggleButton.addEventListener('click', function() {
        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
        } else {
            passwordInput.type = 'password';
        }
    });
}
function removeThisPT(){
    var email=document.getElementById("confirmationEmailSpan").innerText;
    console.log("CAZZO "+email);
    var id = email.replace(/@/g, "-");
    id=id.trim();
    $.ajax({
        type:"POST",
        url: "ServletRemovePT",
        data: {email: email},
        dataType: 'json',
        success: function (response){
            document.getElementById(id).remove();
        },
        error: function (){
            console.log("errore");
        }
    })
}
function setConfirmationEmail(email){
    console.log("-------------------------------------------------------------------"+email);
    $('#confirmationEmailSpan').text(email);
}