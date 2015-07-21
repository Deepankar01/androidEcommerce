<?php

$code = $_REQUEST['QRvalue'];
$buyer = $_REQUEST['buyer'];
$seller = $_REQUEST['seller'];

$codes = explode(".",$code);
$category=explode("'",$codes[0])[1];
$subcategory=$codes[1];
$sno = explode("'",$codes[2])[0];

//check in the database
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "u810384278_ecom";


// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) 
{
    die("Connection failed: " . $conn->connect_error);

} 
//appending the productcode
$code = $category.'.'.$subcategory;
$sql = "select * from buy_product where category_code=$code and sno=$sno and Buyer='$buyer' and seller='$seller'";
$result = mysqli_query($conn,$sql) or die(mysqli_error($conn));
$rowcount=mysqli_num_rows($result);

if($rowcount==1)
{
   //change the status from Pending to Completed
   $sql = "update buy_product set status='Complete' where category_code=$code and sno=$sno and Buyer='$buyer' and seller='$seller'";
   $result = mysqli_query($conn,$sql) or die(mysqli_error($conn));
   if(mysqli_affected_rows($conn)==1)
   {
		echo "Done";
   }
   else
   {
	   echo "not Done";
   }
   
}
else
{
   echo "not Done";
}

mysqli_close($conn);
?>
		