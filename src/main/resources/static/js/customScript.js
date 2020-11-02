/********** For page animation ********************/
new WOW().init();

jQuery(function ($) {

  /********** For dropdown ********************/
  $(".sidebar-dropdown > a").click(function () {
    $(".sidebar-submenu").slideUp(200);
    if (
      $(this)
        .parent()
        .hasClass("active")
    ) {
      $(".sidebar-dropdown").removeClass("active");
      $(this)
        .parent()
        .removeClass("active");
    } else {
      $(".sidebar-dropdown").removeClass("active");
      $(this)
        .next(".sidebar-submenu")
        .slideDown(200);
      $(this)
        .parent()
        .addClass("active");
    }
  });

  /********** For sidebar toogle button ********************/
  $("#close-sidebar").click(function () {
    $(".page-wrapper").removeClass("toggled");
  });
  $("#show-sidebar").click(function () {
    $(".page-wrapper").addClass("toggled");
  });

  /********** For tooltip ********************/
  $('[data-toggle="tooltip"]').tooltip();

  /********** For loadmore products ********************/
  let x=8;                                               // number of products to display intially
  $('#loadMoreList > .individual-product').slice(0, x).show();
  
  // function invoke on page scroll
  $(window).scroll(function(e) {
    if($(window).scrollTop() == $(document).height() - $(window).height()) {
      e.preventDefault();
      x = x+4;
      $('#loadMoreList > .individual-product').slice(0, x).fadeIn(2000);                    // loading more products as page scrolls down
    }
});

	/********** User upload profile picture ********************/
	
	$("#picture").change(function () {
        var fileExtension = ['jpeg', 'jpg', 'png', 'gif', 'bmp'];
        if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
        	$("#output").show();
            $("#output").removeClass("alert-success");
        	$("#output").addClass("alert-danger");
            $("#output").text("Invalid format. Only : "+fileExtension.join(', ') + " are allowed");
            $("#fileUploadBtn").hide();
        } else {
        	$("#output").hide();
        	$("#fileUploadBtn").show();
        }
    });
    
	$("#fileUploadBtn").click((event) => {
        event.preventDefault();
        fileUploadFunction();
    });

	function fileUploadFunction() {
 
	    // Get form
	    var form = $('#upload-profile-picture')[0];
	    var data = new FormData(form);
	 	//var hid = $('#profilePic').value();
	 	
	    $.ajax({
	        type: "POST",
	        enctype: 'multipart/form-data',
	        url: "/api/file/upload",
	        data: data,
	        processData: false,
	        contentType: false,
	        cache: false,
	        success: (data) => {
	        	$("#output").show();
	        	let spt = data.split("@=@");
	        	if(spt[1] == 1) {
	        		$("#output").removeClass("alert-success");
	        		$("#output").addClass("alert-danger");
	        	} else {
	        		$("#output").removeClass("alert-danger");
	        		$("#output").addClass("alert-success");
	        		$('#profilePic').val(spt[2]);
	        	}
	            $("#output").text(spt[0]);
	        },
	        error: (e) => {
	        	$("#output").show();
	        	$("#output").removeClass("alert-success");
	        	$("#output").addClass("alert-danger");
	            $("#output").text(e.responseText);
	        }
	    });
	}
    
    /********** Select all checkbox ********************/
    
    $("#checkBoxAll").click(function () {
    	if($(this).is(":checked"))
    		$(".chkCheckBoxId").prop("checked",true);
    	else
    		$(".chkCheckBoxId").prop("checked",false);
    });
    
    /********** Enable/Disable mark as read button as per checkboxes ********************/
    
    $('#markAllAsRead').prop('disabled', true);

	$('.markAllAsReadCheckBoxes').change(function(){
	    $('#markAllAsRead').prop('disabled', $('.markAllAsReadCheckBoxes:checked').length == 0);
	});
    
    
    /********** User modal do transaction form ********************/

  $("#modal-transaction-form").validate({
    rules: {
      transactionQuantity: {
        required: true,
        digits: true
      },
      comment: {
        required: true
      }
    },
    messages: {
      transactionQuantity: {
        required: "Please enter quantity",
        digits: "Invalid quantity"
      },
      comment: {
        required: "Please enter comment"
      }
    }
});

    /********** Inventory transaction for user ********************/

	 $("#modal-transaction-form").submit(function(event) {
	 	 event.preventDefault();
	 	 if(event.result == true) {
	     	updateTransactionQuantityFunction();
	     }
	 });
 	
 	 function updateTransactionQuantityFunction() {
 
	 	var form = $("#modal-transaction-form")[0];
		var data = new FormData(form);
	 	
	 	$.ajax({
        type:"POST",
        data: data,
        url:"/user/rest/updateTransctionQuantity",
        processData: false,
        contentType: false,
        cache: false,
        success: (data) => {
          $("#outputAjaxMessage").show();
        	let spt = data.split("@=@");
        	if(spt[1] == "notExist") {
        		$("#outputAjaxMessage").removeClass("alert-success");
        		$("#outputAjaxMessage").addClass("alert-danger");
        	} else {
        		$("#outputAjaxMessage").removeClass("alert-danger");
        		$("#outputAjaxMessage").addClass("alert-success");
        		$("#appendQuantity-"+spt[2].toString()).text(spt[1]);
        		$("#appendQuantity-"+spt[2].toString()).parent("div").addClass("slowbackground-inventory");
        	}
        	
            	$("#outputAjaxMessage").text(spt[0]);
	        },
	       error: (e) => {
	       		$("#outputAjaxMessage").show();
	        	$("#outputAjaxMessage").removeClass("alert-success");
	        	$("#outputAjaxMessage").addClass("alert-danger");
	            $("#outputAjaxMessage").text(e.responseText);
	        }
	    });
	 }
	 
	 /********** Image viewer ********************/
	 
	 $('.pop').on('click', function() {
		$('.imagepreview').attr('src', $(this).attr('src'));
		$('#imagemodal').modal('show');   
	});
    
     /********** Add or remove from favorite ********************/
     
    $('.addToFavourite').on('click', function() {
		var val =  $(this).attr('id'); 
		var data1 = val.split("-");
		var data = data1[1];
		$.ajax({
	        type:"POST",
	        data: { favId: data },
	        url:"/user/rest/addToFavourite",
	        success: (data) => {
		        let spt = data.split("@=@");
	        	if(spt[1] == "notExist") {
	        		alert(spt[0]);
	        	} else if(spt[0] == "insert"){
	        		$("#favouriteProduct-"+spt[1].toString()).css("color","#371DB3");
	        		$("#favouriteProducts-"+spt[1].toString()).css("color","#371DB3");
	        	} else {
	        		$("#favouriteProduct-"+spt[1].toString()).css("color","#FFF");
	        		$("#favouriteProducts-"+spt[1].toString()).css("color","#FFF");
	        	}
	        	$("#dynamicFavouriteCount").text(spt[2]);
		     },
		       error: (e) => {
		       		alert(e);
		    }
	    });
	});
	
	  /********** Add or remove from favorite (favorite page) ********************/
     
    $('.addToFavouriteFav').on('click', function() {
		var val =  $(this).attr('id'); 
		var data1 = val.split("-");
		var data = data1[1];
		$.ajax({
	        type:"POST",
	        data: { favId: data },
	        url:"/user/rest/addToFavourite",
	        success: (data) => {
		        let spt = data.split("@=@");
	        	if(spt[1] == "notExist") {
	        		alert(spt[0]);
	        	} else if(spt[0] == "insert"){
	        		$("#appendProduct-"+spt[1].toString()).fadeIn("slow");
	        	} else {
	        		$("#appendProduct-"+spt[1].toString()).fadeOut("slow");
	        	}
	        	$("#dynamicFavouriteCount").text(spt[2]);
		     },
		       error: (e) => {
		       		alert(e);
		    }
	    });
	});
	
	
	/********** For homepage slider ********************/
	
	$('#bannerSlider').carousel();
	var winWidth = $(window).innerWidth();
	$(window).resize(function () {
	
	    if ($(window).innerWidth() < winWidth) {
	        $('.carousel-inner>.item>img').css({
	            'min-width': winWidth, 'width': winWidth
	        });
	    }
	    else {
	        winWidth = $(window).innerWidth();
	        $('.carousel-inner>.item>img').css({
	            'min-width': '', 'width': ''
	        });
	    }
	});
	
});