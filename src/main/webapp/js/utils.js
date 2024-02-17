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
    var email=document.getElementById("confirmationEmailForPTDeleteSpan").innerText;
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
function upgradeThisUser() {
    var email = document.getElementById("confirmationEmailForPendingUserSpan").innerText;
    var id = email.replace(/@/g, "-")+"1";
    $.ajax({
        type: "POST",
        url: "ServletUpgradeUser",
        data: { email: email },
        dataType: 'json',
        success: function(user) {
            // Qui puoi utilizzare l'oggetto User ricevuto dalla risposta AJAX
            console.log("Oggetto User ricevuto:", user);

            // Crea un nuovo elemento <tr> con i valori dell'oggetto User
            console.log(id);
            var tr = $("<tr>")
                .attr("id", id)
                .attr("onclick", "setConfirmationEmailForPTDelete('" + user.email + "')")
                .attr("data-bs-toggle", "modal")
                .attr("data-bs-target", "#dialogForElimination");
            tr.append($("<td>").addClass("text-center align-middle").text(user.email));
            tr.append($("<td>").addClass("text-center align-middle").text(user.nome));
            tr.append($("<td>").addClass("text-center align-middle").text(user.cognome));
            tr.append($("<td>").addClass("text-center align-middle").text(user.telefono));

            // Aggiungi il nuovo elemento <tr> al <tbody>
            $("#tabellaPTBody").append(tr);
            console.log("CAZOOLONE "+id);
            document.getElementById(id).remove();
        },
        error: function() {
            console.log("Errore durante la richiesta AJAX.");
        }
    });
}

function setConfirmationEmailForPTDelete(email){
    $('#confirmationEmailForPTDeleteSpan').text(email);
}
function setConfirmationEmailForPendingUser(email){
    $('#confirmationEmailForPendingUserSpan').text(email);
}
var selectedRowPT=null;
function setEmailPT(email,row){
    if (selectedRowPT !== null) {
        selectedRowPT.classList.remove('table-active');
    }
    row.classList.add('table-active');
    selectedRowPT = row;
    document.getElementById("emailPT").value=email;
}