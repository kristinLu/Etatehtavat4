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
		sisalto += "<tr id='rivi_" + rivi.id + "'>";
		sisalto += "<td>" + rivi.etunimi + "</td>";
		sisalto += "<td>" + rivi.sukunimi + "</td>";
		sisalto += "<td>" + rivi.puhelin + "</td>";
		sisalto += "<td>" + rivi.sposti + "</td>";
		sisalto += "</tr>";
	}
	document.getElementById("tiedot").innerHTML = sisalto;
}