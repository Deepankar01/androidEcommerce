<?php

//error_reporting(0);
$subCategoryList= array();

if(isset($_POST['category']) && $_POST['category'] != '') 
{
    $selectedCategory = $_POST['category'];
	class SubCategoryList
	{
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

	$sql = "select categoryId from Category_List where categoryName='$selectedCategory'";
	$result = mysqli_query($conn,$sql) or die(mysqli_error($conn));
	$row = $result->fetch_assoc();
	$categoryId = $row["categoryId"];
	
	$sql = "select name,SubCategoryID from subcategory_list where CategoryID=$categoryId";
	$result = mysqli_query($conn,$sql) or die(mysqli_error($conn));
	$number_rows = $result->num_rows;
	
	for($i=0; $i < $number_rows; $i++)
	{
		$subCategoryList[$i] = new SubCategoryList();
		$row = $result->fetch_assoc();
		$subCategoryList[$i]->categoryName = $row["name"];
		$subCategoryList[$i]->categoryId = $row["SubCategoryID"];
	}
	
	mysqli_close($conn);
	echo json_encode($subCategoryList);
	
}