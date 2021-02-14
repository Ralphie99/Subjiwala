<?php
if(isset($_GET['sortby'])){
	$sortby = $_GET['sortby'];
} else {
	$sortby = "date_added";
}
if(isset($_GET['way'])){
	$way = $_GET['way'];
} else {
	$way = "DESC";
}
if(isset($_GET['filterby'])){
	$filterby = $_GET['filterby'];
}
else {
	$filterby = NULL;	
}
include "storescripts/connect_to_mysql.php";
if($filterby == NULL){
	$sql = mysql_query("SELECT * FROM products ORDER BY $sortby $way");
}
else {
	$sql = mysql_query("SELECT * FROM products WHERE category='$filterby' ORDER BY $sortby $way");	
}
$i = 0;
$productCount = mysql_num_rows($sql); 
if ($productCount > 0) {
	$i = 0;
$dyn_table = '<table height="300px" align="center">';
while($row = mysql_fetch_array($sql)){ 
    
    $id = $row["id"];
    $product_name = $row["product_name"];
	$price = $row["price"];
	$date_added = strftime("%b %d, %Y", strtotime($row["date_added"]));
    if ($i %8 == 0) { 
        
		
					
		$dyn_table .= '<tr height="300px"align="center">
		               <td>
						<a href="product.php?id=' . $id . '"><img style="border:#666 0px solid;" src="inventory_images/' . $id . '.jpg" alt="' . $product_name . '" width="200" height="200"  /></a>
						<br>' . $product_name . '<br>
						' . $price . '<br>
						<a class="btn btn-primary"href="product.php?id=' . $id . '">View Details</a>
						</td>';
    } else {
        $dyn_table .= '<td><a href="product.php?id=' . $id . '"><img style="border:#666 0px solid;" src="inventory_images/' . $id . '.jpg" alt="' . $product_name . '" width="200" height="200"  /></a>
						<br>' . $product_name . '<br>
						' . $price . '<br>
						 <a class="btn btn-primary"href="product.php?id=' . $id . '">View Details</a>
						</td>';
    }
	
    $i++;
	if ($i%8 == 0)
		$dyn_table .= '</tr>';
}
$dyn_table .= '</table>';	
	
	
}
mysql_close();
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
	
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<title>Sabjiwala-Products lists</title>
<link rel="stylesheet" href="style/style.css" type="text/css" media="screen" />
<style>
img{
float: left;
margin-right: 5px;	
}
</style>
</head>
<body>


	<div id="page">
		<nav class="colorlib-nav" role="navigation">
			<div class="top-menu">
				<div class="container">
					<div class="row">
						<div class="col-xs-2">
							<div id="colorlib-logo"><a href="http://localhost/store/index.html">SabjiWala.com</a></div>
						</div>
						<div class="col-xs-10 text-right menu-1">
							<ul>
								<li><a href="http://localhost/store/index.php">Home</a></li> &nbsp; &middot; &nbsp; <li><a href="http://localhost/store/list_all_products.php">Products</a></li> &nbsp; &middot; &nbsp; <li><a href="http://localhost/store/help.php">Help</a></li> &nbsp; &middot; &nbsp; <li><a href="http://localhost/store/contact.php">Contact</a></li>
								<?php
error_reporting(E_ERROR | E_WARNING | E_PARSE);

session_start();
if(!isset($_SESSION['user']))
{
 ?><li><a href="http://localhost/store/user_registration.php">Register</a></li><?php } ?>
								
								<li><?php
error_reporting(E_ERROR | E_WARNING | E_PARSE);

session_start();
if(!isset($_SESSION['user']))
{
 ?>
<a href="http://localhost/store/user_login.php">Login</a> | <a href="http://localhost/store/forgetpass.php">Reset Your Password</a></li><?php } ?>
<?php
error_reporting(E_ERROR | E_WARNING | E_PARSE);

session_start();
if(isset($_SESSION['user']))
{
 ?>
 You are logged in as: <?php echo $_SESSION["user"] ?> <a href="http://localhost/store/logout.php">Logout</a> | <a href="http://localhost/store/cart.php">Your Cart</a> | <a href="http://localhost/store/user_profile.php">Edit profile</a></td>
<?php } ?>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</nav>
		<?php 
  	if (isset($_GET['success'])){
			echo "Successful Registration.";
	}
  	if (isset($_GET['userloginsuccess'])){
			echo "Successful Login.";
	}
	if (isset($_GET['resetsuccess'])){
			echo "Password Successfully Changed.";
	}
  ?>
  <div class="colorlib-loader"></div>
  <aside id="colorlib-hero" class="breadcrumbs">
			<div class="flexslider">
				<ul class="slides">
			   	<li style="background-image: url(images/cover-img-1.jpg);">
			   		<div class="overlay"></div>
			   		<div class="container-fluid">
			   			<div class="row">
				   			<div class="col-md-6 col-md-offset-3 col-sm-12 col-xs-12 slider-text">
				   				<div class="slider-text-inner text-center">
				   					<h1>Products</h1>
				   					<h2 class="bread"><span><a href="http://localhost/store/index.php">Home</a></span> <span>Shop</span></h2>
				   				</div>
				   			</div>
				   		</div>
			   		</div>
			   	</li>
			  	</ul>
		  	</div>
		</aside>
