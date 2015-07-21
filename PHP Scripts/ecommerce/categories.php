<?php
error_reporting(E_ERROR | E_PARSE);
$categoryList = array();
class CategoryList{
	public $categoryName="";
	public $categoryId="";
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

$sql = "select * from Category_List";
$result = mysqli_query($conn,$sql) or die(mysqli_error($conn));
$number_rows = $result->num_rows;

for($i=0; $i < $number_rows; $i++)
{
    $categroyList[$i] = new CategoryList();
    $row = $result->fetch_assoc();
    $categoryList[$i]->categoryName = $row["categoryName"];
    $categoryList[$i]->categoryId = $row["categoryId"];
  
}

mysqli_close($conn);
echo json_encode($categoryList);

?>
