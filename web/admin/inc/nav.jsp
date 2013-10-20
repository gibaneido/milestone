			<div id="nav">
				<ul id="jsddm" class="nav clearfix">
					<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id eq 1 && (sessionScope.usuarioLogado.empreendimento.id eq 1 || sessionScope.usuarioLogado.empreendimento.id eq 8))}">
						<li id="usuarios">
							<a href="${pageContext.request.contextPath}/admin/usuario/list.jhtml">Usuarios</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id eq 1 && (sessionScope.usuarioLogado.empreendimento.id eq 2 || sessionScope.usuarioLogado.empreendimento.id eq 8))}">
						<li id="clientes">
							<a href="${pageContext.request.contextPath}/admin/cliente/list.jhtml">Clientes</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id ne 2 && sessionScope.usuarioLogado.empreendimento.id ne 1)}">
						<li id="fornecedores">
							<a href="${pageContext.request.contextPath}/admin/fornecedor/list.jhtml">Fornecedores</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id eq 1 && (sessionScope.usuarioLogado.empreendimento.id eq 1 || sessionScope.usuarioLogado.empreendimento.id eq 8))}">
						<li id="gts">
							<a href="${pageContext.request.contextPath}/admin/projeto/list.jhtml">GT's</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id ne 2 && sessionScope.usuarioLogado.empreendimento.id ne 1)}">
						<li id="fornecedores">
							<a href="${pageContext.request.contextPath}/admin/produto/list.jhtml">Produtos</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id ne 2 && sessionScope.usuarioLogado.empreendimento.id ne 1)}">
						<li id="operacoes">
							<a href="${pageContext.request.contextPath}/admin/operacao/list.jhtml">Opera&ccedil;&otilde;es</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id ne 2 && sessionScope.usuarioLogado.empreendimento.id ne 1)}">
						<li id="unidades">
							<a href="${pageContext.request.contextPath}/admin/unidade/list.jhtml">Unidades</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id ne 2 && sessionScope.usuarioLogado.empreendimento.id ne 1)}">
						<li id="posicoes">
							<a href="${pageContext.request.contextPath}/admin/posicao/list.jhtml">Posi&ccedil;&otilde;es</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id ne 2 && sessionScope.usuarioLogado.empreendimento.id ne 1)}">
						<li id="subPosicoes">
							<a href="${pageContext.request.contextPath}/admin/sub-posicao/list.jhtml">Sub-Posi&ccedil;&otilde;es</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id ne 2 && sessionScope.usuarioLogado.empreendimento.id ne 1)}">
						<li id="itensComerciais">
							<a href="${pageContext.request.contextPath}/admin/item-comercial/list.jhtml">Itens Comerciais</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.usuarioLogado.god || sessionScope.usuarioLogado.empreendimento.id ne 1}">
						<li id="grafico">
							<a href="${pageContext.request.contextPath}/cliente/chart.jhtml?idCliente=1">Gráfico</a>
						</li>
					</c:if>
				</ul>
			</div>