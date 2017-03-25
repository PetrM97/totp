<?php

// Settings
$auth1_addr = "auth.php";
$auth2_addr = "auth2.php";
$totp_addr = "localhost:8080";
$data_file = ".htpasswd";
$hash_method = "{plain}";
$users = load_data();
save_data();
session_start();

function load_data() {
	global $data_file;
	global $hash_method;

	$users = array();
	$data = fopen($data_file, "r");
	while(!feof($data)) {
		// Replace \n in every line and get array {user => pass}
  		$line = explode( $hash_method,str_replace("\n","",fgets($data)) );
		if( count($line) > 1 ){
			$users[$line[0]] = $line[1];
		}
	}
	fclose($data);

	return $users;
}

function save_data(){
	global $users;
	global $data_file;
	global $hash_method;

	file_put_contents($data_file,"");
	foreach ($users as $user => $pass) {
		$line = $user . '{plain}' . $pass . "\n";
		file_put_contents($data_file,$line,FILE_APPEND);
	}
}

function addUser($username, $password){
	global $users;

	$username = strtolower($username);
	if( array_key_exists($username, $users) ){
		return false;
	}else{
		$users[$username] = hashPass($password);
		save_data();
		return true;
	}
}

function removeUser(){
	global $users;
	$username = $_SESSION['user'];

	if( !array_key_exists($username, $users) ){
		return false;
	}else{
		unset($users[$username]);
		save_data();
		return true;
	}
}

function hashPass($string){
	// TODO: hashing passwords
	return $string;
}

function authenticate(){
	global $auth1_addr;
	global $auth2_addr;

	if( !isset($_SESSION['logged_in']) ){
		header('Location: ' . $auth1_addr);
		die();
	}elseif( !isset($_SESSION['totp']) ){
		header('Location: ' . $auth2_addr);
		die();
	}
}

function pass_verify(){
	global $users;

	$user = strtolower($_SESSION['user']);
	$pass = hashPass($_SESSION['pass']);
	// Check username and password
	if( array_key_exists($user, $users) &&
	( strcmp($pass,$users[$user]) == 0 ) ){
		$_SESSION['logged_in'] = True;
		return True;
	}else{
		return False;
		unset($_SESSION['logged_in']);
	}
}

function totp_add($user){
	global $totp_addr;

	$url = $totp_addr . '/users';

	$curl = curl_init($url);
        $curl_post_data = $user;
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($curl, CURLOPT_POST, true);
        curl_setopt($curl, CURLOPT_POSTFIELDS, $curl_post_data);
        $curl_response = curl_exec($curl);
        curl_close($curl);

        $json = json_decode($curl_response, true);
        if( $json['status'] == "ok"){
                return $json['secret'];
        }else {
                return null;
        }
}

function totp_exists(){
	global $totp_addr;
	$user = $_SESSION['user'];

	$url = $totp_addr . "/users/" . $user;

        $curl = curl_init($url);
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
        $curl_response = curl_exec($curl);
        curl_close($curl);

        $json = json_decode($curl_response, true);
        if( $json['exists'] == "1" ){
                return True;
        }else{
		return False;
	}
}

function totp_auth($code){
	global $totp_addr;
	$user = $_SESSION['user'];

	if( !totp_exists() ){
		$_SESSION['totp'] = True;
		return True;
	}

	$url = $totp_addr . '/users/' . $user;

        $curl = curl_init($url);
        $curl_post_data = $code;
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($curl, CURLOPT_POST, true);
        curl_setopt($curl, CURLOPT_POSTFIELDS, $curl_post_data);
	$curl_response = curl_exec($curl);
        curl_close($curl);

        $json = json_decode($curl_response, true);
	if( $json['valid'] == "true"){
		$_SESSION['totp'] = True;
                return True;
	}else {
		unset($_SESSION['totp']);
		// session fixation for security
		session_regenerate_id(true);
        	return False;
	}
}

function totp_reset(){
	global $totp_addr;
	$user = $_SESSION['user'];

	$url = $totp_addr . '/users/' . $user;

        $curl = curl_init($url);
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($curl, CURLOPT_CUSTOMREQUEST, "PUT");
        $curl_response = curl_exec($curl);
        curl_close($curl);

        $json = json_decode($curl_response, true);
        return $json['secret'];

}

function totp_delete(){
	global $totp_addr;
	$user = $_SESSION['user'];

	$url = $totp_addr . '/users/' . $user;

        $curl = curl_init($url);
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($curl, CURLOPT_CUSTOMREQUEST, "DELETE");
        $curl_response = curl_exec($curl);
        curl_close($curl);

        $json = json_decode($curl_response, true);
        if( $json['exists'] == "true"){
                return True;
        }else {
                return False;
        }
}

function logout(){
	unset($_SESSION['logged_in']);
	unset($_SESSION['totp']);
	session_destroy();
}

function write_secret($username, $secret){
	$message = "";
	$message .= "TOTP heslo je <input id='secret' value=" . $secret . ">";
	$message .= "<script>new Clipboard('.btn')</script>";
	$message .= "<button class='btn' data-clipboard-target='#secret'>Zkopírovat</button>";
	$message .= '<br>';
	$data = "otpauth://totp/" . $username . "@TOTP-Demo?secret=" . $secret;
	$url = "https://chart.googleapis.com/chart?cht=qr&chs=300x300&chl=" . $data;
	$message .= "<img src='" . $url . "' />";
	return $message;
}
