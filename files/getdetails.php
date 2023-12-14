
<?php

require 'db_connect.php';


$sql="SELECT * FROM `menu`";

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
