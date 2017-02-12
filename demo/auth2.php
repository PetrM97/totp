<?php
require 'totp.php';
session_start();

// Lze ověřit pouze přihlášeného uživatele
if ( !isset($_SESSION['user']) ){
	header('Location: auth.php');
	die();
}

$message = "";

if( isset($_POST['code']) ){
	if( !totp_auth($_POST['code']) ){
		$message = "Nesprávné heslo";
	}else{
		$message = "Přihlášen";
	}
}

?>
<?DOCTYPE html>
<html>
<head>
	<title>Dvoufázové ověření</title>
	<meta charset="utf-8" />
	<link rel="stylesheet" href="auth2.css">
</head>
<body>
	<form action="" method="post">
		<p id="head">
			<h2>Dvoufázové ověření</h2>
			Zadejte Váš jednorázový ověřovací kód.
		</p>
		<br>
		<input id="in" type="number" name="code"
onpaste="setTimeout(function(){submit()},0);" required autofocus>
		<input id="sub" type="submit" class="btn" value="Odeslat">
		<p id="message"><?php echo $message ?></p>
	</form>
<body>
<html>
