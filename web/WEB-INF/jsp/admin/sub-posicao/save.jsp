<div id="sub-posicao-form" title="Criar Sub-Posi&ccedil;&atilde;o">
			<p class="validateTips">Os campos com * são obrigat&oacute;rios.</p>
			<form>
				<input type="hidden" name="itemPosicao.id" id="idPosicao"/>
				<input type="hidden" name="id" id="idSubPosicao"/>
				<fieldset>
					<label for="subPosicao">Sub-Posi&ccedil;&atilde;o*</label>
					<input type="text" name="subPosicao" id="subPosicao" value="" class="text ui-widget-content ui-corner-all" /><br/>
					<label for="descricao">Descri&ccedil;&atilde;o*</label>
					<input type="text" name="descricao" id="descricao" value="" class="text ui-widget-content ui-corner-all" /><br/>
					<label for="qtdDireita">Esquerda*</label>
					<input type="text" name="qtdEsquerda" id="qtdEsquerda" value="" class="text ui-widget-content ui-corner-all small" />
					<label for="qtdEsquerda">Direita*</label>					
					<input type="text" name="qtdDireita" id="qtdDireita" value="" class="text ui-widget-content ui-corner-all small" /><br/>
					<label for="codigoNorma">C&oacute;digo/Norma</label>
					<input type="text" name="codigoNorma" id="codigoNorma" value="" class="text ui-widget-content ui-corner-all" /><br/>
					<label for="material">Material*</label>
					<input type="text" name="material" id="material" value="" class="text ui-widget-content ui-corner-all" /><br/>
					<label for="larguraDiametro">Largura/Di&acirc;metro*</label>
					<input type="text" name="larguraDiametro" id="larguraDiametro" value="" class="text ui-widget-content ui-corner-all small" /><br/>
					<label for="comprimento">Comprimento*</label>
					<input type="text" name="comprimento" id="comprimento" value="" class="text ui-widget-content ui-corner-all small" /><br/>
					<label for="comprimento">Espessura/Alma</label>
					<input type="text" name="espessuraAlma" id="espessuraAlma" value="" class="text ui-widget-content ui-corner-all small" /><br/>
					<label for="altura">Altura</label>
					<input type="text" name="altura" id="altura" value="" class="text ui-widget-content ui-corner-all small" /><br/>
					<label for="comprimento">Fornecedor</label>
					<select name="idFornecedor" id="idFornecedor">
						<option value="0">Selecione o Fornecedor</option>
						<c:forEach var="fornecedor" items="${fornecedorList}">
							<option value="<c:out value="${fornecedor.id}"/>" <c:if test="${fornecedor.id eq idFornecedor}">selected="selected"</c:if>><c:out value="${fornecedor.razaoSocial}"/></option>
						</c:forEach>
					</select><br/>
					<label for="observacao">Observa&ccedil;&atilde;o</label>
					<input type="text" name="observacao" id="observacao" value="" class="text ui-widget-content ui-corner-all" />
				</fieldset>
			</form>
		</div>