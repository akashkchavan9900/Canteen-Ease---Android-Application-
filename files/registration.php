<?php
require 'db_connect.php';
if($con->connect_error)
{
    die("Connection failed: " . $conn->connect_error);
}
else
{
    $posts = array();
    $name = $_REQUEST["name"];
    $dob = $_REQUEST["dob"];
    $m_number = $_REQUEST["m_number"];
    $college_id = $_REQUEST["college_id"];
    $address = $_REQUEST["address"];
    $password = $_REQUEST["password"];
    $gender= $_REQUEST["gender"];
    $department = $_REQUEST["department"];
    $acedemic_year = $_REQUEST["acedemic_year"];
    $wallet = $_REQUEST["0"];


      $sql =  "INSERT INTO `student_registration` (`pk_id`, `name`, `dob`, `m_number`, `college_id`, `address`, `password`, `gender`,`department`, `acedemic_year`, `wallet`) VALUES (NULL, '$name', '$dob', '$m_number', '$college_id', '$address', '$password', '$gender', '$department', '$acedemic_year', '0');";
       
        if($con->query($sql))
        {
         $posts['Data'] = array("status" => "1", "message" => "Inserted Successfully");
        }
        else
        {
          $posts['Data'] = array("status" => "0", "message" => "Not Inserted");
        }
    echo json_encode($posts);
}
?>            







