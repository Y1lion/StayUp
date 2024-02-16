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