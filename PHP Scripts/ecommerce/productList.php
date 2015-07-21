<?php

$productList = array();
$subCategory = $_REQUEST['subCategory'];

class ProductList
{
	public $name="";
	public $price="";
	public $thumbnail="";
    public $sno="";
	public $seller="";
	public $QRValue="";
}

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


$sql = "SELECT * FROM subcategory_list where name='$subCategory'";
$result = mysqli_query($conn,$sql) or die(mysqli_error($conn));
$row = $result->fetch_assoc();
$category_code = $row['CategoryID'].'.'.$row['SubCategoryID'];

$sql = "SELECT * FROM product_list where category_code=$category_code";
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
