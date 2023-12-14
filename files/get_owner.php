<?php

require 'db_connect.php';

//$id = $_POST["id"];
$phone = $_POST["phone"];



$sql = "SELECT * FROM `canteen_owner` WHERE phone = '$phone'";


$res = mysqli_query($con, $sql);


if ($res != false) 
{
   while ($row = $res->fetch_assoc()) 
   {
    $output[] = $row;
}

	echo json_encode(array("message" => "success", "Reporting_List" => $output));
}
 
?>

