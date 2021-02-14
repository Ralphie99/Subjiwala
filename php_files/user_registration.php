<?php 
error_reporting(E_ALL);
ini_set('display_errors', '1');
?>
<?php 
include "storescripts/connect_to_mysql.php";
if (isset($_POST['name'])) {
    $name = mysql_real_escape_string($_POST['name']);
    $login = mysql_real_escape_string($_POST['login']);
    $password = mysql_real_escape_string($_POST['password']);
    $cpassword = mysql_real_escape_string($_POST['cpassword']);	
	$mobile = mysql_real_escape_string($_POST['mobile']);
	$security = mysql_real_escape_string($_POST['security']);
	$securityanswer = mysql_real_escape_string($_POST['answer']);
	$email = mysql_real_escape_string($_POST['email']);
	$address = mysql_real_escape_string($_POST['address']);
	$city = mysql_real_escape_string($_POST['city']);
	$state = mysql_real_escape_string($_POST['state']);
	$pin = mysql_real_escape_string($_POST['pin']);
	if($password != $cpassword){
		echo 'Your passwords do not match., <a href="user_registration.php">Refill here</a>';
		exit();
	}
	$sql = mysql_query("SELECT id FROM customer WHERE mobile='$mobile' LIMIT 1");
	$userMatch = mysql_num_rows($sql); 
    if ($userMatch > 0) {
		echo 'Sorry your mobile number is already registered into the system, <a href="user_registration.php">click here</a>';
		exit();
	}
	$sql = mysql_query("INSERT INTO customer (login, password, name, mobile, security, securityanswer, email, address, city, state, pin) 
        VALUES('$login', '$password','$name','$mobile','$security','$securityanswer','$email','$address','$city','$state','$pin')") or die (mysql_error());
     $pid = mysql_insert_id();
	header("location: index.php?success"); 
    exit();
}
?>
<!DOCTYPE HTML>
<html>
	<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Sabjiwala-Registration</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="author" content="" />

  <!-- Facebook and Twitter integration -->
	<meta property="og:title" content=""/>
	<meta property="og:image" content=""/>
	<meta property="og:url" content=""/>
	<meta property="og:site_name" content=""/>
	<meta property="og:description" content=""/>
	<meta name="twitter:title" content="" />
	<meta name="twitter:image" content="" />
	<meta name="twitter:url" content="" />
	<meta name="twitter:card" content="" />

	<link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700" rel="stylesheet">
	
	<!-- Animate.css -->
	<link rel="stylesheet" href="css/animate.css">
	<!-- Icomoon Icon Fonts-->
	<link rel="stylesheet" href="css/icomoon.css">
	<!-- Bootstrap  -->
	<link rel="stylesheet" href="css/bootstrap.css">

	<!-- Magnific Popup -->
	<link rel="stylesheet" href="css/magnific-popup.css">

	<!-- Flexslider  -->
	<link rel="stylesheet" href="css/flexslider.css">

	<!-- Owl Carousel -->
	<link rel="stylesheet" href="css/owl.carousel.min.css">
	<link rel="stylesheet" href="css/owl.theme.default.min.css">
	
	<!-- Date Picker -->
	<link rel="stylesheet" href="css/bootstrap-datepicker.css">
	<!-- Flaticons  -->
	<link rel="stylesheet" href="fonts/flaticon/font/flaticon.css">

	<!-- Theme style  -->
	<link rel="stylesheet" href="css/style.css">

	<!-- Modernizr JS -->
	<script src="js/modernizr-2.6.2.min.js"></script>
	<!-- FOR IE9 below -->
	<!--[if lt IE 9]>
	<script src="js/respond.min.js"></script>
	<![endif]-->
<script type="text/javascript" language="javascript"> 
<!--
function validateMyForm ( ) { 
    var isValid = true;
    if ( document.userForm.name.value == "" ) { 
	    alert ( "Please type Your Name" ); 
	    isValid = false;
    } else if ( document.userForm.login.value == "" ) { 
	    alert ( "Please type Your Login Name" ); 
	    isValid = false;
    } else if ( document.userForm.password.value == "" ) { 
	    alert ( "Please type Your Password" ); 
	    isValid = false;
    } else if ( document.userForm.cpassword.value == "" ) { 
	    alert ( "Please confirm Your Password" ); 
	    isValid = false;
    } else if ( document.userForm.security.value == "" ) { 
	    alert ( "Please select security question" ); 
	    isValid = false;
    } else if ( document.userForm.answer.value == "" ) { 
	    alert ( "Please select security question answer" ); 
	    isValid = false;
    } else if ( document.userForm.mobile.value == "" ) { 
            alert ( "Please enter your Mobile Number" ); 
            isValid = false;
    } else if ( document.userForm.email.value == "" ) { 
            alert ( "Please provide your Email" ); 
            isValid = false;
    } else if ( document.userForm.address.value == "" ) { 
            alert ( "Please provide your address" ); 
            isValid = false;
    } else if ( document.userForm.city.value == "" ) { 
            alert ( "Please provide your city" ); 
            isValid = false;
    } else if ( document.userForm.state.value == "" ) { 
            alert ( "Please provide your state" ); 
            isValid = false;
    } else if ( document.userForm.pin.value == "" ) { 
            alert ( "Please provide your pin" ); 
            isValid = false;
    }
    return isValid;
}
//-->
</script>
	</head>
	<body>
		
	<div class="colorlib-loader"></div>

	<div id="page">
		<?php
error_reporting(E_ERROR | E_WARNING | E_PARSE);

session_start();
if(!isset($_SESSION['user']))
{
 ?>


    <td width="68%" align="right"> <a href="http://localhost/store/user_login.php">Login</a> | <a href="http://localhost/store/forgetpass.php">Reset Your Password</a></td>
<?php } ?>
<?php
error_reporting(E_ERROR | E_WARNING | E_PARSE);

session_start();
if(isset($_SESSION['user']))
{
 ?>


    <td width="68%" align="right"> You are logged in as: <?php echo $_SESSION["user"] ?> <a href="http://localhost/store/logout.php">Logout</a> | <a href="http://localhost/store/cart.php">Your Cart</a> | <a href="http://localhost/k/kk/user_profile.php">Edit profile</a></td>
<?php } ?>
	</tr>
  <tr>
    <td colspan="2"><a href="http://localhost/store/index.php">Home</a> &nbsp; &middot; &nbsp; <a href="http://localhost/store/list_all_products.php">Products</a> &nbsp; &middot; &nbsp; <a href="http://localhost/store/help.php">Help</a> &nbsp; &middot; &nbsp; <a href="http://localhost/store/contact.php">Contact</a></td>
		<aside id="colorlib-hero" class="breadcrumbs">
			<div class="flexslider">
				<ul class="slides">
			   	<li style="background-image: url(images/cover-img-1.jpg);">
			   		<div class="overlay"></div>
			   		<div class="container-fluid">
			   			<div class="row">
				   			<div class="col-md-6 col-md-offset-3 col-sm-12 col-xs-12 slider-text">
				   				<div class="slider-text-inner text-center">
				   					<h1>Contact</h1>
				   					<h2 class="bread"><span><a href="index.php">Home</a></span> <span>Contact</span></h2>
				   				</div>
				   			</div>
				   		</div>
			   		</div>
			   	</li>
			  	</ul>
		  	</div>
		</aside>

		<div id="colorlib-contact">
			<div class="container">
				<div class="row">
					
					<div class="col-md-10 col-md-offset-1">
						<div class="contact-wrap">
							<h3>Get In Touch</h3>
							<form action="" enctype="multipart/form-data" name="userForm" id="userForm" method="post">
								<div class="row form-group">
									<div class="col-md-6 padding-bottom">
										<label for="name">Name</label>
										<input name="name" type="text" id="name"  class="form-control"/>
									</div>
									<div class="col-md-6">
										<label for="login">Login</label>
										<input name="login" type="text" id="login" size="20" class="form-control"/>
									</div>
								</div>

								<div class="row form-group">
									<div class="col-md-12">
										<label for="password">Password</label>
										<input name="password" type="password" id="password" size="20"class="form-control" />
									</div>
								</div>

								<div class="row form-group">
									<div class="col-md-12">
										<label for="cpassword">Confirm Password</label>
										<input name="cpassword" type="password" class="form-control"id="cpassword" size="20" />
									</div>
								</div>
								
								<div class="row form-group">
									<div class="col-md-12">
										<label for="security">Security Question</label>
										<select name="security"class="form-control" id="security">
          <option value=""></option>
          <option value="What is your school name?">What is your school name?</option>
          <option value="What is your mother's first maiden name?">What is your mother's first maiden name?</option>
          <option value="What is your favourite hobby?">What is your favourite hobby?</option>
          <option value="What is your nick name?">What is your nick name?</option>
          <option value="What is your pet name?">What is your pet name?</option>
          <option value="What is your favourite game?">What is your favourite game?</option>
          </select>
									</div>
								</div>
								
								<div class="row form-group">
									<div class="col-md-12">
										<label for="answert">Answer</label>
										<input name="answer" type="text" id="answer" size="40"class="form-control" />
									</div>
								</div>
								
								<div class="row form-group">
									<div class="col-md-12">
										<label for="mobile">Mobile</label>
										<input name="mobile" type="text" class="form-control"id="mobile" size="10" />
									</div>
								</div>
								
								<div class="row form-group">
									<div class="col-md-12">
										<label for="email">Email</label>
										<input name="email" type="text"class="form-control" id="email" size="50" />
									</div>
								</div>
								
								<div class="row form-group">
									<div class="col-md-12">
										<label for="address">Address</label>
										<textarea name="address" id="address" cols="64" class="form-control"rows="5"></textarea>
									</div>
								</div>

								<div class="row form-group">
									<div class="col-md-12">
										<label for="city">City</label>
										<input name="city" type="text" id="city" class="form-control"size="20" />
									</div>
								</div>
								
								<div class="row form-group">
									<div class="col-md-12">
										<label for="State">State</label>
										<input name="state" type="text" id="state" size="20"class="form-control" />
									</div>
								</div>
								
								<div class="row form-group">
									<div class="col-md-12">
										<label for="pin">Pincode</label>
										<input name="pin" type="text" id="pin" size="6"class="form-control"/>
									</div>
								</div>
								<div class="form-group text-center">
									<input type="submit" name="button" id="button" value="Register Now"  class="btn btn-primary" onclick="javascript:return validateMyForm();"/>
								</div>
							</form>		
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="colorlib-subscribe">
			<div class="overlay"></div>
			<div class="container">
				<div class="row">
					<div class="col-md-8 col-md-offset-4">
						<div class="col-md-6 text-center">
							<a href="http://localhost/store/contact.php"><h2><i class="icon-paperplane" ></i>Give Your Feedback</h2></a>
						</div>
						
					</div>
				</div>
			</div>
		</div>

		<footer id="colorlib-footer" role="contentinfo">
			<div class="container">
				<div class="row row-pb-md">
					<div class="col-md-3 colorlib-widget">
						<h4>About Store</h4>
						<p>Facilis ipsum reprehenderit nemo molestias. Aut cum mollitia reprehenderit. Eos cumque dicta adipisci architecto culpa amet.</p>
						<p>
							<ul class="colorlib-social-icons">
								<li><a href="#"><i class="icon-twitter"></i></a></li>
								<li><a href="#"><i class="icon-facebook"></i></a></li>
								<li><a href="#"><i class="icon-linkedin"></i></a></li>
								<li><a href="#"><i class="icon-dribbble"></i></a></li>
							</ul>
						</p>
					</div>
					<div class="col-md-2 colorlib-widget">
						<h4>Customer Care</h4>
						<p>
							<ul class="colorlib-footer-links">
								<li><a href="#">Contact</a></li>
								<li><a href="#">Returns/Exchange</a></li>
								<li><a href="#">Gift Voucher</a></li>
								<li><a href="#">Wishlist</a></li>
								<li><a href="#">Special</a></li>
								<li><a href="#">Customer Services</a></li>
								<li><a href="#">Site maps</a></li>
							</ul>
						</p>
					</div>
					<div class="col-md-2 colorlib-widget">
						<h4>Information</h4>
						<p>
							<ul class="colorlib-footer-links">
								<li><a href="#">About us</a></li>
								<li><a href="#">Delivery Information</a></li>
								<li><a href="#">Privacy Policy</a></li>
								<li><a href="#">Support</a></li>
								<li><a href="#">Order Tracking</a></li>
							</ul>
						</p>
					</div>

					<div class="col-md-2">
						<h4>News</h4>
						<ul class="colorlib-footer-links">
							<li><a href="blog.html">Blog</a></li>
							<li><a href="#">Press</a></li>
							<li><a href="#">Exhibitions</a></li>
						</ul>
					</div>

					<div class="col-md-3">
						<h4>Contact Information</h4>
						<ul class="colorlib-footer-links">
							<li>291 South 21th Street, <br> Suite 721 New York NY 10016</li>
							<li><a href="tel://1234567920">+ 1235 2355 98</a></li>
							<li><a href="mailto:info@yoursite.com">info@yoursite.com</a></li>
							<li><a href="#">yoursite.com</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="copy">
				<div class="row">
					<div class="col-md-12 text-center">
						<p>
							
							<span class="block"><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="icon-heart2" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></span> 
							<span class="block">Demo Images: <a href="http://unsplash.co/" target="_blank">Unsplash</a> , <a href="http://pexels.com/" target="_blank">Pexels.com</a></span>
						</p>
					</div>
				</div>
			</div>
		</footer>
	</div>


	<div class="gototop js-top">
		<a href="#" class="js-gotop"><i class="icon-arrow-up2"></i></a>
	</div>
	
	<!-- jQuery -->
	<script src="js/jquery.min.js"></script>
	<!-- jQuery Easing -->
	<script src="js/jquery.easing.1.3.js"></script>
	<!-- Bootstrap -->
	<script src="js/bootstrap.min.js"></script>
	<!-- Waypoints -->
	<script src="js/jquery.waypoints.min.js"></script>
	<!-- Flexslider -->
	<script src="js/jquery.flexslider-min.js"></script>
	<!-- Owl carousel -->
	<script src="js/owl.carousel.min.js"></script>
	<!-- Magnific Popup -->
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/magnific-popup-options.js"></script>
	<!-- Date Picker -->
	<script src="js/bootstrap-datepicker.js"></script>
	<!-- Stellar Parallax -->
	<script src="js/jquery.stellar.min.js"></script>
	
	<!-- Main -->
	<script src="js/main.js"></script>

	</body>
</html>

