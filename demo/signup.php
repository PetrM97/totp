<?php
require 'totp.php';

$message = "";

if( isset($_POST['user']) && isset($_POST['pass']) ){
        if( addUser($_POST['user'],$_POST['pass'])){
                $message .= "Uživatel " . $_POST['user'] . " byl vytvořen.<br>";

                if( isset($_POST['totp_check']) ){
                        $secret = totp_add($_POST['user']);
                        if( $secret != null ){
                                $message .= "TOTP heslo je " . $secret;
                        }else{
                                $message .= "Chyba při vytváření TOTP hesla";
                        }
                }
        }else{
                $message .= "Uživatel již existuje";
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
        <a href="/">Zpět</a>
	<form action="" method="post">
                <h2>Registrace</h2>
		<input id="user" type="text" name="user" required autofocus>
                <input id="pass" type="password" name="pass" required>
                <label>Vygenerovat TOTP
                        <input id="check" type="checkbox" name="totp_check">
                </label>
		<input id="register" type="submit" value="Odeslat">
		<p id="message"><?php echo $message ?></p>
	</form>
</body>
</html>
