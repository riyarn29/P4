<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.JobRequirementListCtl"%>
<%@page import="com.rays.pro4.Bean.JobRequirementBean"%>
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
<title>Job Requirement List</title>

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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.JobRequirementBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.JOBREQUIREMENT_LIST_CTL%>" method="post">
		<center>

			<div align="center">
				<h1>Job Requirement List</h1>

				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<%
				List jlist = (List) request.getAttribute("jlist");

				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<JobRequirementBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<tr>
					<th></th>

				<!-- 	<td align="center"> -->
					<label>Title:</font> 
					</label> <input type="text" name="title" align = "left"
						placeholder="Enter Title"
						value="<%=ServletUtility.getParameter("title", request)%>"> &nbsp;
					
				
				
			 &nbsp;	<%-- <label>Client:</font> 
					</label>
						<%
							HashMap map = new HashMap();
							map.put("Riya", "Riya");
							map.put("Akash", "Akash");
							map.put("Preeti", "Preeti");

							String hlist = HTMLUtility.getList("client", String.valueOf(bean.getClient()), map);
						%> <%=hlist%>
						
						
						</td> --%>
						
					
						
					 <label>Client</font>:
					</label>	<%=HTMLUtility.getList("client",String.valueOf(bean.getClient()) , jlist) %>
						
						&nbsp;	<label>Open Date </font> 
					</label> <input type="text" name="date" id="udatee"
						placeholder="Enter Date"
						value="<%=ServletUtility.getParameter("date", request)%>"> &nbsp;
					
							
						<label>Description:</font> 
					</label> <input type="text" name="riya"
						placeholder="Enter Description "
						value="<%=ServletUtility.getParameter("riya", request)%>"> &nbsp;
					
					
					
				<%-- 	 <label>Client</font> :
					</label> <%=HTMLUtility.getList("client", String.valueOf(bean.getClient()), jlist)%>
						&nbsp; 
  --%>
						<input type="submit" name="operation"
						value="<%=JobRequirementListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation" value="<%=JobRequirementListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr style="background: pink">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>Title</th>
					<th>Client</th>
					<th>Open Date</th>
					<th>Description</th>
					
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
					<td><%=bean.getTitle()%></td>
					<td><%=bean.getClient()%></td>
					<td><%=bean.getOpenDate()%></td>
					<td><%=bean.getDescription()%></td>
					
					<td><a href="JobRequirementCtl?id=<%=bean.getId()%>">Edit</a></td>
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
						value=<%=JobRequirementListCtl.OP_PREVIOUS%>></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value=<%=JobRequirementListCtl.OP_PREVIOUS%>></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value=<%=JobRequirementListCtl.OP_DELETE%>></td>
					<td><input type="submit" name="operation"
						value=<%=JobRequirementListCtl.OP_NEW%>></td>

				
					<td align="right"><input type="submit" name="operation"
						value=<%=JobRequirementListCtl.OP_NEXT%>
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>

				</tr>

			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value=<%=JobRequirementListCtl.OP_BACK%>></td>
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

