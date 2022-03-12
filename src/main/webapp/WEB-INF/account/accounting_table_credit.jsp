<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"   %>
<%@ taglib tagdir="/WEB-INF/tags"                  prefix="a"   %>

<style>
  .table_type { font-family: Georgia; font-size: 11pt; text-align: center; }
  .ctext  { font-family: Georgia; font-size: 11pt; text-align: center; }
</style>

<script>

</script>

<div style="display:flex;">
  <table border=1 style="width:95%; max-width:900px;">
    <tr style="border:0;">
      <td style="border:0;" colspan=5>
        <h1 style="text-align:center; margin-bottom: 0; margin-bottom: 0;">Issued Credit</h1>
      </td>
    </tr>
    <tr style="height:30px;">
      <th class=ctext>Family ID</th>
      <th class=ctext>Credit</th>
      <th class=ctext>Father</th>
      <th class=ctext>Mother</th>
      <th class=ctext>Email</th>
    </tr>
    
    <c:forEach items="${ record_table }" var="row">
      <tr style="height:40px;">
        <td class=ctext><a href="family_profile?familyid=${ row.fam_id }">${ row.fam_id }</a></td>
        <td class=ctext><a:acc value="${ row.credit }" /></td>
        <td class=ctext>${ row.f_name }</td>
        <td class=ctext>${ row.m_name }</td>
        <td class=ctext>${ row.email }</td>
      </tr>
    </c:forEach>
    
    <tr style="height:50px;">
      <td style="text-align:left">Total Credit Issued</td>
      <td class=ctext>${ total_credit }</td>
    </tr>
  </table>
  
  <div style="flex-grow:1; margin-left:20px;">
    <form name="table_type" style="position:sticky; top:75px;">
      <select style="width:120px; overflow-y:auto" size="5">
        <option class="table_type" onclick="location.href='accounting_table_all';">All</option>
        <option class="table_type" onclick="location.href='accounting_table_overpaid';">Overpaid</option>
        <option class="table_type" onclick="location.href='accounting_table_unpaid';">Unpaid</option>
        <option class="table_type" onclick="location.href='accounting_table_credit';" selected>Credit</option>
        <option class="table_type" onclick="location.href='accounting_table_refunds';">Refunds</option>
      </select>
    </form>
  </div>
</div>