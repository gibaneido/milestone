/**
* @author Paulo Martins <phmartins6@gmail.com>
*/

jQuery(function() {
	Admin._init();
});

var Admin = {
	
	_vars: {
		
	},

	_init: function() {
		try {
			this.grid();
		} catch (e) {
			alert('Error: ' + e.description);
		}
	},
	
	_is_number: function(value) {
		var regexNumeros = /^\d+$/;
		console.log(value);
		if ( regexNumeros.test( value ))
			return true;
		else
			return false;
	},
	
	email: function() {
		$("input[name=txtemail]").focus();
	},
	
	menu: function(menu) {	
		if (menu) {
			$('ul#jsddm li#' + menu.split('-')[1] + ' a:eq(0)').addClass('hover');
			$('ul.subnav li a#' + menu).addClass('hover');
		}
		
		var timeout    = 500;
		var closetimer = 0;
		var ddmenuitem = 0;
		
		function jsddm_open()
		{  jsddm_canceltimer();
		   jsddm_close();
		   $(this).find('ul li a').css('width',$(this).width());
		   ddmenuitem = $(this).find('ul').css('visibility', 'visible');}
		
		function jsddm_close()
		{  if(ddmenuitem) ddmenuitem.css('visibility', 'hidden');}
		
		function jsddm_timer()
		{  closetimer = window.setTimeout(jsddm_close, timeout);}
		
		function jsddm_canceltimer()
		{  if(closetimer)
		   {  window.clearTimeout(closetimer);
		      closetimer = null;}}
		
		$(document).ready(function()
		{  $('#jsddm > li').bind('mouseover', jsddm_open)
		   $('#jsddm > li').bind('mouseout',  jsddm_timer)});
		
		document.onclick = jsddm_close;
		
		$("a[rel='inactive']").css({'cursor':'default'});
		$("a[rel='inactive']").bind('click', function(){
			return false;
		});
	},
	
	grid: function() {
		var background_color = '';
		var color = '';
		
		$('table.grid tr:odd').css('background-color','#EEEEEE');
		$('table.grid tr td')
			.mouseover(function(){
				background_color = $(this).parent().css('background-color');
				color = $(this).parent().css('color');
				
				$(this).parent().css('background-color','#ee1a2f');
				$(this).parent().css('color','#FFFFFF');
			})
			.mouseout(function(){
				$(this).parent().css('background-color', background_color);
				$(this).parent().css('color', color);
			});
		
	},
	
	back: function() {
		$("a[rel=back]").click(function(e){
			e.preventDefault();
			history.back(-1);
		});
	}
};
function updateTips( t , tips) {
	tips.append( t ).addClass( "ui-state-highlight" );
	setTimeout(function() {
		tips.removeClass( "ui-state-highlight", 1500 );
	}, 500 );
}

function checkLength( o, n, min, max, tips) {
	if ( o.val().length > max || o.val().length < min ) {
		o.addClass( "ui-state-error" );
		updateTips( "O campo " + n + " precisa estar entre " + min + " e " + max + " caracteres.", tips);
		return false;
	} else {
		return true;
	}
}

function checkSelected( o, n, tips) {
	if ( o.val() < 1 ) {
		o.addClass( "ui-state-error" );
		updateTips( "Verifique o campo " + n + ".", tips);
		return false;
	} else {
		return true;
	}
}

function checkRegexp( o, regexp, n, tips ) {
	if ( !( regexp.test( o.val() ) ) ) {
		o.addClass("ui-state-error");
		updateTips(n, tips);
		return false;
	} else {
		return true;
	}
}
