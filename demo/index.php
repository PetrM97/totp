<?php
require 'totp.php';

$message = "";

if( isset($_GET['logout']) ){
        logout();
}

if( isset($_GET['delete']) ){
        totp_delete();
        removeUser();
}

if( isset($_GET['activate']) ){
        $message = "Váš TOTP kód je: ";
        $message .= totp_add($_SESSION['user']);
}

if( isset($_GET['totp']) ){
        $message = "Váš nový TOTP kód je: ";
        $message .= totp_reset();
}

if( isset($_GET['deactivate']) ){
        totp_delete();
        $message = "TOTP deaktivováno";
}

authenticate();

?>
<!DOCTYPE html>
<html>
<head>
	<title>Přihlášen</title>
	<meta charset="utf-8" />
        <link rel="stylesheet" href="style.css">
</head>
<body>
        <h2><?php echo 'Vítej ' . $_SESSION['user'] ?></h2>
        <a href="?activate">Aktivovat TOTP</a><br>
        <a href="?deactivate">Deaktivovat TOTP</a><br>
        <a href="?totp">Vygenerovat nový TOTP kód</a><br>
        <a href="?logout">Odhlásit se</a><br>
        <a href="?delete">Smazat účet</a><br>
	<p id="message"><?php echo $message ?></p>
</body>
</html>
