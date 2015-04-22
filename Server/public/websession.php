<?php
//TODO db
session_start();
$user_check=$_SESSION['login_user'];
if(!isset($user_check)){
    header('Location: index.php');
}
?>
