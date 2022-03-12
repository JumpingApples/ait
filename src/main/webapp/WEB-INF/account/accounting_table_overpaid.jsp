<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"   %>
<%@ taglib tagdir="/WEB-INF/tags"                  prefix="a"   %>

<style>
  .symb { font-family: Lucida Console; font-size: 11pt; width: 1%; }
  .table_type { font-family: Georgia; font-size: 11pt; text-align: center; }
  .ctext  { font-family: Georgia; font-size: 11pt; text-align: center; }
</style>

<script>

</script>

<div style="display:flex;">
  <table border=1 style="width:95%; max-width:900px;">
    <tr style="border:0;">
      <td style="border:0;" colspan=17>
        <h1 style="text-align:center; margin-bottom: 0; margin-bottom: 0;">Overpaid Accounts</h1>
      </td>
    </tr>
    <tr style="height:30px;">
      <th class=ctext>Family ID</th>
      <th class=ctext>Tuition</th>
      <th class=ctext colspan="2">Fees</th>
      <th class=ctext colspan="2">Other</th>
      <th class=ctext colspan="2">Refunded</th>
      <th class=ctext colspan="2">Payments</th>
      <th class=ctext colspan="2">Credit</th>
      <th class=ctext colspan="2">Balance</th>
      <th class=ctext>Father</th>
      <th class=ctext>Mother</th>
      <th class=ctext>Email</th>
    </tr>
    
    <c:forEach items="${ record_table }" var="row">
      <tr style="height:40px;">
        <td class=ctext><a href="family_profile?familyid=${ row.fam_id }">${ row.fam_id }</a></td>
        <td class=ctext><a:acc value="${ row.tuition }" /></td>
        <td class=ctext colspan="2"><a:acc value="${ row.fees }" /></td>
        <td class=ctext colspan="2"><a:acc value="${ row.other_charges }" /></td>
        <td class=ctext colspan="2"><a:acc value="${ -1 * row.refunds }" /></td>
        <td class=ctext colspan="2"><a:acc value="${ row.payments }" /></td>
        <td class=ctext colspan="2"><a:acc value="${ row.credit }" /></td>
        <td class=ctext colspan="2"><a:acc value="${ row.balance }" /></td>
        <td class=ctext>${ row.f_name }</td>
        <td class=ctext>${ row.m_name }</td>
        <td class=ctext>${ row.email }</td>
      </tr>
    </c:forEach>
    
    <tr style="height:30px;">
      <th style="border:none;"></th>
      <th class=ctext>Tuition</th>
      <th class=ctext colspan="2">Fees</th>
      <th class=ctext colspan="2">Other</th>
      <th class=ctext colspan="2">Refunded</th>
      <th class=ctext colspan="2">Payments</th>
      <th class=ctext colspan="2">Credit</th>
      <th class=ctext colspan="2">Balance</th>
    </tr>
    <tr style="height:50px;">
      <td style="text-align:left">Total</td>
      <td class=ctext>${ total_tuition }</td>
      <td class=symb>+</td><td class=ctext>${ total_fees }</td>
      <td class=symb>+</td><td class=ctext>${ total_other }</td>
      <td class=symb>+</td><td class=ctext>${ -1 * total_refunds }</td>
      <td class=symb>-</td><td class=ctext>${ total_payments }</td>
      <td class=symb>-</td><td class=ctext>${ total_credit }</td>
      <td class=symb>=</td><td class=ctext>${ total_balance }</td>
    </tr>
  </table>
  
  <div style="flex-grow:1; margin-left:20px;">
    <form name="table_type" style="position:sticky; top:75px;">
      <select style="width:120px; overflow-y:auto" size="5">
        <option class="table_type" onclick="location.href='accounting_table_all';">All</option>
        <option class="table_type" onclick="location.href='accounting_table_overpaid';" selected>Overpaid</option>
        <option class="table_type" onclick="location.href='accounting_table_unpaid';">Unpaid</option>
        <option class="table_type" onclick="location.href='accounting_table_credit';">Credit</option>
        <option class="table_type" onclick="location.href='accounting_table_refunds';">Refunds</option>
      </select>
    </form>
  </div>
</div>