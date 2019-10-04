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
@Table(name = "subscription")
public class SubscriptionPo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Type( type = "json" )
    @Column( columnDefinition = "json" ,name = "requestor")
    private Map<String, List<String>> requestor;

    @Email
    @Column(name = "target", unique = true)  //target email
    private String target;


    @Column(name = "remark")
    private String remark;


    @Column(name = "lastModifiedTimestamp")
    private Long lastModifiedTimestamp;
}
