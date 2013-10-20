function editar(idSubPosicao){
	$.ajax({
		type:"get",
		url: "/milestone/admin/sub-posicao/editar.jhtml"+"?ram=" + Math.random() + "&idItem=" + idSubPosicao,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.item != undefined){
				var item = response.item;
				$( "#idSubPosicao" ).val(item.id);
				$( "#subPosicao" ).val(item.subPosicao);
				$( "#descricao" ).val(item.descricao);
				$( "#qtdEsquerda" ).val(item.qtdEsquerda);
				$( "#qtdDireita" ).val(item.qtdDireita);
				$( "#codigoNorma" ).val(item.codigoNorma);
				$( "#material" ).val(item.material);
				$( "#larguraDiametro" ).val(item.larguraDiametro);
				$( "#comprimento" ).val(item.comprimento);
				$( "#espessuraAlma" ).val(item.espessuraAlma);
				$( "#altura" ).val(item.altura);
				$( "#idFornecedor" ).val(item.idFornecedor);
				$( "#observacao" ).val(item.observacao);
				$( "#sub-posicao-form" ).dialog( "open" );
			}
		}
	});
}
function refreshGrid(idPosicao){
	$("#grid").find("tr:gt(0)").remove();
	$.ajax({
		type:"get",
		url: "/milestone/admin/sub-posicao/ajax-list.jhtml"+"?ram=" + Math.random() + "&idPosicao=" + idPosicao,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.itens != undefined){
				for(var i = 0; i < response.itens.length; i++){
					var item = response.itens[i].item;
					var html = "";
					if(response.edit != undefined && response.edit){
						html = "<tr>" +
						"<td  class=\"cinza\"><a href=\"#\" class=\"editar\" rel=\"" + item.id + "\">" + item.subPosicao + "</a></td>" +
						"<td>" + item.descricao + "</td>" +
						"<td>" + item.qtdEsquerda + "</td>" +
						"<td>" + item.qtdDireita + "</td>" +
						"<td>" + item.porcentagem + "%</td>" +
						"<td><img class=\"delete\" id=\"" + item.id + "\" src=\"/milestone/img/close.png\" /></td>" +
						"</tr>";
					}else{
						html = "<tr>" +
						"<td  class=\"cinza\">" + item.subPosicao + "</td>" +
						"<td>" + item.descricao + "</td>" +
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
				$("#idUnidade").html(options);
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
					options += '<option value="' + operacao.id + '">' + operacao.op + '-' + operacao.descricao + '</option>';
				}
				$("#idOperacao").html(options);
			}else{
				var options = '<option value="">Nenhum registro encontrado</option>';
				$("#idOperacao").html(options);
				$("#idUnidade").html(options);
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
				var options = '<option value="">Selecione a Unidade</option>';
				for(var i = 0; i < response.unidades.length; i++){
					var unidade = response.unidades[i].unidade;
					options += '<option value="' + unidade.id + '">' + unidade.numero + '</option>';
				}
				$("#idUnidade").html(options);
			}else{
				var options = '<option value="">Nenhum registro encontrado</option>';
				$("#idUnidade").html(options);
			}
		}
	});
});

