/**
 * 
 */
package br.com.projeto.entity.helper;

import br.com.projeto.entity.Usuario;

/**
 * @author gb
 * 
 */
public class UsuarioHelper {

	public static Boolean isValid(final Usuario usuario) {
		Boolean is = Boolean.FALSE;
		if (usuario != null) {
			is = Boolean.TRUE;
		}
		return is;
	}

}
