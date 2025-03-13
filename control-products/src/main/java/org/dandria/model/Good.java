package org.dandria.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "goods")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    @NotBlank
    String name;
    @Column
    @NotBlank
    String category;
    @Column
    Double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Good good = (Good) o;
        return Objects.equals(id, good.id)
                && Objects.equals(name, good.name)
                && Objects.equals(category, good.category)
                && Objects.equals(price, good.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, price);
    }
}
