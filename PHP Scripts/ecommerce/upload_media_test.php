<?php
include 'DBConfiguration.php';


// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);

}

$sql = "select sno from Product_List";
$result = mysqli_query($conn, $sql) or die(mysqli_error($conn));
$sno = 0;
$number_rows = $result->num_rows;

for ($i = 0; $i < $number_rows; $i++) {
    $row = $result->fetch_assoc();
    $sno = $row["sno"];
}

//$extension = explode(".", $_FILES["uploaded"]["name"])[1];
$filename = ($sno + 1) . '.png';

$s = 'fail';
$target_path1 = "images/thumb/";
$target_path1 = $target_path1 . $filename;
if (move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $target_path1)) {
    $s = 'success';
} else {
    $s = 'fail';
}

class ProductList
{
    public $name = "";
    public $price = "";
    public $thumbnail = "";
    public $category_code = "";
    public $subcategory_code = "";
    public $seller = "";
    public $description = "";
}

$productList = new ProductList();
$headers = apache_request_headers();
$productList = new ProductList();
$productList->name = $headers['product_name'];
$productList->price = $headers['product_price'];
$productList->thumbnail = $target_path1;
$productList->category_code = $headers['product_category'];
$productList->subcategory_code = $headers['product_subcategory'];
$productList->seller = $headers['product_seller'];
$productList->description = $headers['product_description'];


$sql = "Select CategoryID,SubCategoryID from subcategory_list where name='$productList->subcategory_code'";
$result = mysqli_query($conn,$sql) or die(mysqli_error($conn));
$row = $result->fetch_assoc();

//appending the productcode
$code = $row['CategoryID'] . '.' . $row['SubCategoryID'];
$sql = "Insert into Product_List(thumbnail,name,price,category_code,description,seller) values('$productList->thumbnail','$productList->name',$productList->price,'$code','$productList->description','$productList->seller')";
$result = mysqli_query($conn, $sql) or die(mysqli_error($conn));

mysqli_close($conn);
?>