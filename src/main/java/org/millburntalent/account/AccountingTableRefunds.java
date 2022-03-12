package org.millburntalent.account;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.millburntalent.DataConnection;
import org.millburntalent.MiotServlet;

@WebServlet("/account/accounting_table_refunds")
public class AccountingTableRefunds extends MiotServlet {
	private static final long serialVersionUID = 1L;
	
	

	public AccountingTableRefunds() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		DataConnection connection = new DataConnection().init();
		if (connection == null) {
			response.getWriter().append("SQL connection failed.");
			return;
		}
		
		String query = "SELECT family.familyID,"
				+ "            CASE WHEN fathername = '' THEN fathercname ELSE fathername END AS fathername,"
				+ "            CASE WHEN mothername = '' THEN mothercname ELSE mothername END AS mothername,"
				+ "            e_mail,"
				+ "            FLOOR(CASE WHEN refunds.refunds IS NULL THEN 0 ELSE refunds.refunds END) AS refunds"
				+ "          FROM family"
				+ ""
				+ "          LEFT OUTER JOIN"
				+ "             (SELECT familyID, SUM(amount) AS refunds FROM payment"
				+ "              WHERE paymenttype = 'Refund'"
				+ "                AND payment.SchoolYear = " + readSemesterYear(request)
				+ "              GROUP BY familyID) AS refunds"
				+ "          ON refunds.familyID = family.familyID"
				+ ""
				+ "          WHERE refunds > 0"
				+ "          ORDER BY family.familyID;";
		
		ResultSet result = connection.query(query);
		try {
			List<BeanAccountingRecord> table = new ArrayList<BeanAccountingRecord>();
			float total_refunds  = 0;
			
			while (result.next()) {
				BeanAccountingRecord row = new BeanAccountingRecord();
				row.setFam_id(result.getString(1));
				row.setF_name(result.getString(2));
				row.setM_name(result.getString(3));
				row.setEmail(result.getString(4));
				row.setRefunds(result.getFloat(5));

				table.add(row);
				
				total_refunds  += row.getRefunds();
			}
			
			request.setAttribute("record_table", table);
			request.setAttribute("total_refunds", total_refunds);
		} catch (SQLException e) {
			System.out.println("Result set not properly handled at: AccountingTableRefunds");
			e.printStackTrace();
		} finally {
			connection.close();
			
			request.getRequestDispatcher("/WEB-INF/header.jsp").include(request, response);
			request.getRequestDispatcher("/WEB-INF/account/accounting_table_refunds.jsp").include(request, response);
			request.getRequestDispatcher("/WEB-INF/footer.jsp").include(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
