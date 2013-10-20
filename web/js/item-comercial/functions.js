function editar(idItemComercial){
	$.ajax({
		type:"get",
		url: "/milestone/admin/item-comercial/editar.jhtml"+"?ram=" + Math.random() + "&idItemComercial=" + idItemComercial,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.item != undefined){
				var item = response.item;
				$( "#idItemComercial" ).val(item.id);
				$( "#descricao" ).val(item.descricao);
				$( "#qtdEsquerda" ).val(item.qtdEsquerda);
				$( "#qtdDireita" ).val(item.qtdDireita);
				$( "#codigoNorma" ).val(item.codigoNorma);
				$( "#idFornecedor" ).val(item.idFornecedor);
				$( "#item-comercial-form" ).dialog( "open" );
			}
		}
	});
}
function refreshGrid(idProduto,idUnidade){
	$("#grid").find("tr:gt(0)").remove();
	var urlAjax = "";
	if(idUnidade != "" && idUnidade > 0){
		urlAjax = "/milestone/admin/item-comercial/ajax-list-by-unidade.jhtml"+"?ram=" + Math.random() + "&idUnidade=" + idUnidade;
	}else{
		urlAjax = "/milestone/admin/item-comercial/ajax-list-by-produto.jhtml"+"?ram=" + Math.random() + "&idProduto=" + idProduto;
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
						var fornecedor = "-";
						if(item.fornecedor != undefined){
							fornecedor = item.fornecedor;
						}
						var html = "";
						if(response.edit != undefined && response.edit){
							html = "<tr>" +
							"<td class=\"cinza\"><a href=\"#\" class=\"editar\" rel=\"" + item.id + "\">" + item.descricao + "</a></td>" + 
							"<td>" + fornecedor + "</td>" + 
							"<td>" + item.qtdEsquerda + "</td>" + 
							"<td>" + item.qtdDireita + "</td>" + 
							"<td>" + item.porcentagem + "%</td>" +
							"<td><img class=\"delete\" id=\" " + item.id + " \" src=\"/milestone/img/close.png\" /></td>" +
							"</tr>";
						}else{
							html = "<tr>" +
							"<td class=\"cinza\">" + item.descricao + "</td>" + 
							"<td>" + fornecedor + "</td>" + 
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
						"<td  colspan=\"5\">Nenhum registro encontrado</td>" +
						"</tr>" );
				}
				$( ".editar" ).click(function() {
					editar($(this).attr("rel"));
				});
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
				$("#selIdProduto").html(options);
			}else{
				var options = '<option value="">Nenhum registro encontrado</option>';
				$("#selIdProduto").html(options);
				$("#idOperacao").html(options);
				$("#selIdUnidade").html(options);
			}
		}
	});
});

$('#selIdProduto').change(function(){
	$.ajax({
		type:"get",
		url: "/milestone/admin/operacao/ajax-list.jhtml"+"?ram=" + Math.random() + "&idProduto=" + $(this).val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined){
				var options = '<option value="">Selecione a Opera&ccedil;&atilde;o</option>';
				for(var i = 0; i < response.operacoes.length; i++){
					var operacao = response.operacoes[i].operacao;
					options += '<option value="' + operacao.id + '">' + operacao.op + '-' + operacao.descricao + '</option>';
				}
				$("#idOperacao").html(options);
				$("#idProduto").val($('#selIdProduto').val());
			}else{
				var options = '<option value="">Nenhum registro encontrado</option>';
				$("#idOperacao").html(options);
				$("#selIdUnidade").html(options);
			}
		}
	});
	refreshGrid($(this).val(),0);
	if($(this).val() > 0){
		$(".box_errors").hide();
	}
});

$('#idOperacao').change(function(){
	$.ajax({
		type:"get",
		url: "/milestone/admin/unidade/ajax-list.jhtml"+"?ram=" + Math.random() + "&idOperacao=" + $(this).val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined){
				var options = '<option value="">Selecione a Unidade</option>';
				for(var i = 0; i < response.unidades.length; i++){
					var unidade = response.unidades[i].unidade;
					options += '<option value="' + unidade.id + '">' + unidade.numero + '</option>';
				}
				
				$("#selIdUnidade").html(options);
			}else{
				var options = '<option value="">Nenhum registro encontrado</option>';
				$("#selIdUnidade").html(options);
			}
		}
	});
});

