package com.TFG.tempo.data.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Petition implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long petitionId;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date creationDate;

  @ManyToOne
  @JoinColumn(referencedColumnName = "userId")
  private User creator;

  @ManyToOne
  @JoinColumn(referencedColumnName = "userId")
  private User receiver;

  @ManyToOne
  @JoinColumn(referencedColumnName = "reunionId")
  private Reunion reunion;

  @ManyToOne
  @JoinColumn(referencedColumnName = "teamId")
  private Team team;

  @ManyToOne
  @JoinColumn(referencedColumnName = "freeDayId")
  private AssignedFreeDay assignedFreeDay;

  private String status;
}
