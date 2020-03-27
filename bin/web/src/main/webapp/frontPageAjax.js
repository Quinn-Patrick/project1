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

function fetchUser() {
	  let url = "http://localhost:8080/project1web/frontPage.html";
	
	  let xhr = new XMLHttpRequest();
	
	  xhr.onreadystatechange = function() {
		  if(this.readyState == 4 && this.status == 200) {
				let target = document.getElementById("userInfo");
				let emp = JSON.parse(sessionStorage.getItem("currentUser"));
				// Then we can retrieve the value from the client-side session
				console.log(emp);
				target.innerHTML = "Hello, " + emp.firstName + " " + emp.lastName;
		  }
	  }
	  xhr.open("GET", url);
	  xhr.send();
}
	
fetchUser();