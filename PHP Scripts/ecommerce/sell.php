<?php

class ProductList{
	public $name="";
	public $price="";
	public $thumbnail="";
	public $category_code="";
	public $subcategory_code="";
	public $seller="";
	public $description="";
}

$productList= new ProductList();

$productList = new ProductList();
$productList->name=$_REQUEST['name'];
$productList->price=$_REQUEST['price'];
$productList->thumbnail=$_REQUEST['thumbnail'];
$productList->category_code=$_REQUEST['category_code'];
$productList->subcategory_code=$_REQUEST['subcategory_code'];
$productList->seller=$_REQUEST['seller'];
$productList->description=$_REQUEST['description'];


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
$code = $productList->category_code.'.'.$productList->subcategory_code;
$sql = "Insert into Product_List(thumbnail,name,price,category_code,description,seller) values($productList->thumbnail,$productList->name,$productList->price,$code,$productList->description,$productList->seller)";
$result = mysqli_query($conn,$sql) or die(mysqli_error($conn));

if(mysqli_affected_rows($conn)==1)
{
    echo "success";
}
else
{
  echo "fail";
}
mysqli_close($conn);
?>		