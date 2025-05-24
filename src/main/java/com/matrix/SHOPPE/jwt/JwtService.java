package com.matrix.SHOPPE.jwt;

import com.matrix.SHOPPE.model.entity.User;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String issueToken(User user);

    Claims validateToken(String token);
}
