package com.TFG.tempo.data.entities;


import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class User implements Serializable {

  @Id
  @GeneratedValue
  private Long userId;

  private int workedHours;

  private int weeklyWorkHours;

  private int availableFreeDays;

  @NaturalId(mutable = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @NaturalId(mutable = true)
  private String email;

  private boolean enabled;

  private boolean tokenExpired;

  @ManyToOne
  private User personInCharge;

  @ManyToMany
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(
          name = "user_id"),
      inverseJoinColumns = @JoinColumn(
          name = "role_id"))
  private Collection<Role> roles;

}
