<?php


$buyer = $_REQUEST['email'];
$code = $_REQUEST['QRvalue'];
$codes = explode(".",$code);
$category=explode("'",$codes[0])[1];
$subcategory=$codes[1];
$sno = explode("'",$codes[2])[0];


include 'DBConfiguration.php';

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) 
{
    die("Connection failed: " . $conn->connect_error);

}
 

$sql = "SELECT * FROM Product_List where sno=$sno";
$result=mysqli_query($conn,$sql) or die(mysqli_error($conn));

$number_rows = $result->num_rows;
if($number_rows==1)
{
		$row = $result->fetch_assoc();
		$sno = $row["sno"];
		$thumb = $row["thumbnail"];
		$name = $row["name"];
		$price = $row["price"];
		$category_code = $row["category_code"];
		$description = $row["description"];
		$seller = $row["seller"];

		$sql = "insert into Buy_Product (sno,thumbnail,name,price,category_code,description,seller,Buyer) values('$sno','$thumb','$name','$price','$category_code','$description','$seller','$buyer')";
		$result=mysqli_query($conn,$sql) or die(mysqli_error($conn));
		if(mysqli_affected_rows($conn)==1)
		{
			$sql = "Delete FROM Product_List where sno=$sno";
			mysqli_query($conn,$sql) or die(mysqli_error($conn));
			if(mysqli_affected_rows($conn)==1)
			{
				echo "success";
			}
			else{
				echo "fail";
			}
		}
		else
		{
			echo "fail";
		}

}
else
{
	echo "fail";
}	

mysqli_close($conn);
?>