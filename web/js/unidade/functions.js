function editar(idUnidade){
	$.ajax({
		type:"get",
		url: "/milestone/admin/unidade/editar.jhtml"+"?ram=" + Math.random() + "&idUnidade=" + idUnidade,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.unidade != undefined){
				var unidade = response.unidade;
				$( "#idUnidade" ).val(unidade.id);
				$( "#numero" ).val(unidade.numero);
				if(unidade.descricao != "null"){
					$( "#descricao" ).val(unidade.descricao);	
				}
				$( "#qtdEsquerda" ).val(unidade.qtdEsquerda);
				$( "#qtdDireita" ).val(unidade.qtdDireita);
				$( "#unidade-form" ).dialog( "open" );
			}
		}
	});
}

function refreshGrid(idOperacao){
	$("#grid").find("tr:gt(0)").remove();
	$.ajax({
		type:"get",
		url: "/milestone/admin/unidade/ajax-list.jhtml"+"?ram=" + Math.random() + "&idOperacao=" + idOperacao,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.unidades != undefined){
				for(var i = 0; i < response.unidades.length; i++){
					var unidade = response.unidades[i].unidade;
					var html = "";
					if(response.edit != undefined && response.edit){
						html= "<tr>" +
						"<td class=\"cinza\"><a href=\"#\" class=\"editar\" rel=\"" + unidade.id + "\">" + unidade.numero + "</a></td>" + 
						"<td>" + unidade.qtdDireita + "</td>" +
						"<td>" + unidade.qtdEsquerda + "</td>" +
						"<td>" + unidade.porcentagem + "%</td>" +
						"<td><img class=\"delete\" id=\" " + unidade.id + " \" src=\"/milestone/img/close.png\" /></td>" +
						"</tr>";
					}else{
						html = "<tr>" +
						"<td class=\"cinza\">" + unidade.numero + "</td>" + 
						"<td>" + unidade.qtdDireita + "</td>" +
						"<td>" + unidade.qtdEsquerda + "</td>" +
						"<td>" + unidade.porcentagem + "%</td>" +
						"<td>&nbsp;</td>" +
						"</tr>";
					}
					$( "#grid tbody" ).append( html );
					if(response.edit != undefined && response.edit){
						$('table td img.delete').click(function(){
							deleteUnidade($(this));
						});	
					}
				}
			}else{
				$( "#grid tbody" ).append( "<tr>" +
					"<td  colspan=\"6\">Nenhum registro encontrado</td>" +
					"</tr>" );
			}
			$( ".editar" ).click(function() {
				editar($(this).attr("rel"));
			});
		}
	});
}

$('#idProjeto').change(function(){
	$("#idProjetoApontamento").val($(this).val());
	$.ajax({
		type:"get",
		url: "/milestone/admin/produto/ajax-list.jhtml"+"?ram=" + Math.random() + "&idProjeto=" + $(this).val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined){
				var options = '<option value="">Selecione o Produto</option>';
				for(var i = 0; i < response.produtos.length; i++){
					var produto = response.produtos[i].produto;
					options += '<option value="' + produto.id + '">' + produto.descricao + '</option>';
				}
				$("#idProduto").html(options);
			}else{
				var options = '<option value="">Nenhum registro encontrado</option>';
				$("#idProduto").html(options);
			}
		}
	});
});

$('#idProduto').change(function(){
	$.ajax({
		type:"get",
		url: "/milestone/admin/operacao/ajax-list.jhtml"+"?ram=" + Math.random() + "&idProduto=" + $(this).val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.operacoes != undefined){
				$("#selectable").find("li").remove();
				for(var i = 0; i < response.operacoes.length; i++){
					var operacao = response.operacoes[i].operacao;
					$( "#selectable" ).append( "<li id=\"" + operacao.id + "\" class=\"ui-widget-content\">" + operacao.op + "-" + operacao.descricao + "</li>");
				}
				$("#selectable li").click(function() {
					$(this).addClass("selected").siblings().removeClass("selected");
					$("#idOperacao").val(this.id);
					refreshGrid(this.id);
				});
				$("ol#selectable li:first").click();
			}
		}
	});
});

