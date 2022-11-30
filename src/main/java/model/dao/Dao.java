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
		String url = "jdbc:sqlite:" + path + "/webapps/" + db;
		//Eclipse url: "jdbc:sqlite:" + path.substring(0, path.indexOf(".metadata")).replace("\\", "/") + db;
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

	public Asiakas getItem(int asiakas_id) {
		Asiakas asiakas = null;
		try {
			con = yhdista();
			if (con != null) {
				stmtPrep = con.prepareStatement("SELECT * FROM asiakkaat WHERE asiakas_id=?");
				stmtPrep.setInt(1, asiakas_id);
        		rs = stmtPrep.executeQuery();
        		if (rs.isBeforeFirst()) {
        			rs.next();
					asiakas = new Asiakas();
					asiakas.setAsiakas_id(rs.getInt("asiakas_id"));
					asiakas.setEtunimi(rs.getString("etunimi"));
					asiakas.setSukunimi(rs.getString("sukunimi"));
					asiakas.setPuhelin(rs.getString("puhelin"));
					asiakas.setSposti(rs.getString("sposti"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sulje();
		}
		return asiakas;
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
				stmtPrep = con.prepareStatement("SELECT * FROM asiakkaat WHERE UPPER(etunimi) LIKE ? OR UPPER(sukunimi) LIKE ? ORDER BY asiakas_id");
				stmtPrep.setString(1, "%" + hakusana.toUpperCase() + "%");
				stmtPrep.setString(2, "%" + hakusana.toUpperCase() + "%");
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

	public boolean addItem(Asiakas asiakas) {
		boolean lisatty = false;
		try {
			con = yhdista();
			if (con != null) {
				stmtPrep = con.prepareStatement("INSERT INTO asiakkaat (etunimi, sukunimi, puhelin, sposti) VALUES (?, ?, ?, ?)");
				stmtPrep.setString(1, asiakas.getEtunimi());
				stmtPrep.setString(2, asiakas.getSukunimi());
				stmtPrep.setString(3, asiakas.getPuhelin());
				stmtPrep.setString(4, asiakas.getSposti());
				stmtPrep.executeUpdate();
				lisatty = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sulje();
		}
		return lisatty;
	}

	public boolean changeItem(Asiakas asiakas) {
		boolean paivitetty = false;
		try {
			con = yhdista();
			if (con != null) {
				stmtPrep=con.prepareStatement("UPDATE asiakkaat SET etunimi=?, sukunimi=?, puhelin=?, sposti=? WHERE asiakas_id=?");
				stmtPrep.setString(1, asiakas.getEtunimi());
				stmtPrep.setString(2, asiakas.getSukunimi());
				stmtPrep.setString(3, asiakas.getPuhelin());
				stmtPrep.setString(4, asiakas.getSposti());
				stmtPrep.setInt(5, asiakas.getAsiakas_id());
				stmtPrep.executeUpdate();
				paivitetty = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sulje();
		}
		return paivitetty;
	}

	public boolean removeItem(int asiakas_id) {
		boolean poistettu = false;
		try {
			con = yhdista();
			if (con != null) {
				stmtPrep = con.prepareStatement("DELETE FROM asiakkaat WHERE asiakas_id=?");
				stmtPrep.setInt(1, asiakas_id);
				stmtPrep.executeUpdate();
				poistettu = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sulje();
		}
		return poistettu;
	}
	
	public String findUser(String uid, String pwd) {
		String nimi = null;
		try {
			con = yhdista();
			if (con != null) {
				stmtPrep = con.prepareStatement("SELECT * FROM asiakkaat WHERE sposti=? AND salasana=?");
				stmtPrep.setString(1, uid);
				stmtPrep.setString(2, pwd);
        		rs = stmtPrep.executeQuery();
        		if (rs.isBeforeFirst()) {
        			rs.next();
        			nimi = rs.getString("etunimi") + " " + rs.getString("sukunimi");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sulje();
		}
		return nimi;
	}
}