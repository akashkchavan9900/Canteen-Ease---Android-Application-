<?php

    require 'db_connect.php';
    $student_wallet = 0;

    $posts = array();
    
    $owner_id = $_REQUEST["owner_id"];
    $student_id = $_REQUEST["student_id"];
    $amount = $_REQUEST["amount"];
    $m_number = $_REQUEST["m_number"];
    $owner_password = $_REQUEST["password"];
    
    $sql1="SELECT * FROM `canteen_owner` WHERE pk_id = '$owner_id'";
    $query1 = mysqli_query($con, $sql1);
    while ($row = $query1->fetch_assoc()) 
    {
      $password = $row['password'];
   
    }
   

    if($owner_password == $password)
    {
        
        $sql2="SELECT * FROM `student_registration` WHERE pk_id = '$student_id'";
        $query2 = mysqli_query($con, $sql2);
        while ($row = $query2->fetch_assoc()) 
        {
          $student_wallet = $row['wallet'];  
          $student_wallet = $student_wallet + $amount;
          
        }
        
        $sql3 = "UPDATE student_registration SET wallet = '$student_wallet' WHERE pk_id = '$student_id' AND m_number = '$m_number'";
        $query3=mysqli_query($con,$sql3);
    
  
    
        if(($query1 == 1) && ($query2 == 1))
        {
         $posts['Data'] = array("status" => "1", "message" => "Recharge Successfully");
        }
        else
        {
          $posts['Data'] = array("status" => "0", "message" => "Recharge Unsuccessful");
        }
        
    }
    else
    {
        $posts['Data'] = array("status" => "0", "message" => "Incorrect Password!");
    }
    
    echo json_encode($posts);
    
     
?>