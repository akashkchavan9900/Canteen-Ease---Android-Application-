<?php

require 'db_connect.php';

if (isset($_POST["m_number"]) && isset($_POST["password"])) 
{

    $m_number = $_POST["m_number"];
    $password = $_POST["password"];

    $sql = "SELECT pk_id FROM `student_registration` WHERE `m_number` = '$m_number' AND `password` = '$password' ";

    $result = $con->query($sql);
                
    while($row = $result->fetch_assoc())
    {
        $output =$row;
    }	
                
    if(!empty($output))
    {
                
        //$message["status"] = 1;
        // print json_encode($message + $output);   

        $json = array($output);
        echo json_encode(array('status' => 1, 'message' =>'success', 'data'=>$json));             
    }
    else
    {
        $message["status"] = 0;
        // print json_encode($message); 

        $json = array($message);
        echo json_encode(array('status' => 0, 'message' =>'failure', 'data'=>$json));               
    }            
       
}
else 
{
    $response['message'] = 'Required parameters are not available';
    print json_encode($response);
}
            

?>           