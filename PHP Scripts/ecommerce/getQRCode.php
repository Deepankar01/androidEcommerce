<?php

$productList = array();
$buyer = $_REQUEST['buyer'];

class ProductList{
	public $name="";
	public $price="";
	public $thumbnail="";
    public $sno="";
	public $seller="";
	public $QRValue="";
}


include 'DBConfiguration.php';

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) 
{
    die("Connection failed: " . $conn->connect_error);

} 

$sql = "SELECT * FROM buy_product where Buyer=$buyer and status='Pending'";
$result = mysqli_query($conn,$sql) or die(mysqli_error($conn));
$number_rows = $result->num_rows;
for($i=0; $i < $number_rows; $i++)
{
    $productList[$i] = new ProductList();
    $row = $result->fetch_assoc();
    $productList[$i]->name = $row["name"];
    $productList[$i]->price = $row["price"];
    $productList[$i]->thumbnail = $row["thumbnail"];
    $productList[$i]->sno = $row["sno"];
	$productList[$i]->seller = $row["seller"];
	$productList[$i]->QRValue = $row["category_code"].'.'.$row["sno"];
}

mysqli_close($conn);
echo json_encode($productList);

?>