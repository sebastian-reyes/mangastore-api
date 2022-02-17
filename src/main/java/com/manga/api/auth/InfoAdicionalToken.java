package com.manga.api.auth;

import java.util.HashMap;
import java.util.Map;

import com.manga.api.interfaceService.IUsuarioService;
import com.manga.api.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

@Component
public class InfoAdicionalToken implements TokenEnhancer{

    @Autowired
    private IUsuarioService uService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication){
        
        Usuario usuario = uService.findByEmail(authentication.getName());

        Map<String, Object> info = new HashMap<>();
        info.put("nombres", usuario.getNombres());
        info.put("apellidos", usuario.getApellidos());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
