function lisaaTiedot() {
	let formData = serialize_form(document.lomake);
    fetch("asiakkaat", {method: "POST", headers: {"Content-Type": "application/json; charset=UTF-8"}, body: formData})
    .then(response => response.json())
   	.then(responseObj => {
   		if (responseObj.response == 0) {
   			document.getElementById("ilmo").innerHTML = "Asiakkaan lisäys epäonnistui.";
        } else if (responseObj.response == 1) {
        	document.getElementById("ilmo").innerHTML = "Asiakkaan lisäys onnistui.";
			document.lomake.reset();
		}
		setTimeout(function(){document.getElementById("ilmo").innerHTML = "";}, 3000);
   	})
   	.catch(errorText => console.error("Fetch failed: " + errorText));
}

function haeAsiakkaat() {
	let hakusana = document.getElementById("hakusana").value;
	fetch("asiakkaat?hakusana=" + hakusana, {method: "GET", headers: {"Content-Type": "application/x-www-form-urlencoded"}})
	.then(response => response.json())
	.then(response => printItems(response))
	.catch(errorText => console.error("Fetch failed: " + errorText));
}

function printItems(lista) {
	let sisalto = "";
	for (let rivi of lista) {
		sisalto += "<tr id='rivi_" + rivi.asiakas_id + "'>";
		sisalto += "<td>" + rivi.etunimi + "</td>";
		sisalto += "<td>" + rivi.sukunimi + "</td>";
		sisalto += "<td>" + rivi.puhelin + "</td>";
		sisalto += "<td>" + rivi.sposti + "</td>";
		sisalto += "<td><a id='muuta' href='muutaasiakas.jsp?asiakas_id=" + rivi.asiakas_id + "'>Muuta</a>&nbsp;";
		sisalto += "<span id='poista' onclick=varmistaPoisto('" + rivi.asiakas_id + "','" + rivi.etunimi + "','" + rivi.sukunimi + "')>Poista</span></td>";
		sisalto += "</tr>";
	}
	document.getElementById("tiedot").innerHTML = sisalto;
}

function haeAsiakas() {
    fetch("asiakkaat?asiakas_id=" + requestURLParam("asiakas_id"), {method: "GET", headers: {"Content-Type": "application/x-www-form-urlencoded"}})
    .then(response => response.json())
   	.then(response => {
   		document.getElementById("asiakas_id").value=response.asiakas_id;
   		document.getElementById("etunimi").value=response.etunimi;
   		document.getElementById("sukunimi").value=response.sukunimi;
   		document.getElementById("puhelin").value=response.puhelin;
   		document.getElementById("sposti").value=response.sposti;
   	})
   	.catch(errorText => console.error("Fetch failed: " + errorText));
}

function paivitaTiedot() {
	let formData = serialize_form(document.lomake);
    fetch("asiakkaat", {method: "PUT", headers: {"Content-Type": "application/json; charset=UTF-8"}, body: formData})
    .then(response => response.json())
   	.then(responseObj => {
   		if (responseObj.response == 0) {
   			document.getElementById("ilmo").innerHTML = "Asiakkaan muutos epäonnistui.";
        } else if (responseObj.response == 1) {
        	document.getElementById("ilmo").innerHTML = "Asiakkaan muutos onnistui.";
			document.lomake.reset();
		}
		setTimeout(function(){document.getElementById("ilmo").innerHTML = "";}, 3000);
   	})
   	.catch(errorText => console.error("Fetch failed: " + errorText));
}

function poistaAsiakas(asiakas_id, etunimi, sukunimi) {
    fetch("asiakkaat?asiakas_id=" + asiakas_id, {method: "DELETE"})
    .then(response => response.json())
   	.then(responseObj => {
   		if (responseObj.response == 0) {
			alert("Asiakkaan poisto epäonnistui.");
        } else if (responseObj.response == 1) {
			document.getElementById("rivi_" + asiakas_id).style.backgroundColor = "red";
			alert("Asiakkaan " + etunimi + " " + sukunimi + " poisto onnistui.");
			haeAsiakkaat();
		}
   	})
   	.catch(errorText => console.error("Fetch failed: " + errorText));
}