function editar(idPosicao){
	$.ajax({
		type:"get",
		url: "/milestone/admin/posicao/editar.jhtml"+"?ram=" + Math.random() + "&idItem=" + idPosicao,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.item != undefined){
				var item = response.item;
				$( "#idPosicao" ).val(item.id);
				$( "#posicao" ).val(item.posicao);
				$( "#descricao" ).val(item.descricao);
				$( "#qtdEsquerda" ).val(item.qtdEsquerda);
				$( "#qtdDireita" ).val(item.qtdDireita);
				if(item.codigoNorma != "null"){
					$( "#codigoNorma" ).val(item.codigoNorma);	
				}
				$( "#material" ).val(item.material);
				$( "#larguraDiametro" ).val(item.larguraDiametro);
				$( "#comprimento" ).val(item.comprimento);
				$( "#espessuraAlma" ).val(item.espessuraAlma);
				if(item.altura != "null"){
					$( "#altura" ).val(item.altura);
				}
				$( "#idFornecedor" ).val(item.idFornecedor);
				if(item.observacao){
					$( "#observacao" ).val(item.observacao);					
				}
				$( "#posicao-form" ).dialog( "open" );
				if(item.material == ""){
					$('input[name=conjuntoSoldado]').attr('checked', true);
					$("#conjuntoSoldadoLayer").css("display", "none");
					$( "#posicao-form" ).height(250);
				}
			}
		}
	});
}
function refreshGrid(idUnidade){
	$("#grid").find("tr:gt(0)").remove();
	$.ajax({
		type:"get",
		url: "/milestone/admin/posicao/ajax-list.jhtml"+"?ram=" + Math.random() + "&idUnidade=" + idUnidade,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.itens != undefined){
				for(var i = 0; i < response.itens.length; i++){
					var item = response.itens[i].item;
					var cs = "-";
					if(item.conjuntoSoldado == "S"){
						cs = "<img src=\"/milestone/img/ok.png\" />";
					}
					var html = "";
					if(response.edit != undefined && response.edit){
						html = "<tr>" +
						"<td  class=\"cinza\"><a href=\"#\" class=\"editar\" rel=\"" + item.id + "\">" + item.posicao + "</a></td>" +
						"<td>" + item.descricao + "</td>" +
						"<td>" + cs + "</td>" +
						"<td>" + item.qtdEsquerda + "</td>" +
						"<td>" + item.qtdDireita + "</td>" +
						"<td>" + item.porcentagem + "%</td>" +
						"<td><img class=\"delete\" id=\" " + item.id + " \" src=\"/milestone/img/close.png\" /></td>" +
						"</tr>";
					}else{
						html = "<tr>" +
						"<td  class=\"cinza\">" + item.posicao + "</td>" +
						"<td>" + item.descricao + "</td>" +
						"<td>" + cs + "</td>" +
						"<td>" + item.qtdEsquerda + "</td>" +
						"<td>" + item.qtdDireita + "</td>" +
						"<td>" + item.porcentagem + "%</td>" +
						"<td>&nbsp;</td>" +
						"</tr>";
					}
					$( "#grid tbody" ).append( html );
					if(response.edit != undefined && response.edit){
						$('table td img.delete').click(function(){
							deleteItem($(this));
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
function refreshGridApontamento(idUnidade){
	$("#grid-modal").find("tr:gt(0)").remove();
	var urlAjax = "";
	if(idUnidade != "" && idUnidade > 0){
		urlAjax = "/milestone/admin/posicao/ajax-list-by-unidade.jhtml"+"?ram=" + Math.random() + "&idUnidade=" + idUnidade;
	}
	if(urlAjax != ""){
		$.ajax({
			type:"get",
			url: urlAjax,
			dataType:"json",
			cache: false,
			success: function(response){
				if(response != undefined && response.itens != undefined){
					for(var i = 0; i < response.itens.length; i++){
						var item = response.itens[i].item;
						$( "#grid-modal tbody" ).append( "<tr>" +
								"<td class=\"cinza\">" + item.posicao + "<input type=\"hidden\" name=\"idsPosicoes\" value=\"" + item.id + "\"/><input type=\"hidden\" name=\"idsApontamentos\" value=\"" + item.apontamento.id + "\"/></td>" + 
								"<td><input type=\"text\" name=\"cronogramaInicio\" id=\"cronogramaInicio_" + item.id + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\" /></td>" +
								"<td><input type=\"text\" name=\"cronogramaFim\" id=\"cronogramaFim_" + item.id + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\" /></td>" +
								"<td><input type=\"text\" name=\"previstoInicio\" id=\"previsto_Inicio" + item.id + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\"/></td>" +
								"<td><input type=\"text\" name=\"porcentagem\" id=\"porcentagem" + item.id + "\" value=\"\" class=\"text ui-widget-content ui-corner-all\" style=\"width:30px;\"/></td>" +
								"</tr>" );
						bindDatePicker();

					}
				}
			}
		});
	}
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
				$("#idOperacao").html(options);
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
			if(response != undefined){
				var options = '<option value="">Selecione a Operacao</option>';
				for(var i = 0; i < response.operacoes.length; i++){
					var operacao = response.operacoes[i].operacao;
					options += '<option value="' + operacao.id + '">' + operacao.op + ' - ' + operacao.descricao + '</option>';
				}
				$("#idOperacao").html(options);
			}else{
				var options = '<option value="">Nenhum registro encontrado</option>';
				$("#idOperacao").html(options);
			}
		}
	});
});

$('#idOperacao').change(function(){
	$.ajax({
		type:"get",
		url: "/milestone/admin/unidade/ajax-list.jhtml"+"?ram=" + Math.random() + "&idOperacao=" + $(this).val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.unidades != undefined){
				$("#selectable").find("li").remove();
				for(var i = 0; i < response.unidades.length; i++){
					var unidade = response.unidades[i].unidade;
					$( "#selectable" ).append( "<li id=\"" + unidade.id + "\" class=\"ui-widget-content\">" + unidade.numero + "</li>");
				}
				$("#selectable li").click(function() {
					$(this).addClass("selected").siblings().removeClass("selected");
					$("#idUnidade").val(this.id);
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
		url: "/milestone/admin/sub-etapa/ajax-list-by-etapa.jhtml"+"?ram=" + Math.random() + "&idEtapa=" + $(this).val() + "&nivel=PO",
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
		url: "/milestone/admin/posicao/ajax-list-by-unidade-and-sub-etapa.jhtml"+"?ram=" + Math.random() + "&idUnidade=" + $("#idUnidade").val() + "&idSubEtapa=" + $(this).val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.itens != undefined){
				$("#grid-modal").find("tr:gt(1)").remove();
				if(response.itens[0] != undefined && response.itens[0].item.apontamento.na != undefined && response.itens[0].item.apontamento.na){
					$("#na").attr("checked",true);
					$("#grid-modal").css("display", "none");
					$( "#apontamentos" ).height(60);
				}else{
					for(var i = 0; i < response.itens.length; i++){
						var item = response.itens[i].item;
						$( "#grid-modal tbody" ).append( "<tr>" +
								"<td class=\"cinza\">" + item.posicao + "<input type=\"hidden\" name=\"idsPosicoes\" value=\"" + item.id + "\"/><input type=\"hidden\" name=\"idsApontamentos\" value=\"" + item.apontamento.id + "\"/></td>" + 
								"<td><input type=\"text\" name=\"cronogramaInicio\" id=\"cronogramaInicio_" + item.id + "\" value=\"" + item.apontamento.cronogramaInicio + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\" /></td>" +
								"<td><input type=\"text\" name=\"cronogramaFim\" id=\"cronogramaFim_" + item.id + "\" value=\"" + item.apontamento.cronogramaFim + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\" /></td>" +
								"<td><input type=\"text\" name=\"previstoInicio\" id=\"previstoInicio_" + item.id + "\" value=\"" + item.apontamento.previstoInicio + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\"/></td>" +
								"<td><input type=\"text\" name=\"previstoFim\" id=\"previstoFim_" + item.id + "\" value=\"" + item.apontamento.previstoFim + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\"/></td>" +
								"<td>" +
									"<input type=\"hidden\" class=\"porcentOld\" name=\"porcentOld\" id=\"" + item.id + "\" value=\"" + item.apontamento.porcentagem + "\"/>" +
									"<input type=\"text\" name=\"porcentagem\" id=\"porcentagem" + item.id + "\" value=\"" + item.apontamento.porcentagem + "\" class=\"text ui-widget-content ui-corner-all\" style=\"width:30px;\"/>" +
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
	$("#idUnidade").val(this.id);
	refreshGrid(this.id);
});

$("#conjuntoSoldado").click(function() {
	if ($("#conjuntoSoldado").attr("checked")){
		$("#conjuntoSoldadoLayer").css("display", "none");
		$( "#dialog-form" ).height(250);
	}else{
		$("#conjuntoSoldadoLayer").css("display", "block");
		$( "#dialog-form" ).height(500);
	}
});

var posicao = $( "#posicao" ),
	descricao = $( "#descricao" ),
	qtdEsquerda = $( "#qtdEsquerda" ),
	qtdDireita = $( "#qtdDireita" ),
	material = $( "#material" ),
	codigoNorma = $( "#codigoNorma" ),
	altura = $( "#altura" ),
	larguraDiametro = $( "#larguraDiametro" ),
	comprimento = $( "#comprimento" ),
	espessuraAlma = $( "#espessuraAlma" ),
	observacao = $( "#observacao" ),
	idFornecedor = $( "#idFornecedor" ),
	allFields = $( [] ).add( posicao )
		.add( descricao )
		.add( qtdEsquerda )
		.add( qtdDireita )
		.add( material )
		.add( codigoNorma )
		.add( altura )
		.add( larguraDiametro )
		.add( comprimento )
		.add( espessuraAlma )
		.add( idFornecedor )
		.add( observacao ),
	tips = $( ".validateTips" );

function validate(){
	var bValid = true;
	allFields.removeClass( "ui-state-error" );

	bValid = bValid && checkLength( posicao, "posicao", 2, 45, tips);
	bValid = bValid && checkLength( descricao, "descricao", 2, 45, tips);
	if(bValid && (qtdEsquerda.val().length == 0 && qtdDireita.val().length == 0)){
		bValid = bValid && false;
		updateTips("O campo Esquerda ou Direita precisam ser informados",tips);
		qtdEsquerda.addClass( "ui-state-error" );
		qtdDireita.addClass( "ui-state-error" );
		
	}
	if(!$("input[type=checkbox]").is(":checked")){
		bValid = bValid && checkLength( material, "Material", 2, 45, tips);
		bValid = bValid && checkLength( codigoNorma, "Codigo/Norma", 0, 45, tips);
		bValid = bValid && checkLength( altura, "Altura", 0, 9, tips);
		bValid = bValid && checkLength( larguraDiametro, "Largura/Diametro", 1, 9, tips);
		bValid = bValid && checkLength( comprimento, "Comprimento", 1, 9, tips);
		bValid = bValid && checkLength( espessuraAlma, "Espessura/Alma", 1, 9, tips);
		bValid = bValid && checkLength( observacao, "Observacao", 0, 255, tips);	
	}else{
		material.val("");
		codigoNorma.val("");
		altura.val("");
		larguraDiametro.val("");
		comprimento.val("");
		espessuraAlma.val(""),
		observacao.val("");
		idFornecedor.val(0);
	}
	

	bValid = bValid && checkRegexp( posicao, /^([0-9a-z -_])+$/i, "Posicao possui caracteres indesejados.", tips);
	bValid = bValid && checkRegexp( descricao, /^([0-9a-z -_])+$/i, "Descricao possui caracteres indesejados.", tips);
	
	if(bValid && qtdEsquerda.val().length > 0){
		bValid = bValid && checkRegexp( qtdEsquerda, /^([0-9])+$/, "Esquerda possui caracteres indesejados.", tips);
	}
	if(bValid && qtdDireita.val().length > 0){
		bValid = bValid && checkRegexp( qtdDireita, /^([0-9])+$/, "Direita possui caracteres indesejados.", tips);
	}
	
	if(!$("input[type=checkbox]").is(":checked")){
		bValid = bValid && checkRegexp( material, /^([0-9a-z -_�])+$/i, "Material possui caracteres indesejados.", tips);
		if(codigoNorma.val() != null && codigoNorma.val() != ""){
			bValid = bValid && checkRegexp( codigoNorma, /^([0-9a-z -_])+$/i, "Codigo/Norma possui caracteres indesejados.", tips);
		}
		if(altura.val() != null && altura.val() != ""){
			bValid = bValid && checkRegexp( altura, /^([0-9])+$/, "Altura possui caracteres indesejados.", tips);
		}
		if(espessuraAlma.val() != null && espessuraAlma.val() != ""){
			bValid = bValid && checkRegexp( espessuraAlma, /^([0-9])+$/, "Espessura/Alma possui caracteres indesejados.", tips);
		}
		bValid = bValid && checkRegexp( larguraDiametro, /^([0-9])+$/, "Largura/Diametro possui caracteres indesejados.", tips);
		bValid = bValid && checkRegexp( comprimento, /^([0-9])+$/, "Comprimento possui caracteres indesejados.", tips);
		if(observacao.val() != null && observacao.val() != ""){
			bValid = bValid && checkRegexp( observacao, /^[a-z]([0-9a-z_])+$/i, "Observacao possui caracteres indesejados.", tips);
		}
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

function saveItem(obj){
	var cs = "N";
	if($("input[type=checkbox]").is(":checked")){
		cs = "S";
	}
	$.ajax({
		type:"post",
		url: "/milestone/admin/posicao/save.jhtml"+"?ram=" + Math.random() + 
			"&idUnidade=" + $("#idUnidade").val() + 
			"&id=" + $("#idPosicao").val() + 
			"&posicao=" + posicao.val() +
			"&descricao=" + descricao.val() +
			"&qtdEsquerda=" + qtdEsquerda.val() + 
			"&qtdDireita=" + qtdDireita.val() +
			"&conjuntoSoldado=" + cs +
			"&material=" + material.val() +
			"&codigoNorma=" + codigoNorma.val() +
			"&altura=" + altura.val() +
			"&larguraDiametro=" + larguraDiametro.val() +
			"&comprimento=" + comprimento.val() +
			"&espessuraAlma=" + espessuraAlma.val() +
			"&observacao=" + observacao.val() +
			"&idFornecedor=" + idFornecedor.val(),
			
		dataType:"json",
		cache: false,
		success: function(response){
			if($( "#grid tbody tr:eq(0) td:eq(0)" ).attr("colspan") > 1){
				$("#grid").find("tr:gt(0)").remove();
			}
			
			if($("#idPosicao").val() > 0){
				refreshGrid($("#idUnidade").val());
			}else{
				var csCheck = "-";
				if(cs == "S"){
					csCheck = "<img src=\"/milestone/img/ok.png\" />";
				}
				if(response.item.id > 0){
					$( "#grid tbody" ).append( "<tr>" +
							"<td class=\"cinza\"><a href=\"#\" class=\"editar\" rel=\"" + response.item.id + "\">" + posicao.val() + "</a></td>" +
							"<td>" + descricao.val() + "</td>" +
							"<td>" + csCheck + "</td>" +
							"<td>" + qtdEsquerda.val() + "</td>" +
							"<td>" + qtdDireita.val() + "</td>" +
							"<td>0%</td>" +
							"<td><img class=\"delete\" id=\" " + response.item.id + " \" src=\"/milestone/img/close.png\" /></td>" +
							"</tr>" ); 
					$('table td img.delete').click(function(){
						deleteItem($(this));
					});
					$( ".editar" ).click(function() {
						editar($(this).attr("rel"));
					});
				}
			}
			$( "#posicao-form" ).dialog( "close" );
		}
	});
}

function addApontamentos(obj){
	dataString = $("#apontamento-posicao").serialize();
	$.ajax({
		type:"post",
		url: "/milestone/admin/apontamento/posicao-save.jhtml"+"?ram=" + Math.random(),
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

function deleteItem(obj){
	$( "#dialog:ui-dialog" ).dialog( "destroy" );
	$( "#dialog-confirm" ).dialog({
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			"Confirmar": function() {
				$.ajax({
					type:"post",
					url: "/milestone/admin/posicao/delete.jhtml"+"?ram=" + Math.random() + "&idItem=" + obj.attr("id"),
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
		url: "/milestone/admin/projeto/etapas/ajax-list.jhtml"+"?ram=" + Math.random() + "&idProjeto=" + idProjeto + "&nivel=PO",
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
    deleteItem($(this));
});

$("#na").click(function() {
	if ($("#na").attr("checked")){
		$("#grid-modal").css("display", "none");
		$( "#apontamentos" ).height(60);
	}else{
		$("#grid-modal").css("display", "block");
		$( "#apontamentos" ).height(500);
	}
});


$(function() {
	$( "#dialog:ui-dialog" ).dialog( "destroy" );
	
	$( "#posicao-form" ).dialog({
		autoOpen: false,
		height: 630,
		width: 350,
		modal: true,
		buttons: {
			"Salvar": function() {
				
				if (validate()) {
					saveItem($(this));
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

	$( "#add-posicao" ).click(function() {
		$( "#posicao-form" ).dialog( "open" );
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

