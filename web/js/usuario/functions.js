var nome = $( "#nome" ),
	email = $( "#email" ),
	senha = $( "#senha" ),
	perfil = $( "#idPerfil" ),
	empreendimento = $( "#idEmpreendimento" ),
	allFields = $( [] ).add( nome ).add( email ).add( senha ).add( perfil ).add( empreendimento ),
	tips = $( ".validateTips" );

function validate(){
	var bValid = true;
	allFields.removeClass( "ui-state-error" );
	bValid = bValid && checkLength( nome, "Nome", 3, 45, tips);
	bValid = bValid && checkRegexp( email, /^[A-Za-z0-9_\-\.]+@[A-Za-z0-9_\-\.]{2,}\.[A-Za-z0-9]{2,}(\.[A-Za-z0-9])?/ , "E-mail Inválido.", tips);
	bValid = bValid && checkLength( senha, "Senha", 3, 45, tips);
	bValid = bValid && checkSelected( perfil, "Perfil", tips);
	bValid = bValid && checkSelected( empreendimento, "Empreendimento", tips);
	return bValid;
}
$( "#add-usuario" ).click(function() {
	tips.empty();
	if (validate()) {
		$("#novoUsuario").submit();
	}
});

$('#idPerfil').change(function(){
	$.ajax({
		type:"get",
		url: "/milestone/admin/empreendimento/ajax-list.jhtml"+"?ram=" + Math.random() + "&idPerfil=" + $(this).val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined){
				var options = '<option value="">Selecione o Empreendimento</option>';
				for(var i = 0; i < response.empreendimentos.length; i++){
					var empreendimento = response.empreendimentos[i].empreendimento;
					options += '<option value="' + empreendimento.id + '">' + empreendimento.descricao + '</option>';
				}
				$("#idEmpreendimento").html(options);
			}
		}
	});
});