<div align="center" id="mainWrapper">	
   		
		
  <div id="pageContent">
    <p>Sort By: 
<select name="menu" onChange="window.document.location.href=this.options[this.selectedIndex].value;" value="Choose">
		<option value = ''> Choose </option>
        <option value="http://localhost/store/list_all_products.php?sortby=price&way=DESC&filterby=<?php 
		if(isset($_GET['filterby']))
			echo $filterby; 
		else
			echo NULL;
		?>">Price: High to Low</option>
        <option value="http://localhost/store/list_all_products.php?sortby=price&way=ASC&filterby=<?php 
		if(isset($_GET['filterby']))
			echo $filterby; 
		else
			echo NULL;
		?>">Price: Low to High</option>
		<option value="http://localhost/store/list_all_products.php?sortby=date_added&way=DESC&filterby=<?php 
		if(isset($_GET['filterby']))
			echo $filterby; 
		else
			echo NULL;
		?>">Date Added</option>
 </select> 
| Filter By: 
<select name="menu" onChange="window.document.location.href=this.options[this.selectedIndex].value;" value="Choose">
		<option value = ''> Choose </option>
         <option value="http://localhost/store/list_all_products.php">None</option>
          <option value="http://localhost/store/list_all_products.php?filterby=Footwear&sortby=<?php 
		if(isset($_GET['sortby']))
			echo $sortby; 
		else
			echo "date_added";
		?>&way=<?php 
		if(isset($_GET['way']))
			echo $way; 
		else
			echo "DESC";
		?>">Footwear</option>
          <option value="http://localhost/store/list_all_products.php?filterby=Clothing&sortby=<?php 
		if(isset($_GET['sortby']))
			echo $sortby; 
		else
			echo "date_added";
		?>&way=<?php 
		if(isset($_GET['way']))
			echo $way; 
		else
			echo "DESC";
		?>">Clothing</option>
          <option value="http://localhost/store/list_all_products.php?filterby=Watches&sortby=<?php 
		if(isset($_GET['sortby']))
			echo $sortby; 
		else
			echo "date_added";
		?>&way=<?php 
		if(isset($_GET['way']))
			echo $way; 
		else
			echo "DESC";
		?>">Watches</option>
          <option value="http://localhost/store/list_all_products.php?filterby=HandBag&sortby=<?php 
		if(isset($_GET['sortby']))
			echo $sortby; 
		else
			echo "date_added";
		?>&way=<?php 
		if(isset($_GET['way']))
			echo $way; 
		else
			echo "DESC";
		?>">HandBag</option>
          <option value="http://localhost/store/list_all_products.php?filterby=Perfumes&sortby=<?php 
		if(isset($_GET['sortby']))
			echo $sortby; 
		else
			echo "date_added";
		?>&way=<?php 
		if(isset($_GET['way']))
			echo $way; 
		else
			echo "DESC";
		?>">Perfumes</option>
          <option value="http://localhost/store/list_all_products.php?filterby=Jewellery&sortby=<?php 
		if(isset($_GET['sortby']))
			echo $sortby; 
		else
			echo "date_added";
		?>&way=<?php 
		if(isset($_GET['way']))
			echo $way; 
		else
			echo "DESC";
		?>">Jewellery</option>
          <option value="http://localhost/store/list_all_products.php?filterby=Sunglasses&sortby=<?php 
		if(isset($_GET['sortby']))
			echo $sortby; 
		else
			echo "date_added";
		?>&way=<?php 
		if(isset($_GET['way']))
			echo $way; 
		else
			echo "DESC";
		?>">Sunglasses</option>
          <option value="http://localhost/store/list_all_products.php?filterby=EBooks&sortby=<?php 
		if(isset($_GET['sortby']))
			echo $sortby; 
		else
			echo "date_added";
		?>&way=<?php 
		if(isset($_GET['way']))
			echo $way; 
		else
			echo "DESC";
		?>">EBooks</option>
          <option value="http://localhost/store/list_all_products.php?filterby=DVD&sortby=<?php 
		if(isset($_GET['sortby']))
			echo $sortby; 
		else
			echo "date_added";
		?>&way=<?php 
		if(isset($_GET['way']))
			echo $way; 
		else
			echo "DESC";
		?>">DVD's</option>
          <option value="http://localhost/store/list_all_products.php?filterby=Gaming&sortby=<?php 
		if(isset($_GET['sortby']))
			echo $sortby; 
		else
			echo "date_added";
		?>&way=<?php 
		if(isset($_GET['way']))
			echo $way; 
		else
			echo "DESC";
		?>">Gaming</option>
		<option value="http://localhost/store/list_all_products.php?filterby=Vegitables&sortby=<?php 
		if(isset($_GET['sortby']))
			echo $sortby; 
		else
			echo "date_added";
		?>&way=<?php 
		if(isset($_GET['way']))
			echo $way; 
		else
			echo "DESC";
		?>">Vegitables</option>
 </select> 
    </p></br></br>
    <?php echo $dyn_table; ?>
  </div>
  <?php include_once("template_footer.php");?>
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
Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved </i> by <a href="https://rakttdaan.in" target="_blank">Rakttdaan Team</a>
<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></span> 
							
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