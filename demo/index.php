<?php
require 'totp.php';
authenticate();

$message = "";

if( isset($_GET['logout']) ){
        logout();
        authenticate();
}

if( isset($_GET['totp']) ){
        $message = "Váš nový TOTP kód je: ";
        $message .= totp_reset();
}

?>
<!DOCTYPE html>
<html>
<head>
	<title>Přihlášen</title>
	<meta charset="utf-8" />
</head>
<body>
        <h2><?php echo 'Vítej ' . $_SESSION['user'] ?></h2>
        <a href="?totp">Vygenerovat nový TOTP kód</a><br>
        <a href="?logout">Odhlásit se</a><br>
	<p id="message"><?php echo $message ?></p>
</body>
</html>
