package com.TFG.tempo.data.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
public class Team {

  @Id
  @GeneratedValue
  private Long teamId;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date creationDate;

  private String name;

  @ManyToOne
  private User owner;

  @ManyToMany
  @JoinTable(
      name = "user_teams",
      joinColumns = @JoinColumn(
          name = "user_id"),
      inverseJoinColumns = @JoinColumn(
          name = "team_id"))
  private List<User> employees;

  private String description;
}
