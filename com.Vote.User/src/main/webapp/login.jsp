<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<style type="text/css">
.paint-card {
	box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.3);
	margin-top: 10%;
}
</style>
</head>
<body>

	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card paint-card">
					<div class="card-body">
						<form action="loginuser" method="Post">
							
							<div class="mb-3">
								<label for="exampleInputPassword1" class="form-label">Email</label>
								<input type="email" class="form-control"
									id="exampleInputPassword1" name="email">
							</div>
							<div class="mb-3">
								<label for="exampleInputPassword1" class="form-label">Password</label>
								<input type="password" class="form-control"
									id="exampleInputPassword1" name="password">
							</div>

							<a href="index.jsp"></a>
							<input type="submit" class="btn btn-primary text-center">

						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>