/**
 * form validations
 */

jQuery(function ($) {

/********** Sign In form ********************/

	$("#login-form").validate({
    rules: {
      email: {
        required: true,
        email: true
      },
      password: {
        required: true
      }
    },
    messages: {
      email: {
        required: "Please enter email address",
        email: "Please enter a valid email address"
      },
      password: {
        required: "Please enter password"
      }
    }

  });

/********** Registration form ********************/

  $("#register-form").validate({
    rules: {
      firstName: {
        required: true
        },
      email: {
        required: true,
        email: true
      },
      password: {
        required: true
      },
      cpassword: {
        required: true,
        equalTo : "#password"
      }
    },
    messages: {
      email: {
        required: "Please enter email address",
        email: "Please enter a valid email address"
      },
      firstName: {
        required: "Please enter first name"
      },
      password: {
        required: "Please enter password"
      },
      cpassword: {
        required: "Please enter confirm password",
        equalTo: "Password not matched"
      }
    }
});


/********** Change Password form ********************/

  $("#change-password-form").validate({
    rules: {
      password: {
        required: true
      },
      newPassword: {
        required: true
      },
      reNewPassword: {
        required: true,
        equalTo : "#newPassword"
      }
    },
    messages: {
      password: {
        required: "Please enter current password"
      },
      newPassword: {
        required: "Please enter new password"
      },
      reNewPassword: {
        required: "Please enter confirm password",
        equalTo: "Password not matched"
      }
    }
});


/********** Edit profile form ********************/

  $("#edit-profile-form").validate({
    rules: {
      firstName: {
        required: true
      },
      phoneNo: {
        digits: true
      }
    },
    messages: {
      firstName: {
        required: "Please enter first name"
      },
      phoneNo: {
        digits: "Invalid phone number"
      }
    }
});

/********** Contact us form ********************/

  $("#contact-support-form").validate({
    rules: {
      subject: {
        required: true
      }
    },
    messages: {
      subject: {
        required: "Please enter subject"
      }
    }
});


});