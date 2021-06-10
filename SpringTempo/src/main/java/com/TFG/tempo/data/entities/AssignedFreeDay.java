package com.TFG.tempo.data.entities;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

@Builder(toBuilder = true)
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class AssignedFreeDay implements Serializable {

  private static final long serialVersionUID = -8299055893559896471L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long freeDayId;

  @Temporal(TemporalType.DATE)
  private Date date;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

}