$('#idEtapa').change(function(){
	$.ajax({
		type:"get",
		url: "/milestone/admin/sub-etapa/ajax-list-by-etapa.jhtml"+"?ram=" + Math.random() + "&idEtapa=" + $(this).val() + "&nivel=UN",
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined){
				var options = '<option value="">Selecione a Sub-Etapa</option>';
				for(var i = 0; i < response.subEtapas.length; i++){
					var subEtapa = response.subEtapas[i].subEtapa;
					options += '<option value="' + subEtapa.id + '">' + subEtapa.descricao + '</option>';
				}
				$("#idSubEtapa").html(options);
			}
		}
	});
	$("#na").attr("checked",false);
});

$('#idSubEtapa').change(function(){
	$.ajax({
		type:"get",
		url: "/milestone/admin/unidade/ajax-list-by-operacao-and-sub-etapa.jhtml"+"?ram=" + Math.random() + "&idOperacao=" + $("#idOperacao").val() + "&idSubEtapa=" + $(this).val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.unidades != undefined){
				$("#grid-modal").find("tr:gt(1)").remove();
				if(response.unidades[0].unidade.apontamento.na != undefined && response.unidades[0].unidade.apontamento.na){
					$("#na").attr("checked",true);
					$("#grid-modal").css("display", "none");
					$( "#apontamentos" ).height(60);
				}else{
					for(var i = 0; i < response.unidades.length; i++){
						var unidade = response.unidades[i].unidade;
						$( "#grid-modal tbody" ).append( "<tr>" +
								"<td class=\"cinza\">" + unidade.numero + "<input type=\"hidden\" name=\"idsUnidades\" value=\"" + unidade.id + "\"/><input type=\"hidden\" name=\"idsApontamentos\" value=\"" + unidade.apontamento.id + "\"/></td>" + 
								"<td><input type=\"text\" name=\"cronogramaInicio\" id=\"cronogramaInicio_" + unidade.id + "\" value=\"" + unidade.apontamento.cronogramaInicio + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\" /></td>" +
								"<td><input type=\"text\" name=\"cronogramaFim\" id=\"cronogramaFim_" + unidade.id + "\" value=\"" + unidade.apontamento.cronogramaFim + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\" /></td>" +
								"<td><input type=\"text\" name=\"previstoInicio\" id=\"previstoInicio_" + unidade.id + "\" value=\"" + unidade.apontamento.previstoInicio + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\"/></td>" +
								"<td><input type=\"text\" name=\"previstoFim\" id=\"previstoFim_" + unidade.id + "\" value=\"" + unidade.apontamento.previstoFim + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\"/></td>" +
								"<td>" +
									"<input type=\"hidden\" class=\"porcentOld\" name=\"porcentOld\" id=\"" + unidade.id + "\" value=\"" + unidade.apontamento.porcentagem + "\"/>" +
									"<input type=\"text\" name=\"porcentagem\" id=\"porcentagem" + unidade.id + "\" value=\"" + unidade.apontamento.porcentagem + "\" class=\"text ui-widget-content ui-corner-all\" style=\"width:30px;\"/>" +
								"</td>" +
								"</tr>" );
						bindDatePicker();
					}
				}
			}
		}
	});
	$("#na").attr("checked",false);
	$("#grid-modal").css("display", "block");
	$( "#apontamentos" ).height(500);
});


$("#selectable li").click(function() {
	$(this).addClass("selected").siblings().removeClass("selected");
	$("#idOperacao").val(this.id);
	refreshGrid(this.id);
});

