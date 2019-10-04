package com.spgroup.test.Data;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Map;


@Entity
@Data
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name = "block")
public class BlockPo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Email
    @Column(name = "blocked")   //the email been block by someone
    private String blocked;

    @Type( type = "json" )
    @Column( columnDefinition = "json" ,name = "blockedBy")
    private Map<String, List<String>> blockedBy;   //some one that block another one


    @Column(name = "remark")
    private String remark;


    @Column(name = "lastModifiedTimestamp")
    private Long lastModifiedTimestamp;
}
