<?php 
$PageTitle = "Add Supplier";

$myaction = isset($_REQUEST["myaction"])?strtolower(trim($_REQUEST["myaction"])):"";
$id = isset($_REQUEST["id"])?intval($_REQUEST["id"]):0;
$err = isset($_REQUEST["err"])?strtolower(trim($_REQUEST["err"])):"";
$errfld = isset($_REQUEST["errfld"])?$_REQUEST["errfld"]:"";

$sorton = isset($_REQUEST["sorton"])?$_REQUEST["sorton"]:"";
$sort = isset($_REQUEST["sort"])?$_REQUEST["sort"]:"";
$Keywords_Username = isset($_REQUEST["Keywords_Username"])?$_REQUEST["Keywords_Username"]:"";

if($myaction == "edit")
	$PageTitle = "Update Member";

//include("../includes/admintop.php");

if ($myaction == "edit" && $id==0)
{
	header ("Location: listsupplier.php");
	exit;
}

$myTxtFlds = Array ("supp_name","supp_address", "supp_mo_no", "supp_phone_no", "supp_email", "supp_pan_no", "supp_bank_name", "supp_ac_no", "supp_rtgs", "supp_branch","product_ids","vat_no");

$myNumFlds = Array ();

$myVals = populateFields("Initialize",$myTxtFlds,$myNumFlds,"");

if (strlen($err) > 0 && $err != "none")
{
	$myVals = populateFields("GPC",$myTxtFlds,$myNumFlds,"");
}				

elseif ($myaction == "edit")
{
	$qry = "SELECT * FROM tbl_supplier WHERE id=$id";
	$result = mysql_query($qry) or die("can not select ec");
					
	if (!($arow = mysql_fetch_array($result)))
	{
		header ("Location: error.php");
		exit;
	}

	$myVals = populateFields("DB",$myTxtFlds,$myNumFlds,$arow);
	
	mysql_free_result($result);

}

$mymsg = "";
	
if (strlen($err) > 0)
{
	if ($err == "dup")
		$mymsg = "Member <b>".$myVals["supp_email"]."</b> already exists.";
	elseif ($err == "none")
		$mymsg = "Details has been updated successfully.";
	elseif ($err == "compulsory")
		$mymsg = "$errfld can not be blank. Please enter $errfld.";
	elseif ($err == "invalid")
		$mymsg = "$errfld is invalid.";
	elseif ($err == "cpass")
		$mymsg = "Confirmed password does not match with password.";
		elseif ($err == "mno1")
		$mymsg = "$errfld size is not 10 number";
	elseif ($err == "mno2")
		$mymsg = "$errfld is not a number";
	elseif ($err == "ac_no")
		$mymsg = "A/c Number is not a number";
elseif ($err == "supp_rtgs")
		$mymsg = "Rtgs Number is not a number";
		
	if($mymsg != "")
		echo "<center><span class='err'>$mymsg</span></center><br>";
}

