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
    die();
}

if ( !pass_verify() ) {
    auth();
} else {
        header('Location: auth2.php');
        die();
}
?>
