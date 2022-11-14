package control;

import java.io.IOException;
import java.util.ArrayList;

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
		if (!hakusana.equals("")) {
			asiakkaat = dao.getAllItems(hakusana);
		} else {
			asiakkaat = dao.getAllItems();
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().println(new Gson().toJson(asiakkaat));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("Asiakkaat.doPost()");
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("Asiakkaat.doPut()");
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("Asiakkaat.doDelete()");
	}
}