?>
<?php /*?><script language="javascript">
function submitForm(frm)
{
	CompulsoryTxtFlds = Array("supp_name","supp_address", "supp_mo_no", "supp_phone_no", "supp_email", "supp_pan_no", "supp_bank_name", "supp_ac_no", "supp_rtgs", "supp_branch");

	FriendlyNames = Array("Supplier Name","Supplier Address", "Supplier Mobile Number", "Supplier Phone Number", "Supplier Email", "Supplier Pan Number", "Supplier Bank Name", "Supplier A/c No", "Supplier Rtgs", "Supplier Branch");


	for(i=0;i<CompulsoryTxtFlds.length;i++)
	{
		myfld = eval("frm."+CompulsoryTxtFlds[i])
		
		if(Trim(myfld.value) == "")
		{
			alert(FriendlyNames[i]+" can not be blank.");
			myfld.focus();
			return false;
		}
	}
	 var numbers = /^[0-9]+$/;  
      if(frm.supp_ac_no.value.match(numbers))  
      {  
    
      }
	  else{
	    alert('A/c is not a number.'); 
		return false; 
	  }
	   if(frm.supp_rtgs.value.match(numbers))  
      {  
    
      }
	  else{
	    alert('Rtgs is not a number.'); 
		return false; 
	  }
	if(!validateEmail(frm.supp_email))
	{
		alert("Invalid Email.");
		frm.supp_email.focus();
		return false;
	}
	
	return true;
}
</script><?php */?><link href="../css/admincss.css" rel="stylesheet" type="text/css" />
<link href="../css/calendar-win2k-1.css" rel="stylesheet" type="text/css">
<form name="frm1" method="POST" action="supplier_popup_db.php" onsubmit="return submitForm(this);">
  <br>
  <table width="500" border="0" align="center" cellpadding="0" cellspacing="0" class="tblbg">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="2">
          <tr> 
            <td valign="top" align="left" class="tblheader" colspan="3">Details </td>
          </tr>
	
		  <tr>
            <td width="41%" class="tblrow">Supplier Name<font color="#FF0000"> *</font> </td>
            <td width="5%" align="center" class="tblrow">:</td>
            <td width="54%" class="tblrow">
              <input name="supp_name" type="text" class="input" id="supp_name" value="<?php echo $myVals["supp_name"]?>" maxlength="100">            </td>
          </tr>
          <tr>
            <td class="tblrow">Supplier Address<font color="#FF0000"> *</font> </td>
            <td align="center" class="tblrow">:</td>
            <td class="tblrow">
              <input name="supp_address" type="text" class="input" id="supp_address" value="<?php echo $myVals["supp_address"]?>" maxlength="255">            </td>
          </tr>
        <tr>
            <td class="tblrow">Supplier Mobile Number<font color="#FF0000"> *</font> </td>
            <td align="center" class="tblrow">:</td>
            <td class="tblrow">
              <input name="supp_mo_no" type="text" class="input" id="supp_mo_no" value="<?php echo $myVals["supp_mo_no"]?>" maxlength="55">           
              <br /> Pls Enter Mobile Number like(9999999999,9999999999) </td>
          </tr>
          <tr>
            <td class="tblrow">Supplier Phone Number<font color="#FF0000">*</font> </td>
            <td align="center" class="tblrow">:</td>
            <td class="tblrow">
              <input name="supp_phone_no" type="text" class="input" id="supp_phone_no" value="<?php echo $myVals["supp_phone_no"]?>" maxlength="21">            </td>
          </tr>
          <tr>
            <td class="tblrow">Supplier Email<font color="#FF0000"> *</font> </td>
            <td align="center" class="tblrow">:</td>
            <td class="tblrow">
              <input name="supp_email" type="text" class="input" id="supp_email" value="<?php echo $myVals["supp_email"]?>" maxlength="100">            </td>
          </tr>
          <tr>
            <td class="tblrow">Supplier Pan Number<font color="#FF0000"> *</font> </td>
            <td align="center" class="tblrow">:</td>
            <td class="tblrow">
			 <input name="supp_pan_no" type="text" class="input" id="supp_pan_no" value="<?php echo $myVals["supp_pan_no"]?>" maxlength="11">
			</td>
          </tr>
		  <tr> 
            <td valign="top" align="left" class="tblheader" colspan="3">Bank Details </td>
          </tr>
		    <tr>
            <td class="tblrow">Supplier Bank  Name<font color="#FF0000"> *</font> </td>
            <td align="center" class="tblrow">:</td>
            <td class="tblrow">
			 <input name="supp_bank_name" type="text" class="input" id="supp_bank_name" value="<?php echo $myVals["supp_bank_name"]?>" maxlength="100">
			</td>
          </tr>
		    <tr>
            <td class="tblrow">Supplier A/c Number<font color="#FF0000"> *</font> </td>
            <td align="center" class="tblrow">:</td>
            <td class="tblrow">
			 <input name="supp_ac_no" type="text" class="input" id="supp_ac_no" value="<?php echo $myVals["supp_ac_no"]?>" maxlength="20">
			</td>
          </tr>
		    <tr>
            <td class="tblrow">Supplier Rtgs<font color="#FF0000"> *</font> </td>
            <td align="center" class="tblrow">:</td>
            <td class="tblrow">
			 <input name="supp_rtgs" type="text" class="input" id="supp_rtgs" value="<?php echo $myVals["supp_rtgs"]?>" maxlength="11">
			</td>
          </tr>
		    <tr>
            <td class="tblrow">Supplier Branch<font color="#FF0000"> *</font> </td>
            <td align="center" class="tblrow">:</td>
            <td class="tblrow">
			 <input name="supp_branch" type="text" class="input" id="supp_branch" value="<?php echo $myVals["supp_branch"]?>" maxlength="100">
			</td>
          </tr>
            <tr>
            <td class="tblrow">Vat Number</td>
            <td align="center" class="tblrow">:</td>
            <td class="tblrow">
              <input name="vat_no" type="text" class="input" id="vat_no" value="<?php echo $myVals["vat_no"]?>" maxlength="50">            </td>
          </tr>
         <tr> 
            <td valign="top" align="left" class="tblheader" colspan="3">Products </td>
          </tr>
        <tr> 
            <td valign="top" align="left" class="tblrow" colspan="3">
			<?php
			$pro_id_array="";
			$sql="select * from tbl_product where Active='Yes'";
			$qsql=mysql_query($sql);
			$totpro=mysql_num_rows($qsql);
			$i=0;
			$ii=1;
			if($totpro>0)
			{?>
				<table border="0" width="100%">
            <tr>
				<?php while($valproduct=mysql_fetch_array($qsql))
				{
				
				if($pro_id_array=="")
				{
					$pro_id_array=$valproduct['id'];
				}
				else
				{
						$pro_id_array=$pro_id_array.",".$valproduct['id'];
				}
			if($i%3==0)
			{ 
				echo "</tr><tr>";
			}
			?>
		     
            <td>
            
           <?php echo $ii.". ".$valproduct['product_name']; ?></td>
         
  			<?php
			$i++;
			$ii++; 
				}?>
                 </tr>
            </table>
            
                <?php
			}
			
			?>
			
			<input type="hidden" name="product_ids" value="<?php echo $pro_id_array; ?>"  />
			 
			
			</td>
          </tr>
          
          <tr align="center">
            <td colspan="3" class="tblrow"><input name="btn1" type="submit"  value="Save" class="but">&nbsp;&nbsp;<?php /*?><input name="btn12" type="button"  value="Cancel" class="but" onclick="javascript: window.location='listcompany.php?sort=<?php echo $sort?>&sorton=<?php echo $sorton?>&Keywords_Username=<?php echo $Keywords_Username?>';"><?php */?></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <br>
  <input type="hidden" name="myaction" value="<?php echo $myaction?>">
  <input type="hidden" name="id" value="<?php echo $id?>">
</form>
<?php
include('../includes/adminbottom.php'); 
?>