<?php

  require 'koneksi.php';
  class user{}

  if(isset($_REQUEST['nama']) && isset($_REQUEST['username']) && isset($_REQUEST['password']) ){
    $nama =$_REQUEST["nama"];
    $username =$_REQUEST["username"]; 
    $password =$_REQUEST["password"];
    

    $sql = "INSERT INTO tbl_user (nama, username, password)
    VALUES ('$nama','$username','$password')";
    $hasil = mysqli_query($conn, $sql);

    if($hasil){
        $response = new user();
        $response->success = true;
        echo json_encode($response);
    }

  } else{
    echo "No datas=";
  }


?>