$('#selIdUnidade').change(function(){
	refreshGrid(0,$(this).val());
	if($(this).val() > 0){
		$(".box_errors").hide();
	}
	$("#idUnidade").val($('#selIdUnidade').val());
});

$('#idEtapa').change(function(){
	$.ajax({
		type:"get",
		url: "/milestone/admin/sub-etapa/ajax-list-by-etapa.jhtml"+"?ram=" + Math.random() + "&idEtapa=" + $(this).val() + "&nivel=IC",
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
});

$('#idSubEtapa').change(function(){
	var urlAjax = "";
	if($("#selIdUnidade").val() != "" && $("#selIdUnidade").val() > 0){
		urlAjax = "/milestone/admin/item-comercial/ajax-list-by-unidade-and-sub-etapa.jhtml"+"?ram=" + Math.random() + "&idUnidade=" + idUnidade.val() + "&idSubEtapa=" + $(this).val();
	}else{
		urlAjax = "/milestone/admin/item-comercial/ajax-list-by-produto-and-sub-etapa.jhtml"+"?ram=" + Math.random() + "&idProduto=" + idProduto.val() + "&idSubEtapa=" + $(this).val();
	}
	if(urlAjax != ""){
		$.ajax({
			type:"get",
			url: urlAjax,
			dataType:"json",
			cache: false,
			success: function(response){
				if(response != undefined && response.itens != undefined){
					$("#grid-modal").find("tr:gt(1)").remove();
					
					if(response.itens[0].item.apontamento.na){
						$("#na").attr("checked",true);
						$("#grid-modal").css("display", "none");
						$( "#apontamentos" ).height(60);
					}else{
						for(var i = 0; i < response.itens.length; i++){
							var item = response.itens[i].item;
							$( "#grid-modal tbody" ).append( "<tr>" +
									"<td class=\"cinza\">" + item.descricao + "<input type=\"hidden\" name=\"idsItensComerciais\" value=\"" + item.id + "\"/><input type=\"hidden\" name=\"idsApontamentos\" value=\"" + item.apontamento.id + "\"/></td>" + 
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
	}
});

var descricao = $( "#descricao" ),
	qtdEsquerda = $( "#qtdEsquerda" ),
	qtdDireita = $( "#qtdDireita" ),
	codigoNorma = $( "#codigoNorma" ),
	idFornecedor = $( "#idFornecedor" ),
	idProduto = $( "#idProduto" ),
	idUnidade = $( "#idUnidade" ),
	allFields = $( [] ).add( descricao )
		.add( qtdEsquerda )
		.add( qtdDireita )
		.add( codigoNorma )
		.add( idFornecedor )
		.add( idProduto )
		.add( idUnidade ),
	tips = $( ".validateTips" );

function validate(){
	var bValid = true;
	allFields.removeClass( "ui-state-error" );

	bValid = bValid && checkLength( descricao, "Descricao", 2, 45, tips);
	if(bValid && (qtdEsquerda.val().length == 0 && qtdDireita.val().length == 0)){
		bValid = bValid && false;
		updateTips("O campo Esquerda ou Direita precisam ser informados",tips);
		qtdEsquerda.addClass( "ui-state-error" );
		qtdDireita.addClass( "ui-state-error" );
		
	}
	bValid = bValid && checkLength( codigoNorma, "codigoNorma", 3, 16, tips);
	if(idProduto == "" && idUnidade == ""){
		updateTips( "Selecione um Produto ou uma Unidade", tips);
		bValid = bValid && false;
	}
	bValid = bValid && checkRegexp( descricao, /^([0-9a-z -_])+$/i, "Descricao possui caracteres indesejados.", tips);
	if(bValid && qtdEsquerda.val().length > 0){
		bValid = bValid && checkRegexp( qtdEsquerda, /^([0-9])+$/, "Esquerda possui caracteres indesejados.", tips);
	}
	if(bValid && qtdDireita.val().length > 0){
		bValid = bValid && checkRegexp( qtdDireita, /^([0-9])+$/, "Direita possui caracteres indesejados.", tips);
	}
	bValid = bValid && checkRegexp( codigoNorma, /^([0-9a-z -_])+$/i, "Codigo/Norma possui caracteres indesejados.", tips);
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
	$.ajax({
		type:"post",
		url: "/milestone/admin/item-comercial/save.jhtml"+"?ram=" + Math.random() + 
			"&idProduto=" + idProduto.val() + 
			"&idUnidade=" + idUnidade.val() +
			"&id=" + $("#idItemComercial").val() + 
			"&descricao=" + descricao.val() +
			"&qtdEsquerda=" + qtdEsquerda.val() + 
			"&qtdDireita=" + qtdDireita.val() +
			"&codigoNorma=" + codigoNorma.val() +
			"&idFornecedor=" + idFornecedor.val(),
			
		dataType:"json",
		cache: false,
		success: function(response){
			if($( "#grid tbody tr:eq(0) td:eq(0)" ).attr("colspan") > 1){
				$("#grid").find("tr:gt(0)").remove();
			}
			if($("#idItemComercial").val() > 0){
				refreshGrid($("#idProduto").val(), $("#idUnidade").val());
			}else{
				if(response.itemComercial.id > 0){
					$( "#grid tbody" ).append( "<tr>" +
							"<td class=\"cinza\"><a href=\"#\" class=\"editar\" rel=\"" + response.itemComercial.id + "\">" + descricao.val() + "</a></td>" +
							"<td>" + response.itemComercial.fornecedor + "</td>" +
							"<td>" + qtdEsquerda.val() + "</td>" +
							"<td>" + qtdDireita.val() + "</td>" +
							"<td>0%</td>" +
							"<td><img class=\"delete\" id=\"" + response.itemComercial.id + "\" src=\"/milestone/img/close.png\" /></td>" +
							"</tr>" ); 
					$('table td img.delete').click(function(){
						deleteItem($(this));
					});
					$( ".editar" ).click(function() {
						editar($(this).attr("rel"));
					});
				}
			}
			$( "#item-comercial-form" ).dialog( "close" );
		}
	});
}

function addApontamentos(obj){
	dataString = $("#apontamento-item-comercial").serialize();
	$.ajax({
		type:"post",
		url: "/milestone/admin/apontamento/item-comercial-save.jhtml"+"?ram=" + Math.random(),
		data: dataString,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response.itemComercial.id > 0){
				$( "#grid tbody" ).append( "<tr>" +
						"<td class=\"cinza\">" + descricao.val() + "</td>" +
						"<td>" + descricao.val() + "</td>" +
						"<td>" + qtdEsquerda.val() + "</td>" +
						"<td>" + qtdDireita.val() + "</td>" +
						"<td><img class=\"delete\" id=\"" + response.itemComercial.id + "\" src=\"/milestone/img/close.png\" /></td>" +
						"</tr>" ); 
				$('table td img.delete').click(function(){
					deleteItem($(this));
				});
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
					url: "/milestone/admin/item-comercial/delete.jhtml"+"?ram=" + Math.random() + "&idItemComercial=" + obj.attr("id"),
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
		url: "/milestone/admin/projeto/etapas/ajax-list.jhtml"+"?ram=" + Math.random() + "&idProjeto=" + idProjeto + "&nivel=IC",
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
	
	$( "#item-comercial-form" ).dialog({
		autoOpen: false,
		height: 320,
		width: 350,
		modal: true,
		buttons: {
			"Salvar": function() {
				if (validate()) {
					$("#idProduto").val($('#selIdProduto').val());
					$("#idUnidade").val($('#selIdUnidade').val());
					saveItem($(this));
				}
			},
			Cancelar: function() {
				$( this ).dialog( "close" );
				$("#idProduto").val($('#selIdProduto').val());
				$("#idUnidade").val($('#selIdUnidade').val());
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

	$( "#add-item-comercial" ).click(function() {
		if($('#selIdProduto').val() == "" &&  $('#selIdUnidade').val() == ""){
			$(".box_errors").show();
		}else{
			$( "#item-comercial-form" ).dialog( "open" );
		}
		
	});
	
	$( "#apontar" ).click(function() {
		
		if($('#selIdProduto').val() == "" && $('#selIdUnidade').val() == ""){
			$(".box_errors").show();
		}else{
			$("#idProduto").val($('#selIdProduto').val());
			$("#idUnidade").val($('#selIdUnidade').val());
			$("#grid-modal").css("display", "none");
			$("#na").attr("checked",false);
			$( "#apontamentos" ).dialog( "open" );
			tips.empty();
			refreshEtapas($("#idProjeto").val());
			$("#idEtapa option:first").attr('selected','selected');
			$("#idSubEtapa option:first").attr('selected','selected');
			$("#grid-modal").find("tr:gt(1)").remove();
		}
	});
});