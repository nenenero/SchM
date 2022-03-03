function rand() {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange=function () {
        if(xhr.readyState===4 && xhr.status===200){
            document.getElementById("rand").innerText=xhr.responseText;
        }
    };
    console.log(document.getElementById("rand").innerText);
    console.log(xhr.responseText);
    xhr.open('GET','rand',true)
    xhr.send();
}