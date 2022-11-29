function requestURLParam(sParam) {
    let sPageURL = window.location.search.substring(1);
    let sURLVariables = sPageURL.split("&");
    for (let i = 0; i < sURLVariables.length; i++) {
        let sParameterName = sURLVariables[i].split("=");
        if (sParameterName[0] == sParam) {
            return sParameterName[1];
        }
    }
}
function tutkiJaLisaa() {
	if (tutkiTiedot()) {
		lisaaTiedot();
	}
}

function tutkiJaPaivita() {
	if (tutkiTiedot()) {
		paivitaTiedot();
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

function serialize_form(form) {
	return JSON.stringify(
	    Array.from(new FormData(form).entries())
	        .reduce((m, [key, value]) => Object.assign(m, {[key]: value}), {})
	        );
}

function varmistaPoisto(asiakas_id, etunimi, sukunimi) {
	if (confirm("Poistetaanko asiakas " + etunimi + " " + sukunimi + "?")) {
		poistaAsiakas(asiakas_id, etunimi, sukunimi);
	}
}

function asetaFocus(target) {
	document.getElementById(target).focus();
}

function tutkiKey(event, target) {
	if (event.keyCode == 13) {
		if (target == "listaa") {
			haeAsiakkaat();
		} else if (target == "lisaa") {
			tutkiJaLisaa();
		} else if (target == "paivita") {
			tutkiJaPaivita();
		}
	} else if (event.keyCode == 113) {
		document.location="listaaasiakkaat.jsp";
	}
}