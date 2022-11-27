<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="script/main.js"></script>
<link rel="stylesheet" href="css/tyylit.css">
<title>Asiakkaan lisäys</title>
</head>
<body>
<form name="lomake">
	<table>
		<thead>	
			<tr>
				<th class="oikea" colspan="5"><a href="listaaasiakkaat.jsp">Takaisin listaukseen</a></th>
			</tr>		
			<tr>
				<th>Etunimi</th>
				<th>Sukunimi</th>
				<th>Puhelin</th>
				<th>Sposti</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><input type="text" name="etunimi" id="etunimi"></td>
				<td><input type="text" name="sukunimi" id="sukunimi"></td>
				<td><input type="text" name="puhelin" id="puhelin"></td>
				<td><input type="text" name="sposti" id="sposti"></td> 
				<td><input type="button" value="Lisää" onclick="tutkiJaLisaa()"></td>
			</tr>
		</tbody>
	</table>
</form>
<p id="ilmo"></p>
</body>
</html>