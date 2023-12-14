
<?php
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST');
header("Access-Control-Allow-Headers: X-Requested-With");
$servername = "localhost";
$username = "root";
$password = "";
$db = "ewallet_canteen";
$con = new mysqli($servername, $username, $password, $db);
?>