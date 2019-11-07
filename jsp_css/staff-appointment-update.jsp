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

<!-- Custom styles for this template-->
<link href="css/sb-admin.css" rel="stylesheet">

</head>

<body style="background-color: aliceblue">

	<!--nav bar-->
	<nav class="navbar navbar-expand navbar-dark bg-dark static-top">

		<a class="navbar-brand mr-1" href="staff-dashboard.jsp">Dashboard</a>



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

	<!--div class="container"-->
	<div class="card card-register mx-auto mt-5">
		<div class="card-header">Update Appointment</div>
		<div class="card-body">
			<form
				action="${pageContext.request.contextPath}/StaffServlet?action=update_appointment&id=${editAppointment.appointmentId}"
				method="post">



				<div class="form-group">
					<div class="form-label-group">
						<select id="cusId" name="customerId" class="form-control"
							required="required">
							<option value="${editAppointment.customerId}">${editAppointment.customerId}</option>
							<c:forEach items="${cusList}" var="custId">
								<option value="${custId}">${custId}</option>
							</c:forEach>

						</select>
					</div>
				</div>

				<div class="form-group">
					<div class="form-label-group">
						<select id="docId" name="doctorId" class="form-control"
							required="required">
							<option value="${editAppointment.doctorId}">${editAppointment.doctorId}</option>
							<c:forEach items="${docList}" var="doctId">
								<option value="${doctId}">${doctId}</option>
							</c:forEach>

						</select>
					</div>
				</div>



				<div class="form-group">
					<div class="form-label-group">
						<input type="text" id="date" name="date"
							onchange="dateValidation(event)" class="form-control"
							placeholder="Date" required="required" value="${app_date}">
						<label for="date">Date : MM/DD/YYYY</label>
					</div>
				</div>

				<div class="form-group">
					<div class="form-label-group">
						<input type="text" id="time" name="time" onchange="timeValidation(event)"
							class="form-control" placeholder="Time" required="required"
							value="${editAppointment.time}"> <label for="time">Time(HH:MM
							in 24hours)</label>
					</div>
				</div>

				<div class="form-group">
					<div class="form-label-group">
						<input type="text" id="isPaid" name="ispaid" class="form-control"
							placeholder="Is Paid" required="required"
							value="${edit_appointment.isPaid}"> <label for="isPaid">Is
							Paid(TRUE or FALSE)</label>
					</div>
				</div>





				<input type="submit" class="btn btn-primary btn-block"
					value="Update" />
			</form>

		</div>
	</div>
	<!--/div-->

	<script>
		function dateValidation(event) {

			today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth() + 1; //As January is 0.
			var yyyy = today.getFullYear();

			if (dd < 10)
				dd = '0' + dd;
			if (mm < 10)
				mm = '0' + mm;

			var date1 = new Date(mm + "/" + dd + "/" + yyyy);
			var date2str = document.getElementById("date").value
			var date2 = new Date(date2str);
			var app_day = date2.getDay();
			var diffTime = Math.abs(date2.getTime() - date1.getTime());
			var diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

			if (diffDays > 14 || app_day > 5) {
				alert("Enter a valid date : Date differance between today and appointment day should below 14 and Weekend days can't use as date.");
				document.getElementById("date").value = "";
			}

		}
		
		function timeValidation(event) {

			var time = document.getElementById("time").value;
			var timeStr = time.split(":");
			
			//console.log(timeStr[0]);
			
			if(!((timeStr[0]>=9 && timeStr[0]<=17)&&(timeStr[1]>=00 && timeStr[1]<=59))){
				alert("Enter a valid time : Time should be in between 09:00 and 17:00");
				document.getElementById("time").value = "";
			}
			
			if(timeStr[1]==17 && timeStr[0]>0){
				alert("Enter a valid time : Time should be in between 09:00 and 17:00");
				document.getElementById("time").value = "";
			}
			

		}
	</script>

	<!-- Bootstrap core JavaScript-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

</body>

</html>
