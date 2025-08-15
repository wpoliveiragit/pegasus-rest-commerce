package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.infra.exception.BadRequestException;

public class CommonMethodCore {

    public void valid(Object obj, String message) {
        if (obj == null) throw new BadRequestException(message);
    }

}
