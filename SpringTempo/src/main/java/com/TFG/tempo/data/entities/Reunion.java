package com.TFG.tempo.data.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Reunion implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reunionId;

  @Temporal(TemporalType.TIMESTAMP)
  private Date date;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date creationDate;

  @ManyToOne
  @JoinColumn(referencedColumnName = "userId")
  private User creator;

  @ManyToMany
  @JoinTable(
      name = "user_reunion",
      joinColumns = @JoinColumn(
          name = "user_id"),
      inverseJoinColumns = @JoinColumn(
          name = "reunion_id"))
  private List<User> assistant;


  @ManyToOne
  @JoinColumn(referencedColumnName = "teamId")
  private Team team;

  @Column(length = 800)
  private String description;

}
