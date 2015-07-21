<?php

class DB_Functions
{
    private $db;

    function __construct()
    {
        require_once 'DB_Connect.php';

        //Instantiate and establish a connection
        $this->db = new DB_Connnect();
        $this->db->connect();
    }

    function __destruct()
    {

    }

    /**
     * For REGISTRATION
     * Stores new users
     * returns user details
     */
    public function storeUser($name, $email, $password)
    {
        $uuid = uniqid('', true);

        //Call hashSSHA custom method which return an associated array $hash => "encrypted", "salt"
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"];
        $salt = $hash["salt"];

        $result = mysql_query("INSERT INTO users(unique_id, name, email, encrypted_password, salt, created_at) VALUES('$uuid','$name','$email','$encrypted_password','$salt',NOW())");

        //Check for succesfull store (REGISTRATION)
        if ($result) {
            //get user details
            $uid = mysql_insert_id(); //last inserted id
            $result = mysql_query("SELECT * FROM users WHERE uid=$uid");

            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }

    /**for AUTHENTICATION
     *get user by email and password
     * @param $email
     * @param $password
     * @return array|bool|resource
     */
    public function getUserByEmailAndPassword($email, $password)
    {
        $result = mysql_query("SELECT * FROM users WHERE email = '$email'") or die(mysql_error());
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysql_fetch_array($result);
            $salt = $result['salt'];
            $encrypted_password = $result['encrypted_password'];
            $hash = $this->checkhashSSHA($salt, $password);

            //check for password equality
            if ($hash == $encrypted_password) {
                //correct authentication details
                return $result;
            }
            else {
                //user not found
                return false;
            }
        }
    }

    /**
     * Check for user existence
     * @returns boolean
     */
    public function isUserExisted($email) {
        $result = mysql_query("SELECT email FROM users WHERE email = '$email' ");
        $no_of_rows = mysql_num_rows($result);
        if($no_of_rows > 0)
            return true;
        else
            return false;
    }

    /**
     * Encrypting password
     * @param $password
     * @return salt and encrypted password
     */
    public function hashSSHA($password)
    {
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);

        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }

    /**
     * Decrypting password
     * @param $salt $password
     * @return hash string
     */
    public function checkhashSSHA($salt, $password)
    {
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
        return $hash;
    }
}

?>