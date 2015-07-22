<?php

$productList = array();

class ProductList{
	public $name="";
	public $price="";
	public $thumbnail="";
    public $sno="";
}

include 'DBConfiguration.php';

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) 
{
    die("Connection failed: " . $conn->connect_error);

} 

$sql = "SELECT name,price,thumbnail,sno FROM Product_List";
$result = $conn->query($sql);

$number_rows = $result->num_rows;

for($i=0; $i < $number_rows; $i++)
{
    $productList[$i] = new ProductList();
    $row = $result->fetch_assoc();
    $productList[$i]->name = $row["name"];
    $productList[$i]->price = $row["price"];
    $productList[$i]->thumbnail = $row["thumbnail"];
    $productList[$i]->sno = $row["sno"];
}

mysqli_close($conn);
echo json_encode($productList);
?>