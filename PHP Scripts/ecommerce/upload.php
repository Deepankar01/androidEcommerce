<?php
$s='fail';
$target_path1 = "images/thumb/";
$target_path1 = $target_path1 . basename( $_FILES['uploaded_file']['name']);
echo $target_path1;
if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $target_path1)) {
    $s='success';
} else{
    $s='fail';
}
echo $s;
?>