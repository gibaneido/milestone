function editar(idProduto){
	$.ajax({
		type:"get",
		url: "/milestone/admin/produto/editar.jhtml"+"?ram=" + Math.random() + "&idProduto=" + idProduto,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.produto != undefined){
				var produto = response.produto;
				$( "#idProduto" ).val(produto.id);
				$( "#descricao" ).val(produto.descricao);
				if(produto.numero != "null"){
					$( "#numero" ).val(produto.numero);					
				}
				$( "#produto-form" ).dialog( "open" );
			}
		}
	});
}

function refreshGrid(idProjeto){
	$("#grid").find("tr:gt(0)").remove();
	$.ajax({
		type:"get",
		url: "/milestone/admin/produto/ajax-list.jhtml"+"?ram=" + Math.random() + "&idProjeto=" + idProjeto,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.produtos != undefined){
				for(var i = 0; i < response.produtos.length; i++){
					var produto = response.produtos[i].produto;
					var html = "";
					if(response.edit != undefined && response.edit){
						html = "<tr>" +
						"<td class=\"cinza\"><a href=\"#\" class=\"editar\" rel=\"" + produto.id + "\">" + produto.descricao + "</a></td>" + 
						"<td>" + produto.numero + "</td>" + 
						"<td><p class=\"ToolText\" onMouseOver=\"javascript:this.className='ToolTextHover'\" onMouseOut=\"javascript:this.className='ToolText'\">" + produto.porcentagem + "%<span>aeeeee</span></p></td>" +
						"<td><img class=\"delete\" id=\" " + produto.id + " \" src=\"/milestone/img/close.png\" /></td>" +
						"</tr>";	
					}else{
						html = "<tr>" +
						"<td class=\"cinza\">" + produto.descricao + "</td>" + 
						"<td>" + produto.numero + "</td>" + 
						"<td><p class=\"ToolText\" onMouseOver=\"javascript:this.className='ToolTextHover'\" onMouseOut=\"javascript:this.className='ToolText'\">" + produto.porcentagem + "%<span>aeeeee</span></p></td>" +
						"<td>&nbsp;</td>" +
						"</tr>";
					}
					$( "#grid tbody" ).append( html );
					if(response.edit != undefined && response.edit){
						$('table td img.delete').click(function(){
							deleteProduto($(this));
						});
					}
				}
			}else{
				$( "#grid tbody" ).append( "<tr>" +
					"<td colspan=\"3\">Nenhum registro encontrado</td>" +
					"</tr>" );
			}
			
			$( ".editar" ).click(function() {
				editar($(this).attr("rel"));
			});
			
		}
	});
}

