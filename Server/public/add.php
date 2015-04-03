<?php
$script = "/st1/cppopovich/Programming/CS495/add.pl";

$tbl = $_POST["tbl"];
$args = $_POST["args"];

$tbl = escapeshellarg($tbl);
$args = escapeshellarg($args);

$result = `perl $script $tbl $args`;
echo $result;
?>
