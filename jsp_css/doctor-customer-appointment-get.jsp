<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>AMC</title>

<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">

<!-- Page level plugin CSS-->
<link href="vendor/datatables/dataTables.bootstrap4.css"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="css/sb-admin.css" rel="stylesheet">

</head>

<body id="page-top">

	<nav class="navbar navbar-expand navbar-dark bg-dark static-top">

		<a class="navbar-brand mr-1" href="doctor-dashboard.jsp">Dashboard</a>



		<!-- Navbar -->
		<ul class="nav navbar-nav navbar-right" style="margin-left: 1050px">

			<li class="nav-item dropdown no-arrow"><a
				class="nav-link dropdown-toggle" href="#" id="userDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> <i class="fas fa-user-circle fa-fw"></i>
			</a>
				<div class="dropdown-menu dropdown-menu-right"
					aria-labelledby="userDropdown">
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/LoginServlet?action=load_profile">Edit
						Profile</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/LoginServlet?action=logout">Logout</a>
				</div></li>
		</ul>

	</nav>


	<div class="card card-register mx-auto mt-5">
		<div class="card-header"></div>
		<div class="card-body">
			<form id="cus"
				action="${pageContext.request.contextPath}/DoctorServlet?action=get_customer_history"
				method="post">




				<div class="form-group">
					<div class="form-label-group">
						<select id="cusId" name="customerId" class="form-control"
							required="required">
							<option>Select Customer</option>
							<c:forEach items="${cusList}" var="custId">
								<option value="${custId}">${custId}</option>
							</c:forEach>

						</select>
					</div>
				</div>





				<input type="submit" class="btn btn-primary btn-block"
					value="Get Details" />
			</form>

		</div>
	</div>


	

	<!--  script type="text/javascript"
		src="http://code.jquery.com/jquery-1.10.1.min.js"></script>

	<script>
		$(document).ready(function() {

			$('#cus').on('submit', function(event) {
				event.preventDefault();

				

				$.ajax($(this).attr('action'), {
					type : 'get',
					data : $('#cus').serialize()
				//this can send all data in form
				}).done(function(response) {
					
					 var trHTML = '';
                     var obj = $.parseJSON(response.responseText);
                     for (var i = 0; i < obj.length; i++) {
                         trHTML += '<tr><td>'+obj[i].appointmentId}+'</td><td>'+obj[i].customerId}+'</td><td>'+obj[i].date}+'</td><td>'+obj[i].medicine}+'</td>'+
							'<td>'+obj[i].feedback}+'</td>'+
							'<td>'+obj[i].charge}+'</td>'+
							'<td>'+obj[i].comment}+'</td>'+
							'<td>'+obj[i].isPaid}+'</td></tr>';
                     }
                     $("#dataTable tbody").append(trHTML);
					
				});
			});

		});
	</script-->

	<!-- Bootstrap core JavaScript-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Page level plugin JavaScript-->
	<script src="vendor/chart.js/Chart.min.js"></script>
	<script src="vendor/datatables/jquery.dataTables.js"></script>
	<script src="vendor/datatables/dataTables.bootstrap4.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="js/sb-admin.min.js"></script>

	<!-- Demo scripts for this page-->
	<script src="js/demo/datatables-demo.js"></script>
	<script src="js/demo/chart-area-demo.js"></script>

</body>

</html>
