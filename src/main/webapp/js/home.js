function imageScrolled(button){
    var img=document.getElementById("scrollableImg");
    var path;
    if(button==="leftButton"){
        if(img.src.includes("images/fitness2.jpg")) {
            img.src = "images/fitness1.jpg";
        }else if(img.src.includes("images/fitness3.jpg")){
            img.src = "images/fitness2.jpg";
        }
    }else if(button==="rightButton"){
        if(img.src.includes("images/fitness2.jpg")) {
            img.src = "images/fitness3.jpg";
        }else if(img.src.includes("images/fitness1.jpg")){
            img.src = "images/fitness2.jpg";
        }
    }
}