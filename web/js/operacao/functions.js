function editar(idOperacao){
	$.ajax({
		type:"get",
		url: "/milestone/admin/operacao/editar.jhtml"+"?ram=" + Math.random() + "&idOperacao=" + idOperacao,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.operacao != undefined){
				var operacao = response.operacao;
				$( "#idOperacao" ).val(operacao.id);
				$( "#op" ).val(operacao.op);
				$( "#descricao" ).val(operacao.descricao);
				$( "#numeroDesenho" ).val(operacao.numeroDesenho);
				$( "#qtdEsquerda" ).val(operacao.qtdEsquerda);
				$( "#qtdDireita" ).val(operacao.qtdDireita);
				$( "#operacao-form" ).dialog( "open" );
			}
		}
	});
}

function refreshGrid(idProduto){
	$("#grid").find("tr:gt(0)").remove();
	$.ajax({
		type:"get",
		url: "/milestone/admin/operacao/ajax-list.jhtml"+"?ram=" + Math.random() + "&idProduto=" + idProduto,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.operacoes != undefined){
				for(var i = 0; i < response.operacoes.length; i++){
					var operacao = response.operacoes[i].operacao;
					var html = "";
					if(response.edit != undefined && response.edit){
						html = "<tr>" +
						"<td class=\"cinza\"><a href=\"#\" class=\"editar\" rel=\"" + operacao.id + "\">" + operacao.op + "</a></td>" +
						"<td>" + operacao.descricao + "</td>" + 
						"<td>" + operacao.numeroDesenho + "</td>" +
						"<td>" + operacao.qtdDireita + "</td>" +
						"<td>" + operacao.qtdEsquerda + "</td>" +
						"<td>" + operacao.porcentagem + "%</td>" +
						"<td><img class=\"delete\" id=\" " + operacao.id + " \" src=\"/milestone/img/close.png\" /></td>" +
						"</tr>";
					}else{
						html = "<tr>" +
						"<td class=\"cinza\">" + operacao.op + "</td>" +
						"<td>" + operacao.descricao + "</td>" + 
						"<td>" + operacao.numeroDesenho + "</td>" +
						"<td>" + operacao.qtdDireita + "</td>" +
						"<td>" + operacao.qtdEsquerda + "</td>" +
						"<td>" + operacao.porcentagem + "%</td>" +
						"<td>&nbsp;</td>" +
						"</tr>";
					}
					$( "#grid tbody" ).append( html );
					if(response.edit != undefined && response.edit){
						$('table td img.delete').click(function(){
							deleteOperacao($(this));
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
	//alert($( "#grid tbody tr:eq(0) td:eq(0)" ).attr("colspan"));
}

$('#idProjeto').change(function(){
	$("#idProjetoApontamento").val($(this).val());
	$.ajax({
		type:"get",
		url: "/milestone/admin/produto/ajax-list.jhtml"+"?ram=" + Math.random() + "&idProjeto=" + $(this).val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.produtos != undefined){
				$("#selectable").find("li").remove();
				for(var i = 0; i < response.produtos.length; i++){
					var produto = response.produtos[i].produto;
					$( "#selectable" ).append( "<li id=\"" + produto.id + "\" class=\"ui-widget-content\">" + produto.descricao + "</li>");
				}
				$("#selectable li").click(function() {
					$(this).addClass("selected").siblings().removeClass("selected");
					$("#idProduto").val(this.id);
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
		url: "/milestone/admin/sub-etapa/ajax-list-by-etapa.jhtml"+"?ram=" + Math.random() + "&idEtapa=" + $(this).val() + "&nivel=OP",
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
	listApontamentos($(this).val());
	$("#na").attr("checked",false);
	$("#grid-modal").css("display", "block");
	$( "#apontamentos" ).height(500);
});

$("#selectable li").click(function() {
	$(this).addClass("selected").siblings().removeClass("selected");
	$("#idProduto").val(this.id);
	refreshGrid(this.id);
});

$("#na").click(function() {
	if ($("#na").attr("checked")){
		$("#grid-modal").css("display", "none");
		$( "#apontamentos" ).height(60);
	}else{
		$("#grid-modal").css("display", "block");
		$( "#apontamentos" ).height(500);
		
		listApontamentos($("#idSubEtapa").val());
	}
});

function listApontamentos(idSubEtapa){
	$.ajax({
		type:"get",
		url: "/milestone/admin/operacao/ajax-list-by-produto-and-sub-etapa.jhtml"+"?ram=" + Math.random() + "&idProduto=" + $("#idProduto").val() + "&idSubEtapa=" + idSubEtapa,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.operacoes != undefined){
				$("#grid-modal").find("tr:gt(1)").remove();
				if(response.operacoes[0].operacao.apontamento.na){
					$("#na").attr("checked",true);
					$("#grid-modal").css("display", "none");
					$( "#apontamentos" ).height(60);
				}else{
					for(var i = 0; i < response.operacoes.length; i++){
						var operacao = response.operacoes[i].operacao;
						$( "#grid-modal tbody" ).append( "<tr>" +
								"<td class=\"cinza\">" + operacao.descricao + "<input type=\"hidden\" name=\"idsOperacoes\" value=\"" + operacao.id + "\"/><input type=\"hidden\" name=\"idsApontamentos\" value=\"" + operacao.apontamento.id + "\"/></td>" + 
								"<td><input type=\"text\" name=\"cronogramaInicio\" id=\"cronogramaInicio_" + operacao.id + "\" value=\"" + operacao.apontamento.cronogramaInicio + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\" /></td>" +
								"<td><input type=\"text\" name=\"cronogramaFim\" id=\"cronogramaFim_" + operacao.id + "\" value=\"" + operacao.apontamento.cronogramaFim + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\" /></td>" +
								"<td><input type=\"text\" name=\"previstoInicio\" id=\"previstoInicio_" + operacao.id + "\" value=\"" + operacao.apontamento.previstoInicio + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\"/></td>" +
								"<td><input type=\"text\" name=\"previstoFim\" id=\"previstoFim_" + operacao.id + "\" value=\"" + operacao.apontamento.previstoFim + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\"/></td>" +
								"<td>" +
									"<input type=\"hidden\" class=\"porcentOld\" name=\"porcentOld\" id=\"" + operacao.id + "\" value=\"" + operacao.apontamento.porcentagem + "\"/>" +
									"<input type=\"text\" name=\"porcentagem\" id=\"porcentagem" + operacao.id + "\" value=\"" + operacao.apontamento.porcentagem + "\" class=\"text ui-widget-content ui-corner-all\" style=\"width:30px;\"/>" +
								"</td>" +
								"</tr>" );
						bindDatePicker();
					}
				}
			}
		}
	});
}

var op = $( "#op" ),
	descricao = $( "#descricao" ),
	numeroDesenho = $( "#numeroDesenho" ),
	qtdEsquerda = $( "#qtdEsquerda" ),
	qtdDireita = $( "#qtdDireita" ),
	allFields = $( [] ).add( op ).add( descricao ).add( numeroDesenho ).add( qtdEsquerda ).add( qtdDireita ),
	tips = $( ".validateTips" );

function validate(){
	var bValid = true;
	allFields.removeClass( "ui-state-error" );

	bValid = bValid && checkLength( op, "OP", 1, 45, tips);
	bValid = bValid && checkLength( descricao, "descricao", 2, 45, tips);
	bValid = bValid && checkLength( numeroDesenho, "Numero Desenho", 3, 45, tips);
	if(bValid && (qtdEsquerda.val().length == 0 && qtdDireita.val().length == 0)){
		bValid = bValid && false;
		updateTips("O campo Esquerda ou Direita precisam ser informados",tips);
		qtdEsquerda.addClass( "ui-state-error" );
		qtdDireita.addClass( "ui-state-error" );
		
	}

	bValid = bValid && checkRegexp( op, /^([0-9a-z -_])+$/i, "OP possui caracteres indesejados.", tips);
	bValid = bValid && checkRegexp( descricao, /^([0-9a-z -_])+$/i, "Descricao possui caracteres indesejados.", tips);
	bValid = bValid && checkRegexp( numeroDesenho, /^([0-9a-z -_])+$/i, "Numero Desenho possui caracteres indesejados.", tips);
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

function saveOperacao(obj){
	$.ajax({
		type:"post",
		url: "/milestone/admin/operacao/save.jhtml"+"?ram=" + Math.random() + "&idProduto=" + $("#idProduto").val() + "&id=" + $("#idOperacao").val() + "&op=" + op.val() + "&descricao=" + descricao.val() + "&numeroDesenho=" + numeroDesenho.val() + "&qtdEsquerda=" + qtdEsquerda.val() + "&qtdDireita=" + qtdDireita.val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if($( "#grid tbody tr:eq(0) td:eq(0)" ).attr("colspan") > 1){
				$("#grid").find("tr:gt(0)").remove();
			}
			if($("#idProduto").val() > 0){
				refreshGrid($("#idProduto").val());
			}else{
				if(response.operacao.id > 0){
					$( "#grid tbody" ).append( "<tr>" +
							"<td class=\"cinza\"><a href=\"#\" class=\"editar\" rel=\"" + operacao.id + "\">" + op.val() + "</td>" +
							"<td>" + descricao.val() + "</td>" + 
							"<td>" + numeroDesenho.val() + "</td>" +
							"<td>" + qtdEsquerda.val() + "</td>" +
							"<td>" + qtdDireita.val() + "</td>" +
							"<td>0%</td>" +
							"<td><img class=\"delete\" id=\" " + response.operacao.id + " \" src=\"/milestone/img/close.png\" /></td>" +
							"</tr>" ); 
					$('table td img.delete').click(function(){
						deleteOperacao($(this));
					});
					$( ".editar" ).click(function() {
						editar($(this).attr("rel"));
					});
				}
			}
			$( "#operacao-form" ).dialog( "close" );
		}
	});
}

function addApontamentos(obj){
	dataString = $("#apontamento-operacao").serialize();
	$.ajax({
		type:"post",
		url: "/milestone/admin/apontamento/operacao-save.jhtml"+"?ram=" + Math.random(),
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

function deleteOperacao(obj){
	$( "#dialog:ui-dialog" ).dialog( "destroy" );
	$( "#dialog-confirm" ).dialog({
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			"Confirmar": function() {
				$.ajax({
					type:"post",
					url: "/milestone/admin/operacao/delete.jhtml"+"?ram=" + Math.random() + "&idOperacao=" + obj.attr("id"),
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
		url: "/milestone/admin/projeto/etapas/ajax-list.jhtml"+"?ram=" + Math.random() + "&idProjeto=" + idProjeto + "&nivel=OP",
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
    deleteOperacao($(this));
});

$(function() {
	$( "#dialog:ui-dialog" ).dialog( "destroy" );
	
	$( "#operacao-form" ).dialog({
		autoOpen: false,
		height: 330,
		width: 350,
		modal: true,
		buttons: {
			"Salvar": function() {
				
				if(validate()) {
					saveOperacao($(this));
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

	$( "#add-operacao" ).click(function() {
		$( "#operacao-form" ).dialog( "open" );
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