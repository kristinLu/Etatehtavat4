package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.Dao;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String logout = request.getParameter("logout");
		if (logout != null) {
			HttpSession session = request.getSession(true);
			session.invalidate();
			response.sendRedirect("index.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Dao dao = new Dao();
		String uid = request.getParameter("uid");
		String hashedPwd = request.getParameter("hashedPwd");
		String nimi = dao.findUser(uid, hashedPwd);
		response.setContentType("text/html; charset=UTF-8");
		if (nimi != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("kayttaja", nimi);
			response.getWriter().print("listaaasiakkaat.jsp");
		} else {
			response.getWriter().print("index.jsp?unknown=1");
		}
	}
}