var numero = $( "#numero" ),
	descricao = $( "#descricao" ),
	qtdEsquerda = $( "#qtdEsquerda" ),
	qtdDireita = $( "#qtdDireita" ),
	allFields = $( [] ).add( numero ).add( descricao ).add( qtdEsquerda ).add( qtdDireita ),
	tips = $( ".validateTips" );

function validate(){
	var bValid = true;
	allFields.removeClass( "ui-state-error" );

	bValid = bValid && checkLength( numero, "numero", 2, 45, tips);
	bValid = bValid && checkLength( descricao, "descricao", 0, 45, tips);
	if(bValid && (qtdEsquerda.val().length == 0 && qtdDireita.val().length == 0)){
		bValid = bValid && false;
		updateTips("O campo Esquerda ou Direita precisam ser informados",tips);
		qtdEsquerda.addClass( "ui-state-error" );
		qtdDireita.addClass( "ui-state-error" );
		
	}

	bValid = bValid && checkRegexp( numero, /^([0-9a-z -_])+$/i, "Numero possui caracteres indesejados.", tips);
	if(descricao.val() != null && descricao.val() != ""){
		bValid = bValid && checkRegexp( descricao, /^([0-9a-z -_])+$/i, "Descricao possui caracteres indesejados.", tips);	
	}
	
	if(bValid && qtdEsquerda.val().length > 0){
		bValid = bValid && checkRegexp( qtdEsquerda, /^([0-9])+$/, "Esquerda possui caracteres indesejados.", tips);
	}
	if(bValid && qtdDireita.val().length > 0){
		bValid = bValid && checkRegexp( qtdDireita, /^([0-9])+$/, "Direita possui caracteres indesejados.", tips);
	}
	return bValid;
}

function validateAP(){
	var bValid = true;
	$.each($(".porcentOld"), function(index, hidden) { 
		var bMsg = "";
		if($("#porcentagem" + hidden.id).val() > 0){
			if($("#cronogramaInicio_" + hidden.id).val().length != 10 ||
				$("#cronogramaFim_" + hidden.id).val().length != 10 ||
				$("#previstoInicio_" + hidden.id).val().length != 10 ||
				$("#previstoFim_" + hidden.id).val().length != 10
			){
				bValid = false;
				bMsg = bMsg + "<p>Para preencher a porcentagem, e preciso preencher as datas</p>";
			}
		}
			
		if(parseFloat($("#porcentagem" + hidden.id).val()) < parseFloat(hidden.value)){
			  bValid = false;
			  bMsg = bMsg + "<p>A porcentagem dos itens deve ser sempre crescente.</p>";
		}
		if(parseFloat($("#porcentagem" + hidden.id).val()) > 100){
			bValid = false;
			bMsg = bMsg + "<p>A porcentagem deve ser menor ou igual a 100.</p>";
		}
		if(!bValid){
			updateTips(bMsg, tips);
		}	
		
	});

	return bValid;
}

function saveUnidade(obj){
	$.ajax({
		type:"post",
		url: "/milestone/admin/unidade/save.jhtml"+"?ram=" + Math.random() + 
			"&idOperacao=" + $("#idOperacao").val() + 
			"&id=" + $("#idUnidade").val() +
			"&descricao=" + descricao.val() +
			"&numero=" + numero.val() + 
			"&qtdEsquerda=" + qtdEsquerda.val() + 
			"&qtdDireita=" + qtdDireita.val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if($( "#grid tbody tr:eq(0) td:eq(0)" ).attr("colspan") > 1){
				$("#grid").find("tr:gt(0)").remove();
			}
			if($("#idUnidade").val() > 0){
				refreshGrid($("#idOperacao").val());
			}else{
				
				if(response.unidade.id > 0){
					$( "#grid tbody" ).append( "<tr>" +
							"<td class=\"cinza\"><a href=\"#\" class=\"editar\" rel=\"" + response.unidade.id + "\">" + numero.val() + "</td>" + 
							"<td>" + qtdEsquerda.val() + "</td>" +
							"<td>" + qtdDireita.val() + "</td>" +
							"<td>0%</td>" +
							"<td><img class=\"delete\" id=\" " + response.unidade.id + " \" src=\"/milestone/img/close.png\" /></td>" +
							"</tr>" ); 
					$('table td img.delete').click(function(){
						deleteUnidade($(this));
					});
					$( ".editar" ).click(function() {
						editar($(this).attr("rel"));
					});
				}
			}
			$( "#unidade-form" ).dialog( "close" );
		}
	});
}

