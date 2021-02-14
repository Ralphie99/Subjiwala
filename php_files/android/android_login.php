<?php
       
        include 'db.php';
        $username = $_GET['username'];
		$pass = $_GET['pass'];
	
		$sql = "SELECT id FROM customer WHERE login='$username' AND password='$pass' LIMIT 1";
	        $check = mysqli_fetch_array(mysqli_query($conn,$sql));

		if(isset($check)){
		echo '1';
		}else{
				
			echo '0';
		
		}
			
	        mysqli_close($con);
		
		
		?>