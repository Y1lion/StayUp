<%
    String sessionID = null;
    Cookie[] cookies = request.getCookies(); // Ottieni tutti i cookie dalla richiesta
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("JSESSIONID")) { // Verifica se il cookie ha nome "JSESSIONID"
                sessionID = cookie.getValue(); // Ottieni il valore del cookie come sessionID
                break;
            }
        }
    }
    String dataFromSession=null;
    String nameSession=null;
    String surnameSession=null;
    String roleSession=null;
    if (sessionID != null) {
        HttpSession sessionOfCookie = request.getSession(false); // Ottieni l'oggetto HttpSession associato alla sessionID
        if (sessionOfCookie != null) {
            dataFromSession = (String) sessionOfCookie.getAttribute("email"); // Ottieni i dati desiderati dalla sessione utilizzando il metodo getAttribute()
            nameSession = (String) sessionOfCookie.getAttribute("name");
            surnameSession = (String) sessionOfCookie.getAttribute("surname");
            roleSession = (String) sessionOfCookie.getAttribute("role");
        }
    }

%>