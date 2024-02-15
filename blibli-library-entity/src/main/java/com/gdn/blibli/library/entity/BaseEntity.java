package com.gdn.blibli.library.entity;

import java.io.Serializable;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class BaseEntity implements Serializable {
  private static final long serialVersionUID = -5196941038640972466L;

  @Id
  private String id;
  @Version
  private Long version;
  @CreatedBy
  private String createdBy;
  @CreatedDate
  private Long createdDate;
  @LastModifiedBy
  private String updatedBy;
  @LastModifiedDate
  private Long updatedDate;
  private String storeId;
  private Boolean markForDelete;

}
