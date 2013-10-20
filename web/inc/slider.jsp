<%@ include file="/admin/inc/taglibs.jsp" %>
<select class="slider" name="${param.nome}" id="${param.id}">									
</select>
<script>
$(function(){
	$('select#${param.id}').selectToUISlider({
		labels: 12
	}).hide();
});

	var dtSelected = '${param.dtSelected}';
	var mesIniProj = <fmt:formatDate pattern="MM" value="${project.dataInicio}"/>;
	var mesFimProj = <fmt:formatDate pattern="MM" value="${project.dataFim}"/>;
	var id = '#${param.id}';
	var ini = new Date(<fmt:formatDate pattern="yyyy" value="${project.dataInicio}"/>,mesIniProj-1,<fmt:formatDate pattern="dd" value="${project.dataInicio}"/>,0,0,0,0);
	var fim = new Date(<fmt:formatDate pattern="yyyy" value="${project.dataFim}"/>,mesFimProj-1,<fmt:formatDate pattern="dd" value="${project.dataFim}"/>,0,0,0,0);
	var loop = ini;
	
	var mesFim = ((fim.getMonth()+1) > 9)? (fim.getMonth()+1) : '0' + (fim.getMonth()+1);
	var diaFim = (fim.getDate() > 9)? (fim.getDate()) : '0' + (fim.getDate());
	var strFim = fim.getFullYear() + "" + mesFim + "" + diaFim;
	var options = '';
	var meses = ["Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"];
	var mesAtual = -1;
	
	for(var strLoop = '';strLoop != strFim;loop.setDate(loop.getDate() + 1)){
		var month = ((loop.getMonth()+1) > 9)? (loop.getMonth()+1) : '0' + (loop.getMonth()+1);
		var day = (loop.getDate() > 9)? (loop.getDate()) : '0' + (loop.getDate());
		
		if(mesAtual != loop.getMonth()){
			options += '<optgroup label=\"' + meses[loop.getMonth()] + '\">';	
		}
		
		strLoop = loop.getFullYear() + "" + month + "" + day;
		
		if(strLoop == dtSelected){
			options += '<option selected="selected" value=\"' + day + '/' + month + '/' + loop.getFullYear() + '\">' + 
			day + '/' + month + '/' + loop.getFullYear() +
			'</option>';
		}else{
			options += '<option value=\"' + day + '/' + month + '/' + loop.getFullYear() + '\">' + 
			day + '/' + month + '/' + loop.getFullYear() +
			'</option>';		
		}
		
		mesAtual = loop.getMonth();
	}
	$(id).append(options);
	</script>