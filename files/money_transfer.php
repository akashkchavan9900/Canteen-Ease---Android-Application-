<?php

    require 'db_connect.php';
    $sender_wallet = 0;
    $receiver_wallet = 0;

    $posts = array();

    $sender_phone = $_REQUEST["sender_phone"];
    $receiver_phone = $_REQUEST["receiver_phone"];
    $amount = $_REQUEST["amount"];
    $created_date = date('Y-m-d');


    $sql1 = "INSERT INTO `money_transfer_transaction` (`pk_id`, `sender_phone`, `receiver_phone`, `amount`, `created_date`) VALUES (NULL, '$sender_phone', '$receiver_phone', '$amount', '$created_date');";
    $query1=mysqli_query($con,$sql1);
    
    //Sender Wallet
    $sql2="SELECT * FROM `student_registration` WHERE m_number = '$sender_phone'";
    $query2 = mysqli_query($con, $sql2);
    while ($row = $query2->fetch_assoc()) 
    {
      $sender_wallet = $row['wallet'];  
      $sender_wallet = $sender_wallet - $amount;
    }
    //echo $sender_wallet;
    $sql3 = "UPDATE student_registration SET wallet = '$sender_wallet' WHERE m_number = '$sender_phone'";
    $query3=mysqli_query($con,$sql3);
    
  
    
    //Receiver Wallet
    
    $sql4="SELECT * FROM `student_registration` WHERE m_number = '$receiver_phone'";
    $query4 = mysqli_query($con, $sql4);
    while ($row = $query4->fetch_assoc()) 
    {
      $receiver_wallet = $row['wallet'];  
      $receiver_wallet = $receiver_wallet + $amount;
    }
    //echo $sender_wallet;
    $sql5 = "UPDATE student_registration SET wallet = '$receiver_wallet' WHERE m_number = '$receiver_phone'";
    $query5=mysqli_query($con,$sql5);
    
        if(($query1 == 1) && ($query3 == 1) && ($query4 == 1) && ($query5 == 1))
        {
         $posts['Data'] = array("status" => "1", "message" => "Inserted Successfully");
        }
        else
        {
          $posts['Data'] = array("status" => "0", "message" => "Not Inserted");
        }
        echo json_encode($posts);
    
     
?>