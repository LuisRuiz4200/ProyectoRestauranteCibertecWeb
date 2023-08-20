package com.web.restaurante.seguridad;

import lombok.AllArgsConstructor;

import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.restaurante.business.ComentarioService;
import com.web.restaurante.model.*;
import com.web.restaurante.repository.UsuarioRepository;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class CustomUsuarioDetalleService implements UserDetailsService {

	private UsuarioRepository repository;
	private HttpSession session = null;
	private ComentarioService comentarioService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Usuario usuario = repository.findByEmailUsuario(email);
		

		if (!Objects.isNull(usuario)) {
			
			/*ESTA BLOQUE DE CODIGO ALMACENA LA SESSION*/
			session.setAttribute("usuario", usuario);
			session.setAttribute("tipoUsuario",usuario.getTipoUsuario().getIdTipoUsuario());

			
			session.setAttribute("listaComentario", comentarioService.listar());
			session.setAttribute("imagen", Base64.getMimeEncoder().encodeToString(usuario.getImagenUsuario()));
			/*FIN DEL BLOQUE DEL CODIGO ATTE. LUIS RUIZ*/
			
			return new User(usuario.getEmailUsuario(), usuario.getPasswordUsuario(),
					rolesAuthority(usuario.getTipoUsuario()));
		}
		// si no se encuentra
		else {
			throw new UsernameNotFoundException("Usuario o Password No válido");
		}

	} // fin de loadUserByUsername

	/*
	 * private Collection<? extends GrantedAuthority>
	 * rolesAuthority(Collection<TipoUsuario> roles){
	 * 
	 * Collection<? extends GrantedAuthority> rolesAuthority = roles.stream()
	 * 
	 * .map(rol -> new SimpleGrantedAuthority(rol.getDesTipoUsuario()))
	 * 
	 * .collect(Collectors.toList());
	 * 
	 * return rolesAuthority; } //fin de rolesAuthority
	 */
	private Collection<? extends GrantedAuthority> rolesAuthority(TipoUsuario rol) {
		return Collections.singletonList(new SimpleGrantedAuthority(rol.getDesTipoUsuario()));
	}
}
