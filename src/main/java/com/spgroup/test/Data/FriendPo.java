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
@Table(name = "friend")
public class FriendPo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Email
    @Column(name = "source")  //source email
    private String source;

    @Type( type = "json" )
    @Column( columnDefinition = "json" ,name = "targets") //target emails that has friend relationship with source
    private Map<String, List<String>> targets;

    @Column(name = "remark")
    private String remark;


    @Column(name = "lastModifiedTimestamp")
    private Long lastModifiedTimestamp;
}
