		<div id="posicao-form" title="Criar Posi&ccedil;&atilde;o">
			<p class="validateTips">Os campos com * são obrigat&oacute;rios.</p>
			<form>
				<input type="hidden" name="unidade.id" id="idUnidade"/>
				<input type="hidden" name="id" id="idPosicao"/>
				<fieldset>
					<label for="posicao">Posi&ccedil;&atilde;o*</label>
					<input type="text" name="posicao" id="posicao" value="" class="text ui-widget-content ui-corner-all" /><br/>
					<label for="descricao">Descri&ccedil;&atilde;o*</label>
					<input type="text" name="descricao" id="descricao" value="" class="text ui-widget-content ui-corner-all" /><br/>
					<label for="qtdDireita">Esquerda*</label>
					<input type="text" name="qtdEsquerda" id="qtdEsquerda" value="" class="text ui-widget-content ui-corner-all small" />
					<label for="qtdEsquerda">Direita*</label>
					<input type="text" name="qtdDireita" id="qtdDireita" value="" class="text ui-widget-content ui-corner-all small" /><br/>
					<label for="conjuntoSoldado">Conjunto Soldado</label>
					<input type="checkbox" name="conjuntoSoldado" id="conjuntoSoldado" value="S"/></br></br>
					<div id="conjuntoSoldadoLayer" style="padding: 10px 0pt 0pt;">
						<label for="codigoNorma">C&oacute;digo/Norma</label>
						<input type="text" name="codigoNorma" id="codigoNorma" value="" class="text ui-widget-content ui-corner-all" /><br/>
						<label for="material">Material*</label>
						<input type="text" name="material" id="material" value="" class="text ui-widget-content ui-corner-all" />
						<label for="larguraDiametro">Largura/Di&acirc;metro*</label>
						<input type="text" name="larguraDiametro" id="larguraDiametro" value="" class="text ui-widget-content ui-corner-all small" /><br/>
						<label for="comprimento">Comprimento*</label>
						<input type="text" name="comprimento" id="comprimento" value="" class="text ui-widget-content ui-corner-all small" /><br/>
						<label for="comprimento">Espessura/Alma*</label>
						<input type="text" name="espessuraAlma" id="espessuraAlma" value="" class="text ui-widget-content ui-corner-all small" /><br/>
						<label for="altura">Altura</label>
						<input type="text" name="altura" id="altura" value="" class="text ui-widget-content ui-corner-all small" /><br/>
						<label for="comprimento">Fornecedor</label>
						<select name="idFornecedor" id="idFornecedor">
							<option value="0">Selecione o Fornecedor</option>
							<c:forEach var="fornecedor" items="${fornecedorList}">
								<option value="<c:out value="${fornecedor.id}"/>" <c:if test="${fornecedor.id eq idFornecedor}">selected="selected"</c:if>><c:out value="${fornecedor.razaoSocial}"/></option>
							</c:forEach>
						</select>
						<label for="observacao">Observa&ccedil;&atilde;o</label>
						<input type="text" name="observacao" id="observacao" value="" class="text ui-widget-content ui-corner-all" />
					</div>
				</fieldset>
			</form>
		</div>