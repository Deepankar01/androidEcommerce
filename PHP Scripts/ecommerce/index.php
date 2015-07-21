<?php

    /**
     * Accepts all API requests
     * Accepts GET and POST request
     *
     * Each request is identified by a TAG
     * Response will be JSON data
     *
     */

    /**
     * Check for POST request
     */
    error_reporting(0);
    if(isset($_POST['tag']) && $_POST['tag'] != '') {
        $tag = $_POST['tag'];

        //include db_handler
        require_once 'include/DB_Functions.php';

        $db = new DB_Functions();

        //response array
        $response =array("tag" => $tag,"error"=>false);

        if($tag=='login') {
            //Request type is LOGIN
            $email = $_POST['email'];
            $password = $_POST['password'];

            //check for user
            $user =$db->getUserByEmailAndPassword($email,$password);

            if($user != false) {
                //user found
                $response["error"]=false;
                $response["uid"]=$user["unique_id"];
                $response["user"]["name"]=$user["name"];
                $response["user"]["email"]=$user["email"];
                $response["user"]["created_at"]=$user["created_at"];
                $response["user"]["updated_at"]=$user["updated_at"];

                echo json_encode($response);
            }
            else {
                //user not found
                //echo json with error = true
                $response["error"] = true;
                $response["error_msg"] = "Incorrect email address or password.";

                echo json_encode($response);
            }
        }
        else if ($tag=='register') {
            //Request is to register a new user
            $name=$_POST['name'];
            $email=$_POST['email'];
            $password=$_POST['password'];

            //check if there is already a user with same email address
            if($db->isUserExisted($email)) {
                //user already exists send error response
                $response["error"] = true;
                $response["error_msg"]="User already registered.";

                echo json_encode($response);
            }
            else {
                //store user
                $user = $db->storeUser($name,$email,$password);
                if($user) {
                    //user stored successfully
                    $response["error"]=false;
                    $response["uid"]=$user["unique_id"];
                    $response["user"]["name"]=$user["name"];
                    $response["user"]["email"]=$user["email"];
                    $response["user"]["created_at"]=$user["created_at"];
                    $response["user"]["updated_at"]=$user["updated_at"];

                    echo json_encode($response);
                }
                else {
                    //failure in registration
                    $response["error"] =true;
                    $response["error_msg"] = "We encountered an error. Please try again later.";
                    echo json_encode($response);
                }
            }
        }
        else {
            //unknown tag value
            $response["error"]=true;
            $response["error_msg"]="Invalid TAG value.";
            echo json_encode($response);
        }
    }
else {
    $response["error"]=true;
    $response["error_msg"]="TAG parameter missing. Accepts POST only.";
    echo json_encode($response);
}

?>	