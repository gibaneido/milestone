var razaoSocial = $( "#razaoSocial" ),
	cnpj = $( "#cnpj" ),
	allFields = $( [] ).add( razaoSocial ).add( cnpj ),
	tips = $( ".validateTips" );

function validate(){
	var bValid = true;
	allFields.removeClass( "ui-state-error" );
	bValid = bValid && checkLength( razaoSocial, "Razão Social", 3, 45, tips);
	bValid = bValid && checkRegexp( cnpj, /^\d{2}[.]\d{3}[.]\d{3}[\/]\d{4}[-]\d{2}$/ , "CNPJ Inválido.", tips);
	return bValid;
}
$( "#add-cliente" ).click(function() {
	tips.empty();
	if (validate()) {
		$("#novoCliente").submit();
	}
});