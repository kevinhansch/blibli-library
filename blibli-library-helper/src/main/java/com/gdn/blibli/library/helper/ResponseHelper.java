package com.gdn.blibli.library.helper;

import com.gdn.blibli.library.entity.BaseEntity;
import com.gdn.blibli.library.web.model.BaseResponse;

import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseHelper {
  public static <T extends BaseResponse, U extends BaseEntity> T setBaseResponse(T response,
                                                                                 U entity) {
    response.setId(entity.getId());
    response.setVersion(entity.getVersion());
    response.setCreatedBy(entity.getCreatedBy());
    response.setCreatedDate(entity.getCreatedDate());
    response.setStoreId(entity.getStoreId());
    response.setUpdatedBy(entity.getUpdatedBy());
    response.setUpdatedDate(entity.getUpdatedDate());
    return response;
  }

  public static boolean isResponseValid(GdnRestSingleResponse<?>... clientResponses) {
    if (clientResponses == null || clientResponses.length == 0) {
      return false;
    }

    for (GdnRestSingleResponse<?> response : clientResponses) {
      if (response == null || !response.isSuccess() || response.getValue() == null) {
        return false;
      }
    }
    return true;
  }
}
