var descricao = $( "#descricao" ),
	idCliente = $( "#idCliente" ),
	allFields = $( [] ).add( descricao ).add( idCliente ),
	tips = $( ".validateTips" );

function validate(){
	var bValid = true;
	allFields.removeClass( "ui-state-error" );

	bValid = bValid && checkLength( descricao, "Descricao", 3, 45, tips);
	bValid = bValid && checkSelected( idCliente, "Cliente", tips);

	bValid = bValid && checkRegexp( descricao, /^[a-z]([0-9a-z -_])+$/i, "Descricao possui caracteres indesejados.", tips);
	return bValid;
}
$( "#create-gt" ).click(function() {
	if (validate()) {
		$("#novoProjeto").submit();
	}
});