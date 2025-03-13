package org.dandria.model;

import jdk.jfr.Timestamp;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "local_goods")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodLocal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    @NotBlank
    Long goodId;
    @Column
    @NotBlank
    Long placeId;
    @Column
    @NotBlank
    @Timestamp
    LocalDateTime dateTime;
    @Column
    Boolean beep;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodLocal goodLocal = (GoodLocal) o;
        return Objects.equals(id, goodLocal.id)
                && Objects.equals(goodId, goodLocal.goodId)
                && Objects.equals(placeId, goodLocal.placeId)
                && Objects.equals(dateTime, goodLocal.dateTime)
                && Objects.equals(beep, goodLocal.beep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, goodId, placeId, dateTime, beep);
    }
}
