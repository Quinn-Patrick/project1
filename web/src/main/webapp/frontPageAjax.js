function sendData() {
  console.log("Made it here.");
  let url = "http://localhost:8080/project1web/frontPage.html";

  
  
  let xhr = new XMLHttpRequest();

  xhr.onreadystatechange = function() {
	  console.log("Made it here.");
	  if(this.readyState == 4 && this.status == 200) {
		  
		  window.location = "http://localhost:8080/project1web/newRequest.html";
	  }
  }
  xhr.open("POST", url);
  xhr.send();
}

function viewData() {
	  console.log("Made it here.");
	  let url = "http://localhost:8080/project1web/frontPage.html";

	  
	  
	  let xhr = new XMLHttpRequest();

	  xhr.onreadystatechange = function() {
		  console.log("Made it here.");
		  if(this.readyState == 4 && this.status == 200) {
			  
			  window.location = "http://localhost:8080/project1web/viewRequest.html";
		  }
	  }
	  xhr.open("POST", url);
	  xhr.send();
	}