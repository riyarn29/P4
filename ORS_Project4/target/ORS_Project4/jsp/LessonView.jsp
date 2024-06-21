
<%@page import="com.rays.pro4.controller.LessonCtl"%>
<%@page import="com.rays.pro4.controller.BankCtl"%>
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
<title>Lesson Page</title>

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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.LessonBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.LESSON_CTL%>" method="post">

			<%
				List s = (List) request.getAttribute("sub");
			%>


			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update lesson </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add lesson</font></th>
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
					<th align="left"> Lesson Name <span style="color: red">*</span>
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
					<th align="left">Author <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="author"
						placeholder="Enter author" size="25"
						value="<%=DataUtility.getStringData(bean.getAuthor())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("author", request)%></font></td>

				</tr>
				
				<tr>
					<th style="padding: 3px"></th>
				</tr>

<tr>
					<tr>
					<th align="left">SUBJECT <span style="color: red">*</span> :
					</th>
					<td>
					<%
							HashMap map = new HashMap();
							map.put("Science", "Science");
							map.put("Maths", "Maths");
							map.put("Hindi", "Hindi");
							map.put("English", "English");

							String htmlList = HTMLUtility.getList("subject", bean.getSubject(), map);
						%> <%=htmlList%>
				</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("subject", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left"> Author dob<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="dob"
						placeholder="Enter author dob" size="25" readonly="readonly"
						id="udatee" value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr>
					&nbsp;
						&nbsp;
					<%
						if (bean.getId() > 0) {
					%>
						&nbsp;<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value ="<%=LessonCtl.OP_UPDATE %>" > &nbsp;
						&nbsp; <input type="submit" name="operation" value ="<%=LessonCtl.OP_CANCEL%>"
					></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=LessonCtl.OP_SAVE %>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=LessonCtl.OP_RESET%>"></td>

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
