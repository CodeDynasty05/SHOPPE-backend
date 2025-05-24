package com.matrix.SHOPPE.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String phone;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ToString.Exclude
    @EqualsExclude
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Authority> authorities = new HashSet<>();

    @ToString.Exclude
    @EqualsExclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,mappedBy = "user")
    private List<Address> addresses;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @ToString.Exclude
    @EqualsExclude
    private Set<Product> wishlistProducts = new HashSet<>();

    public void addToWishlist(Product product) {
        wishlistProducts.add(product);
        product.getWishlistUsers().add(this);
    }

    public void removeFromWishlist(Product product) {
        wishlistProducts.remove(product);
        product.getWishlistUsers().remove(this);
    }
}
