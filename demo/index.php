<?php

$user = 'demo';
$service_url = 'localhost:8080/users/' . $user;
$curl = curl_init($service_url);
$curl_post_data = $_GET["code"];
curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
curl_setopt($curl, CURLOPT_POST, true);
curl_setopt($curl, CURLOPT_POSTFIELDS, $curl_post_data);
$curl_response = curl_exec($curl);
curl_close($curl);

echo $curl_response

?>
