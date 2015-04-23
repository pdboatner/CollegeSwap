<?php
include('websession.php');
?>
<!DOCTYPE html>
<html>
<head>
<title>Your Home Page</title>
</head>
<body>
<div id="profile">
<b id="welcome">Welcome : <i><?php echo $login_session; ?></i></b>
<b id="logout"><a href=weblogout.php>Log Out</a></b>
</div>
<br />
<form action="request.php" method="POST">
Table:<br>
<input type="text" name="tbl" value="textbook">
<br>
Args:<br>
<input type="text" name="args" value="subject=CS">
<br>
Sort:<br>
<input type="text" name="sort" value="+price">
<br><br>
<input type="submit" value="Submit">
</body>
</html>
