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

@WebServlet(urlPatterns = {"/account/accounting_table",
						   "/account/accounting_table_all"})
public class AccountingTableAll extends MiotServlet {
	private static final long serialVersionUID = 1L;
	
	

	public AccountingTableAll() {
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
				+ "            tuition,"
				+ "            CASE WHEN tuition = 0 THEN 0 ELSE "
				+ 			   hardCodedAdminFee() + " + 0 + " + hardCodedPDFee() + " END AS fees,"
				+ "            FLOOR(CASE WHEN other.other IS NULL THEN 0 ELSE other.other END) AS other,"
				+ "            FLOOR(CASE WHEN refunds.refunds IS NULL THEN 0 ELSE refunds.refunds END) AS refunds,"
				+ "            FLOOR(CASE WHEN paid.paid IS NULL THEN 0 ELSE paid.paid END) AS paid,"
				+ "            FLOOR(CASE WHEN credit.credit IS NULL THEN 0 ELSE credit.credit END) AS credit"
				+ "          FROM family,"
				+ "             (SELECT family.familyID,"
				+ "                SUM(CASE"
				+ "                      WHEN tuition IS NULL THEN 0"
				+ "                      WHEN class_registration.regStatus = 'C' THEN 0"
				+ "                      ELSE tuition"
				+ "                    END) AS tuition"
				+ "              FROM family"
				+ "              LEFT OUTER JOIN family_member"
				+ "                ON family.familyID = family_member.familyID"
				+ "              LEFT OUTER JOIN class_registration"
				+ "                ON family_member.memberID = class_registration.studentID"
				+ "              LEFT OUTER JOIN course_year"
				+ "                ON class_registration.courseID = course_year.courseID"
				+ "                AND class_registration.schoolYear = course_year.year"
				+ "              WHERE course_year.year = " + readSemesterYear(request)
				+ "              GROUP BY family.familyID"
				+ "             ) AS regis_tuitions"
				+ ""
				+ "          LEFT OUTER JOIN"
				+ "             (SELECT familyID, SUM(amount) AS other FROM fee_billed"
				+ "              WHERE item NOT IN ('Registration Fee','Online Discount','Late Fee')"
				+ "                AND year = " + readSemesterYear(request)
				+ "              GROUP BY familyID) AS other"
				+ "          ON other.familyID = regis_tuitions.familyID"
				+ ""
				+ "          LEFT OUTER JOIN"
				+ "             (SELECT familyID, SUM(amount) AS paid FROM payment"
				+ "              WHERE paymenttype = 'Payment'"
				+ "                AND payment.SchoolYear = " + readSemesterYear(request)
				+ "              GROUP BY familyID) AS paid"
				+ "          ON paid.familyID = regis_tuitions.familyID"
				+ ""
				+ "          LEFT OUTER JOIN"
				+ "             (SELECT familyID, SUM(amount) AS refunds FROM payment"
				+ "              WHERE paymenttype = 'Refund'"
				+ "                AND payment.SchoolYear = " + readSemesterYear(request)
				+ "              GROUP BY familyID) AS refunds"
				+ "          ON refunds.familyID = regis_tuitions.familyID"
				+ ""
				+ "          LEFT OUTER JOIN"
				+ "             (SELECT familyID, SUM(amount) AS credit FROM payment"
				+ "              WHERE (paymenttype = 'Credit' OR paymenttype = 'Leftover Credit' OR paymenttype = 'Previous Unpaid Tuition')"
				+ "                AND payment.SchoolYear = " + readSemesterYear(request)
				+ "              GROUP BY familyID) AS credit"
				+ "          ON credit.familyID = regis_tuitions.familyID"
				+ ""
				+ "          WHERE family.familyID = regis_tuitions.familyID"
				+ "          ORDER BY family.familyID;";
		
		ResultSet result = connection.query(query);
		try {
			List<BeanAccountingRecord> table = new ArrayList<BeanAccountingRecord>();
			float total_tuition  = 0;
			float total_fees     = 0;
			float total_other    = 0;
			float total_refunds  = 0;
			float total_payments = 0;
			float total_credit   = 0;
			float total_balance  = 0;
			
			while (result.next()) {
				BeanAccountingRecord row = new BeanAccountingRecord();
				row.setFam_id(result.getString(1));
				row.setF_name(result.getString(2));
				row.setM_name(result.getString(3));
				row.setEmail(result.getString(4));
				row.setTuition(result.getFloat(5));
				row.setFees(result.getFloat(6));
				row.setOther_charges(result.getFloat(7));
				row.setRefunds(result.getFloat(9));
				row.setPayments(result.getFloat(8));
				row.setCredit(result.getFloat(10));
				
				float balance = row.getTuition() + row.getFees()     + row.getOther_charges()
							  + row.getRefunds() - row.getPayments() - row.getCredit();
				row.setBalance(balance);

				table.add(row);
				
				total_tuition  += row.getTuition();
				total_fees     += row.getFees();
				total_other    += row.getOther_charges();
				total_refunds  += row.getRefunds();
				total_payments += row.getPayments();
				total_credit   += row.getCredit();
				total_balance  += row.getBalance();
			}
			
			request.setAttribute("record_table", table);
			request.setAttribute("total_tuition", total_tuition);
			request.setAttribute("total_fees", total_fees);
			request.setAttribute("total_other", total_other);
			request.setAttribute("total_refunds", total_refunds);
			request.setAttribute("total_payments", total_payments);
			request.setAttribute("total_credit", total_credit);
			request.setAttribute("total_balance", total_balance);
		} catch (SQLException e) {
			System.out.println("Result set not properly handled at: AccountingTableAll");
			e.printStackTrace();
		} finally {
			connection.close();
			
			request.getRequestDispatcher("/WEB-INF/header.jsp").include(request, response);
			request.getRequestDispatcher("/WEB-INF/account/accounting_table_all.jsp").include(request, response);
			request.getRequestDispatcher("/WEB-INF/footer.jsp").include(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
