<div id="pageHeader">
 
    
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


    <td width="68%" align="right"> You are logged in as: <?php echo $_SESSION["user"] ?> <a href="http://localhost/store/logout.php">Logout</a> | <a href="http://localhost/store/cart.php">Your Cart</a> | <a href="http://localhost/store/user_profile.php">Edit profile</a></td>
<?php } ?>
	</tr>
  <tr>
    <td colspan="2"><a href="http://localhost/store/index.php">Home</a> &nbsp; &middot; &nbsp; <a href="http://localhost/store/list_all_products.php">Products</a> &nbsp; &middot; &nbsp; <a href="http://localhost/store/help.php">Help</a> &nbsp; &middot; &nbsp; <a href="http://localhost/store/contact.php">Contact</a></td>
    </tr>
  
</div>