<?php
	require_once(__dir__ . '/../common/init.php');
    require_once(DMK_LIB_PATH . '/api_clear_table.php');
	
	$api = new ApiClearTable();
    $api->run();    
?> 
