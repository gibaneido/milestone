		<div id="item-comercial-form" title="Criar Item Comercial">
			<p class="validateTips">Os campos com * são obrigat&oacute;rios.</p>
			<form>
				<input type="hidden" name="id" id="idProduto"/>
				<input type="hidden" name="id" id="idUnidade"/>
				<input type="hidden" name="id" id="idItemComercial"/>
				<fieldset>
					<label for="descricao">Descri&ccedil;&atilde;o*</label>
					<input type="text" name="descricao" id="descricao" value="" class="text ui-widget-content ui-corner-all" /><br/>
					<label for="qtdEsquerda">Esquerda*</label>
					<input type="text" name="qtdEsquerda" id="qtdEsquerda" value="" class="text ui-widget-content ui-corner-all small" />
					<label for="qtdDireita">Direita*</label>
					<input type="text" name="qtdDireita" id="qtdDireita" value="" class="text ui-widget-content ui-corner-all small" /><br/>
					<label for="codigoNorma">C&oacute;digo/Norma*</label>
					<input type="text" name="codigoNorma" id="codigoNorma" value="" class="text ui-widget-content ui-corner-all" /><br/>
					<label for="fornecedor">Fornecedor</label>
					<select name="idFornecedor" id="idFornecedor">
						<option value="0">Selecione o Fornecedor</option>
						<c:forEach var="fornecedor" items="${fornecedorList}">
							<option value="<c:out value="${fornecedor.id}"/>" <c:if test="${fornecedor.id eq idFornecedor}">selected="selected"</c:if>><c:out value="${fornecedor.razaoSocial}"/></option>
						</c:forEach>
					</select>
				</fieldset>
			</form>
		</div>