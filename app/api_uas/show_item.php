<?php

  require 'koneksi.php';

  $sql = "SELECT * FROM tbl_barang";
  $result = mysqli_query($conn, $sql);
  $data = array();

  while ($row = mysqli_fetch_assoc($result)) {
   
    $data[] = $row;

  }

  $string = json_encode($data);
  echo $string;


?>
