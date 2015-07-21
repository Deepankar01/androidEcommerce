<?php

foreach (apache_request_headers() as $name => $value) 
{
    echo "$name: $valuen";
}
?>