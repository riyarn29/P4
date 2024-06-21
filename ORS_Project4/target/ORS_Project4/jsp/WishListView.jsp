<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.ProductListCtl"%>
<%@page import="com.rays.pro4.Bean.WishBean"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Wish List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>

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
		
		});
	});
</script>

</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.WishBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.WISH_LIST_CTL%>" method="post">
		<center>

			<div align="center">
				<h1>Wish List</h1>

				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<%
				List plist = (List) request.getAttribute("prlist");

				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<WishBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<tr>
					<th></th>

				<!-- 	<td align="center"> -->
					<label>UserName:</font> 
					</label> <input type="text" name="UserName" align = "left"
						placeholder="Enter UserName"
						value="<%=ServletUtility.getParameter("UserName", request)%>"> &nbsp;
					
				
				
			 &nbsp;	
					
						
					 <label>Product</font>:
					</label>	<%=HTMLUtility.getList("Product",String.valueOf(bean.getProduct()) ,plist) %>
						
						&nbsp;	<label> Date </font> 
					</label> <input type="text" name="Dob" id="udatee"
						placeholder="Enter Date"
						value="<%=ServletUtility.getParameter("Dob", request)%>"> &nbsp;
					
							
						<label>Remark:</font> 
					</label> <input type="text" name="Remark"
						placeholder="Enter Remark "
						value="<%=ServletUtility.getParameter("Remark", request)%>"> &nbsp;
					
					
					
				<%-- 	 <label>Client</font> :
					</label> <%=HTMLUtility.getList("client", String.valueOf(bean.getClient()), jlist)%>
						&nbsp; 
  --%>
						<input type="submit" name="operation"
						value="<%=ProductListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation" value="<%=ProductListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr style="background: pink">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>Product</th>
					<th>UserName</th>
					<th>Date</th>
					<th>Remark</th>
					
					<th>Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
							bean = it.next();
				%>



				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>"></td>
					<td><%=index++%></td>
					<td><%=bean.getProduct()%></td>
					<td><%=bean.getUserName()%></td>
					<td><%=bean.getDob()%></td>
					<td><%=bean.getRemark()%></td>
					
					<td><a href="WishCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>

			<table width="100%">


				<tr>
					<th></th>
					<%
						if (pageNo == 1) {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value=<%=ProductListCtl.OP_PREVIOUS%>></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value=<%=ProductListCtl.OP_PREVIOUS%>></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value=<%=ProductListCtl.OP_DELETE%>></td>
					<td><input type="submit" name="operation"
						value=<%=ProductListCtl.OP_NEW%>></td>

				
					<td align="right"><input type="submit" name="operation"
						value=<%=ProductListCtl.OP_NEXT%>
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>

				</tr>

			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value=<%=ProductListCtl.OP_BACK%>></td>
			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
	</form>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>

	</center>

	<%@include file="Footer.jsp"%>
</body>
</html>

