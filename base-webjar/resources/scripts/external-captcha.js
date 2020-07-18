!function ($) {
	
	"use strict";
	
	var Captcha = function(element, options) {
		this.$element = $(element)
		this.$image = this.$element.parent().siblings("img");
		this.$reload = this.$element.siblings('div').find('[data-trigger="reload"]');
		this.$reload.on('click.captcha', $.proxy(this.reset, this))
		this.$element.on('focus', $.proxy(this.clear, this))
		this.listen();
	}
	
	Captcha.prototype = {
		reset: function(e) {
	    	var newSrc = this.$image.attr("src");
			var srcArr = newSrc.split("?")
			var id = new Date().getTime();
			var element = this.$element;
			$(element).attr("data-id",id)
			newSrc = srcArr[0]+"?captchaId="+id;
			this.$image.attr("src", newSrc);
			$(element).val("")
			if(e != 'error') {
				this.clear();
			}
	    },
	    clear: function() {
	    	var element = this.$element;
	    	var previous = $(element).parent().siblings(".errors");
			if(previous) {
				previous.remove();
			}
	    },
	    listen: function() {
	    	var that = this;
	    	var element = this.$element;
	    	var form = this.$element.closest("form");
            if (form.length) {
                form.submit(function(e) {
                	var data = validateCaptcha($(element).attr("data-page"), $(element).attr("data-id"), $(element).val());
                	if(!data.isValid) {
                		var pError = $(element).siblings(".errors"); 
	              		if(pError[0] == undefined) {
	              			that.clear();
	              			$('<p class="errors">Invalid captcha code.</p>').insertAfter($(element).parent())
	              			that.reset('error');
	              			e.preventDefault();
	              		}
                	}
                });
            }
	    }
	}

	function validateCaptcha(page, captchaId, captchaValue) {
		var aadata = null;
		$.ajax({
			'async': false,
			'global': false,
			headers: { 'X-CSRF-Token': csrf_token },
			type: "POST", 
			action: 'xhttp',
			url: contextPath + "/captcha/" + page + "/validate?captchaId=" + captchaId, 
			data: { captcha: captchaValue },
			success : function(data) {
				aadata = data;
			}
		});
		return aadata;
    }
  
	$.fn.captcha = function (options) {
	    return this.each(function () {
	      var $this = $(this)
	      , data = $this.data('captcha')
	      if (!data) $this.data('captcha', (data = new Captcha(this, options)))
	      if (typeof options == 'string') data[options]()
	    })
	}
	$.fn.captcha.Constructor = Captcha

}(window.jQuery);