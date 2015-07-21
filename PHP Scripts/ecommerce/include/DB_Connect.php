<?php

class DB_Connnect
{
    function __construct()
    {

    }

    function __destruct()
    {

    }

    public function connect()
    {

        //require_once 'Config.php';

        define("DB_HOST",'localhost');
        define("DB_USER",'root');
        define("DB_PASSWORD",'');
        define("DB_DATABASE",'u810384278_ecom');
        $con = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD) or die(mysql_error());

        mysql_select_db(DB_DATABASE);

        return $con;
    }

    public function close()
    {
        mysql_close();
    }
}

?>