package com.gdn.blibli.library.entity;

import com.gdn.blibli.library.constant.CollectionName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = CollectionName.MEMBER)
public class Member extends BaseEntity {
  private String name;
}
