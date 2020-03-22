let xhr = new XmlHttpRequest;
let url = "http://localhost:8080/project1web/viewRequest.html";
xhr.onreadystatechange = function() {
	  if(this.readyState == 4 && this.status == 200) {
		  
		  
	  }
}
xhr.open("GET", url);
xhr.send();
console.log(JSON.stringify(data));