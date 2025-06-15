<?php
session_start();

unset($_SESSION['token']);
unset($_SESSION['usuario']);

header("Location: ./formulario_login.php");
exit;
