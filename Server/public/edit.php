<?php
$script = "/st1/cppopovich/Programming/CS495/edit.pl";

$tbl = $_POST["tbl"];
$key = $_POST["key"];
$args = $_POST["args"];

$tbl = escapeshellarg($tbl);
$key = escapeshellarg($key);
$args = escapeshellarg($args);

$result = `perl $script $tbl $key $args`;
echo $result;
?>
