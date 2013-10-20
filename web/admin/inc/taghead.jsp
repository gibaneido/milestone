<%@page pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored ="false" %>
<meta http-equiv="content-language" content="pt-BR" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="expires" content = "0" />
<meta http-equiv="imagetoolbar" content="no" />
<meta name="MSSmartTagsPreventParsing" content="TRUE" />
<meta name="author" content="GLPSenior" />
<meta name="reply-to" content="gilberto.lira@gmail.com" />
<meta name="description" content="descrição vai aqui" />
<meta name="keywords" content="palavras-chave vão aqui" />
<meta name="robots" content="ALL" />
<meta name="revisit-after" content="7 days" />
<meta name="rating" content="general" />
<link rel="SHORTCUT ICON" href="/img/ico/favicon.ico" />
<link rel="apple-touch-icon" href="/img/ico/apple-touch-icon.png" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/blitzer/jquery-ui-1.8.17.custom.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/blitzer/ui.slider.extras.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ui.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/admin.js"></script>
<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/validation.js"></script>
<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/libs/jquery-1.7.1.min.js"></script>
<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/libs/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/libs/selectToUISlider.jQuery.js"></script>
<script>
function bindDatePicker(){
	$(function() {
		$.datepicker.setDefaults({dateFormat: 'dd/mm/yy',
            dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado','Domingo'],
            dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
            dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
            monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro', 'Outubro','Novembro','Dezembro'],
            monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set', 'Out','Nov','Dez'],
            nextText: 'Próximo',
            prevText: 'Anterior'
		});
		$( ".datepicker" ).datepicker();
		$( ".datepicker" ).datepicker( "option", "dateFormat", "dd/mm/yy" );
	});
	
	$.datepicker.setDefaults({
	    onSelect: function () {
	        $(this).focus();
	        $(this).nextAll('input, button, textarea, a').filter(':first').focus();
	    }
	});
	
}
	
bindDatePicker();	

</script>
