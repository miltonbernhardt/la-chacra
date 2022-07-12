package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.entities.TipoEmbalaje;

public class EmbalajeNotFoundException extends NotFoundException{
    public EmbalajeNotFoundException(){ super(ErrorMessages.MSG_EMBALAJE_NOT_FOUND);}
    public EmbalajeNotFoundException(TipoEmbalaje t, Queso q){ super(ErrorMessages.MSG_EMBALAJE_NOT_FOUND + ": " +t.toString() + " - " + q.getTipoQueso());}
}
