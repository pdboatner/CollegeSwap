<?php
session_start();
$error='';
if (empty($_POST['name']) || empty($_POST['pass'])) {
        $error = "Username or Password is invalid";
}else{
    $name=$_POST['name'];
    $pass=$_POST['pass'];
    $path = '/st1/cppopovich/Programming/CS495/login.pl';
    $name = escapeshellarg($name);
    $pass = escapeshellarg($pass);
    $result = `perl $path $name $pass`;
    if($result === 'success'){
        $_SESSION['login_user']=$name;
    }else{
        $error = "Username or Password is invalid";
    }
    print $result;
}
?>
