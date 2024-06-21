

<%@page import="com.rays.pro4.controller.WishCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Wish Page</title>

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
			yearRange : '1980:2002',
		});
	});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.WishBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.WISH_CTL%>" method="post">

			<div align="center">
				<h1>
					<%
						List rlist = (List) request.getAttribute("rlist");
					%>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Wish </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Wish </font></th>
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
					<th align="left">Product<span style="color: red">*</span> :
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Phone", "Phone");
							map.put("Book", "Book");
							map.put("Leptop", "Leptop");

							String hlist = HTMLUtility.getList("Product", String.valueOf(bean.getProduct()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Product", request)%></font></td>
				</tr>





				<tr>
					<th align="left">Date Of Birth<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="Dob"
						placeholder="Enter Date of Birth" size="25" id="udatee"
						readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("Dob", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>


				<tr>
					<th align="left">User Name<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="UserName"
						placeholder="Enter UserName" size="25"
						value="<%=DataUtility.getStringData(bean.getUserName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("UserName", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="left">Remark<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="Remark"
						placeholder="Enter  Remark" size="25"
						value="<%=DataUtility.getStringData(bean.getRemark())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Remark", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>






				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=WishCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=WishCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=WishCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=WishCtl.OP_RESET%>"></td>

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
