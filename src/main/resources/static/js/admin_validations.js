/**
 * form validations
 */

jQuery(function ($) {

/********** Admin add product category form ********************/

  $("#admin-product-category-form").validate({
    rules: {
      categoryName: {
        required: true
      }
    },
    messages: {
      categoryName: {
        required: "Please enter product category"
      }
    }
});


/********** Admin add product form ********************/

jQuery.validator.addMethod("greaterThan", function(value, element, params) {
	return parseInt($(params[0]).val()) <= parseInt($(params[1]).val());
});

  $("#admin-product-form").validate({
    rules: {
      productName: {
        required: true
      },
      productCategory: {
        required: true
      },
      inStock: {
        required: true,
        digits: true
      },
      allotedQuantity: {
        required: true,
        digits: true,
        greaterThan: ["#inStock","#allotedQuantity"]
      }
    },
    messages: {
      productName: {
        required: "Please enter product name"
      },
      productCategory: {
        required: "Please select product category"
      },
      inStock: {
        required: "Please enter quantity in stock",
        digits: "Invalid quantity"
      },
      allotedQuantity: {
        required: "Please enter quantity in stock",
        digits: "Invalid quantity",
        greaterThan: "Alloted quantity should be greater than quantity"
      }
    }
});

/********** Admin modal quantity update form ********************/

  $("#modal-quantity-form").validate({
    rules: {
      updateInStock: {
        required: true,
        digits: true
      },
      comment: {
        required: true
      }
    },
    messages: {
      updateInStock: {
        required: "Please enter quantity in stock",
        digits: "Invalid quantity"
      },
      comment: {
        required: "Please enter comment"
      }
    }
});

/********** Admin update quantity  ********************/

 $("#modal-quantity-form").submit(function(event) {
 	 event.preventDefault();
 	 if(event.result == true) {
     	updateQuantityFunction();
     }
 });
 
 function updateQuantityFunction() {
 
 	var form = $("#modal-quantity-form")[0];
	var data = new FormData(form);
 
 	$.ajax({
        type:"POST",
        data: data,
        url:"/admin/rest/updateQuantity",
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
        		$("#appendQuantity-"+spt[2].toString()).parents("td").addClass("slowbackground");
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
 
 


});