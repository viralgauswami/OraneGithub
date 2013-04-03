<?php
if(strtoupper($_SERVER["REQUEST_METHOD"])!="POST")
{
	header ("Location: error.php");
	exit;
}

include("../includes/connection.php");
include("../includes/admin_checksession.php");
include("../includes/functions.php");

$myaction = isset($_REQUEST["myaction"])?strtolower(trim($_REQUEST["myaction"])):"";
$id = isset($_REQUEST["id"])?intval($_REQUEST["id"]):0;

if ($myaction=="updatestat")
{
	$sorton = isset($_REQUEST["sorton"])?$_REQUEST["sorton"]:"";
	$sort = isset($_REQUEST["sort"])?$_REQUEST["sort"]:"";
	$Keywords_Username = isset($_REQUEST["Keywords_Username"])?$_REQUEST["Keywords_Username"]:"";

	if(isset($_POST["AllMember_Ids"]) && count($_POST["AllMember_Ids"]) > 0)
	{
		$myAllMember_Ids = implode(",", $_POST["AllMember_Ids"]);
		
		$qry = "UPDATE tbl_supplier SET `Active`= 'No' WHERE id IN ($myAllMember_Ids)";
		mysql_query($qry) or die("can not update status".mysql_error());
		
		if(isset($_POST["Active_Member_Ids"]) && count($_POST["Active_Member_Ids"]) > 0)
		{	
			$myActive_Member_Ids = implode(",", $_POST["Active_Member_Ids"]);
		
			$qry = "UPDATE tbl_supplier SET `Active` = 'Yes' WHERE id IN ($myActive_Member_Ids)";
			mysql_query($qry) or die("can not activate status<br>$qry".mysql_error());
		}
	}
	
	header("Location: listsupplier.php?upstatushsus=1&sort=".$sort."&sorton=".$sorton."&Keywords_Username=".$Keywords_Username);
	exit;
}

if ($myaction=="delete")
{
	$sorton = isset($_REQUEST["sorton"])?$_REQUEST["sorton"]:"";
	$sort = isset($_REQUEST["sort"])?$_REQUEST["sort"]:"";
	$Keywords_Username = isset($_REQUEST["Keywords_Username"])?$_REQUEST["Keywords_Username"]:"";

	if(isset($_POST["Member_Ids"]) && is_array($_POST["Member_Ids"]) && count($_POST["Member_Ids"]) >0)
	{
		$qry = "DELETE FROM tbl_supplier WHERE id IN (".implode(",", $_POST["Member_Ids"]).")";
		mysql_query($qry) or die("can not delete members".mysql_error()."<br>$qry");
	}

	header("Location: listsupplier.php?sort=".$sort."&sorton=".$sorton."&Keywords_Username=".$Keywords_Username);
	exit;
}

$myTxtFlds = Array ("supp_name","supp_address", "supp_mo_no", "supp_phone_no", "supp_email", "supp_pan_no", "supp_bank_name", "supp_ac_no", "supp_rtgs", "supp_branch","product_ids","vat_no");

$myNumFlds = Array ();

$CompulsoryTxtFlds = Array ("supp_name","supp_address", "supp_mo_no", "supp_phone_no", "supp_email", "supp_pan_no", "supp_bank_name", "supp_ac_no", "supp_rtgs", "supp_branch");
$CompulsoryNumFlds = Array();

$FriendlyNames = Array ("Supplier Name","Supplier Address", "Supplier Mobile Number", "Supplier Phone Number", "Supplier Email", "Supplier Pan Number", "Supplier Bank Name", "Supplier A/c No", "Supplier Rtgs", "Supplier Branch");

checkCompulsory($CompulsoryTxtFlds,$CompulsoryNumFlds,$FriendlyNames,"supplier.php");

if(strlen(trim($_POST["supp_mo_no"]))> 0)
{
	 $_POST["supp_mo_no"];
	 $tests=explode(",",$_POST["supp_mo_no"]);
	foreach ($tests as $element) 
	{
	
		if (is_numeric($element)) {
		
			if(strlen($element)!=10)
			{
				myForm("supplier.php","mno1","Mobile Number");
			}
			
			
		} else {
		myForm("supplier.php","mno2","Mobile Number");
			
		}
	}
	
}
if(strlen(trim($_POST["supp_email"]))> 0 && !validateEmail($_POST["supp_email"]))
{
	myForm("supplier.php","invalid","Supplier Email");
}
if(is_numeric($_POST["supp_ac_no"]))
{
}
else
{
		myForm("supplier.php","ac_no","A/c Number");
}
if(is_numeric($_POST["supp_rtgs"]))
{
}
else
{
		myForm("supplier.php","supp_rtgs","Rtgs Number");
}


if ($myaction=="edit")
{
	if($id==0)
	{
		header ("Location: listsupplier.php");
		exit;
	}


	$qryDup = "SELECT supp_email FROM tbl_supplier WHERE lcase(supp_email)='".strtolower(setGPC($_POST["supp_email"],""))."' 
					AND id!=$id";
}
else
	$qryDup = "SELECT supp_email FROM tbl_supplier WHERE lcase(supp_email)='".strtolower(setGPC($_POST["supp_email"],""))."'";


$resultDup = mysql_query($qryDup) or die("can not check for duplicate".mysql_error());
$reccnt = mysql_num_rows($resultDup);

if($reccnt > 0)
{
	myForm("supplier.php", "dup", "");
	exit;
}		

mysql_free_result($resultDup);

$UpdateFlds = UpdateFlds($myTxtFlds,$myNumFlds);



if ($myaction=="edit")
{
	$qry = "UPDATE tbl_supplier SET ".$UpdateFlds." WHERE id=$id";
	mysql_query($qry) or die("can not update.");
	
}
else
{
	$qry = "INSERT INTO tbl_supplier SET  ".$UpdateFlds;
	mysql_query($qry) or die("error inserting values".mysql_error()); 
	
}

//header ("Location: listsupplier.php");
//exit;
?><script language="javascript">javascript: window.close();</script>