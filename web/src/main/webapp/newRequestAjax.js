function sendData() {
  console.log("Made it here.");
  let url = "http://localhost:8080/project1web/newRequest.html";
  
  let amountField = document.getElementById("amount");
  let typeField = document.getElementsByName("type");
  let descField = document.getElementById("desc");
  
  let data = {
		  	description: descField.value,
		    amount: amountField.value,
		    type: parseRadios(),
		    
  };
  
  console.log(data.type);
  
  console.log(document.getElementById("desc").value);
  console.log(descField.value);
  console.log(data.description);
  
  function parseRadios(){
	  j = 0;
	  for(i = 0; i < typeField.length; i++){
		  if(typeField[i].checked) return j;
		  j++;
	  }
	  return 0;
  }
  
  let xhr = new XMLHttpRequest();

  xhr.onreadystatechange = function() {
	  if(this.readyState == 4 && this.status == 200) {
		  
		  window.location = "http://localhost:8080/project1web/frontPage.html";
	  }
  }
  xhr.open("POST", url);
  xhr.send(JSON.stringify(data));
  console.log(JSON.stringify(data));
}