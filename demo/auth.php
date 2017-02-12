<?php
require 'totp.php';

session_start();

if(isset($_GET['logout'])){
        logout();
        header('Location: auth.php');
        die();
}

if(isset($_SESSION['user'])){
        $_SESSION['user'] = $_SERVER['PHP_AUTH_USER'];
        $_SESSION['pass'] = $_SERVER['PHP_AUTH_PW'];
}

function auth() {
    header('WWW-Authenticate: Basic realm="TOTP Demo"');
    header('HTTP/1.0 401 Unauthorized');
    $_SESSION['user'] = $_SERVER['PHP_AUTH_USER'];
    $_SESSION['pass'] = $_SERVER['PHP_AUTH_PW'];
    echo "Pro přihlášení zadejte správné uživatelské jméno a heslo\n";
    echo $_SESSION['user'] . "\n";
    echo $_SESSION['pass'] . "\n";
    die();
}

if ( !pass_verify() ) {
    auth();
} else {
        //authenticate();
        echo "Logged in: " . htmlspecialchars($_SESSION["logged_in"]);
        echo "<p>Verify: " . pass_verify() . "</p>";
        echo "<p>Welcome: " . htmlspecialchars($_SESSION['user']) . "<br />";
        echo "<form action='' method='post'>\n";
        echo "<input type='submit' value='Re Authenticate' />\n";
        echo "</form></p>\n";
        echo "<a href='?logout'>Logout</a>\n";
        echo "<a href='auth2.php'>Pokracovat</a>";
}
?>