$('#idEtapa').change(function(){
	$.ajax({
		type:"get",
		url: "/milestone/admin/sub-etapa/ajax-list-by-etapa.jhtml"+"?ram=" + Math.random() + "&idEtapa=" + $(this).val() + "&nivel=PD",
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
		url: "/milestone/admin/produto/ajax-list-by-projeto-and-sub-etapa.jhtml"+"?ram=" + Math.random() + "&idProjeto=" + $("#idProjeto").val() + "&idSubEtapa=" + $(this).val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined && response.produtos != undefined){
				$("#grid-modal").find("tr:gt(1)").remove();
				if(response.produtos[0].produto.apontamento.na){
					$("#na").attr("checked",true);
					$("#grid-modal").css("display", "none");
					$( "#apontamentos" ).height(60);
				}else{
					for(var i = 0; i < response.produtos.length; i++){
						var produto = response.produtos[i].produto;
						$( "#grid-modal tbody" ).append( "<tr>" +
								"<td class=\"cinza\">" + produto.descricao + "<input type=\"hidden\" name=\"idsProdutos\" value=\"" + produto.id + "\"/><input type=\"hidden\" name=\"idsApontamentos\" value=\"" + produto.apontamento.id + "\"/></td>" + 
								"<td><input type=\"text\" name=\"cronogramaInicio\" id=\"cronogramaInicio_" + produto.id + "\" value=\"" + produto.apontamento.cronogramaInicio + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\" /></td>" +
								"<td><input type=\"text\" name=\"cronogramaFim\" id=\"cronogramaFim_" + produto.id + "\" value=\"" + produto.apontamento.cronogramaFim + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\" /></td>" +
								"<td><input type=\"text\" name=\"previstoInicio\" id=\"previstoInicio_" + produto.id + "\" value=\"" + produto.apontamento.previstoInicio + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\"/></td>" +
								"<td><input type=\"text\" name=\"previstoFim\" id=\"previstoFim_" + produto.id + "\" value=\"" + produto.apontamento.previstoFim + "\" class=\"text ui-widget-content ui-corner-all \" style=\"width:80px;\"/></td>" +
								"<td>" +
									"<input type=\"hidden\" class=\"porcentOld\" name=\"porcentOld\" id=\"" + produto.id + "\" value=\"" + produto.apontamento.porcentagem + "\"/>" +
									"<input type=\"text\" name=\"porcentagem\" id=\"porcentagem" + produto.id + "\" value=\"" + produto.apontamento.porcentagem + "\" class=\"text ui-widget-content ui-corner-all\" style=\"width:30px;\"/>" +
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
	$("#idProjeto").val(this.id);
	$("#idProjetoApontamento").val(this.id);
	refreshGrid(this.id);
});

var descricao = $( "#descricao" ),
	numero = $( "#numero" ),
	allFields = $( [] ).add( descricao ).add( numero ),
	tips = $( ".validateTips" );

function validate(){
	var bValid = true;
	allFields.removeClass( "ui-state-error" );

	bValid = bValid && checkLength( descricao, "Descricao", 3, 45, tips);
	bValid = bValid && checkLength( numero, "Numero", 0, 45, tips);	
	
	
	bValid = bValid && checkRegexp( descricao, /^[a-z]([0-9a-z -_])+$/i, "Descricao possui caracteres indesejados.", tips);
	if(numero.val() != null && numero.val() != ""){
		bValid = bValid && checkRegexp( numero, /^[a-z]([0-9a-z -_])+$/i, "Numero possui caracteres indesejados", tips);	
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

function saveProduto(obj){
	$.ajax({
		type:"post",
		url: "/milestone/admin/produto/save.jhtml"+"?ram=" + Math.random() + 
		"&idProjeto=" + $("#idProjeto").val() + 
		"&id=" + $("#idProduto").val() + 
		"&descricao=" + descricao.val() + 
		"&numero=" + numero.val(),
		dataType:"json",
		cache: false,
		success: function(response){
			if($( "#grid tbody tr:eq(0) td:eq(0)" ).attr("colspan") > 1){
				$("#grid").find("tr:gt(0)").remove();
			}
			if($("#idProduto").val() > 0){
				refreshGrid($("#idProjeto").val());
			}else{
				if(response.produto.id > 0){
					$( "#grid tbody" ).append( "<tr>" +
							"<td class=\"cinza\"><a href=\"#\" class=\"editar\" rel=\"" + response.produto.id + "\">" + descricao.val() + "</td>" + 
							"<td>" + numero.val() + "</td>" + 
							"<td>0%</td>" +
							"<td><img class=\"delete\" id=\" " + response.produto.id + " \" src=\"/milestone/img/close.png\" /></td>" +
							"</tr>" ); 
					$('table td img.delete').click(function(){
						deleteProduto($(this));
					});
					$( ".editar" ).click(function() {
						editar($(this).attr("rel"));
					});
				}
			}
			$( "#produto-form" ).dialog( "close" );
		}
	});
}

function addApontamentos(obj){
	dataString = $("#apontamento-produto").serialize();
	$.ajax({
		type:"post",
		url: "/milestone/admin/apontamento/produto-save.jhtml"+"?ram=" + Math.random(),
		data: dataString,
		dataType:"json",
		cache: false,
		success: function(response){
			if(response != undefined){
				
			}
			$( "#produto-form" ).dialog( "close" );
		}
	});
}

function deleteProduto(obj){
	$( "#dialog:ui-dialog" ).dialog( "destroy" );
	$( "#dialog-confirm" ).dialog({
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			"Confirmar": function() {
				$.ajax({
					type:"post",
					url: "/milestone/admin/produto/delete.jhtml"+"?ram=" + Math.random() + "&idProduto=" + obj.attr("id"),
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
		url: "/milestone/admin/projeto/etapas/ajax-list.jhtml"+"?ram=" + Math.random() + "&idProjeto=" + idProjeto + "&nivel=PD",
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
    deleteProduto($(this));
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
	
	$( "#produto-form" ).dialog({
		autoOpen: false,
		height: 300,
		width: 350,
		modal: true,
		buttons: {
			"Salvar": function() {
				
				if(validate()) {
					saveProduto($(this));
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

	$( "#add-produto" ).click(function() {
		$( "#produto-form" ).dialog( "open" );
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