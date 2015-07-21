<?php
	$email_id = $_REQUEST['email_id'];
	$transactionList = array();
	class TransactionList{
		public $date = "";
		public $otherParty = "";
		public $cost = "";
		public $isBuy = "";
		public $isSell = "";		
	}
	//check in the database	$servername = "localhost";	$username = "root";	$password = "";	$dbname = "u810384278_ecom";	// Create connection	$conn = new mysqli($servername, $username, $password, $dbname);	// Check connection	if ($conn->connect_error) 	{		die("Connection failed: " . $conn->connect_error);	} 		$sql = "select * from buy_product where Buyer='$email_id' and Status='Complete'";	$result = mysqli_query($conn,$sql) or die(mysqli_error($conn));	$number_rows = $result->num_rows;	for($i=0; $i < $number_rows; $i++)	{		$transactionList[$i] = new transactionList();		$row = $result->fetch_assoc();		$transactionList[$i]->date = $row['Time'];    	$transactionList[$i]->otherParty = $row['seller'];   	    $transactionList[$i]->cost = $row['price'];		$transactionList[$i]->isBuy = TRUE;		$transactionList[$i]->isSell = FALSE;	}	$sql = "select * from buy_product where seller='$email_id' and Status='Complete'";	$result = mysqli_query($conn,$sql) or die(mysqli_error($conn));	$number_rows2 = $result->num_rows;	for($i=$number_rows; $i < $number_rows2; $i++)	{		$transactionList[$i] = new transactionList();		$row = $result->fetch_assoc();		$transactionList[$i]->date = $row['Time'];    	$transactionList[$i]->otherParty = $row['Buyer'];   	    $transactionList[$i]->cost = $row['price'];		$transactionList[$i]->isBuy = FALSE;		$transactionList[$i]->isSell = TRUE;	}		mysqli_close($conn);	echo json_encode($transactionList);
	
?>