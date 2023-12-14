<?php

include 'db_connect.php';

$posts = array();
 
    
    $name = $_REQUEST["name"];
    $quantity = $_REQUEST["quantity"];
    $prize = $_REQUEST["prize"];
    
    
    $sql =	"INSERT INTO `menu` (`pk_id`, `name`, `quantity`, `prize`) VALUES (NULL, '$name', '$quantity', '$prize');";

          $query=mysqli_query($con,$sql);
       
        if($query == 1)
        {
         $posts['Data'] = array("status" => "1", "message" => "Inserted Successfully");
        }
        else
        {
          $posts['Data'] = array("status" => "0", "message" => "Not Inserted");
        }
        echo json_encode($posts);

?>