<?php

  require 'koneksi.php';
	class barang{}

  if(isset($_REQUEST['nama']) && isset($_REQUEST['harga']) && isset($_REQUEST['stok']) ){
    $nama =$_REQUEST["nama"];
    $harga =$_REQUEST["harga"]; 
    $stok =$_REQUEST["stok"];
    
    if(isset($_REQUEST['photo'])){
      $photo =$_REQUEST["photo"];
      $randName = rand() . time() . ".jpg";
      $target_dir = "img/" . $randName;

      file_put_contents($target_dir, base64_decode($photo));

    } else{
      $photo = null;
    }


    $sql = "INSERT INTO tbl_barang (nama, harga, stok, photo)
    VALUES ('$nama','$harga','$stok','$randName')";
    $hasil = mysqli_query($conn, $sql);
    $select = mysqli_query($conn, "select * from tbl_barang order by id DESC limit 1");
    // $data = array();
    // while ($row = mysqli_fetch_assoc($select)) {
    //   $data[] = $row;
    // }
    $row = mysqli_fetch_assoc($select);
    $response = new barang();
    $response->success = true;
    $response->data = $row;
    $json = json_encode($response);
    // $json = json_encode($row = mysqli_fetch_assoc($select));
    echo $json;

    // if ($select){
    //   $data = mysqli_fetch_array($select);
    //   $response = new barang();
    //   $response->success = true;
    //   $response->id = $row['id'];
    //   $response->nama = $row['nama'];
    //   $response->harga = $row['harga'];
    //   $response->stok = $row['stok'];
    //   $response->photo = $row['photo'];
    //   die(json_encode($response));

    
    // } else{

    // }
    

  } else{
    echo "No datas=";
  }


?>
