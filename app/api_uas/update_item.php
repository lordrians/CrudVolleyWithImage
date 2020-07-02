<?php

  require 'koneksi.php';
	class barang{}

  if(isset($_REQUEST['nama']) && isset($_REQUEST['harga']) && isset($_REQUEST['stok']) && isset($_REQUEST['id'])){
    $id =$_REQUEST["id"];
    $nama =$_REQUEST["nama"];
    $harga =$_REQUEST["harga"]; 
    $stok =$_REQUEST["stok"];
    $randName = '';

    if(isset($_REQUEST['photo'])){
      $getPhoto = mysqli_query($conn, "select photo from tbl_barang where id = '$id' ");
      $LastImage = mysqli_fetch_assoc($getPhoto);
      if (!empty($LastImage)) {
        unlink('img/'. $LastImage['photo']);
      }
      
      $photo = $_REQUEST["photo"];
      $randName = rand() . time() . ".jpg";
      $target_dir = "img/" . $randName;

      file_put_contents($target_dir, base64_decode($photo));

    } else{
      $photo = null;
    }

    $sql = "Update tbl_barang set nama = '$nama', harga = '$harga', stok = '$stok', photo = '$randName' where id = '$id'";
    $hasil = mysqli_query($conn, $sql);
    $select = mysqli_query($conn, "select * from tbl_barang where id = '$id' ");
    
    $row = mysqli_fetch_assoc($select);
	
    if (!empty($row)){
      $response = new barang();
      $response->success = true;
      $response->data = $row;
      // $response->id = $row['id'];
      // $response->nama = $row['nama'];
      // $response->harga = $row['harga'];
      // $response->stok = $row['stok'];
      // $response->photo = $row['photo'];
      die(json_encode($response));
      
    } else { 
      $response = new usr();
      $response->success = false;
      $response->message = "Username atau password salah";
      die(json_encode($response));
    }

  } else{
    echo "No datas=";
  }




?>
