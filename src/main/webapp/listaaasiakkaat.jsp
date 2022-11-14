<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="script/main.js"></script>
<link rel="stylesheet" href="css/tyylit.css">
<title>Asiakkaat</title>
</head>
<body>
<table id="asiakkaat">
	<thead>
		<tr>
			<th id="label" colspan="2">Hakusana:</th>
			<th><input type="text" id="hakusana"></th>
			<th><input type="button" value="Hae" id="hakunappi" onclick="haeAsiakkaat()"></th>
		</tr>
		<tr>
			<th>Etunimi</th>
			<th>Sukunimi</th>
			<th>Puhelin</th>
			<th>Sposti</th>
		</tr>
	</thead>
	<tbody id="tiedot"></tbody>
</table>
<script>haeAsiakkaat();</script>
</body>
</html>