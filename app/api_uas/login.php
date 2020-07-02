<?php
	
	include_once "koneksi.php";

	class usr{}
	
	$username = $_REQUEST["username"];
	$password = $_REQUEST["password"];
	
	if ((empty($username)) || (empty($password))) { 
		$response = new usr();
		$response->success = false;
		$response->message = "Kolom tidak boleh kosong"; 
		die(json_encode($response));
	}
	
	$query = mysqli_query($conn, "SELECT * FROM tbl_user WHERE username='$username' AND password='$password'");
	
	$row = mysqli_fetch_assoc($query);
	
	if (!empty($row)){
		$response = new usr();
		$response->success = true;
		$response->user = $row;
		die(json_encode($response));
		
	} else { 
		$response = new usr();
		$response->success = false;
		$response->message = "Username atau password salah";
		die(json_encode($response));
	}
	
	mysqli_close($con);

?>