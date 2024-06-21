
<%@page import="com.rays.pro4.controller.VehicleCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.UserCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>User Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udatee").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:20024',
		//dateFormat:'yy-mm-dd'
		});
	});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.VehicleBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.VEHICLE_CTL%>" method="post">

			<%
				List list = (List) request.getAttribute("vList");
			%>


			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Vehicle </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Vehicle</font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> 

			<table>
				<tr>
					<th align="left">Name <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="name"
						placeholder="Enter Name" size="25"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Purchase BY <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="purchaseBy"
						placeholder="Enter puchaseby" size="25"
						value="<%=DataUtility.getStringData(bean.getPurchaseBy())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("purchaseBy", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Company <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList("company", String.valueOf(bean.getCompany()), list)%></td>
					<td style="position: fixed"><font style="position: fixed"
						color="red"> <%=ServletUtility.getErrorMessage("company", request)%></font></td>
				</tr>
				
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Manufactured BY <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="manufacturedBy"
						placeholder="Enter manufacturedBy" size="25"
						value="<%=DataUtility.getStringData(bean.getManufacturedBy())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("manufacturedBy", request)%></font></td>
				</tr>
				
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">purchase date<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="dob"
						placeholder="Enter Date" size="25" readonly="readonly"
						id="udatee" value="<%=DataUtility.getDateString(bean.getPurchaseDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr>
					<%
						if (bean.getId() > 0) {
					%>
					
					&nbsp;
					<td align = "right" colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value ="<%=VehicleCtl.OP_UPDATE %>" > &nbsp;
						&nbsp; <input type="submit" name="operation" value ="<%=VehicleCtl.OP_CANCEL%>"
					></td>

					<%
						} else {
					%>
					
					&nbsp;&nbsp;
					<td align = "right" scolspan="2" >&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=VehicleCtl.OP_SAVE %>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=VehicleCtl.OP_RESET%>"></td>

					<%
						}
					%>
				</tr>
			</table>
		</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>
