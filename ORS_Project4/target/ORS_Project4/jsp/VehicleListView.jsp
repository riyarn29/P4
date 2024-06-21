<%@page import="com.rays.pro4.controller.VehicleListCtl"%>
<%@page import="com.rays.pro4.Bean.VehicleBean"%>
<%@page import="com.rays.pro4.Model.RoleModel"%>
<%@page import="com.rays.pro4.Model.UserModel"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.UserListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Vehicle List</title>

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
			yearRange : '1980:2002',
		//  mindefaultDate : "01-01-1962"
		});
	});
</script>

</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.VehicleBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.VEHICLE_LIST_CTL%>" method="post">
		<center>

			<div align="center">
				<h1>Vehicle List</h1>

				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<%
				List nlist = (List) request.getAttribute("vlist");

				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<VehicleBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<tr>
					<th></th>

				<!-- 	<td align="center"> -->
					<label>Purchase By:</font> 
					</label> <input type="text" name="purchaseBy"
						placeholder="Enter Purchase by"
						value="<%=ServletUtility.getParameter("purchaseBy", request)%>"> &nbsp;
					
					<label>manufacturedBy</font> :
					</label> <input type="text" name="manufacturedBy"
						placeholder="Enter manufactured by"
						value="<%=ServletUtility.getParameter("manufacturedBy", request)%>">&nbsp;
					
					 <label>Company</font> :
					</label> <%=HTMLUtility.getList("company", String.valueOf(bean.getCompany()), nlist)%>
						&nbsp;
   <label>Name</font> :
					</label> <input type="text" name="name" placeholder="Enter  Name"
						value="<%=ServletUtility.getParameter("name", request)%>">

						&nbsp; <%-- 	<td align="center"><label>dob</font> :
					</label> <input type="text" name="dob" placeholder="Enter dob" id ="udatee"
						value="<%=ServletUtility.getParameter("dob", request)%>"> --%> <label></font>
					</label> <%-- <%=HTMLUtility.getList("roleid", String.valueOf(bean.getRoleId()), rlist) %> --%>

						
						&nbsp; <%--  <label>Last Name </font> :
					</label> <%=HTMLUtility.getList("lastName", String.valueOf(bean.getFirstName()), llist)%>
						&nbsp; --%> <%-- <label>FirstName</font> :
					</label> <%=HTMLUtility.getList("firstName", String.valueOf(bean.getFirstName()), flist)%>
						&nbsp; --%> <%--	  <%=HTMLUtility.getList("loginid", String.valueOf(bean.getRoleId()), ulist)%>
 --%> &nbsp; <%-- <label>LastName</label><input type="text" name="LastName" value<%= ServletUtility.getParameter("LastName", request) %>> --%>

						<label>Date</font> :
					</label> <input type="text" name="dob" placeholder="Enter dob" id="udatee"
						readonly="readonly"
						value="<%=ServletUtility.getParameter("dob", request)%>"> &nbsp;
						<input type="submit" name="operation"
						value=<%=VehicleListCtl.OP_SEARCH%>> &nbsp; <input
						type="submit" name="operation" value="reset"></td>
				</tr>
			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr style="background: pink">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>Name</th>
					<th>Purchase BY</th>
					<th>Purchase Date</th>
					<th>Company</th>
					<th>Manufactured By</th>
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
					<td><%=bean.getName()%></td>
					<td><%=bean.getPurchaseBy()%></td>
					<td><%=bean.getPurchaseDate()%></td>
					<td><%=bean.getCompany()%></td>
					<td><%=bean.getManufacturedBy()%></td>
					<td><a href="VehicleCtl?id=<%=bean.getId()%>">Edit</a></td>
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
						value=<%=VehicleListCtl.OP_PREVIOUS%>></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value=<%=VehicleListCtl.OP_PREVIOUS%>></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value=<%=VehicleListCtl.OP_DELETE%>></td>
					<td><input type="submit" name="operation"
						value=<%=VehicleListCtl.OP_NEW%>></td>

					<%--  <%	UserModel model = new UserModel();
                     %>
                     
                     <% if(list.size() < pageSize || model.nextPK()-1 == bean.getId() ){%>

                     		<td align="right"><input type="submit" name="operation" disabled="disabled" value="<%=UserListCtl.OP_NEXT%>"></td>
                     <% }else{%>
                     		<td align="right"><input type="submit" name="operation" value="<%=UserListCtl.OP_NEXT%>"></td>
                     <%} %>
        --%>
					<td align="right"><input type="submit" name="operation"
						value=<%=VehicleListCtl.OP_NEXT%>
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>

				</tr>

			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value=<%=VehicleListCtl.OP_BACK%>></td>
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

