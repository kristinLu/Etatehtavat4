package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Asiakas;
import model.dao.Dao;

@WebServlet("/asiakkaat/*")
public class Asiakkaat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Asiakkaat() {
        //System.out.println("Asiakkaat.Asiakkaat()");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("Asiakkaat.doGet()");
		Dao dao = new Dao();
		ArrayList<Asiakas> asiakkaat;
		String hakusana = request.getParameter("hakusana");
		String asiakas_id = request.getParameter("asiakas_id");
		String strJSON = "";
		if (hakusana != null) {
			if (!hakusana.equals("")) {
				asiakkaat = dao.getAllItems(hakusana);
			} else {
				asiakkaat = dao.getAllItems();
			}
			strJSON = new Gson().toJson(asiakkaat);
		} else if (asiakas_id != null) {
			Asiakas asiakas = dao.getItem(Integer.parseInt(asiakas_id));
			strJSON = new Gson().toJson(asiakas);
		} else {
			asiakkaat = dao.getAllItems();
			strJSON = new Gson().toJson(asiakkaat);
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().println(strJSON);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("Asiakkaat.doPost()");
		Dao dao = new Dao();
		String strJSONInput = request.getReader().lines().collect(Collectors.joining());
		Asiakas asiakas = new Gson().fromJson(strJSONInput, Asiakas.class);
		response.setContentType("application/json; charset=UTF-8");
		if (dao.addItem(asiakas)) {
			response.getWriter().println("{\"response\":1}");
		} else {
			response.getWriter().println("{\"response\":0}");
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("Asiakkaat.doPut()");
		Dao dao = new Dao();
		String strJSONInput = request.getReader().lines().collect(Collectors.joining());
		Asiakas asiakas = new Gson().fromJson(strJSONInput, Asiakas.class);
		response.setContentType("application/json; charset=UTF-8");
		if (dao.changeItem(asiakas)) {
			response.getWriter().println("{\"response\":1}");
		} else {
			response.getWriter().println("{\"response\":0}");
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("Asiakkaat.doDelete()");
		Dao dao = new Dao();
		int asiakas_id = Integer.parseInt(request.getParameter("asiakas_id"));
		response.setContentType("application/json; charset=UTF-8");
		if (dao.removeItem(asiakas_id)) {
			response.getWriter().println("{\"response\":1}");
		} else {
			response.getWriter().println("{\"response\":0}");
		}
	}
}
