
<?php

require 'db_connect.php';


$sql="SELECT * FROM `student_registration`";

$res = mysqli_query($con, $sql);


if ($res != false) 
{
   while ($row = $res->fetch_assoc()) 
   {
    $output[] = $row;
}

	echo json_encode(array("message" => "success", "Registered_Student_List" => $output));
}
 
?>
