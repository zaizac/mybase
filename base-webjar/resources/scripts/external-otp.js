!function($) {

	"use strict";

	var t = 0;
	var interval = 15
	var intervalTimer = interval + ":00"

	var Otp = function(element, options) {
		this.$verify = $(element); // this.$otpEmail.siblings().find('[data-trigger="verify"]')
		this.$verify.on('click.otp', $.proxy(this.verify, this))
		this.$otpType = this.$verify.siblings('[data-provides="otpType"]')
		this.$otpTimer = this.$verify.parent().parent().siblings(
				'[data-provides="otpTimer"]')
		this.$otpTimer.attr('value', intervalTimer)
		this.$otpVal = this.$verify.parent().parent().siblings(
				'[data-provides="otpVal"]')

		this.$otpEmail = this.$verify.parent().siblings(
				'[data-provides="otpEmail"]')
		this.$otpMobile = this.$verify.parent().siblings(
				'[data-provides="otpMobile"]')
				
		this.$errMsg = this.$verify.parent().parent().siblings("p.errors")
		this.$reset = this.$otpEmail.siblings().find('[data-trigger="reset"]')
		this.$reset.on('click.otp', $.proxy(this.reset, this))
		this.$validate = this.$otpEmail.siblings().find(
				'[data-trigger="validate"]')
	}

	Otp.prototype = {
		validateEmail : function(email) {
			var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
			return emailReg.test(email);
		},
		reset : function() {
			this.$reset.hide();
			this.$verify.show();
			this.$validate.hide();
			this.$otpEmail.prop("readonly", "");
			this.$otpEmail.val('');
			this.$otpVal.val('0');
			this.$btnValidate.hide(); // OTP validate button hide
			this.$otpCode.prop("disabled", true);
			this.$btnGen.show();
		},
		verify : function() {
			if (this.$otpEmail.val() == '') {
				this.$errMsg.html(prop.errEmailReq);
			} else {
				this.$errMsg.html('')
				var isEmailValid = this.validateEmail(this.$otpEmail.val());
				if (isEmailValid) {
					this.$errMsg.html('')
					if(this.$otpTimer.val() == "0:00" || this.$otpTimer.val() == '') {
						this.$otpTimer.val(intervalTimer)
					}
					this.modal(this.$otpEmail.val(), this.$otpMobile.val())
					if (this.$otpVal.val() == '1') {
						this.hideBtnGen()
						this.$otpCode.prop('disabled')
					} else {
						this.showBtnGen()
						this.$otpCode.prop('disabled', true)
					}
				} else {
					this.$errMsg.html(prop.errEmailFmt);
				}
			}
		},
		modal : function(email, mobile) {
			var content = '<div class="row">' + '<div class="col-sm-3">'
					+ '<label>'
					+ prop.otpSendTo
					+ '</label>'
					+ '</div>'
					+ '<div class="col-sm-9">'
					+ (mobile == undefined ? ''
							: '&nbsp;<i id="lblOtpMobileVal" class="fa fa-mobile"/>'
									+ '&nbsp;<i>'
									+ mobile
									+ '</i>'
									+ '<br id="brOtpMobileVal"/>')
					+ '<i class="fa fa-envelope"/>'
					+ '&nbsp;<i>'
					+ email
					+ '</i>'
					+ '</div>'
					+ '</div>'
					+ '<h6 id="otpErrorMsg" />'
					+ '<div class="row otp-input">'
					+ '<div class="col-sm-12">'
					+ '<div class="input-group">'
					+ '<input type="text" id="otpCode" maxlength="6" class="form-control"/>'
					+ '<div class="input-group-append">'
					+ '<span id="generateOtp" class="input-group-text btn btn-primary" >'
					+ prop.otpTacGen
					+ '</span>'
					+ '<span id="validateOtp" class="input-group-text btn btn-secondary"">'
					+ prop.otpTacVld
					+ '</span>'
					+ '</div>'
					+ '</div>'
					+ '</div>'
					+ '</div>'
					+ '<div class="row text-left" style="margin-bottom:0 !important;">'
					+ '<div class="col-sm-12">'
					+ 'Enter your OTP within <span id="otpTimerDisplay" /> minutes'
					+ '<label id="otpGenMsg" class="errors">'
					+ prop.otpExpInfo
					+ '</label>' + '</div>' + '</div>';
			this.$modal = bootbox.dialog({
				closeButton : true,
				message : content,
				title : "<i class=\"fa fa-lock fa-7x fa-fw\"></i>"
						+ prop.otpTitle,
				className : "otp-popup"
			});
			this.$otpCode = $(document.querySelector("input#otpCode"))
			this.$btnGen = $(document.querySelector("#generateOtp"))
			this.$btnGen.on('click.otp', $.proxy(this._generate, this))
			this.$btnValidate = $(document.querySelector("#validateOtp"))
			this.$btnValidate.on('click.otp', $.proxy(this._validate, this))
			this.$errMsgModal = $(document.querySelector("#otpErrorMsg"))
			this.$otpTimerDisplay = $(document
					.querySelector("#otpTimerDisplay"))
		},
		_generate : function() {
			var method = "GET";
			var inputUrl = "";
			var dataArr = {};
			dataArr.otpId = this.$verify.attr("data-id")
			if (this.$otpMobile) {
				inputUrl = contextPath + '/otp/generate';
				dataArr.emailAddress = this.$otpEmail.val();
				dataArr.mobile = this.$otpMobile.val();
			} else {
				inputUrl = contextPath + '/otp/generate';
				dataArr.emailAddress = this.$otpEmail.val();
			}

			this.$otpCode.val(''); // Reset to empty
			this.$btnGen.prop("disabled", true); // Disable button

			if(this.$otpTimer.val() == "0:00" || this.$otpTimer.val() == '') {
				this.$otpTimer.val(intervalTimer)
			}
			
			var data = this.send(method, inputUrl, dataArr)
			if (data) {
				var msgs = data.split('*^*');
				if (msgs.length > 1) {
					if (msgs[1] == 1) {
						this.otpAlert(msgs[0])
						this.hideBtnGen()
						this.$otpTimer.val(intervalTimer);
						this.$otpCode.prop("disabled", false);
					} else {
						this.otpAlert(msgs[0], true)
						this.showBtnGen()
						this.$otpCode.prop("disabled", true);
					}
					this.$btnGen.prop("disabled", false);
					this.$otpVal.val(msgs[1]);
				}

				var that = this;
				this.startTimer(that);
				setTimeout(function() {
					that.otpAlert('')
					that.showBtnGen()
					that.$btnGen.prop("disabled", false);
					that.$otpCode.prop("disabled", false);
				}, interval * 60 * 1000);
			} else {
				this.otpAlert(data, true)
				this.$btnValidate.hide();
				this.$btnGen.show();
				this.$btnGen.prop("disabled", false);
				this.$otpCode.prop("disabled", true);
			}

		},
		_validate : function() {
			var method = "POST";			
			var otpType = this.$otpType.val();
			var otp = this.$otpCode.val();
			var otpId = this.$verify.attr("data-id")
			
			var inputUrl = contextPath + '/otp/validate';

			this.otpAlert('') // Reset Alert Message

			if (otp == '') {
				this.otpAlert('OTP is required', true);
				return;
			}
			var dataArr = {
				otpCode : otp,
				otpId : otpId
			};
			var data = this.send(method, inputUrl, dataArr)
			if (data) {
				this.$otpCode.val('');
				if (data.isValid && !data.isExpired) {
					this.stopTimer();
					this.$otpVal.val('0');
					this.otpAlert(data.message);
					this.$errMsg.html('');
					bootbox.hideAll();
					if (this.$otpType.val() == 'email') {
						this.$verify.hide();
						this.$validate.show();
						this.$otpEmail.prop("readonly", "readonly");
						this.$otpVal.val('1');
						this.$reset.show();
					} else {
						this.$errMsgModal.replaceWith(
								'<b class="font-green">' + prop.succVerOTP + '</b>');
					}

				} else {
					if (data.isExpired) {
						this.$otpVal.val('0');
						this.otpAlert(data.message, true)
						this.showBtnGen()
					} else {
						this.otpAlert(data.message, true)
						this.hideBtnGen()
					}
				}

			} else {
				this.otpAlert(data, true);
				this.hideBtnGen()
			}
		},
		send : function(method, inputUrl, dataArr) {
			var aaData = null;
			$.ajax({
				'async' : false,
				'global' : false,
				headers : {
					'X-CSRF-Token' : csrf_token
				},
				type : method,
				action : 'xhttp',
				url : inputUrl,
				data : dataArr,
				success : function(data) {
					aaData = data;
				}
			});
			return aaData;
		},
		showBtnGen: function() {
			this.$btnGen.show();
			this.$btnValidate.hide();
		},
		hideBtnGen: function() {
			this.$btnGen.hide();
			this.$btnValidate.show();
		},
		otpAlert : function(message, type) {
			this.$errMsgModal.html(message);
			if (type) {
				this.$errMsgModal.attr('style', 'color:red')
			} else {
				this.$errMsgModal.attr('style', 'color:green')
			}
		},
		startTimer : function(e) {
			var presentTime = e.$otpTimer.val();
			var timeArray = presentTime.split(/[:]+/);
			var m = timeArray[0];
			var s = checkSecond((timeArray[1] - 1));
			if (s == 59) {
				m = m - 1
			}
			e.$otpTimer.val(m + ":" + s);
			e.$otpTimerDisplay.html(m + ":" + s);
			if (m > 0 || s > 0) {
				t = setTimeout(e.startTimer, 1000, e);
			} else {
				e.stopTimer(e)
			}
		},
		stopTimer: function () {
			clearTimeout(t)
			this.$otpTimer.val(intervalTimer);
		}
	}

	$.fn.otp = function(options) {
		return this.each(function() {
			var $this = $(this), data = $this.data('otp')
			if (!data)
				$this.data('otp', (data = new Otp(this, options)))
			if (typeof options == 'string')
				data[options]()
		})
	}
	$.fn.otp.Constructor = Otp

	function checkSecond(sec) {
		if (sec < 10 && sec >= 0) {
			sec = "0" + sec
		}
		// add zero in front of numbers < 10
		if (sec < 0) {
			sec = "59"
		}		
		return sec;
	}

}(window.jQuery);
