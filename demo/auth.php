<?php

$totp_addr = "auth2.php";

function authenticate() {
    header('WWW-Authenticate: Basic realm="TOTP Demo"');
    header('HTTP/1.0 401 Unauthorized');
    echo "Pro přihlášení zadejte správné uživatelské jméno a heslo\n";
    exit;
}

if (!isset($_SERVER['PHP_AUTH_USER']) ||
    ($_POST['SeenBefore'] == 1 && $_POST['OldAuth'] == $_SERVER['PHP_AUTH_USER'])) {
    authenticate();
} else {
    echo "<p>Welcome: " . htmlspecialchars($_SERVER['PHP_AUTH_USER']) . "<br />";
    echo "Old: " . htmlspecialchars($_REQUEST['OldAuth']);
    echo "<form action='' method='post'>\n";
    echo "<input type='hidden' name='SeenBefore' value='1' />\n";
    echo "<input type='hidden' name='OldAuth' value=\"" . htmlspecialchars($_SERVER['PHP_AUTH_USER']) . "\" />\n";
    echo "<input type='submit' value='Re Authenticate' />\n";
    echo "</form></p>\n";
	echo "<a href='" . $totp_addr . "'>Pokracovat</a>";
}
?>

