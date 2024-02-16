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
var selectedRowPT=null;
function setEmailPT(email,row){
    if (selectedRowPT !== null) {
        selectedRowPT.classList.remove('table-active');
    }
    row.classList.add('table-active');
    selectedRowPT = row;
    document.getElementById("emailPT").value=email;
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

var gg=1;
var identificationGG1=1;
var identificationGG2=1;
var identificationGG3=1;
var identificationGG4=1;
var identificationGG5=1;
function addDay(){

    if(gg<5)
        var identification;
        var trainingSheetContainer = document.getElementById("trainingSheet" + gg);

        if (trainingSheetContainer) {
            gg++;
            if(gg===2){
                identification=identificationGG2;
                document.getElementById("gg2").value=identification;
            }else if(gg===3){
                identification=identificationGG3;
                document.getElementById("gg3").value=identification;
            }else if(gg===4){
                identification=identificationGG4;
                document.getElementById("gg4").value=identification;
            }else if(gg===5){
                identification=identificationGG5;
                document.getElementById("gg5").value=identification;
            }
            var newTrainingSheet = document.createElement('div');
            newTrainingSheet.setAttribute('name', 'trainingSheet' + gg);
            newTrainingSheet.setAttribute('id', 'trainingSheet' + gg);
            newTrainingSheet.classList.add('card', 'w-75', 'shadow-lg', 'mx-auto', 'mt-5');

            newTrainingSheet.innerHTML = `
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <span class="h1 card-title text-primary fw-bold mb-0">Day ${gg}</span>
                        <button type="button" class="btn btn-outline-secondary" onclick="addDay()">Add day</button>
                    </div>
                    <hr>
                    <div id="thisExerciseD${gg}" class="d-flex flex-wrap row g-3">
                        <div class="col-md-6 col-sm-12">
                            <label class="text-black ms-2" for="exerciseNameN${identification}D${gg}">Exercise name:</label>
                            <input type="text" class="form-control" name="formNameExerciseN${identification}D${gg}" id="exerciseNameN${identification}D${gg}" required placeholder="Exercise name">
                        </div>
                        <div class="col-md-2 col-sm-12">
                            <label class="text-black ms-2" for="setsN${identification}D${gg}">Sets N°:</label>
                            <input type="number" class="form-control" name="formNameSetsN${identification}D${gg}" id="setsN${identification}D${gg}" required placeholder="Sets N°" min="0" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 109">
                        </div>
                        <div class="col-md-2 col-sm-12">
                            <label class="text-black ms-2" for="repsN${identification}D${gg}">Reps N°:</label>
                            <input type="number" class="form-control" name="formNameRepsN${identification}D${gg}" id="repsN${identification}D${gg}" required placeholder="Reps N°" min="0" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 109">
                        </div>
                        <div class="col-md-2 col-sm-12">
                            <label class="text-black ms-2" for="pauseN${identification}D${gg}">Rest:</label>
                            <input class="form-control" name="formNamePauseN${identification}D${gg}" id="pauseN${identification}D${gg}" required placeholder="Rest">
                        </div>
                    </div>
                    <div id="exerciseAdder" class="mt-3 d-flex justify-content-end">
                        <span  onclick="addExercise(${gg})" class="text-secondary">Add new exercise</span>
                    </div>
                </div>
            `;

            trainingSheetContainer.insertAdjacentElement('afterend', newTrainingSheet);
        } else {
            console.error("Elemento con id 'trainingSheet" + gg + "' non trovato.");
        }
    }

function addExercise(currentGG) {
    var identification;
    if(currentGG===1) {
        identificationGG1++;
        identification=identificationGG1;
        document.getElementById("gg1").value=identification;
    }else if(currentGG===2){
        identificationGG2++;
        identification=identificationGG2;
        document.getElementById("gg2").value=identification;
    }else if(currentGG===3){
        identificationGG3++;
        identification=identificationGG3;
        document.getElementById("gg3").value=identification;
    }else if(currentGG===4){
        identificationGG4++;
        identification=identificationGG4;
        document.getElementById("gg4").value=identification;
    }else if(currentGG===5){
        identificationGG5++;
        identification=identificationGG5;
        document.getElementById("gg5").value=identification;
    }


    var newExerciseDiv = document.createElement('div');
    newExerciseDiv.classList.add('col-md-6', 'col-sm-12');
    newExerciseDiv.innerHTML = `
        <label class="text-black ms-2" for="exerciseNameN${identification}D${currentGG}">Exercise name:</label>
                            <input type="text" class="form-control" name="formNameExerciseN${identification}D${currentGG}" id="exerciseNameN${identification}D${currentGG}" required placeholder="Exercise name">
    `;

    var newSetsDiv = document.createElement('div');
    newSetsDiv.classList.add('col-md-2', 'col-sm-12');
    newSetsDiv.innerHTML = `
        <label class="text-black ms-2" for="setsN${identification}D${currentGG}">Sets N°:</label>
                            <input type="number" class="form-control" name="formNameSetsN${identification}D${currentGG}" id="setsN${identification}D${currentGG}" required placeholder="Sets N°" min="0" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 109">
    `;

    var newRepsDiv = document.createElement('div');
    newRepsDiv.classList.add('col-md-2', 'col-sm-12');
    newRepsDiv.innerHTML = `
        <label class="text-black ms-2" for="repsN${identification}D${currentGG}">Reps N°:</label>
                            <input type="number" class="form-control" name="formNameRepsN${identification}D${currentGG}" id="repsN${identification}D${currentGG}" required placeholder="Reps N°" min="0" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 109">
    `;

    var newRestDiv = document.createElement('div');
    newRestDiv.classList.add('col-md-2', 'col-sm-12');
    newRestDiv.innerHTML = `
        <label class="text-black ms-2" for="pauseN${identification}D${currentGG}">Rest:</label>
                            <input class="form-control" name="formNamePauseN${identification}D${currentGG}" id="pauseN${identification}D${currentGG}" required placeholder="Rest">
    `;


    var exerciseContainer = document.getElementById("thisExerciseD"+currentGG);
    exerciseContainer.appendChild(newExerciseDiv);
    exerciseContainer.appendChild(newSetsDiv);
    exerciseContainer.appendChild(newRepsDiv);
    exerciseContainer.appendChild(newRestDiv);

}

function controllaDate() {
    var dataInizio = document.getElementById("dateStart").value;
    var dataFine = document.getElementById("dateEnd").value;

    // Verifica se entrambe le date sono state inserite
    if (dataInizio && dataFine) {
        // Converti le stringhe in oggetti Date
        var dataInizioObj = new Date(dataInizio);
        var dataFineObj = new Date(dataFine);

        // Verifica se la seconda data è successiva alla prima
        if (dataFineObj <= dataInizioObj) {
            // Mostra un messaggio di errore
            alert("La data di fine deve essere successiva alla data di inizio.");
            // Pulisci il campo della data di fine
            document.getElementById("dateEnd").value = "";
            // Impedisci l'invio del modulo
            return false;
        }
    }
    // Se entrambe le date sono state inserite e la seconda è successiva alla prima, restituisci true
    return true;
}
var selectedRow=null;
function setEmailSub(email,row){
    console.log("ciaoo")
    if (selectedRow !== null) {
        console.log("dentro")
        selectedRow.classList.remove('table-active');
    }
    row.classList.add('table-active');
    selectedRow = row;
    document.getElementById("emailSub").value=email;
}