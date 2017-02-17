<?php
require 'totp.php';
session_start();

$message = "";

if( isset($_POST['user']) && isset($_POST['pass']) ){
        if( addUser($_POST['user'],$_POST['pass']) ){
                $secret = totp_add($_POST['user']);
                if( $secret != null ){
                        $message = "Sdílené heslo je " . $secret;
                }else{
                        $message = "Chyba při vytváření TOTP hesla";
                }
        }else{
                $message = "Uživatel již existuje";
        }
}


?>
<!DOCTYPE html>
<html>
<head>
	<title>Registrace</title>
	<meta charset="utf-8" />
	<link rel="stylesheet" href="style.css">
</head>
<body>
	<form action="" method="post">
                <h2>Registrace</h2>
		<input id="user" type="text" name="user" required autofocus>
                <input id="pass" type="password" name="pass" required>
		<input id="register" type="submit" value="Odeslat">
		<p id="message"><?php echo $message ?></p>
	</form>
</body>
</html>
