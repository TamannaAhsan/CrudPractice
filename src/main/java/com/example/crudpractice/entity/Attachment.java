package com.example.crudpractice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="attachment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;
    private String fileName;
    private String directoryName;
}
