<?php
session_start();
$error='';
if (isset($_POST['submit'])) {
    if (empty($_POST['username']) || empty($_POST['password'])) {
        $error = "Username or Password is invalid";
    }else{
        $name=$_POST['username'];
        $pass=$_POST['password'];
        //TODO check injection
        $path = '/st1/cppopovich/Programming/CS495/login.pl';
        $result = `perl $path $name $pass`;
        if($result === 'success'){//TODO
            $_SESSION['login_user']=$name;
            header("location: webprofile.php");
        }else{
            $error = "Username or Password is invalid";
        }
    }
}
?>
