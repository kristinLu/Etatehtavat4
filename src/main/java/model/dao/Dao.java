package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Asiakas;

public class Dao {
	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep = null;
	private String db = "Myynti.sqlite";
	
	private Connection yhdista() {
		String path = System.getProperty("catalina.base");
		String url = "jdbc:sqlite:" + path.substring(0, path.indexOf(".metadata")).replace("\\", "/") + db;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(url);
			System.out.println("Yhteys avattu.");
		} catch (Exception e) {
			System.out.println("Yhteyden avaus epäonnistui.");
			e.printStackTrace();
		}
		return con;
	}
	
	private void sulje() {
		if (stmtPrep != null) {
			try {
				stmtPrep.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
				System.out.println("Yhteys suljettu.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Asiakas> getAllItems() {
		ArrayList<Asiakas> asiakkaat = new ArrayList<Asiakas>();
		try {
			con = yhdista();
			if (con != null) {
				stmtPrep = con.prepareStatement("SELECT * FROM asiakkaat ORDER BY asiakas_id");
				rs = stmtPrep.executeQuery();
				if (rs != null) {
					while (rs.next()) {
						Asiakas asiakas = new Asiakas();
						asiakas.setAsiakas_id(rs.getInt("asiakas_id"));
						asiakas.setEtunimi(rs.getString("etunimi"));
						asiakas.setSukunimi(rs.getString("sukunimi"));
						asiakas.setPuhelin(rs.getString("puhelin"));
						asiakas.setSposti(rs.getString("sposti"));
						asiakkaat.add(asiakas);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sulje();
		}
		return asiakkaat;
	}
	
	public ArrayList<Asiakas> getAllItems(String hakusana) {
		ArrayList<Asiakas> asiakkaat = new ArrayList<Asiakas>();
		try {
			con = yhdista();
			if (con != null) {
				stmtPrep = con.prepareStatement("SELECT * FROM asiakkaat WHERE etunimi LIKE ? OR sukunimi LIKE ? ORDER BY asiakas_id");
				stmtPrep.setString(1, "%" + hakusana + "%");
				stmtPrep.setString(2, "%" + hakusana + "%");
				rs = stmtPrep.executeQuery();
				if (rs != null) {
					while (rs.next()) {
						Asiakas asiakas = new Asiakas();
						asiakas.setAsiakas_id(rs.getInt("asiakas_id"));
						asiakas.setEtunimi(rs.getString("etunimi"));
						asiakas.setSukunimi(rs.getString("sukunimi"));
						asiakas.setPuhelin(rs.getString("puhelin"));
						asiakas.setSposti(rs.getString("sposti"));
						asiakkaat.add(asiakas);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sulje();
		}
		return asiakkaat;
	}
}
