<?php

  require 'koneksi.php';

  if(isset($_REQUEST['id']) && isset($_REQUEST['photo'])){
    $id =$_REQUEST["id"];
    $photo =$_REQUEST["photo"];

    unlink('img/'.$photo);
    $sql = "delete from tbl_barang where id = '$id'";
    $hasil = mysqli_query($conn, $sql);

  } else{
    echo "No datas=";
  }




?>
