package com.gdn.blibli.library.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class BaseResponse implements Serializable {

  private static final long serialVersionUID = 4784510105500177529L;

  private String id;
  private String storeId;
  private Long createdDate;
  private String createdBy;
  private Long updatedDate;
  private String updatedBy;
  private Long version;

}
