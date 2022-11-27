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
		sisalto += "<td><span id='poista' onclick=varmistaPoisto('" + rivi.asiakas_id + "','" + rivi.etunimi + "','" + rivi.sukunimi + "')>Poista</span></td>";
		sisalto += "</tr>";
	}
	document.getElementById("tiedot").innerHTML = sisalto;
}

function tutkiJaLisaa() {
	if (tutkiTiedot()) {
		lisaaTiedot();
	}
}

function tutkiTiedot() {
	let ilmo = "";
	if (/^[A-zÅÄÖåäö]{2,}(\-[A-zÅÄÖåäö]{2,})?$/.test(document.getElementById("etunimi").value.trim()) == false) {
		ilmo = "Etunimi ei kelpaa!";	
		document.getElementById("etunimi").focus();
	} else if (/^[A-zÅÄÖåäö]{2,}(\-[A-zÅÄÖåäö]{2,})?$/.test(document.getElementById("sukunimi").value.trim()) == false) {
		ilmo = "Sukunimi ei kelpaa!";
		document.getElementById("sukunimi").focus();			
	} else if (/^\d{2,3}\-\d{5,10}$/.test(document.getElementById("puhelin").value.trim()) == false) {
		ilmo = "Syötä puhelinnumero muodossa 012-3456789 / 01-23456789";	
		document.getElementById("puhelin").focus();	
	} else if (/^[A-z0-9]+([\w.-]+)?[A-z0-9]+@[a-z0-9]+([a-z0-9.-]+)?[a-z0-9]+\.[a-z]{2,3}$/.test(document.getElementById("sposti").value.trim()) == false) {
		ilmo = "Sähköposti ei kelpaa!";	
		document.getElementById("sposti").focus();	
	}
	
	if (ilmo != "") {
		document.getElementById("ilmo").innerHTML = ilmo;
		setTimeout(function(){document.getElementById("ilmo").innerHTML = ""; }, 3000);
		return false;
	} else {
		document.getElementById("etunimi").value = isolla(siivoa(document.getElementById("etunimi").value.trim()));
		document.getElementById("sukunimi").value = isolla(siivoa(document.getElementById("sukunimi").value.trim()));
		document.getElementById("puhelin").value = siivoa(document.getElementById("puhelin").value.trim());
		document.getElementById("sposti").value = siivoa(document.getElementById("sposti").value.trim());	
		return true;
	}
}

function siivoa(teksti) {
	teksti = teksti.replace(/</g, "");
	teksti = teksti.replace(/>/g, "");	
	teksti = teksti.replace(/'/g, "''");	
	return teksti;
}

function isolla(teksti) {
	let nimi = teksti.charAt(0).toUpperCase() + teksti.slice(1).toLowerCase();
	if (nimi.includes("-")) {
		let viiva = nimi.indexOf("-")
		nimi = nimi.slice(0, viiva + 1) + nimi.charAt(viiva + 1).toUpperCase() + nimi.slice(viiva + 2);
	}
	return nimi;
}

function lisaaTiedot() {
	let formData = serialize_form(document.lomake);    
    fetch("asiakkaat", {method: "POST", headers: {"Content-Type": "application/json"}, body: formData})
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

function serialize_form(form) {
	return JSON.stringify(
	    Array.from(new FormData(form).entries())
	        .reduce((m, [key, value]) => Object.assign(m, {[key]: value}), {})
	        );	
}

function varmistaPoisto(id, etunimi, sukunimi) {
	if (confirm("Poistetaanko asiakas " + etunimi + " " + sukunimi + "?")) {
		poistaAsiakas(id, etunimi, sukunimi);
	}
}

function poistaAsiakas(id, etunimi, sukunimi) {   
    fetch("asiakkaat?id=" + id, {method: "DELETE"})
    .then(response => response.json())
   	.then(responseObj => {	
   		if (responseObj.response == 0) {
			alert("Asiakkaan poisto epäonnistui.");	        	
        } else if (responseObj.response == 1) { 
			document.getElementById("rivi_" + id).style.backgroundColor = "red";
			alert("Asiakkaan " + etunimi + " " + sukunimi + " poisto onnistui.");
			haeAsiakkaat();        	
		}
   	})
   	.catch(errorText => console.error("Fetch failed: " + errorText));
}