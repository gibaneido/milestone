		<%@ include file="/admin/inc/taglibs.jsp" %>
		<div id="apontamentos" title="Apontamento">
			<p class="validateTips">&nbsp;</p>
			<form id="apontamento-${param.nivel}">
				<input type="hidden" name="idProjeto" id="idProjetoApontamento"/>
				<fieldset class="filter">
					<ul class="clearfix">
						<li>
							<label>
								Etapa :
								<select name="idEtapa" id="idEtapa">
									<option value="">Selecione a Etapa</option>
								</select>
							</label>
						</li>
						<li>
							<label>
								Sub-Etapa :
								<select name="idSubEtapa" id="idSubEtapa">
									<option value="">Selecione a Sub-Etapa</option>
								</select>
							</label>
							<input type="checkbox" name="na" id="na"/>N/A
						</li>
					</ul>
				</fieldset>
				<fieldset>
					<table class="grid" id="grid-modal" style="width:100%;">
						<colgroup>
							<col width="30%">
							<col width="15%">
							<col width="15%">
							<col width="15%">
							<col width="15%">
							<col width="10%">
						</colgroup>
						<thead>
							<tr>
								<th rowspan="2">${param.nivelTitle}</th>
								<th colspan="2" style="text-align:center;">CRONOGRAMA</th>
								<th colspan="2" style="text-align:center;">PREVISTO</th>
								<th rowspan="2">%</th>
							</tr>
							<tr>
								<th style="text-align:center;background-color:#EEEEEE;">INÍCIO</th>
								<th style="text-align:center;background-color:#EEEEEE;">TÉRMINO</th>
								<th style="text-align:center;background-color:#EEEEEE;">INÍCIO</th>
								<th style="text-align:center;background-color:#EEEEEE;">TÉRMINO</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</fieldset>
			</form>
		</div>