<?php

require 'db_connect.php';

//$id = $_POST["id"];
$m_number = $_POST["m_number"];

//$submitdate = $_POST["Y-m-d"];

//$sql = "SELECT * FROM `revenue` WHERE specialization_id='$specialization_id'";

//$sql="SELECT * FROM `voucher`";

//$sql = "SELECT `revenue`.*, student.sname AS student_name, student.contact AS student_contact FROM `stationary_fees_transaction` INNER JOIN student WHERE stationary_fees_transaction.stdid = student.id AND stationary_fees_transaction.submitdate = '$submitdate'";

$sql = "SELECT * FROM `student_registration` WHERE m_number = '$m_number'";


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

