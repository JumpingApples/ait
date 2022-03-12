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

@WebServlet("/account/accounting_table_credit")
public class AccountingTableCredit extends MiotServlet {
	private static final long serialVersionUID = 1L;
	
	

	public AccountingTableCredit() {
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
				+ "            FLOOR(CASE WHEN credit.credit IS NULL THEN 0 ELSE credit.credit END) AS credit"
				+ "          FROM family"
				+ ""
				+ "          LEFT OUTER JOIN"
				+ "             (SELECT familyID, SUM(amount) AS credit FROM payment"
				+ "              WHERE (paymenttype = 'Credit' OR paymenttype = 'Leftover Credit' OR paymenttype = 'Previous Unpaid Tuition')"
				+ "                AND payment.SchoolYear = " + readSemesterYear(request)
				+ "              GROUP BY familyID) AS credit"
				+ "          ON credit.familyID = family.familyID"
				+ ""
				+ "          WHERE credit > 0"
				+ "          ORDER BY family.familyID;";
		
		ResultSet result = connection.query(query);
		try {
			List<BeanAccountingRecord> table = new ArrayList<BeanAccountingRecord>();
			float total_credit   = 0;
			
			while (result.next()) {
				BeanAccountingRecord row = new BeanAccountingRecord();
				row.setFam_id(result.getString(1));
				row.setF_name(result.getString(2));
				row.setM_name(result.getString(3));
				row.setEmail(result.getString(4));
				row.setCredit(result.getFloat(5));

				table.add(row);
				
				total_credit   += row.getCredit();
			}
			
			request.setAttribute("record_table", table);
			request.setAttribute("total_credit", total_credit);
		} catch (SQLException e) {
			System.out.println("Result set not properly handled at: AccountingTableCredit");
			e.printStackTrace();
		} finally {
			connection.close();
			
			request.getRequestDispatcher("/WEB-INF/header.jsp").include(request, response);
			request.getRequestDispatcher("/WEB-INF/account/accounting_table_credit.jsp").include(request, response);
			request.getRequestDispatcher("/WEB-INF/footer.jsp").include(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
