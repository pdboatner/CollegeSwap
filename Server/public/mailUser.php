<?php
$email = $_POST["email"];
$text = $_POST["text"];

$email = escapeshellarg($email);
$text = escapeshellarg($text);

$result = `echo $text | mail $email`;
echo $result;
?>
