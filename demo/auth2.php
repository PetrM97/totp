<?php

$auth_addr = "auth.php";

// Lze ověřit pouze přihlášeného uživatele

if ( !isset($_SERVER['PHP_AUTH_USER']) ){
	header('Location: ' . $auth_addr);
	die();
}

$user = $_SERVER['PHP_AUTH_USER'];
$message = "";

function totp_auth() {
	global $user;
	global $message;
	if( !isset($_POST['code']) ){
		$pass = null;
	}else{
		$pass = $_POST['code'];
        }
	$service_url = 'localhost:8080/users/' . $user;

        $curl = curl_init($service_url);
        $curl_post_data = $pass;
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($curl, CURLOPT_POST, true);
        curl_setopt($curl, CURLOPT_POSTFIELDS, $curl_post_data);
        $curl_response = curl_exec($curl);
        curl_close($curl);

        $json = json_decode($curl_response, true);
       	//echo "Valid " . $json['valid'];
        //echo "Status " . $json['status'];
        if( $json['valid'] == "true" || $json['exists'] == "0"){
		$message = "Přihlášen";
		//header('Location: ' . "index.php");
                return True;
        }
	if( !is_null($pass) ) { $message = "Nesprávné heslo";}
        return False;
}

totp_auth();
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
		<p id="message"><?php echo $message ?></p>
		<input type="number" name="code" required autofocus>
		<input type="submit" value="Odeslat">
	</form>
<body>
<html>
