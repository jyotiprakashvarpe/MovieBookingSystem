<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Movie</title>
<style>
        /* Basics */
        html, body
        {
            padding: 0;
            margin: 0;
            width: 100%;
            height: 100%;
            font-family: "Helvetica Neue" , Helvetica, sans-serif;
            background: #FFFFFF;
        }
        label
        {
            color: #ffffff;
            display: inline-block;
            margin-left: 18px;
            padding-top: 10px;
            font-size: 15px;
        }
        input[type=text], input[type=password],input[type=number]
        {
            font-size: 14px;
            padding-left: 10px;
            margin: 10px;
            margin-top: 12px;
            margin-left: 18px;
            width: 300px;
            height: 35px;
            border: 1px solid #ccc;
            border-radius: 2px;
            box-shadow: inset 0 1.5px 3px rgba(190, 190, 190, .4), 0 0 0 5px #f5f5f5;
        }
        textarea
        {
            font-size: 14px;
            padding-left: 10px;
            margin: 10px;
            margin-top: 12px;
            margin-left: 18px;
            border: 1px solid #ccc;
            border-radius: 2px;
            box-shadow: inset 0 1.5px 3px rgba(190, 190, 190, .4), 0 0 0 5px #f5f5f5;
        }
        input[type=checkbox]
        {
            margin-left: 18px;
            margin-top: 30px;
        }
        input[type=submit]
        {
            margin-left: 50%;
            margin-top: 2%;
            padding: 6px 20px;
            font-size: 14px;
            font-weight: bold;
            color: #fff;
            background-color: #07A8C3;
            background-image: -webkit-gradient(linear, left top, left bottom, from(#07A8C3), to(#6EE4E8));
            background-image: -moz-linear-gradient(top left 90deg, #07A8C3 0%, #6EE4E8 100%);
            background-image: linear-gradient(top left 90deg, #07A8C3 0%, #6EE4E8 100%);
            border-radius: 30px;
            border: 1px solid #07A8C3;
            cursor: pointer;
        }
        input[type=submit]:hover
        {
            background-image: -webkit-gradient(linear, left top, left bottom, from(#b6e2ff), to(#6ec2e8));
            background-image: -moz-linear-gradient(top left 90deg, #b6e2ff 0%, #6ec2e8 100%);
            background-image: linear-gradient(top left 90deg, #b6e2ff 0%, #6ec2e8 100%);
        }
        h1
        {
            border-bottom: solid 1px #ECF2F5;
            padding-left: 18px;
            padding-bottom: 10px;
            color: #07A8C3;
            font-size: 30px;
            font-weight: bold;
            font-family: sans-serif;
            text-align: center;
        }
        table
        {
        	margin-left: auto;
        	margin-right: auto;
        }
        a:link, a:visited {
    background-color: #07A8C3;
    color: white;
    padding: 14px 25px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    
}


a:hover, a:active {
    background-color: #07A8C3;
}
    </style>
<script type="text/javascript">


        function upload_img(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                   // $('#img_id').attr('src', e.target.result);
                    document.getElementById("img_id").src=e.target.result;
                    document.getElementById("img_id").setAttribute("style", "display: block;")
                }

                reader.readAsDataURL(input.files[0]);
            }
        }

    </script>
</head>
<body>
<h1>Add New Movie</h1>
	<form action="addMovie" enctype="multipart/form-data" method="POST">
		<table>

			<tr>
				<td>Movie Title :</td>
				<td><input type="text" name="title" required="required"></td>
			</tr>
			<tr>
				<td>Description :</td>
				<td><textarea rows="4" cols="50" name="description"></textarea>
			</tr>
			<tr>
				<td>Duration :</td>
				<td><input type="number" name="duration" required="required" min="0"
				max="240" maxlength="3">
			</tr>
			<tr>
				<td>Poster :</td>
				<td><input type="file" accept=".jpg"  onchange="upload_img(this);" name="poster" ></td>
				<td><img id="img_id" src="#" height="200" width="200" style="display: none;"/></td>
			</tr>
		</table>
		<input type="submit" value = "Add Movie" />
	</form>
	<a href="/UnionMovieBooking/LogoutController">Logout</a>
		<a href="adminDashboard">Back</a>
</body>
</html>