$('#idUnidade').change(function(){
	$.ajax({
		type:"get",
		url: "/milestone/admin/posicao/ajax-list-conjunto-soldado.jhtml"+"?ram=" + Math.random() + "&idUnidade=" + $(this).val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.itens != undefined){
				$("#selectable").find("li").remove();
				for(var i = 0; i < response.itens.length; i++){
					var item = response.itens[i].item;
					$( "#selectable" ).append( "<li id=\"" + item.id + "\" class=\"ui-widget-content\">" + item.posicao + "</li>");
				}
				$("#selectable li").click(function() {
					$(this).addClass("selected").siblings().removeClass("selected");
					$("#idPosicao").val(this.id);
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
		url: "/milestone/admin/sub-etapa/ajax-list-by-etapa.jhtml"+"?ram=" + Math.random() + "&idEtapa=" + $(this).val() + "&nivel=SP",
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
		url: "/milestone/admin/sub-posicao/ajax-list-by-posicao-and-sub-etapa.jhtml"+"?ram=" + Math.random() + "&idPosicao=" + $("#idPosicao").val() + "&idSubEtapa=" + $(this).val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.itens != undefined){
				$("#grid-modal").find("tr:gt(1)").remove();
				if(response.itens[0].item.apontamento.na != undefined && response.itens[0].item.apontamento.na){
					$("#na").attr("checked",true);
					$("#grid-modal").css("display", "none");
					$( "#apontamentos" ).height(60);
				}else{
					for(var i = 0; i < response.itens.length; i++){
						var item = response.itens[i].item;
						$( "#grid-modal tbody" ).append( "<tr>" +
							"<td class=\"cinza\">" + item.subPosicao + "<input type=\"hidden\" name=\"idsSubPosicoes\" value=\"" + item.id + "\"/><input type=\"hidden\" name=\"idsApontamentos\" value=\"" + item.apontamento.id + "\"/></td>" + 
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
	$("#idPosicao").val(this.id);
	refreshGrid(this.id);
});

var subPosicao = $( "#subPosicao" ),
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
	allFields = $( [] ).add( subPosicao )
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

	bValid = bValid && checkLength( subPosicao, "Sub-Posicao", 2, 45, tips);
	bValid = bValid && checkLength( descricao, "Descricao", 2, 45, tips);
	if(bValid && (qtdEsquerda.val().length == 0 && qtdDireita.val().length == 0)){
		bValid = bValid && false;
		updateTips("O campo Esquerda ou Direita precisam ser informados",tips);
		qtdEsquerda.addClass( "ui-state-error" );
		qtdDireita.addClass( "ui-state-error" );
		
	}
	bValid = bValid && checkLength( material, "Material", 2, 45, tips);
	bValid = bValid && checkLength( codigoNorma, "Codigo Norma", 0, 45, tips);
	bValid = bValid && checkLength( altura, "Altura", 0, 9, tips);
	bValid = bValid && checkLength( espessuraAlma, "Espessura/Alma", 0, 9, tips);
	bValid = bValid && checkLength( larguraDiametro, "Largura/Diametro", 1, 9, tips);
	bValid = bValid && checkLength( comprimento, "Comprimento", 1, 9, tips);
	bValid = bValid && checkLength( observacao, "Observacao", 0, 255, tips);

	bValid = bValid && checkRegexp( subPosicao, /^([0-9a-z -_])+$/i, "Sub-Posicao possui caracteres indesejados.", tips);
	bValid = bValid && checkRegexp( descricao, /^([0-9a-z -_])+$/i, "Descricao possui caracteres indesejados.", tips);
	if(bValid && qtdEsquerda.val().length > 0){
		bValid = bValid && checkRegexp( qtdEsquerda, /^([0-9])+$/, "Esquerda possui caracteres indesejados.", tips);
	}
	if(bValid && qtdDireita.val().length > 0){
		bValid = bValid && checkRegexp( qtdDireita, /^([0-9])+$/, "Direita possui caracteres indesejados.", tips);
	}
	bValid = bValid && checkRegexp( material, /^([0-9a-z -_])+$/i, "Material possui caracteres indesejados.", tips);
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
		bValid = bValid && checkRegexp( observacao, /^([0-9a-z -_])+$/i, "Observacao possui caracteres indesejados.", tips);
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
	var _idFornecedor = idFornecedor.val();
	if (_idFornecedor == null) _idFornecedor = 0;
	$.ajax({
		type:"post",
		url: "/milestone/admin/sub-posicao/save.jhtml"+"?ram=" + Math.random() + 
			"&idPosicao=" + $("#idPosicao").val() + 
			"&id=" + $("#idSubPosicao").val() + 
			"&posicao=" + subPosicao.val() +
			"&descricao=" + descricao.val() +
			"&qtdEsquerda=" + qtdEsquerda.val() + 
			"&qtdDireita=" + qtdDireita.val() +
			"&material=" + material.val() +
			"&codigoNorma=" + codigoNorma.val() +
			"&altura=" + altura.val() +
			"&larguraDiametro=" + larguraDiametro.val() +
			"&comprimento=" + comprimento.val() +
			"&espessuraAlma=" + espessuraAlma.val() +
			"&observacao=" + observacao.val() +
			"&idFornecedor=" + _idFornecedor,
			
		dataType:"json",
		cache: false,
		success: function(response){
			if($( "#grid tbody tr:eq(0) td:eq(0)" ).attr("colspan") > 1){
				$("#grid").find("tr:gt(0)").remove();
			}
			if($("#idSubPosicao").val() > 0){
				refreshGrid($("#idPosicao").val());
			}else{
				if(response.item.id > 0){
					$( "#grid tbody" ).append( "<tr>" +
							"<td class=\"cinza\"><a href=\"#\" class=\"editar\" rel=\"" + response.item.id + "\">" + subPosicao.val() + "</a></td>" +
							"<td>" + descricao.val() + "</td>" +
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
			$( "#sub-posicao-form" ).dialog( "close" );
		}
	});
}

function addApontamentos(obj){
	dataString = $("#apontamento-sub-posicao").serialize();
	$.ajax({
		type:"post",
		url: "/milestone/admin/apontamento/sub-posicao-save.jhtml"+"?ram=" + Math.random(),
		data: dataString,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined){
				
			}
			$( "#sub-posicao-form" ).dialog( "close" );
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
					url: "/milestone/admin/sub-posicao/delete.jhtml"+"?ram=" + Math.random() + "&idItem=" + obj.attr("id"),
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
		url: "/milestone/admin/projeto/etapas/ajax-list.jhtml"+"?ram=" + Math.random() + "&idProjeto=" + idProjeto + "&nivel=SP",
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
	
	$( "#sub-posicao-form" ).dialog({
		autoOpen: false,
		height: 600,
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

	$( "#add-sub-posicao" ).click(function() {
		$( "#sub-posicao-form" ).dialog( "open" );
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