package kkramarenko.ecommerceapp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "country")
@Getter
@Setter
public class Country {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;


    // using @JsonIgnore because we don't need a nested list of states in JSON, when we retrieve list of countries
    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private List<State> states;

}
