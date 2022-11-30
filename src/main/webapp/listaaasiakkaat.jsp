<%@ include file="header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="script/main.js"></script>
<script src="script/io.js"></script>
<link rel="stylesheet" href="css/tyylit.css">
<title>Asiakkaat</title>
</head>
<body onload="asetaFocus('hakusana')" onkeydown="tutkiKey(event, 'listaa')">
<table id="asiakkaat">
	<thead>
		<tr>
			<th colspan="3"><a href="Login?logout=1">Kirjaudu ulos (<%out.print(session.getAttribute("kayttaja"));%>)</a></th>
			<th class="oikea" colspan="2"><a href="lisaaasiakas.jsp">Lisää uusi asiakas</a></th>
		</tr>
		<tr>
			<th class="oikea" colspan="2">Hakusana:</th>
			<th colspan="2"><input type="text" id="hakusana"></th>
			<th><input type="button" value="Hae" id="hakunappi" onclick="haeAsiakkaat(), asetaFocus('hakusana')"></th>
		</tr>
		<tr>
			<th>Etunimi</th>
			<th>Sukunimi</th>
			<th>Puhelin</th>
			<th>Sposti</th>
			<th></th>
		</tr>
	</thead>
	<tbody id="tiedot"></tbody>
</table>
<script>haeAsiakkaat();</script>
</body>
</html>