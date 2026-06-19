package com.fcs.admin.usecase.presenter;

import com.fcs.admin.usecase.exception.BusinessException;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface BasePresenter {

    Logger LOGGER = Logger.getLogger(BasePresenter.class.getName());

    default void prepareFailView(String error) {
        LOGGER.log(Level.SEVERE, "Ocorreu um erro: " + error);
        throw new BusinessException(error);
    }
}
