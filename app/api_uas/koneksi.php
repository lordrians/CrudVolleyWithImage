<?php
  $DB_HOST = "localhost";
  $DB_USER = "root";
  $DB_PASS = "";
  $DB_NAME = "db_uas";

  $conn = mysqli_connect($DB_HOST, $DB_USER, $DB_PASS, $DB_NAME);

  if (!$conn) {
    // code...
    die("Connection Failed : " . mysqli_connect_error());
  }
  else {
    // code...
    // echo "Connection Succes";
  }


 ?>
