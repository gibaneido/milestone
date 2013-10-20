/**
 * 
 */
package br.com.projeto.web.adm.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.projeto.entity.Usuario;
import br.com.projeto.entity.helper.UsuarioHelper;
import br.com.projeto.util.AppRuntimeException;

/**
 * @author mg
 * 
 */
public class AdmSecurityFilter implements Filter {

    private static Logger logger = Logger.getLogger(AdmSecurityFilter.class.getName());

    public void init(FilterConfig arg0) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        logger.info("Processing Filter ... AdmSecurityFilter");

        HttpServletRequest request = (HttpServletRequest) req;

        if (this.isSecureResource(request) && (!this.isSessionAuthorized(request))) {
            throw new AppRuntimeException("Usuario Nao Autorizado!");
        }

        chain.doFilter(req, res);
    }

    public void destroy() {

    }

    boolean isSecureResource(final HttpServletRequest request) {
        Boolean is = Boolean.TRUE;
        String requestPath = request.getRequestURI();
        if (requestPath.endsWith("/adm/login.jhtml") || requestPath.endsWith("/adm/") || requestPath.endsWith("/adm/logout.jhtml")) {
            is = Boolean.FALSE;
        }
        return is;
    }

    boolean isSessionAuthorized(final HttpServletRequest request) {
        Boolean is = Boolean.FALSE;
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(Usuario.USUARIO_LOGADO);
        if (UsuarioHelper.isValid(usuario)) {
            is = Boolean.TRUE;
        }
        return is;
    }

}
