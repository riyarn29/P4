
<%@page import="com.rays.pro4.controller.ProductCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Product Page</title>

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
			yearRange : '1900:2024',
		//dateFormat:'yy-mm-dd'
		});
	});
</script>

<script>
function validateNumericInput(inputField) {
    // Get the value entered by the user
    var inputValue = inputField.value;

    // Regular expression to check if the input is numeric
    var numericPattern = /^\d*$/;

    // Test the input value against the numeric pattern
    if (!numericPattern.test(inputValue)) {
        // If input is not numeric, clear the field
        inputField.value = inputValue.replace(/[^\d]/g, ''); // Remove non-numeric characters
    }
}
</script>


<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ProductBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.PRODUCT_CTL%>" method="post">

			<%-- <%
				List list = (List) request.getAttribute("jList");
			%> --%>


			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update product </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add product</font></th>
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
					<th align="left">Name: <span style="color: red">*</span>

					</th>
					<td><input type="text" name="name" placeholder="Enter name"
						size="25" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>


				<tr>
					<th align="left">Status<span style="color: red">*</span> :
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("sold", "sold");
							map.put("unsold", "unsold");
							
							String hlist = HTMLUtility.getList("status", String.valueOf(bean.getStatus()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left"> Date<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="dob" placeholder="Enter Date"
						size="25" readonly="readonly" id="udatee"
						value="<%=DataUtility.getDateString(bean.getPurchaseDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">Amount: <span style="color: red">*</span>

					</th>
					<td><input type="text" name="amount" placeholder="Enter amount" oninput="validateNumericInput(this)"
						size="25" value="<%=DataUtility.getStringData(bean.getAmount())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("amount", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
					<%
						if (bean.getId() > 0) {
					%>

					&nbsp;
					<td align="right" colspan="2">&nbsp; &emsp; <input
						type="submit" name="operation"
						value="<%=ProductCtl.OP_UPDATE%>"> &nbsp; &nbsp;
						<input type="submit" name="operation"
						value="<%=ProductCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					&nbsp;&nbsp;
					<td align="right" scolspan="2">&nbsp; &emsp; <input
						type="submit" name="operation"
						value="<%=ProductCtl.OP_SAVE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation"
						value="<%=ProductCtl.OP_RESET%>"></td>

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