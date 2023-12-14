
<?php

require 'db_connect.php';

$sender_phone = $_REQUEST['sender_phone'];

$sql="SELECT `money_transfer_transaction`.*, student_registration.name AS receiver_name FROM `money_transfer_transaction` INNER JOIN student_registration WHERE money_transfer_transaction.receiver_phone = student_registration.m_number AND money_transfer_transaction.sender_phone = '$sender_phone'";

$res = mysqli_query($con, $sql);


if ($res != false) 
{
    
   while ($row = $res->fetch_assoc()) 
   {
    $output[] = $row;
   }
    
    if($output != 0)
    {
        echo json_encode(array("message" => "success", "Money_Transfer_History" => $output));
    }
    else
    {
        echo json_encode(array("message" => "failure", "Money_Transfer_History" => $output));
    }
 
	
}

?>
