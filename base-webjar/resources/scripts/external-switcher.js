 <!--/* START THEME SWITCHER */-->
     
	$(".right-side-toggle").click(function () {
	    $(".right-sidebar").slideDown(50);
	    $(".right-sidebar").toggleClass("shw-rside");
	});
  
    $(document).ready(function(){		
    	$("*[data-theme]").click(function(e) {
    		e.preventDefault();
    	    var currentStyle = $(this).attr('data-theme');
       	    var currentLayout= $('*[data-layout].working')[0] == undefined ? switcher.existLayout : $($('*[data-layout].working')[0]).attr('data-layout');
       	 	var currentSidemode= $('*[data-sidemode].working')[0] == undefined ? switcher.existSidemode : $($('*[data-sidemode].working')[0]).attr('data-sidemode');
    	    setLayoutStyles(currentLayout, currentStyle,currentSidemode);
    	    $('#themecolors li a').removeClass('working');
    	    $(this).addClass('working')
		});
    	 
	   	$("*[data-layout]").click(function(e){
	  		e.preventDefault();
	  	    var currentStyle = $('*[data-theme].working')[0] == undefined ? switcher.existStyle : $($('*[data-theme].working')[0]).attr('data-theme');
	  	    var currentLayout= $(this).attr('data-layout');
       	 	var currentSidemode= $('*[data-sidemode].working')[0] == undefined ? switcher.existSidemode : $($('*[data-sidemode].working')[0]).attr('data-sidemode');
	  	    setLayoutStyles(currentLayout, currentStyle,currentSidemode);
	  	    $('#themelayout li a').removeClass('working');
    	    $(this).addClass('working')
		});
	   	
	   	$("*[data-sidemode]").click(function(e){
	  		e.preventDefault();
	  	    var currentStyle = $('*[data-theme].working')[0] == undefined ? switcher.existStyle : $($('*[data-theme].working')[0]).attr('data-theme');
       	    var currentLayout= $('*[data-layout].working')[0] == undefined ? switcher.existLayout : $($('*[data-layout].working')[0]).attr('data-layout');
	  	    var currentSidemode= $(this).attr('data-sidemode');
  	  	setLayoutStyles(currentLayout, currentStyle,currentSidemode);
	  	    $('#themesidemode li a').removeClass('working');
    	    $(this).addClass('working')
		});
	   	
	   	function setLayoutStyles(layout, style, sidemode) {
	   		var ui = '/webjars/monster-ui/';
	   		$('#theme').attr({href: getContextPath() + ui + layout + '/css/colors/' + style +'.css'})
	   		$('#theme').removeAttr('src')
	   		$('#sidemode').attr({href: getContextPath() + ui  + 'sidemode/' + sidemode +'.css'})
	   		$('#sidemode').removeAttr('src')
	   		$('#cssStyle').attr({href: getContextPath() + ui + layout + '/css/style.css'})
	   		$('#cssStyle').removeAttr('src')
	   		$('#jsSlimscroll').attr({href: getContextPath() + ui + layout + '/js/jquery.slimscroll.js'})
	   		$('#jsSlimscroll').removeAttr('src')
	   		$('#jsWaves').attr({href: getContextPath() + ui + layout + '/js/waves.js'})
	   		$('#jsWaves').removeAttr('src')
	   		$('#jsSidebar').attr({href: getContextPath() + ui + layout + '/js/sidebarmenu.js'})
	   		$('#jsSidebar').removeAttr('src')
	   		$('#jsCustom').attr({href: getContextPath() + ui + layout + '/js/custom.min.js'})
	   		$('#jsCustom').removeAttr('src')
	   	}
	   	
	   	window.onload = function setLayoutStyles(currentLayout,currentStyle,currentSidemode) {
	   		$('*[data-theme]').each(function(){
	   			var currStyle = $(this).attr('data-theme');
	   			if(currStyle == switcher.existStyle) {
	   				$(this).addClass('working');
	   			} else {
	   				$(this).removeClass('working');
	   			}
	   		});
	   		$('*[data-sidemode]').each(function(){
	   			var currSidemode = $(this).attr('data-sidemode');
	   			if(currSidemode == switcher.existSidemode) {
	   				$(this).addClass('working');
	   			} else {
	   				$(this).removeClass('working');
	   			}
	   		});
	   		$('*[data-layout]').each(function(){
	   			var currLayout = $(this).attr('data-layout');
	   			if(currLayout == switcher.existLayout) {
	   				$(this).addClass('working');
	   			} else {
	   				$(this).removeClass('working');
	   			}
	   		});
			
		};
		
	});
    
    function applyLayoutStyles() {
   		var currentStyle = $('*[data-theme].working')[0] == undefined ? switcher.existStyle : $($('*[data-theme].working')[0]).attr('data-theme');
   	 	var currentLayout= $('*[data-layout].working')[0] == undefined ? switcher.existLayout : $($('*[data-layout].working')[0]).attr('data-layout');
   	 	var currentSidemode= $('*[data-sidemode].working')[0] == undefined ? switcher.existLayout : $($('*[data-sidemode].working')[0]).attr('data-sidemode');
   	 	
   	 	console.log("Layout: " + currentLayout + " - Style: " + currentStyle);
   	 	
   	 	var reqData = [
   	 		{ "configCode" : "theme", "configVal" : currentStyle} ,
   	 		{ "configCode" : "mode", "configVal" : currentSidemode} ,
   	 		{ "configCode" : "layout", "configVal" : currentLayout }
   	 		];
   	 	
   	 	csrf_token = $('meta[name=_csrf]').attr("content");
   	 	
	 	$.ajax({
			async : false,
			global : false,
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : "POST",
			action : 'xhttp',
			contentType: "application/json; charset=utf-8",
			url : getContextPath() + "/updateTheme",
			data : JSON.stringify(reqData),
			dataType: 'application/json',
			success : function(data) {
				console.log(data)
			}
		});
   	}
   	
   	/* switcher sidebar */
   	$('#service_panel').css("right","-300px");
	$('.setting-icon').on('click',function(){
		if($('.setting-icon').hasClass('open')){
			$(this).removeClass('open');
			$(this).animate({
				"right":"20px",
				"background-position":"0px"
			});
			$('#service_panel').animate({"right":"-300px"});
			$('#shrink-content').animate({"margin-right":"0px"});
			$('.sidebar-r').animate({"margin-right":"0px"});
			$('.topbar .top-navbar').css({"margin-right":"85px","animation":"fadeIn 2.5s ease"});
		} else {
			$(this).addClass('open');
			$(this).animate({
				"right":"265px",
				"background-position":"0px"
			});
			$('#service_panel').animate({"right":"0px"});
			$('#shrink-content').animate({"margin-right":"250px"});
			$('.sidebar-r').animate({"margin-right":"250px"});
			$('.topbar .top-navbar').css({"margin-right":"310px","animation":"fadeIn 2.5s ease"});
		
		}
	});

   	<!--/* END THEME SWITCHER */-->
   	
   	function darklogoError(image) {
   	    image.onerror = "";
   	    image.src = "/webjars/monster-ui/images/logo-icon.png";
   	    return true;
   	}
   	
   	function lightlogoError(image) {
   	    image.onerror = "";
   	    image.src = "/webjars/monster-ui/images/logo-light-icon.png";
   	    return true;
   	}
   	
   	function darktextError(image) {
   	    image.onerror = "";
   	    image.src = "/webjars/monster-ui/images/logo-text.png";
   	    return true;
   	}
   	
   	function lighttextError(image) {
   	    image.onerror = "";
   	    image.src = "/webjars/monster-ui/images/logo-light-text.png";
   	    return true;
   	}