function addApontamentos(obj){
	dataString = $("#apontamento-unidade").serialize();
	$.ajax({
		type:"post",
		url: "/milestone/admin/apontamento/unidade-save.jhtml"+"?ram=" + Math.random(),
		data: dataString,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined){
				
			}
			$( "#dialog-form" ).dialog( "close" );
		}
	});
}

function deleteUnidade(obj){
	$( "#dialog:ui-dialog" ).dialog( "destroy" );
	$( "#dialog-confirm" ).dialog({
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			"Confirmar": function() {
				$.ajax({
					type:"post",
					url: "/milestone/admin/unidade/delete.jhtml"+"?ram=" + Math.random() + "&idUnidade=" + obj.attr("id"),
					dataType:"json",
					cache: false,
					success: function(response){
						if(response.status == 1){
							obj.parent().parent().remove();
						}
					}
				});
				$( this ).dialog( "close" );
			},
			Cancelar: function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

function refreshEtapas(idProjeto){
	$.ajax({
		type:"get",
		url: "/milestone/admin/projeto/etapas/ajax-list.jhtml"+"?ram=" + Math.random() + "&idProjeto=" + idProjeto + "&nivel=UN",
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined){
				var options = '<option value="">Selecione a Etapa</option>';
				for(var i = 0; i < response.etapas.length; i++){
					var etapa = response.etapas[i].etapa;
					options += '<option value="' + etapa.id + '">' + etapa.descricao + '</option>';
				}
				$("#idEtapa").html(options);
			}
		}
	});
}

function setProjetoApontamento(idProjeto){
	$("#idProjetoApontamento").val(idProjeto);
}

$('table td img.delete').click(function(){
    deleteUnidade($(this));
});

$("#na").click(function() {
	if ($("#na").attr("checked")){
		$("#grid-modal").css("display", "none");
		$( "#apontamentos" ).height(60);
	}else{
		$("#grid-modal").css("display", "block");
		$( "#apontamentos" ).height(170);
	}
});

$(function() {
	$( "#dialog:ui-dialog" ).dialog( "destroy" );
	
	$( "#unidade-form" ).dialog({
		autoOpen: false,
		height: 270,
		width: 350,
		modal: true,
		buttons: {
			"Salvar": function() {
				
				if (validate()) {
					saveUnidade($(this));
				}
				
			},
			Cancelar: function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			allFields.val( "" ).removeClass( "ui-state-error" );
		}
	});
	
	$( "#apontamentos" ).dialog({
		autoOpen: false,
		height: 180,
		width: 650,
		modal: true,
		buttons: {
			"Salvar": function() {
				tips.empty();
				if (validateAP()) {
					addApontamentos($(this));
					$(this).dialog( "close" );
				}
			},
			Cancelar: function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			allFields.val( "" ).removeClass( "ui-state-error" );
		}
	});

	$( "#add-unidade" ).click(function() {
		$( "#unidade-form" ).dialog( "open" );
	});
	
	$( "#apontar" ).click(function() {
		$("#grid-modal").css("display", "none");
		$("#na").attr("checked",false);
		$( "#apontamentos" ).dialog( "open" );
		tips.empty();
		refreshEtapas($("#idProjeto").val());
		$("#idEtapa option:first").attr('selected','selected');
		$("#idSubEtapa option:first").attr('selected','selected');
		$("#grid-modal").find("tr:gt(1)").remove();
	});
});
			
$("ol#selectable li:first").click();